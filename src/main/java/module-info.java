module com.thailandcube.scramblemanagerdesktop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires atlantafx.base;

    opens com.thailandcube.scramblemanagerdesktop to javafx.fxml;
    exports com.thailandcube.scramblemanagerdesktop;
    exports com.thailandcube.controllers;
    opens com.thailandcube.controllers to javafx.fxml;
}