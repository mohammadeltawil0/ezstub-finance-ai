import re
import json
import os
from markitdown import MarkItDown

def clean_markdown(text):

    text = re.sub(
        r'\(cid:\d+\)',
        '',
        text
    )

    text = re.sub(
        r'[^\x20-\x7E\n\r\t]',
        '',
        text
    )

    text = re.sub(
        r'\n{3,}',
        '\n\n',
        text
    )
    text = re.sub(
        r'\\@\[\[.*?$',
        '',
        text,
        flags=re.MULTILINE
    )

    text = re.sub(
        r'^\|\s*-+\s*\|.*$',
        '',
        text,
        flags=re.MULTILINE
    )

    text = re.sub(r'\\@\[.*?C', '', text)
    text = re.sub(r'\(cid:\d+\)', '', text)
    text = re.sub(r'[^\x09\x0A\x0D\x20-\x7E]', '', text)
    return text

def sanitize_text(text):

    # bank account numbers
    text = re.sub(
        r'x{4,}\d+',
        '[ACCOUNT_REDACTED]',
        text,
        flags=re.IGNORECASE
    )

    # SSNs
    text = re.sub(
        r'\d{3}-\d{2}-\d{4}',
        '[SSN_REDACTED]',
        text
    )

    # addresses
    text = re.sub(
        r'\d+\s+[A-Za-z0-9\s]+(DRIVE|ROAD|RD|STREET|ST|AVENUE|AVE)',
        '[ADDRESS_REDACTED]',
        text,
        flags=re.IGNORECASE
    )

    return text


def parse_adp_numeric(num_str):
    """Converts space-separated cents or comma values (e.g. '780 00', '1 100 00', '-15 50') into a clean float."""
    if not num_str:
        return 0.0
    # Clean out negative signs, dollar symbols, and extra spaces
    clean = num_str.replace('$', '').replace('-', '').strip()
    
    # If there's a space separating the cents, replace the last space with a dot
    if ' ' in clean:
        parts = clean.split()
        # Recombine everything except the last part with nothing, then add cents
        clean = "".join(parts[:-1]) + "." + parts[-1]
    else:
        clean = clean.replace(',', '')
    try:
        return float(clean)
    except ValueError:
        return 0.0

