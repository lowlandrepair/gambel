import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class hakari extends JFrame {
    private int numberToGuess;
    private int attempts;
    private final int maxAttempts = 10;
    private double playerMoney = 100.0; // Starting money
    private JTextField guessField;
    private JTextField betField;
    private JLabel feedbackLabel;
    private JLabel attemptsLabel;
    private JLabel moneyLabel;
    private JButton guessButton;
    private JButton playAgainButton;

    public hakari() {
        setTitle("Casino Number Guessing Game");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Initialize components
        JLabel instructionLabel = new JLabel("Guess a number between 1 and 50:");
        guessField = new JTextField(10);
        betField = new JTextField(10);
        guessButton = new JButton("Guess");
        playAgainButton = new JButton("Play Again");
        feedbackLabel = new JLabel("");
        attemptsLabel = new JLabel("Attempts left: " + maxAttempts);
        moneyLabel = new JLabel("Money: $" + playerMoney);

        // Add components to the frame
        add(instructionLabel);
        add(guessField);
        add(new JLabel("Enter your bet:"));
        add(betField);
        add(guessButton);
        add(playAgainButton);
        add(feedbackLabel);
        add(attemptsLabel);
        add(moneyLabel);

        // Generate a random number to guess
        resetGame();

        // Add action listener to the guess button
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeGuess();
            }
        });

        // Add action listener to the play again button
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        // Initially disable the play again button
        playAgainButton.setEnabled(false);
    }

    private void resetGame() {
        Random random = new Random();
        numberToGuess = random.nextInt(50) + 1; // Random number between 1 and 50
        attempts = 0;
        feedbackLabel.setText("");
        attemptsLabel.setText("Attempts left: " + (maxAttempts - attempts));
        guessField.setText("");
        betField.setText("");
        guessField.setEnabled(true);
        guessButton.setEnabled(true);
        playAgainButton.setEnabled(false); // Disable play again button
    }

    private void makeGuess() {
        try {
            int playerGuess = Integer.parseInt(guessField.getText());
            double betAmount = Double.parseDouble(betField.getText());
            attempts++;

            if (betAmount > playerMoney) {
                feedbackLabel.setText("You don't have enough money to place that bet!");
                return;
            }

            if (playerGuess < 1 || playerGuess > 100) {
                feedbackLabel.setText("Please enter a number between 1 and 50.");
            } else if (playerGuess == numberToGuess) {
                feedbackLabel.setText("Congratulations! You've guessed the number!");
                playerMoney += betAmount; // Win the bet
                guessField.setEnabled(false);
                guessButton.setEnabled(false);
                playAgainButton.setEnabled(true); // Enable play again button
            } else if (attempts >= maxAttempts) {
                feedbackLabel.setText("Game over! The number was: " + numberToGuess);
                playerMoney -= betAmount; // Lose the bet
                guessField.setEnabled(false);
                guessButton.setEnabled(false);
                playAgainButton.setEnabled(true); // Enable play again button
            } else {
                String hint = playerGuess < numberToGuess ? "Too low!" : "Too high!";
                feedbackLabel.setText(hint);
                attemptsLabel.setText("Attempts left: " + (maxAttempts - attempts));
                playerMoney -= betAmount; // Lose the bet for incorrect guess
            }

            moneyLabel.setText("Money: $" + playerMoney);
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Please enter valid numbers.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            hakari game = new hakari();
            game.setVisible(true);
        });
    }
}