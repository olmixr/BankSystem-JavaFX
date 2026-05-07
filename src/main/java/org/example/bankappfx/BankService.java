package org.example.bankappfx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class BankService implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String[] RANDOM_OWNER_NAMES = {"Maxim", "Nikita", "Sanea", "Misha", "Steve"};

    public static final String SAVEFILENAME = "accounts.ser";

    private static ArrayList<BankAccounts> accounts;

    private final Random random = new Random();

    public BankService() {
        accounts = new ArrayList<>();
    }

    public void createSavingsAccount(int accountNumber, String ownerName, int interestRate) {
        validateAccountData(accountNumber, ownerName);
        accounts.add(new BankSavingAccount(accountNumber, ownerName, 0, interestRate));
    }

    public void createCreditAccount(int accountNumber, String ownerName, int creditLimit, int monthlyFee) {
        validateAccountData(accountNumber, ownerName);
        accounts.add(new BankCreditAccount(accountNumber, ownerName, 0, creditLimit, monthlyFee));
    }

    public int getAccountsCount() {
        return accounts.size();
    }

    public boolean accountNumberExists(int accountNumber) {
        for (BankAccounts account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return true;
            }
        }
        return false;
    }

    private void validateAccountData(int accountNumber, String ownerName) {
        if (accountNumber <= 0) {
            throw new IllegalArgumentException("Account number must be greater than 0.");
        }
        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("Owner name cannot be empty.");
        }
        if (accountNumberExists(accountNumber)) {
            throw new IllegalArgumentException("Account with this number already exists.");
        }
    }

    public BankAccounts findAccountByNumber(int accountNumber) {
        for (int i = 0; i < accounts.size(); i++) {
            BankAccounts account = accounts.get(i);
            if (account != null && account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    public void deleteAccountToNumber(int accountNumber) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accountNumber == accounts.get(i).getAccountNumber()) {
                accounts.remove(i);
            }
        }
    }

    public String randomAccountsGenerate(int numberAccount) {
        accounts.clear();

        for (int i = 0; i < numberAccount; i++) {
            int randomAccountType = random.nextInt(2);
            String randomOwnerName = getRandomOwnerName();

            if (randomAccountType == 1) {
                int randomAccountNumber = random.nextInt(1000);
                int randomInterestRate = random.nextInt(50);
                accounts.add(new BankSavingAccount(randomAccountNumber, randomOwnerName, 0, randomInterestRate));
            } else {
                int randomAccountNumber = random.nextInt(1, 1000);
                int randomCreditLimit = random.nextInt(3000);
                int randomMonthlyFee = random.nextInt(50);
                accounts.add(new BankCreditAccount(randomAccountNumber, randomOwnerName, 0, randomCreditLimit, randomMonthlyFee));
            }
        }

        return "Success!";
    }

    public String transferFromTo(int from, int to, double amount) {
        BankAccounts accountFrom = null;
        BankAccounts accountTo = null;

        if (amount <= 0) {
            return "Amount must be greater than zero.";
        }
        if (from == to) {
            return "You cannot transfer money to the same account.";
        }

        for (int i = 0; i < accounts.size(); i++) {
            if (from == accounts.get(i).getAccountNumber()) {
                accountFrom = accounts.get(i);
            }
            if (to == accounts.get(i).getAccountNumber()) {
                accountTo = accounts.get(i);
            }
        }

        if (accountFrom != null && accountTo != null) {
            if (accountFrom.getBalance() >= amount) {
                accountFrom.withdraw(amount);
                accountTo.deposit(amount);
                return "Transfer completed successfully.";
            } else {
                return "Insufficient funds.";
            }
        } else {
            return "Accounts were not found.";
        }
    }

    public String finalMonth() {
        boolean hasAccount = false;
        BankAccounts monthEndAccount = null;

        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i) != null) {
                hasAccount = true;
                monthEndAccount = accounts.get(i);
                monthEndAccount.calculateMonthEnd();
                return "Month-end calculation completed!";
            }
        }

        if (!hasAccount) {
            return "There are no accounts, so we can't calculate it!";
        }

        return "Unknown error.";
    }

    public String getAllAccountInfo() {
        if (accounts.isEmpty()) {
            return "No accounts!";
        }

        StringBuilder allAccountInfo = new StringBuilder();
        for (int i = 0; i < accounts.size(); i++) {
            appendAccountInfo(allAccountInfo, accounts.get(i));
        }
        return allAccountInfo.toString();
    }

    public String getAccountInfo(int accountNumber) {
        StringBuilder accountInfo = new StringBuilder();

        for (int i = 0; i < accounts.size(); i++) {
            BankAccounts account = accounts.get(i);
            if (accountNumber == account.getAccountNumber()) {
                appendAccountInfo(accountInfo, account);
            }
        }

        return accountInfo.toString();
    }

    public void saveData() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(SAVEFILENAME))) {
            outputStream.writeObject(accounts);
            System.out.println("Accounts saved successfully! - " + SAVEFILENAME);
        } catch (IOException e) {
            System.out.println("Error while saving accounts!");
            e.printStackTrace();
        }
    }

    public void loadAccountsFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(SAVEFILENAME))) {
            accounts = (ArrayList<BankAccounts>) inputStream.readObject();
            System.out.println("Accounts loaded successfully! - " + SAVEFILENAME);
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found: " + SAVEFILENAME);
        } catch (IOException e) {
            System.out.println("Error while loading accounts!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Saved data is invalid!");
            e.printStackTrace();
        }
    }

    private String getRandomOwnerName() {
        int randomOwnerIndex = random.nextInt(RANDOM_OWNER_NAMES.length);
        return RANDOM_OWNER_NAMES[randomOwnerIndex];
    }

    private void appendAccountInfo(StringBuilder infoBuilder, BankAccounts account) {
        if (account instanceof BankSavingAccount savingAccount) {
            infoBuilder.append("Savings account\n");
            infoBuilder.append("Interest rate: ").append(savingAccount.getInterestRate()).append("\n");
        } else if (account instanceof BankCreditAccount creditAccount) {
            infoBuilder.append("Credit account\n");
            infoBuilder.append("Credit limit: ").append(creditAccount.getCreditLimit()).append("\n");
            infoBuilder.append("Monthly fee: ").append(creditAccount.getMonthlyFee()).append("\n");
        }

        infoBuilder.append("Account number: ").append(account.getAccountNumber()).append("\n");
        infoBuilder.append("Owner name: ").append(account.getOwnerName()).append("\n");
        infoBuilder.append("Balance: ").append(account.getBalance()).append("\n");
        infoBuilder.append("----------------\n");
    }
}
