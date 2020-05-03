package de.quastenflossler.deployment;

public enum BasicScene {

    MAIN("main scene", "mainScene.fxml");

    private String name;
    private String fxmlFile;

    BasicScene(final String name, final String fxmlFile) {

        this.name = name;
        this.fxmlFile = fxmlFile;
    }

    public String getName() {
        return name;
    }

    public String getFxmlFile() {
        return fxmlFile;
    }

    @Override
    public String toString() {
        return name;
    }
}
