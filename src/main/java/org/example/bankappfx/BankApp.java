package org.example.bankappfx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class BankApp extends Application {

    private final BankService service = new BankService();

    @Override
    public void start(Stage stage) throws IOException {
        Text titleText = new Text("BANKING APP");

        Button createAccountButton = new Button("Create account");
        Button deleteAccountButton = new Button("Delete account");
        Button depositMoneyButton = new Button("Deposit money");
        Button withdrawMoneyButton = new Button("Withdraw money");
        Button transferMoneyButton = new Button("Transfer money");
        Button showAllAccountsButton = new Button("Show all accounts");
        Button endMonthButton = new Button("End of month");
        Button saveUserDataButton = new Button("Save user data");
        Button loadUserDataButton = new Button("Load user data");
        Button searchAccountButton = new Button("Search account");
        Button autoSaveDataButton = new Button("Auto save data"); // TODO: show true/false state
        Button autoLoadDataButton = new Button("Auto load data"); // TODO: show true/false state
        Button exitButton = new Button("Exit");
        Separator actionsSeparator = new Separator();
        Separator footerSeparator = new Separator();

        createAccountButton.setOnAction(event -> {
            Stage createStage = new Stage();
            createStage.initOwner(stage);
            createStage.initModality(Modality.APPLICATION_MODAL);
            createStage.setTitle("Create account");

            Text accountTypeText = new Text("Select the type of account you want to create:");

            ComboBox<String> accountTypeComboBox = new ComboBox<>();
            accountTypeComboBox.getItems().addAll("Savings Account", "Credit account");
            accountTypeComboBox.setValue("Savings Account");

            Text accountNumberLabel = new Text("Account number:");
            TextField accountNumberField = new TextField();
            accountNumberField.setPromptText("Enter number");
            accountNumberField.setMaxWidth(100);

            Text ownerNameLabel = new Text("Owner name:");
            TextField ownerNameField = new TextField();
            ownerNameField.setPromptText("Enter text");
            ownerNameField.setMaxWidth(100);

            Text primaryFieldLabel = new Text("Deposit interest rate:");
            TextField primaryField = new TextField();
            primaryField.setPromptText("Enter number");
            primaryField.setMaxWidth(100);

            Text monthlyFeeLabel = new Text("Monthly fee:");
            TextField monthlyFeeField = new TextField();
            monthlyFeeField.setPromptText("Enter number");
            monthlyFeeField.setMaxWidth(100);

            monthlyFeeLabel.setVisible(false);
            monthlyFeeLabel.setManaged(false);
            monthlyFeeField.setVisible(false);
            monthlyFeeField.setManaged(false);

            accountTypeComboBox.setOnAction(e -> updateAccountFields(
                    accountTypeComboBox.getValue(),
                    primaryFieldLabel,
                    primaryField,
                    monthlyFeeLabel,
                    monthlyFeeField
            ));

            Button submitButton = new Button("OK");
            Button cancelButton = new Button("Cancel");

            submitButton.setOnAction(event1 -> {
                String accountType = accountTypeComboBox.getValue();
                String accountNumberValue = accountNumberField.getText().trim();
                String ownerNameValue = ownerNameField.getText().trim();
                String primaryValue = primaryField.getText().trim();
                String monthlyFeeValue = monthlyFeeField.getText().trim();

                boolean isCreditAccount = "Credit account".equals(accountType);
                boolean hasEmptyField = accountType == null
                        || accountNumberValue.isEmpty()
                        || ownerNameValue.isEmpty()
                        || primaryValue.isEmpty()
                        || (isCreditAccount && monthlyFeeValue.isEmpty());

                if (hasEmptyField) {
                    showAlert(Alert.AlertType.ERROR, "Fill in all required fields.");
                    return;
                }

                try {
                    int accountNumber = Integer.parseInt(accountNumberValue);

                    if ("Savings Account".equals(accountType)) {
                        int interestRate = Integer.parseInt(primaryValue);
                        service.createSavingsAccount(accountNumber, ownerNameValue, interestRate);
                    } else if ("Credit account".equals(accountType)) {
                        int creditLimit = Integer.parseInt(primaryValue);
                        int fee = Integer.parseInt(monthlyFeeValue);
                        service.createCreditAccount(accountNumber, ownerNameValue, creditLimit, fee);
                    }

                    showAlert(
                            Alert.AlertType.INFORMATION,
                            "Account created. Total accounts: " + service.getAccountsCount()
                    );
                    createStage.close();
                } catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Number fields must contain only digits.");
                } catch (IllegalArgumentException ex) {
                    showAlert(Alert.AlertType.ERROR, ex.getMessage());
                }
            });

            cancelButton.setOnAction(e -> createStage.close());

            HBox buttonsBox = new HBox(10, submitButton, cancelButton);
            buttonsBox.setAlignment(Pos.CENTER);

            VBox dialogLayout = new VBox(
                    10,
                    accountTypeText,
                    accountTypeComboBox,
                    accountNumberLabel,
                    accountNumberField,
                    ownerNameLabel,
                    ownerNameField,
                    primaryFieldLabel,
                    primaryField,
                    monthlyFeeLabel,
                    monthlyFeeField,
                    buttonsBox
            );
            dialogLayout.setAlignment(Pos.CENTER);

            Scene dialogScene = new Scene(dialogLayout, 350, 320);
            createStage.setScene(dialogScene);
            createStage.showAndWait();
        });

        deleteAccountButton.setOnAction(event -> {
            Stage deleteStage = new Stage();
            deleteStage.initOwner(stage);
            deleteStage.initModality(Modality.APPLICATION_MODAL);
            deleteStage.setTitle("Delete account");

            Text accountNumberLabel = new Text("Delete account number:");
            TextField accountNumberField = new TextField();
            accountNumberField.setPromptText("Enter number");
            accountNumberField.setMaxWidth(140);

            Button deleteButton = new Button("Delete");
            Button cancelButton = new Button("Cancel");

            deleteButton.setOnAction(event1 -> {
                String accountNumberValue = accountNumberField.getText().trim();
                if (accountNumberValue.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Enter an account number.");
                    return;
                }

                try {
                    int accountNumber = Integer.parseInt(accountNumberValue);
                    if (accountNumber <= 0) {
                        showAlert(Alert.AlertType.ERROR, "Account number must be greater than zero.");
                        return;
                    }

                    BankAccounts account = service.findAccountByNumber(accountNumber);
                    if (account == null) {
                        showAlert(Alert.AlertType.ERROR, "This account does not exist and cannot be deleted.");
                        return;
                    }

                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.initOwner(deleteStage);
                    confirmationAlert.setTitle("Confirm delete");
                    confirmationAlert.setHeaderText("Delete this account?");
                    confirmationAlert.setContentText(buildDeleteConfirmationText(account));

                    Optional<ButtonType> result = confirmationAlert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        service.deleteAccountToNumber(accountNumber);
                        showAlert(Alert.AlertType.INFORMATION, "Account deleted.");
                        deleteStage.close();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Account was not deleted.");
                    }
                } catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Account number must contain only digits.");
                }
            });

            cancelButton.setOnAction(event1 -> deleteStage.close());

            HBox buttonsBox = new HBox(10, deleteButton, cancelButton);
            buttonsBox.setAlignment(Pos.CENTER);

            VBox dialogLayout = new VBox(10, accountNumberLabel, accountNumberField, buttonsBox);
            dialogLayout.setAlignment(Pos.CENTER);

            Scene deleteScene = new Scene(dialogLayout, 340, 170);
            deleteStage.setScene(deleteScene);
            deleteStage.showAndWait();
        });

        depositMoneyButton.setOnAction(event -> {
            Stage depositStage = new Stage();
            depositStage.initOwner(stage);
            depositStage.initModality(Modality.APPLICATION_MODAL);
            depositStage.setTitle("Deposit");

            Text accountNumberLabel = new Text("Deposit to account:");
            TextField accountNumberField = new TextField();
            accountNumberField.setPromptText("Enter number");
            accountNumberField.setMaxWidth(140);

            Text amountLabel = new Text("How much do you want to deposit?");
            TextField amountField = new TextField();
            amountField.setPromptText("Enter amount");
            amountField.setMaxWidth(140);

            Button submitButton = new Button("Submit");
            Button cancelButton = new Button("Cancel");

            submitButton.setOnAction(event1 -> {
                String accountNumberValue = accountNumberField.getText().trim();
                String amountValue = amountField.getText().trim();

                if (accountNumberValue.isEmpty() || amountValue.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Fill in all required fields.");
                    return;
                }

                try {
                    int accountNumber = Integer.parseInt(accountNumberValue);
                    double amount = Double.parseDouble(amountValue);

                    if (accountNumber <= 0 || amount <= 0) {
                        showAlert(Alert.AlertType.ERROR, "Account number and amount must be greater than zero.");
                        return;
                    }

                    BankAccounts account = service.findAccountByNumber(accountNumber);
                    if (account == null) {
                        showAlert(Alert.AlertType.ERROR, "This account does not exist.");
                        return;
                    }

                    account.deposit(amount);
                    showAlert(Alert.AlertType.INFORMATION, "Deposit completed.");
                    depositStage.close();
                } catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Account number and amount must be numeric.");
                }
            });

            cancelButton.setOnAction(event1 -> depositStage.close());

            HBox buttonsBox = new HBox(10, submitButton, cancelButton);
            buttonsBox.setAlignment(Pos.CENTER);

            VBox dialogLayout = new VBox(10, accountNumberLabel, accountNumberField, amountLabel, amountField, buttonsBox);
            dialogLayout.setAlignment(Pos.CENTER);

            Scene depositScene = new Scene(dialogLayout, 340, 230);
            depositStage.setScene(depositScene);
            depositStage.showAndWait();
        });

        withdrawMoneyButton.setOnAction(event -> {
            Stage withdrawStage = new Stage();
            withdrawStage.initOwner(stage);
            withdrawStage.initModality(Modality.APPLICATION_MODAL);
            withdrawStage.setTitle("Withdraw");

            Text accountNumberLabel = new Text("Withdraw from account:");
            TextField accountNumberField = new TextField();
            accountNumberField.setPromptText("Enter number");
            accountNumberField.setMaxWidth(140);

            Text amountLabel = new Text("How much do you want to withdraw?");
            TextField amountField = new TextField();
            amountField.setPromptText("Enter amount");
            amountField.setMaxWidth(140);

            Button submitButton = new Button("Submit");
            Button cancelButton = new Button("Cancel");

            submitButton.setOnAction(event1 -> {
                String accountNumberValue = accountNumberField.getText().trim();
                String amountValue = amountField.getText().trim();

                if (accountNumberValue.isEmpty() || amountValue.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Fill in all required fields.");
                    return;
                }

                try {
                    int accountNumber = Integer.parseInt(accountNumberValue);
                    double amount = Double.parseDouble(amountValue);

                    if (accountNumber <= 0 || amount <= 0) {
                        showAlert(Alert.AlertType.ERROR, "Account number and amount must be greater than zero.");
                        return;
                    }

                    BankAccounts account = service.findAccountByNumber(accountNumber);
                    if (account == null) {
                        showAlert(Alert.AlertType.ERROR, "This account does not exist.");
                        return;
                    }

                    double balanceBeforeWithdraw = account.getBalance();
                    account.withdraw(amount);

                    if (Double.compare(balanceBeforeWithdraw, account.getBalance()) == 0) {
                        showAlert(Alert.AlertType.ERROR, "Withdrawal failed.");
                        return;
                    }

                    showAlert(Alert.AlertType.INFORMATION, "Money withdrawn.");
                    withdrawStage.close();
                } catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Account number and amount must be numeric.");
                }
            });

            cancelButton.setOnAction(event1 -> withdrawStage.close());

            HBox buttonsBox = new HBox(10, submitButton, cancelButton);
            buttonsBox.setAlignment(Pos.CENTER);

            VBox dialogLayout = new VBox(10, accountNumberLabel, accountNumberField, amountLabel, amountField, buttonsBox);
            dialogLayout.setAlignment(Pos.CENTER);

            Scene withdrawScene = new Scene(dialogLayout, 340, 230);
            withdrawStage.setScene(withdrawScene);
            withdrawStage.showAndWait();
        });

        exitButton.setOnAction(event -> stage.close());

        VBox rootLayout = new VBox(10);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.getChildren().addAll(
                titleText,
                createAccountButton,
                deleteAccountButton,
                depositMoneyButton,
                withdrawMoneyButton,
                transferMoneyButton,
                showAllAccountsButton,
                endMonthButton,
                actionsSeparator,
                saveUserDataButton,
                loadUserDataButton,
                searchAccountButton,
                autoSaveDataButton,
                autoLoadDataButton,
                footerSeparator,
                exitButton
        );

        Scene scene = new Scene(rootLayout, 400, 600);
        stage.setTitle("BANK SYSTEM");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void updateAccountFields(
            String accountType,
            Text primaryFieldLabel,
            TextField primaryField,
            Text monthlyFeeLabel,
            TextField monthlyFeeField
    ) {
        boolean isCreditAccount = "Credit account".equals(accountType);

        if (isCreditAccount) {
            primaryFieldLabel.setText("Credit limit:");
            primaryField.setPromptText("Enter number");
        } else {
            primaryFieldLabel.setText("Deposit interest rate:");
            primaryField.setPromptText("Enter number");
            monthlyFeeField.clear();
        }

        monthlyFeeLabel.setVisible(isCreditAccount);
        monthlyFeeLabel.setManaged(isCreditAccount);
        monthlyFeeField.setVisible(isCreditAccount);
        monthlyFeeField.setManaged(isCreditAccount);
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
