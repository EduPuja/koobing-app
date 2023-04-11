module edu.pujadas.koobing_admin {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens edu.pujadas.koobing_admin to javafx.fxml;
    exports edu.pujadas.koobing_admin;
}