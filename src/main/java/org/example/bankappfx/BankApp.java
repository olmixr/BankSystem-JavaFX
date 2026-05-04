package org.example.bankappfx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class BankApp extends Application  {

    public BankService service = new BankService();

    @Override
    public void start(Stage stage) throws IOException {
        Text BankAccountText = new Text("BANKING APP");

        Button CreateAccount = new Button("Create account");
        Button DeleteAccount = new Button("Delete account");
        Button DepositAccount = new Button("Deposit money");
        Button WithdrawAccount = new Button("Withdraw money");
        Button TransferMoneyAccount = new Button("Transfer from - to");
        Button ShowAllAccounts = new Button("Show all accounts");
        Button EndMonth = new Button("End of month");
        Button SaveDataUsers = new Button("Save data Users");
        Button GetDataUsers = new Button("Get data Users");
        Button SearchAccount = new Button("Search account");
        Button AutoSaveData = new Button("Auto save data"/* Тут должно быть True or False значение*/);
        Button AutoLoadData = new Button("Auto load data"/* Тут должно быть True or False значение попозже сделать*/);
        Button Exit = new Button("Exit");
        Separator separator = new Separator();
        Separator separator1 = new Separator();

        CreateAccount.setOnAction(event -> {
            Stage createStage = new Stage();
            createStage.initOwner(stage);
            createStage.initModality(Modality.APPLICATION_MODAL);
            createStage.setTitle("Create account");

            Text textAccount = new Text("Select the type of account you want to create: ");

            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.getItems().addAll("Savings Account", "Credit account");
            comboBox.setValue("Savings Account");

            Text accountNumberText = new Text("Account number: ");

            TextField accountNumber = new TextField();
            accountNumber.setPromptText("Enter number");
            accountNumber.setMaxWidth(100);

            Text ownerNameText = new Text("Owner name: ");

            TextField ownerName = new TextField();
            ownerName.setPromptText("Enter text");
            ownerName.setMaxWidth(100);


            Text depositInterestText = new Text("Deposit interest rate: ");

            TextField depositInterest = new TextField();
            depositInterest.setPromptText("Enter number");
            depositInterest.setMaxWidth(100);

            Text monthlyFeeText = new Text("Monthly fee: ");

            TextField monthlyFee = new TextField();
            monthlyFee.setPromptText("Enter number");
            monthlyFee.setMaxWidth(100);

            monthlyFeeText.setVisible(false);
            monthlyFeeText.setManaged(false);
            monthlyFee.setVisible(false);
            monthlyFee.setManaged(false);

            comboBox.setOnAction(e -> updateAccountFields(
                    comboBox.getValue(),
                    depositInterestText,
                    depositInterest,
                    monthlyFeeText,
                    monthlyFee
            ));


            Button submit = new Button("OK");
            Button cansel = new Button("CANSEL");


            submit.setOnAction(event1 -> {
                String accountType = comboBox.getValue();
                String accountNumberValue = accountNumber.getText().trim();
                String ownerNameValue = ownerName.getText().trim();
                String firstValue = depositInterest.getText().trim();
                String secondValue = monthlyFee.getText().trim();

                boolean isCreditAccount = "Credit account".equals(accountType);
                boolean hasEmptyField = accountType == null
                        || accountNumberValue.isEmpty()
                        || ownerNameValue.isEmpty()
                        || firstValue.isEmpty()
                        || (isCreditAccount && secondValue.isEmpty());

                if (hasEmptyField) {
                    showAlert(Alert.AlertType.ERROR, "Fill in all required fields.");
                    return;
                }

                try {
                    int accountNumberParsed = Integer.parseInt(accountNumberValue);

                    if ("Savings Account".equals(accountType)) {
                        int interestRate = Integer.parseInt(firstValue);
                        service.createSavingsAccount(accountNumberParsed, ownerNameValue, interestRate);
                    } else if ("Credit account".equals(accountType)) {
                        int creditLimit = Integer.parseInt(firstValue);
                        int monthlyFeeValue = Integer.parseInt(secondValue);
                        service.createCreditAccount(accountNumberParsed, ownerNameValue, creditLimit, monthlyFeeValue);
                    }

                    showAlert(Alert.AlertType.INFORMATION,
                            "Account created. Total accounts: " + service.getAccountsCount());
                    createStage.close();
                } catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Number fields must contain only digits.");
                } catch (IllegalArgumentException ex) {
                    showAlert(Alert.AlertType.ERROR, ex.getMessage());
                }
            });



            cansel.setOnAction(e -> createStage.close());

            HBox buttonsBox = new HBox(10, submit, cansel);
            buttonsBox.setAlignment(Pos.CENTER);


            VBox dialogLayout = new VBox(
                    10,
                    textAccount,
                    comboBox,
                    accountNumberText,
                    accountNumber,
                    ownerNameText,
                    ownerName,
                    depositInterestText,
                    depositInterest,
                    monthlyFeeText,
                    monthlyFee,
                    buttonsBox
            );
            dialogLayout.setAlignment(Pos.CENTER);

            Scene dialogScene = new Scene(dialogLayout, 350, 320);
            createStage.setScene(dialogScene);
            createStage.showAndWait();

        });


        DeleteAccount.setOnAction(event1 -> {
           Stage deleteStage = new Stage();
           deleteStage.initOwner(stage);
           deleteStage.initModality(Modality.APPLICATION_MODAL);
           deleteStage.setTitle("Delete");


            Text TextDeleteAccount = new Text("Delete account number: ");

            TextField accountNumberText = new TextField();
            accountNumberText.setPromptText("Enter number");
            accountNumberText.setMaxWidth(140);

            Button deleteButton = new Button("DELETE");
            Button canselButton = new Button("CANSEL");


            deleteButton.setOnAction(event -> {
                String accountNumber = accountNumberText.getText().trim();
                if (accountNumber.isEmpty()){
                    showAlert(Alert.AlertType.ERROR,"Fill in end account number");
                    return;
                }
                try {
                    int accountNumberParsed = Integer.parseInt(accountNumber);
                    if (accountNumberParsed<=0){
                        showAlert(Alert.AlertType.ERROR,"Номер аккаунта должен быть больше нуля");
                        return;
                    }
                    BankAccounts bankAccounts = service.findAccountByNumber(accountNumberParsed);
                    if (bankAccounts == null){
                        showAlert(Alert.AlertType.ERROR,"Этот аккаунт не существует и мы не можем его удалить");
                        return;
                    }

                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.initOwner(deleteStage);
                    confirmationAlert.setTitle("Confirm delete");
                    confirmationAlert.setHeaderText("Delete this account?");
                    confirmationAlert.setContentText(buildDeleteConfirmationText(bankAccounts));
//                    confirmationAlert.showAndWait();//Добавить удаление или проверить если удалили аккаунт

                    Optional<ButtonType> result = confirmationAlert.showAndWait();

                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        service.deleteAccountToNumber(accountNumberParsed);
                        showAlert(Alert.AlertType.INFORMATION,"Account delete!");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Account not deleted! ");
                    }

                }catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Аккаунт должен содержать только цифры ");
                }
            });

            canselButton.setOnAction(event -> {
                deleteStage.close();
            });

            HBox hbox = new HBox(10,deleteButton,canselButton);
            VBox vbox = new VBox(10, TextDeleteAccount,accountNumberText,hbox);
            hbox.setAlignment(Pos.CENTER);
            vbox.setAlignment(Pos.CENTER);
            Scene deleteScene = new Scene(vbox,340,170);

            deleteStage.setScene(deleteScene);
            deleteStage.showAndWait();

        });



        DepositAccount.setOnAction(event -> {

            Stage depositScene = new Stage();
            depositScene.initOwner(stage);
            depositScene.initModality(Modality.APPLICATION_MODAL);
            depositScene.setTitle("Deposit");

            Text TextDepositNumber = new Text("Deposit to account: ");
            TextField TextFieldNumber = new TextField();
            TextFieldNumber.setPromptText("Enter number");
            TextFieldNumber.setMaxWidth(140);


            Text TextDepositMoney = new Text("How much do you want to transfer?: ");
            TextField TextFieldDepositMoney = new TextField();
            TextFieldDepositMoney.setPromptText("Enter money");
            TextFieldDepositMoney.setMaxWidth(140);

            Button depositButton = new Button("SUBMIT");
            Button canselButton = new Button("CANSEL");


            depositButton.setOnAction(event1 -> {

            String accountNumber = TextFieldNumber.getText().trim();
            String accountToMoney = TextFieldDepositMoney.getText().trim();

            if (accountNumber.isEmpty() && accountToMoney.isEmpty()){
                showAlert(Alert.AlertType.ERROR,"Fill in end account number");
                return;
            }
            try {
                int accountNumberParsed = Integer.parseInt(accountNumber);
                double accountNumberMoneyParsed = Double.parseDouble(accountToMoney);

                if (accountNumberParsed <=0 || accountNumberMoneyParsed <=0){
                    showAlert(Alert.AlertType.ERROR,"Поля должны быть больше нуля");
                    return;
                }

                BankAccounts bankAccounts = service.findAccountByNumber(accountNumberParsed);
                if (bankAccounts == null){
                    showAlert(Alert.AlertType.ERROR,"Этот аккаунт не существует");
                    return;
                }else {
                    bankAccounts.deposit(accountNumberMoneyParsed);//Пополнение
                    showAlert(Alert.AlertType.INFORMATION,"Account deposit!");
                    depositScene.close();

                }

            }catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Аккаунт должен содержать только цифры ");
            }


            });

            canselButton.setOnAction(event1 -> {
                depositScene.close();
            });


            HBox hbox = new HBox(10,depositButton,canselButton);
            VBox vbox = new VBox(10,TextDepositNumber,TextFieldNumber,TextDepositMoney,TextFieldDepositMoney,hbox);
            hbox.setAlignment(Pos.CENTER);
            vbox.setAlignment(Pos.CENTER);
            Scene deleteScene = new Scene(vbox,340,230);

            depositScene.setScene(deleteScene);
            depositScene.showAndWait();
        });


        WithdrawAccount.setOnAction(event -> {

            Stage withdrawStage = new Stage();
            withdrawStage.initOwner(stage);
            withdrawStage.initModality(Modality.APPLICATION_MODAL);
            withdrawStage.setTitle("Withdraw");

            Text textWithdrawNumber = new Text("Withdraw from account: ");
            TextField textFieldNumber = new TextField();
            textFieldNumber.setPromptText("Enter number");
            textFieldNumber.setMaxWidth(140);


            Text textWithdrawMoney = new Text("How much do you want to withdraw?: ");
            TextField textFieldWithdrawMoney = new TextField();
            textFieldWithdrawMoney.setPromptText("Enter money");
            textFieldWithdrawMoney.setMaxWidth(140);

            Button withdrawButton = new Button("SUBMIT");
            Button canselButton = new Button("CANSEL");


            withdrawButton.setOnAction(event1 -> {

                String accountNumber = textFieldNumber.getText().trim();
                String accountToMoney = textFieldWithdrawMoney.getText().trim();

                if (accountNumber.isEmpty() && accountToMoney.isEmpty()){
                    showAlert(Alert.AlertType.ERROR,"Fill in end account number");
                    return;
                }
                try {
                    int accountNumberParsed = Integer.parseInt(accountNumber);
                    double accountNumberMoneyParsed = Double.parseDouble(accountToMoney);

                    if (accountNumberParsed <=0 || accountNumberMoneyParsed <=0){
                        showAlert(Alert.AlertType.ERROR,"Поля должны быть больше нуля");
                        return;
                    }

                    BankAccounts bankAccounts = service.findAccountByNumber(accountNumberParsed);
                    if (bankAccounts == null){
                        showAlert(Alert.AlertType.ERROR,"Этот аккаунт не существует");
                        return;
                    }else {
                        if (bankAccounts.getBalance()<=0){
                            showAlert(Alert.AlertType.ERROR,"Баланс маленький");
                        }else {
                            bankAccounts.withdraw(accountNumberMoneyParsed);//Вывод
                            showAlert(Alert.AlertType.INFORMATION,"Money withdrawn!");
                            withdrawStage.close();
                        }
                    }

                }catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Аккаунт должен содержать только цифры ");
                }


            });

            canselButton.setOnAction(event1 -> {
                withdrawStage.close();
            });


            HBox hbox = new HBox(10,withdrawButton,canselButton);
            VBox vbox = new VBox(10,textWithdrawNumber,textFieldNumber,textWithdrawMoney,textFieldWithdrawMoney,hbox);
            hbox.setAlignment(Pos.CENTER);
            vbox.setAlignment(Pos.CENTER);
            Scene withdrawScene = new Scene(vbox,340,230);

            withdrawStage.setScene(withdrawScene);
            withdrawStage.showAndWait();
        });












        Exit.setOnAction(event -> {
            stage.close();
        });

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(BankAccountText,CreateAccount,DeleteAccount,DepositAccount,WithdrawAccount,TransferMoneyAccount,ShowAllAccounts,EndMonth,separator,SaveDataUsers,GetDataUsers,SearchAccount,AutoSaveData,AutoLoadData,separator1,Exit);

        Scene scene = new Scene(vbox,400,600);
        stage.setTitle("BANK SYSTEM");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void updateAccountFields(
            String accountType,
            Text firstLabel,
            TextField firstField,
            Text secondLabel,
            TextField secondField
    ) {
        boolean isCreditAccount = "Credit account".equals(accountType);

        if (isCreditAccount) {
            firstLabel.setText("Credit limit: ");
            firstField.setPromptText("Enter number");
        } else {
            firstLabel.setText("Deposit interest rate: ");
            firstField.setPromptText("Enter number");
            secondField.clear();
        }

        secondLabel.setVisible(isCreditAccount);
        secondLabel.setManaged(isCreditAccount);
        secondField.setVisible(isCreditAccount);
        secondField.setManaged(isCreditAccount);
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String buildDeleteConfirmationText(BankAccounts account) {
        String accountType = account instanceof BankSavingAccount
                ? "Savings Account"
                : "Credit account";

        return "Type: " + accountType
                + "\nAccount number: " + account.getAccountNumber()
                + "\nOwner: " + account.getOwnerName()
                + "\nBalance: " + String.format("%.2f", account.getBalance());

    }



}
