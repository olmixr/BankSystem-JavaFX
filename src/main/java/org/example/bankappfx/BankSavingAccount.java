package org.example.bankappfx;

public class BankSavingAccount extends BankAccounts {

    private static final long serialVersionUID = 1L;

    private int interestRate;

    public BankSavingAccount(int accountNumber, String ownerName, int balance, int interestRate) {
        super(accountNumber, ownerName, balance);
        this.interestRate = interestRate;
    }

    @Override
    public void calculateMonthEnd() {
        double interestAmount = getBalance() * (interestRate / 100.0);
        setBalance(getBalance() + interestAmount);
    }

    @Override
    public void getAccountInfo() {
        System.out.println("Savings account:");
        super.getAccountInfo();
    }

    public int getInterestRate() {
        return interestRate;
    }
}
