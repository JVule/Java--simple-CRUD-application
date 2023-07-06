module com.example.projektvrtic {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.slf4j;
    requires java.sql;


    opens hr.java.glavna to javafx.fxml;
    exports hr.java.glavna;
}