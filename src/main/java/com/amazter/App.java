package com.amazter;

import com.amazter.enums.SceneType;
import com.amazter.gui.SceneBuilder;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        SceneBuilder sceneBuilder = new SceneBuilder();
        stage.setScene(sceneBuilder.buildScene(SceneType.CREATE_NOTE, stage));
        stage.setMaxHeight(480);
        stage.setMaxWidth(640);
        stage.setMinHeight(300);
        stage.setMinWidth(400);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}

