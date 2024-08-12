package com.example.mathquiz;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.Random;

public class MathController {
    @FXML
    private Label problemLabel;
    @FXML
    private TextField answerField;
    @FXML
    private Label timerLabel;
    @FXML
    private Label scoreLabel;
    private final Random random = new Random();
    private Math currentMath;
    private int score = 0;
    private Timeline timer;

    public void generateProblem() {
        // Reset the score to 0 only if it's the first problem
        if (currentMath == null) {
            score = 0;
            scoreLabel.setText("Score: " + score);
        }

        if (timer != null) {
            timer.stop(); // Stop the previous timer, if running
        }

/*        score = 0; // Reset the score to 0
        scoreLabel.setText("Score: " + score);*/

        int problemType = random.nextInt(3); // 0: Arithmetic Problem, 1: Word Problem 1, 2: Word Problem 2
        String problem;
        int answer;
        int timerDuration;

        if (problemType == 0) {
            timerDuration = 10;
            int num1 = random.nextInt(20) - 10; // Random number between -10 and 10
            int num2 = random.nextInt(20) - 10; // Random number between -10 and 10
            int operator = random.nextInt(4);

            problemLabel.setAlignment(Pos.CENTER);
            answerField.setAlignment(Pos.CENTER);
            timerLabel.setAlignment(Pos.CENTER);
            scoreLabel.setAlignment(Pos.CENTER);

            switch (operator) {
                case 0:
                    problem = num1 + " + " + num2 + " = ?";
                    answer = num1 + num2;
                    break;
                case 1:
                    problem = num1 + " - " + num2 + " = ?";
                    answer = num1 - num2;
                    break;
                case 2:
                    problem = num1 + " * " + num2 + " = ?";
                    answer = num1 * num2;
                    break;
                default:
                    if (num2 != 0) {
                        problem = num1 * num2 + " / " + num2 + " = ?";
                        answer = num1;
                    } else {
                        // Avoid division by zero, regenerate the problem
                        generateProblem();
                        return;
                    }
                    break;
            }
        } else {
            // Word Problems
            if (problemType == 1) {
                // Word Problem 1
                timerDuration = 20;
                int num1 = random.nextInt(20) + 10; // Random number between 10 and 30
                int num2 = random.nextInt(10) + 1; // Random number between 1 and 10

                problemLabel.setAlignment(Pos.CENTER);
                answerField.setAlignment(Pos.CENTER);
                timerLabel.setAlignment(Pos.CENTER);
                scoreLabel.setAlignment(Pos.CENTER);
            } else {
                // Word Problem 2
                timerDuration = 20;
                int num1 = random.nextInt(30) + 20; // Random number between 20 and 50
                int num2 = random.nextInt(10) + 5; // Random number between 5 and 15
                double discountedPrice = num1 - (0.2 * num1);
                answer = (int)discountedPrice; // Convert to an integer for simplicity

                // Reset the alignment of the problem label and other elements to center
                problemLabel.setAlignment(Pos.CENTER);
                answerField.setAlignment(Pos.CENTER);
                timerLabel.setAlignment(Pos.CENTER);
                scoreLabel.setAlignment(Pos.CENTER);
            }
            if (problemType == 2){

                int num1 = random.nextInt(10) + 1; // Random number between 1 and 10
                int num2 = random.nextInt(10) + 1; // Random number between 1 and 10
                int num3 = random.nextInt(10) + 1; // Random number between 1 and 10
                problem = "A store sells " + num1 + " bags of candy, with each bag containing " + num2 + " pieces. If " + num3 + " bags are given away, how many pieces of candy will be left?";
                answer = (num1 - num3) * num2;

                // Reset the alignment of the problem label and other elements to center
                problemLabel.setAlignment(Pos.CENTER);
                answerField.setAlignment(Pos.CENTER);
                timerLabel.setAlignment(Pos.CENTER);
                scoreLabel.setAlignment(Pos.CENTER);
            }else {
                // Generate the scenario and mathematical operation for Word Problem 3
                problem = "A garden has 60 red roses and 45 white roses. If 25% of the red roses and 15% of the white roses are picked for a bouquet, how many roses are left in the garden?";
                answer = (int) ((1 - 0.25) * 60 + (1 - 0.15) * 45); // Calculate the remaining roses

                // Reset the alignment of the problem label and other elements to center
                problemLabel.setAlignment(Pos.CENTER);
                answerField.setAlignment(Pos.CENTER);
                timerLabel.setAlignment(Pos.CENTER);
                scoreLabel.setAlignment(Pos.CENTER);
            }

        }


        currentMath = new Math(problem, answer);
        problemLabel.setText(problem);
        answerField.clear();
        timerLabel.setText(String.valueOf(timerDuration)); // Set the timer label to the calculated duration

        // Start the timer for the specified duration
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            int timeLeft = Integer.parseInt(timerLabel.getText()) - 1;
            if (timeLeft > 0) {
                timerLabel.setText(String.valueOf(timeLeft));
            } else if (timeLeft == 0) {
                problemLabel.setText("Time's up! 😞");
                problemLabel.setTextFill(Color.RED);
                score = 0; // Reset the score to 0
                scoreLabel.setText("Score: " + score);
                timer.stop();
//                problemLabel.setTextFill(Color.WHITE);
            }
        }));
        timer.setCycleCount(timerDuration); // Set the countdown duration
        timer.play();
        problemLabel.setTextFill(Color.WHITE);
    }

    public void checkAnswer() {
        if (currentMath == null) {
            problemLabel.setText("Generate a problem first!"); // Provide feedback to the user
            return; // Exit the method early
        }

        timer.stop(); // Stop the timer when the answer is submitted
        String userAnswer = answerField.getText();
        if (userAnswer.equals(String.valueOf(currentMath.getAnswer()))) {
            // Update the score for correct answers
            problemLabel.setText("Correct! 😊");
            problemLabel.setTextFill(Color.GREEN);
            score++; // Increment the score for correct answers
            scoreLabel.setText("Score: " + score);
        } else {
            // Reset the score to 0 for incorrect answers
            problemLabel.setText("Incorrect! 😞. The correct answer is: " + currentMath.getAnswer());
            problemLabel.setTextFill(Color.RED);
            score = 0; // Reset the score to 0 for incorrect answers
            scoreLabel.setText("Score: " + score);
        }
    }
}

