from flask import Flask, request, jsonify
import tempfile
import os

from ADP_Paystub_Scanner import process_and_sanitize_adp_paystub

app = Flask(__name__)


@app.post("/api/paystub/extract")
def extract_paystub():

    if "file" not in request.files:
        return jsonify({
            "error": "No PDF file provided"
        }), 400

    file = request.files["file"]

    if file.filename == "":
        return jsonify({
            "error": "No file selected"
        }), 400

    if not file.filename.lower().endswith(".pdf"):
        return jsonify({
            "error": "Only PDF files are supported"
        }), 400

    temp_path = None

    try:

        with tempfile.NamedTemporaryFile(
            suffix=".pdf",
            delete=False
        ) as temp_file:

            file.save(temp_file.name)
            temp_path = temp_file.name

        result = process_and_sanitize_adp_paystub(temp_path)

        if isinstance(result, str):
            result = result

        return jsonify(result) if isinstance(result, dict) else result

    except Exception as e:

        return jsonify({
            "error": str(e)
        }), 500

    finally:

        if temp_path and os.path.exists(temp_path):
            os.remove(temp_path)


@app.get("/health")
def health():

    return jsonify({
        "status": "UP"
    })


if __name__ == "__main__":
    app.run(
        host="0.0.0.0",
        port=5050,
        debug=True
    )