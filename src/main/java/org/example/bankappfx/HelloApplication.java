package org.example.bankappfx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Text BankAccountText = new Text("BANK ACCOUNT APP");

        Button CreateAccount = new Button("Create account");
        Button DeleteAccount = new Button("Delete account");
        Button DepositAccount = new Button("Deposit money");
        Button WithDrawAccount = new Button("Withdraw money");
        Button TransferMoneyAccount = new Button("Transfer from - to");
        Button ShowAllAccounts = new Button("Show all accounts");
        Button EndMonth = new Button("End of month");
        Button SaveDataUsers = new Button("Save data Users");
        Button GetDataUsers = new Button("Get data Users");
        Button SearchAccount = new Button("Search account");
        Button AutoSaveData = new Button("Auto save data"/* Тут должно быть True or False значение*/);
        Button AutoLoadData = new Button("Auto load data"/* Тут должно быть True or False значение попозже сделать*/);
        Button Exit = new Button("Exit");


        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(BankAccountText,CreateAccount,DeleteAccount,DepositAccount,WithDrawAccount,TransferMoneyAccount,ShowAllAccounts,EndMonth,SaveDataUsers,GetDataUsers,SearchAccount,AutoSaveData,AutoLoadData);

        Scene scene = new Scene(vbox,400,600);
        stage.setTitle("1");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }
}