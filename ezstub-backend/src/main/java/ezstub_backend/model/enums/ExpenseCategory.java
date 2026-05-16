package ezstub_backend.model.enums;

public enum ExpenseCategory {

    // Food & Drink
    GROCERIES,
    DINING_OUT,
    COFFEE_SHOPS,
    ALCOHOL_BARS,
    CAMPUS_DINING,          // meal plans, campus cafes

    // Transportation
    GAS_FUEL,
    PUBLIC_TRANSIT,
    RIDESHARE,
    PARKING,
    CAR_MAINTENANCE,

    // Housing
    RENT_MORTGAGE,
    UTILITIES,
    HOME_MAINTENANCE,
    FURNITURE_APPLIANCES,
    DORM_SUPPLIES,          // bedding, organizers, fans, etc.

    // Health
    PHARMACY,
    DOCTOR_MEDICAL,
    GYM_FITNESS,
    MENTAL_HEALTH,
    CAMPUS_HEALTH,          // student health center visits

    // Shopping
    CLOTHING,
    ELECTRONICS,
    ONLINE_SHOPPING,
    PERSONAL_CARE,

    // Entertainment
    STREAMING_SUBSCRIPTIONS,
    MOVIES_EVENTS,
    HOBBIES,
    TRAVEL_VACATION,
    GREEK_LIFE,             // dues, formals, fraternity/sorority events
    SPORTS_EVENTS,          // college game tickets, tailgates

    // Finance
    INSURANCE,
    LOAN_PAYMENT,
    SAVINGS_INVESTMENT,
    TAXES,
    BANK_FEES,
    STUDENT_LOAN,           // tracking loan disbursements or payments

    // Income
    PAYCHECK,
    FREELANCE,
    REFUND,
    OTHER_INCOME,
    FINANCIAL_AID,          // scholarships, grants, FAFSA disbursements
    PARENTAL_SUPPORT,       // money from parents/family

    // Education
    TUITION,                // tuition payments
    TEXTBOOKS,              // books and course materials
    SCHOOL_SUPPLIES,        // notebooks, pens, printer ink
    SOFTWARE_SUBSCRIPTIONS, // Adobe, Microsoft 365, Notion, etc.
    ONLINE_COURSES,         // Udemy, Coursera, etc.
    STUDY_ABROAD,           // program fees, travel, housing abroad

    // Misc
    GIFTS_DONATIONS,
    EDUCATION,
    CHILDCARE,
    PETS,
    LAUNDRY,                // laundromat, campus machines
    PRINTING,               // campus print services
    OTHER
}