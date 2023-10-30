module com.example.dictionary {
    requires javafx.controls;
    requires javafx.fxml;


    opens Dictionary to javafx.fxml;
    exports Dictionary;
    exports Dictionary.Controllers;
    opens Dictionary.Controllers to javafx.fxml;
}