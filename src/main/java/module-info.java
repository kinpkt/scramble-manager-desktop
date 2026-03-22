module com.thailandcube {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires atlantafx.base;
    requires java.net.http;
    requires com.google.gson;

    opens com.thailandcube.scramblemanagerdesktop to javafx.fxml;
    opens com.thailandcube.models to com.google.gson;
    exports com.thailandcube.scramblemanagerdesktop;
    exports com.thailandcube.controllers;
    opens com.thailandcube.controllers to javafx.fxml;
}