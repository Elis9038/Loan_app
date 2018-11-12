package com.nagel.loanapp;

class Loan {

    static Loan instance = null;
    double principal = 0;     // the amount borrowed inc. costs
    double interestRate = 0;  // interest rate as a number between 0 and 1 that indicates the rate for 1 period
    int periods = 0;          // the number of periods and thus number of payments

    MainActivity main = new MainActivity();

    public Loan() {
    }

    public static Loan getInstance() {
        if(instance == null)
        {
            synchronized (Loan.class)
            {
                if(instance == null) instance = new Loan();
            }
        }
        return instance;
    }

    public double payment()
    {
        return principal * interestRate / (1 - Math.pow(1 + interestRate, -periods));
    }

    public double outstanding(int n)
    {
        return principal * Math.pow(1 + interestRate, n) - payment() * (Math.pow(1 + interestRate, n) - 1) / interestRate;
    }

    double interest(int n)
    {
        return outstanding(n - 1) * interestRate;
    }

    double repayment(int n)
    {
        return payment() - interest(n);
    }

    // Added two methods to set and get periods variable from another class
    void setPeriods(int per) {
        this.periods = per;
    }
    double getPeriods() { return periods; }

    // Also two methods to change variable values from another class
    public void setPrincipal(double pr) {
        this.principal = pr;
    }

    public void setInterestRate(double interest) {
        this.interestRate = interest;
    }
}
