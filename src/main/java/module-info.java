module com.example.cpsc329pm {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.fasterxml.jackson.databind;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    // Treat TilesFX as an automatic module, this might not work depending on version.
//    requires eu.hansolo.tilesfx; // No module-info available for TilesFX, but Java might resolve it automatically.

    requires com.almasb.fxgl.all;

    opens com.cpsc329pm to javafx.fxml, com.fasterxml.jackson.databind;
    exports com.cpsc329pm;
}
