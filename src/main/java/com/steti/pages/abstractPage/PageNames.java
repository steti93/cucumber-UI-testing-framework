package com.steti.pages.abstractPage;

public enum PageNames {
    HomePage("Welcome to HomePage"),
    OnlineCollegeCourses("All courses page"),
    BusinessCategory("Business Category courses page"),
    Checkout("Checkout page"),
    SuccessOrderConfirmation("Success Order Confirmation page"),
    SuccessLogOut("Success Log Out page"),
    Blog("StraighterLine Blog"),
    LogIn("Log In page"),
    MyLineDashboard("MyLine Dashboard page"),
    ShoppingCart("Shopping Cart page"),
    FreeTrial("Take a free online lesson at StraighterLine."),
    StudentProfile("StudentProfile"),
    PayPalConfirmAccount("Pay Pal Confirm Account Credit Card"),
    PayPalLogIn("Login to PayPal"),
    PayPalPassword("Insert Password for PayPal Account"),
    PayPalAccount("Inside the PayPal Account"),
    PayPalReviewOrder("Review Order in PayPal"),
    AnatomyPhysiologyProductPage("Anatomy & Physiology I product page"),
    SearchResult("SearchResult Page"),
    CatalogPage("CatalogPage"),
    PartnerCollegesPage("Partner College Page"),
    PartnerCollegeInfoPage("Information about the college"),
    EnglishCategory("English Category courses page"),
    GoingBackToCollegeGuidePage("Going Back To College Guide Page"),
    ScholarshipOldPage("Scholarship Page"),
    NotFoundPage("404 Not Found 1"),
    BillingPreferences("Billing Preferences Page"),
    AccountSettings("Account Settings Page"),
    HelpCenter("Help Center Page"),
    GetYourDegreePage("Get your degree page"),
    HowMuchDoesItCost("Pricing page"),
    StudentSuport("Student support page"),
    HelpDescPage("Help desc page"),


//starting new Version Objects name

    HomePage1("HomePage new version"),
    FreeTrial1("Free Trial new version"),
    CollegesPage("Colleges new version page"),
    ScholarshipsPage("Scholarships new version page"),
    DashboardPage("Dashboard new version page"),
    BillingPreferencesPage("Billing Preferences new version page"),
    TranscriptsPage("Transcripts new version page"),
    AccountSettingsPage("Account Settings new version page"),
    PartnerColleges1("Partner Colleges new version page"),
    CourseEquivalency("Course Equivalency for Partner College new version page"),
    CreateAccountPage("Create Account Page new version page"),
    LogInPage("LogIn Page new version"),
    EnrollmentStart("Enrollment start page"),
    EnrollmentCreate("Enrollment create page"),

    //Admin pages
    AdminLoginPage("Admin LogIn page"),
    AdminHomePage("Admin Home Page"),
    AdminUsersPage("Admin Users Page"),
    AdminCreateUserPage("Admin Create User Page"),
    AdminViewUserDetailsPage("Admin View User Details Page"),
    AdminEditUserPage("Admin Edit User Page"),

    //Demo
    FacebookHomePage("");

    private String description;

    PageNames(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
