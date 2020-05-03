module quastenflossler.javafx.deployment {

    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    requires javax.inject;
    requires org.slf4j;
    requires spring.context;
    requires spring.beans;
    requires org.apache.commons.lang3;
    requires spring.boot.autoconfigure;

    exports de.quastenflossler.deployment;

    opens de.quastenflossler.deployment to spring.core;
}