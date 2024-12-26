package com.example.pingpong;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SceneController1 {
    private HelloApplication application;

    // Этот метод будет вызываться для перехода на основную сцену
    public void setApplication(HelloApplication application) {
        this.application = application;

    }

    @FXML
    public void startGame(ActionEvent event) {
        // Переход на основную сцену игры
        application.switchToMainScene();
    }
}