def process_and_sanitize_adp_paystub(pdf_path):
    if not os.path.exists(pdf_path):
        return {
            "error": f"File not found at path: {pdf_path}"
        }

    md = MarkItDown()
    try:
        result = md.convert(pdf_path)
        raw_text = result.text_content
        
    

        sanitized_text = sanitize_text(raw_text)
        clean_text = clean_markdown(sanitized_text) 
    except Exception as e:
        return {
            "error": f"MarkItDown conversion failed: {str(e)}"
        }
    print(clean_text)
    # Base Schema Structure
    payroll_data = {
        "employerName": "Unknown",
        "employerLocation": None,
        "payBeginDate": None,
        "payEndDate": None,
        "checkDate": None,
        "baseHourlyRate": None,
        "totalHoursWorked": 0.0,
        "currentGross": 0.0,
        "ytdGross": 0.0,
        "currentNet": 0.0,
        "ytdNet": 0.0,
        "rateVariations": [],
        "deductions": [],
        "securityStatus": "CLEAN_AND_SANITIZED"
    }

    lines = [line.strip() for line in clean_text.split("\n") if line.strip()]

    # Temporary lists to stitch together the location and name cells dynamically
    name_chunks = []
    location_chunks = []

    # 1. Parse Metadata dynamically without hardcoded employer strings
    for line in lines:
        parts = [p.strip() for p in line.split("|") if p.strip()]
        
        # Capture the dates using the text label structures
        p_begin = re.search(r"Period Beginning:\s*\|\s*\|\s*([\d/]+)", line)
        p_end = re.search(r"Period Ending:\s*\|\s*\|\s*([\d/]+)", line)
        p_date = re.search(r"Pay Date:\s*\|\s*\|\s*([\d/]+)", line)

        if p_begin: 
            payroll_data["payBeginDate"] = p_begin.group(1)
            # The left side of the "Period Beginning" row holds the raw company name cells
            if "Period Beginning:" in line:
                left_side = line.split("Period Beginning:")[0]
                name_chunks = [p.strip() for p in left_side.split("|") if p.strip()]
                
        if p_end: 
            payroll_data["payEndDate"] = p_end.group(1)
            # The left side of the "Period Ending" row holds street address fragments
            if "Period Ending:" in line:
                left_side = line.split("Period Ending:")[0]
                location_chunks.extend([p.strip() for p in left_side.split("|") if p.strip()])
                
        if p_date: 
            payroll_data["checkDate"] = p_date.group(1)
            # The left side of the "Pay Date" row holds city, state, and zip code fields
            if "Pay Date:" in line:
                left_side = line.split("Pay Date:")[0]
                location_chunks.extend([p.strip() for p in left_side.split("|") if p.strip()])

    # Dynamically build and format corporate details if pieces were gathered
    if name_chunks:
        payroll_data["employerName"] = " ".join(name_chunks).title()
    if location_chunks:
        # Rebuild fragments cleanly into a readable corporate layout string
        raw_loc = " ".join(location_chunks)
        # Add a clean tracking comma right before state fields dynamically
        raw_loc = re.sub(r'([A-Za-z]+),?\s+(NEW JERSEY|NJ)', r'\1, \2', raw_loc, flags=re.IGNORECASE)
        payroll_data["employerLocation"] = raw_loc.title()

    # 2. Iterate through table items line-by-line
    for line in lines:
        parts = [p.strip() for p in line.split("|") if p.strip()]
        if not parts:
            continue

        # --- Handle Regular Base Earnings Line ---
        if parts[0] == "Regular":
            rate_str = f"{parts[1]} {parts[2]}"   
            hours_str = f"{parts[3]} {parts[4]}"  
            earnings_str = parts[5]               

            rate_val = parse_adp_numeric(rate_str)
            hours_val = parse_adp_numeric(hours_str)
            earnings_val = parse_adp_numeric(earnings_str)

            payroll_data["baseHourlyRate"] = rate_val
            payroll_data["totalHoursWorked"] = hours_val
            
            payroll_data["rateVariations"].append({
                "description": "Regular Base Pay",
                "appliedRate": rate_val,
                "hours": hours_val,
                "earnings": earnings_val
            })

        # --- Handle Overall Gross Summary Row ---
        elif "Gross" in parts[0] or (len(parts) > 1 and "Gross" in parts[1]):
            # FIXED REGEX: Specifically looks for text structures holding 2 numbers separated by layout pipes
            # This cleanly separates current from YTD even without dot notation
            amounts = [p.strip() for p in line.split("|") if re.search(r'\d+\s+\d+', p)]
            if len(amounts) >= 2:
                payroll_data["currentGross"] = parse_adp_numeric(amounts[0])
                payroll_data["ytdGross"] = parse_adp_numeric(amounts[1])

       # --- Handle Dynamic Statutory Deductions Blocks ---
        elif any(tax in line for tax in ["Income", "Security", "Medicare", "SUI", "Family"]):
            row_text = " ".join(parts)
            tax_match = re.search(r'(Federal Income Tax|Social Security Tax|Medicare Tax|NJ State Income Tax|NJ SUI Tax|NJ Paid Family Leave Ins)', row_text)
            
            if tax_match:
                tax_name = tax_match.group(1)
                
                # FIXED: Removed the broken re.strip line. 
                # This correctly isolates the valid numeric columns (Current and YTD)
                number_matches = re.findall(
                    r'-?\d+\s+\d+',
                    line
                )

                if len(number_matches) >= 2:
                    payroll_data["deductions"].append({
                        "description": tax_name,
                        "currentAmount": parse_adp_numeric(number_matches[0]),
                        "ytdAmount": parse_adp_numeric(number_matches[1])
                    })

        # --- Handle Overall Net Pay Summary Row ---
        elif "Net" in parts[0] or (len(parts) > 1 and "Net" in parts[1]):
            if "Pay" in line:
                amounts = [p.strip() for p in line.split("|") if re.search(r'\d+\s+\d+', p)]
                if amounts:
                    payroll_data["currentNet"] = parse_adp_numeric(amounts[0])
                    payroll_data["ytdNet"] = payroll_data["ytdNet"] or payroll_data["currentNet"]

    return payroll_data


if __name__ == "__main__":
    PDF_FILE_PATH = r"C:\Users\Mohammad\Desktop\FCC-OpenCV-Course\opencv1\Doc_scanner\08-29-2025 MCC_Paystub.pdf"
    
    print("Processing ADP document layout natively...")
    json_output = process_and_sanitize_adp_paystub(PDF_FILE_PATH)
    print(json.dumps(json_output, indent=4))