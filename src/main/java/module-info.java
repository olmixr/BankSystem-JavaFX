module org.example.bankappfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.bankappfx to javafx.fxml;
    exports org.example.bankappfx;
}