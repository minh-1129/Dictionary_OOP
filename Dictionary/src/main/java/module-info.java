module com.example.dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires jlayer;
    requires org.kordamp.bootstrapfx.core;

    opens Dictionary to javafx.fxml;
    exports Dictionary;
    exports Dictionary.Controllers;
    opens Dictionary.Controllers to javafx.fxml;
}