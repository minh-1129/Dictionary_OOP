module com.example.dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires jlayer;
    requires org.kordamp.bootstrapfx.core;
  requires org.json;
  requires org.apache.commons.text;
  requires java.desktop;
  requires string.similarity;

  opens Dictionary to javafx.fxml;
    exports Dictionary;
    exports Dictionary.Controllers;
    exports Dictionary.Controllers.Wordle;
    opens Dictionary.Controllers to javafx.fxml;
    opens Dictionary.Controllers.Wordle to javafx.fxml;
}