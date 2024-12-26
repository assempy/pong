package com.example.pingpong;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class SceneController2 {

    @FXML
    private AnchorPane scene2; // основная сцена

    @FXML
    private ImageView paddle1; // Левая ракетка

    @FXML
    private ImageView paddle2; // Правая ракетка

    @FXML
    private Label frsplScore; // Счёт игрока 1

    @FXML
    private Label scndplScore; // Счёт игрока 2

    @FXML
    private ImageView ball;

    private int player1Score = 0;
    private int player2Score = 0;


    private boolean wPressed = false;
    private boolean sPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    private final double paddleSpeed = 7.0; // скорость ракеток
    private final double ballSpeedX = 9.0; // скорость мяча по горизонтали
    private final double ballSpeedY = 9.0; // скорость мяча по вертикали
    private double ballDirectionX = ballSpeedX; // направление мяча по горизонтали, основываясь на скорости по Х
    private double ballDirectionY = ballSpeedY; // направление мяча по вертикали, основываясь на скорости по У

    private HelloApplication application;  // Добавляем ссылку на HelloApplication

    public void setApplication(HelloApplication application) {
        this.application = application;  // Устанавливаем ссылку на HelloApplication
    }

    private final AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            // Движение столиков
            if (wPressed && paddle1.getLayoutY() > 0) {
                paddle1.setLayoutY(paddle1.getLayoutY() - paddleSpeed);
            }
            if (sPressed && paddle1.getLayoutY() < scene2.getHeight() - paddle1.getFitHeight()) {
                paddle1.setLayoutY(paddle1.getLayoutY() + paddleSpeed);
            }

            if (upPressed && paddle2.getLayoutY() > 0) {
                paddle2.setLayoutY(paddle2.getLayoutY() - paddleSpeed);
            }
            if (downPressed && paddle2.getLayoutY() < scene2.getHeight() - paddle2.getFitHeight()) {
                paddle2.setLayoutY(paddle2.getLayoutY() + paddleSpeed);
            }

            // Движение мяча
            ball.setLayoutX(ball.getLayoutX() + ballDirectionX);
            ball.setLayoutY(ball.getLayoutY() + ballDirectionY);

            // Отражение мяча от верхней и нижней границы
            if (ball.getLayoutY() <= 0 || ball.getLayoutY() >= scene2.getHeight() - ball.getFitHeight()) {
                ballDirectionY *= -1;
            }

            // Отражение мяча от ракеток
            if (ball.getLayoutX() <= paddle1.getLayoutX() + paddle1.getFitWidth() &&
                    ball.getLayoutY() + ball.getFitHeight() >= paddle1.getLayoutY() &&
                    ball.getLayoutY() <= paddle1.getLayoutY() + paddle1.getFitHeight()) {
                ballDirectionX *= -1;
            }
            if (ball.getLayoutX() >= paddle2.getLayoutX() - ball.getFitWidth() &&
                    ball.getLayoutY() + ball.getFitHeight() >= paddle2.getLayoutY() &&
                    ball.getLayoutY() <= paddle2.getLayoutY() + paddle2.getFitHeight()) {
                ballDirectionX *= -1;
            }

            // Если мяч выходит за пределы (правая граница)
            if (ball.getLayoutX() >= scene2.getWidth()) {
                incrfrtscr(); // Увеличиваем счёт игрока 1
                updateScore();
                resetBall();
            }

            // Если мяч выходит за пределы (левая граница)
            if (ball.getLayoutX() <= 0) {
                incrsecscr(); // Увеличиваем счёт игрока 2
                updateScore();
                resetBall();
            }
        }
    };

    @FXML
    public void initialize() {
        // Инициализация анимации
        animationTimer.start();
    }
    // управление ракетками с клавиатуры
    public void setupKeyHandlers(Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code) {
                case W -> wPressed = true;
                case S -> sPressed = true;
                case UP -> upPressed = true;
                case DOWN -> downPressed = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            switch (code) {
                case W -> wPressed = false;
                case S -> sPressed = false;
                case UP -> upPressed = false;
                case DOWN -> downPressed = false;
            }
        });
    }

    private void resetBall() {
        // Сброс мяча в начальное положение
        ball.setLayoutX(scene2.getWidth() / 2);
        ball.setLayoutY(scene2.getHeight() / 2);
        ballDirectionX *= -1; // Меняем направление мяча
    }

    private void incrfrtscr() {
        player1Score++;
    }

    private void incrsecscr() {
        player2Score++;
    }

    private void updateScore() {
        // Обновляем отображение счёта
        frsplScore.setText(String.valueOf(player1Score));
        scndplScore.setText(String.valueOf(player2Score));

        // Проверяем завершение игры
        if (player1Score == 10 || player2Score == 10) {
            endGame("Game Over");
        }
    }


    private void endGame(String message) {
        animationTimer.stop();
        System.out.println(message);
        // Переход на сцену окончания игры с передачей очков обоих игроков
        application.switchToGameOver(player1Score, player2Score);
    }

}

