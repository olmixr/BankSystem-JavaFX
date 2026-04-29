package org.example.bankappfx;
import java.util.ArrayList;

public class BankService {

    ArrayList<BankAccounts> accounts = new ArrayList<BankAccounts>();
////
//    private static int getFreePozisin() {
//        for (int i = 0; i < accounts.length; i++) {
//            if (accounts[i] == null) {
//                return i;
//            }
//        }
//        return -1;
//    }
//
//    private static BankAccount findAccountByNumber(int inputnumber) {
//        for (int i = 0; i < accounts.length; i++) {
//            if ( accounts[i] !=null && accounts[i].getAccountNumber() == inputnumber) {
//                return accounts[i];
//            }
//        }
//        return null;
//    }
////
//    public static void getInfoAllAccount() {
//        boolean hasAccount = false;
//
//        for (int i = 0; i < accounts.length; i++) {
//            if (accounts[i] != null) {
//                hasAccount = true;
//                System.out.println("-------------");
//                System.out.println("Account number: " + i);
//                accounts[i].getAccountInfo();
//                System.out.println("-------------");
//            }
//        }
//        if (hasAccount == false) System.out.println("No accounts!");
//    }
//
//    public static void createAccount() {
//
//        int freepoz = getFreePozisin();
//        if (freepoz != -1) {
//
//            System.out.println("1.SavingsAccount \n2.CreditAccount");
//            int number = scan.nextInt();
//
//            switch (number) {
//                case 1:
//
//                    System.out.println("Create number account: ");
//                    int accountNumber = scan.nextInt();
//
//                    scan.nextLine();
//
//                    System.out.println("Owner name card: ");
//                    String ownerName = scan.nextLine();
//
//                    System.out.println("Deposit interest rate: ");
//                    int interestRate = scan.nextInt();
//
//                    accounts[freepoz] = new SavingsAccount(accountNumber, ownerName, 0, interestRate);
//                    System.out.println("Account success create!");
//                    saveDataIfEnabled();
//                    break;
//                case 2:
//
//                    System.out.println("Create number account: ");
//                    int accountNumberc = scan.nextInt();
//
//                    scan.nextLine();
//
//                    System.out.println("Owner name card: ");
//                    String ownerNamec = scan.nextLine();
//
//                    System.out.println("Credit limit: ");
//                    int creditLimit = scan.nextInt();
//
//                    System.out.println("Commission: ");
//                    int monthlyFee = scan.nextInt();
//
//                    accounts[freepoz] = new CreditAccount(accountNumberc, ownerNamec, 0, creditLimit, monthlyFee);
//                    System.out.println("Account success create!");
//                    saveDataIfEnabled();
//                    break;
//
//                default:
//                    System.out.println("Invalid value!");
//            }
//        } else System.out.println("No place accounts!");
//    }
//
//    public static void depositToAccount() {
//        System.out.println("Account number for the transfer: ");
//        int number = scan.nextInt();
//
//        BankAccount account = findAccountByNumber(number);
//        if (account == null) {
//            System.out.println("That account doesn't exist!");
//            return;
//        }
//        System.out.println("Deposit amount: ");
//        double amount = scan.nextDouble();
//        account.deposit(amount);
//        System.out.println("Success deposit " + amount + " !");
//        saveDataIfEnabled();
//    }
//
//    public static void withdrawToAccount() {
//        System.out.println("Account number for the withdrew: ");
//        int number = scan.nextInt();
//
//        BankAccount account = findAccountByNumber(number);
//        if (account == null) {
//            System.out.println("That account doesn't exist!");
//            return;
//        }
//        System.out.println("Withdrew amount: ");
//        double amount = scan.nextDouble();
//        if (account.getBalance() >= amount) {
//            account.withdraw(amount);
//            System.out.println("Success withdraw: " + amount + " !");
//            saveDataIfEnabled();
//        } else System.out.println("The amount exceeds the withdrawal limit");
//
//    }
//
//    public static void finalMonth() {
//        boolean HesAccount = false;
//        for (int i = 0; i < accounts.length; i++) {
//            if (accounts[i] != null) {
//                HesAccount = true;
//                accounts[i].calculateMonthEnd();
//
//            }
//        }
//        if (HesAccount == false) {
//            System.out.println("There are no accounts, so we can't calculate it!");
//            return;
//        }
//        saveDataIfEnabled();
//    }
//
//    public static BankAccount GetAccountInfo() {
//
//        System.out.println("Enter your account number: ");
//        int accountNumber = scan.nextInt();
//
//        for (int i = 0; i < accounts.length; i++) {
//            if (accounts[i] != null && accounts[i].getAccountNumber() == accountNumber) {
//                System.out.println(accounts[i]);
//                return accounts[i];
//            }
//        }
//        System.out.println("That account doesn't exist!");
//        return null;
//    }
//
//    public static void transfer() {
//        System.out.println("From account number: ");
//        int from = scan.nextInt();
//        BankAccount account1 = findAccountByNumber(from);
//        if (account1 == null) {
//            System.out.println("That account doesn't exist!");
//            return;
//        }
//
//        System.out.println("To account number: ");
//        int to = scan.nextInt();
//        BankAccount account2 = findAccountByNumber(to);
//        if (account2 == null) {
//            System.out.println("That account doesn't exist!");
//            return;
//        }
//
//        System.out.println("Amount: ");
//        double amount = scan.nextDouble();
//        if (amount <= account1.getBalance() && amount > 0) {
//            account1.withdraw(amount);
//            account2.deposit(amount);
//            System.out.println("Successful transfer! Amount: " + amount + "$$");
//            saveDataIfEnabled();
//        } else System.out.println("Insufficient money! Balance: " + account1.getBalance());
//
//    }
//
//    public static boolean deleteAccount(){
//        System.out.println("Account number to be deleted: ");
//        int deleteAccount = scan.nextInt();
//        BankAccount accountDel = findAccountByNumber(deleteAccount);
//        if (accountDel == null) {
//            System.out.println("That account doesn't exist!"+"\n The account has not been deleted!");
//            return false;
//        } else {
//            for (int i = 0; i < accounts.length; i++) {
//                if (accountDel == accounts[i]){
//                    accounts[i] = null;
//                    System.out.println("Your account has been successfully deleted!");
//                    saveDataIfEnabled();
//                    return true;
//                }
//            }
//        } return false;
//    }
//
//    public static void saveData() {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVEFILENAME))) {
//            oos.writeObject(accounts);
//            System.out.println("The file has been saved successfully! - " + SAVEFILENAME);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private static void loadAccountsFromFile() {
//        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(SAVEFILENAME))) {
//            accounts = (BankAccount[]) inputStream.readObject();
//            System.out.println("Accounts loaded successfully! - " + SAVEFILENAME);
//        } catch (IOException e) {
//            System.out.println("File download error!");
//        } catch (ClassNotFoundException e) {
//            System.out.println("Unable to save! Save data invalid!");
//        }
//    }
//
//    public static void SaveAutoLoadSetting() {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVEDATAACC))) {
//            oos.writeObject(infoload);
//            System.out.println("The file has been saved successfully!(autoload) -  " + SAVEDATAACC);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private static void loadAutoLoadSetting() {
//        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(SAVEDATAACC))) {
//            infoload = (boolean) inputStream.readObject();
//            System.out.println("Get info false or true! - " + SAVEDATAACC);
//        } catch (FileNotFoundException e) {
//            System.out.println("The auto-load settings file was not found; using the default value!");
//        } catch (IOException e) {
//            System.out.println("File download error!");
//        } catch (ClassNotFoundException e) {
//            System.out.println("Unable to save! Save data invalid!");
//        }
//    }
//
//    public static boolean AutoSave(){
//        save = !save;
//        return save;
//    }
//
//    public static boolean AutoloadAccounts(){
//        infoload = !infoload;
//        SaveAutoLoadSetting();
//        if(infoload == true){
//            loadAccountsFromFile();
//        }
//        return infoload;
//    }
//    private static void saveDataIfEnabled(){
//        if(!save){
//            return;
//        }
//        saveData();
//        System.out.println("Auto-save worked!");
    }
