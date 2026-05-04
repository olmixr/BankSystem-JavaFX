package org.example.bankappfx;
import java.util.ArrayList;

public class BankService {

    private final ArrayList<BankAccounts> accounts;

    public BankService(){
      accounts = new ArrayList<BankAccounts>();
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


    public BankAccounts findAccountByNumber(int numberAccount){
        for (int i = 0; i < accounts.size() ; i++){
            if (accounts.get(i) !=null && accounts.get(i).getAccountNumber() == numberAccount){
                return accounts.get(i);
            }
        }
        return null;
    }

    public void deleteAccountToNumber(int numberAccount){
        for (int i = 0; i < accounts.size(); i++) {
            if (numberAccount == accounts.get(i).getAccountNumber()){
                accounts.remove(i);
            }
        }

    }

//    public void depositMoney(int number,double value){
//        for (int i = 0; i < accounts.size(); i++) {
//            if (number == accounts.get(i)){
//
//            }
//        }
//
//    }

//    public boolean deleteAccount(int index){
//
//
//    }


    //    private static BankAccount findAccountByNumber(int inputnumber) {
//        for (int i = 0; i < accounts.length; i++) {
//            if ( accounts[i] !=null && accounts[i].getAccountNumber() == inputnumber) {
//                return accounts[i];
//            }
//        }
//        return null;
//    }
////


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


//    private static int getFreePozisin() {
//        for (int i = 0; i < accounts.length; i++) {
//            if (accounts[i] == null) {
//                return i;
//            }
//        }
//        return -1;
//    }
//

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
