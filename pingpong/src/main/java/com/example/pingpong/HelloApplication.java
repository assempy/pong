package com.example.pingpong;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HelloApplication extends Application {

    private Stage stage;

    private static final String URL = "";
    private static final String USER = "";
    private static final String PASSWORD = "";

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        try {
            // Загружаем начальную сцену
            FXMLLoader introLoader = new FXMLLoader(getClass().getResource("intro.fxml"));
            Scene scene1 = new Scene(introLoader.load(), 1536, 865);

            // Настройки для окна
            Image icon = new Image("icon.jpg");
            stage.getIcons().add(icon);
            stage.setTitle("Ping Pong");
            stage.setResizable(false);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("To escape press space");
            stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("space"));

            // Переход в начало игры (передаем ссылку на HelloApplication)
            SceneController1 controller1 = introLoader.getController();
            controller1.setApplication(this);

            // Устанавливаем сцену
            stage.setScene(scene1);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Обрабатываем исключение
        }
    }

    // Метод для переключения на основную сцену игры
    public void switchToMainScene() {
        try {
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("main.fxml"));
            Scene scene2 = new Scene(mainLoader.load(), 1536, 865);

            // Настройка сцены
            SceneController2 controller2 = mainLoader.getController();
            controller2.setApplication(this);
            controller2.setupKeyHandlers(scene2);

            // Устанавливаем сцену
            stage.setScene(scene2);

            stage.setFullScreen(true);
        } catch (IOException e) {
            e.printStackTrace(); // Обрабатываем исключение
        }
    }

    // Метод для переключения на сцену окончания игры
    public void switchToGameOver(int player1Score, int player2Score) {
        try {
            FXMLLoader gameOverLoader = new FXMLLoader(getClass().getResource("gameover.fxml"));
            Scene scene3 = new Scene(gameOverLoader.load(), 1536, 865);

            // Получаем контроллер сцены окончания игры
            SceneController3 controller3 = gameOverLoader.getController();

            // Устанавливаем победителя
            String winner = player1Score > player2Score ? "Player 1" : "Player 2";
            controller3.setWinner(winner);

            // Сохраняем результаты в базу данных
            addScore(player1Score, player2Score);

            // Устанавливаем сцену
            stage.setScene(scene3);
            stage.setFullScreen(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для подключения к базе данных
    public static Connection connect() {
        try {
            //установка соединения
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения: " + e.getMessage());
            return null;
        }
    }

    // Метод для добавления нового результата
    public static void addScore(int player1Score, int player2Score) {
        String query = "INSERT INTO game_results (player1_score, player2_score) VALUES (?, ?)";
        try (Connection conn = connect();
             //выполнение запроса
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, player1Score);
            stmt.setInt(2, player2Score);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка добавления данных: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
