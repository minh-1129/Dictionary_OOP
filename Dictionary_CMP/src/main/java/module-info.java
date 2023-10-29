module dictionary.dictionary_cmp {
    requires javafx.controls;
    requires javafx.fxml;


    opens dictionary.dictionary_cmp to javafx.fxml;
    exports dictionary.dictionary_cmp;
}