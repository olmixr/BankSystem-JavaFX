package org.example.bankappfx;

public class BankCreditAccount extends BankAccounts {

    private static final long serialVersionUID = 1L;

    private int creditLimit;
    private int monthlyFee;

    public BankCreditAccount(int accountNumber, String ownerName, int balance, int creditLimit, int monthlyFee) {
        super(accountNumber, ownerName, balance);
        this.creditLimit = creditLimit;
        this.monthlyFee = monthlyFee;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("The amount you withdraw must be greater than zero!");
            return;
        }

        double newBalance = getBalance() - amount;
        if (newBalance < -creditLimit) {
            System.out.println("Your credit limit has been exceeded!");
            return;
        }

        setBalance(newBalance);
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public int getMonthlyFee() {
        return monthlyFee;
    }

    @Override
    public void calculateMonthEnd() {
        setBalance(getBalance() - monthlyFee);
    }

    @Override
    public void getAccountInfo() {
        System.out.println("Credit account:");
        super.getAccountInfo();
    }
}
