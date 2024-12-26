package com.example.pingpong;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SceneController3 {

    @FXML
    private ImageView Winner1; // Ссылка на картинку победителя Player 1

    @FXML
    private ImageView Winner2; // Ссылка на картинку для Player 2

    // Метод для установки победителя
    public void setWinner(String winner) {
        // Вначале скрываем картинку второго игрока
        Winner2.setVisible(false);

        // В зависимости от победителя, меняем картинку
        if (winner.equals("Player 1")) {
            // Используем getResource для правильной загрузки изображения
            Winner1.setImage(new Image(getClass().getResource("pl1.png").toExternalForm())); // Картинка для Player 1
            Winner1.setVisible(true); // Показываем картинку Player 1
        } else if (winner.equals("Player 2")) {
            Winner1.setVisible(false); // Скрываем картинку Player 1
            // Используем getResource для правильной загрузки изображения
            Winner2.setImage(new Image(getClass().getResource("pl2.png").toExternalForm())); // Картинка для Player 2
            Winner2.setVisible(true); // Показываем картинку Player 2
        }
    }
}
