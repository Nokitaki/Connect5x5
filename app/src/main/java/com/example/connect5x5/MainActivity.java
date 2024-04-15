package com.example.connect5x5;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView playerTurn;
    private TextView winnerMessage; // Add this line
    private boolean player1Turn = true;
    private int movesCount;
    private boolean gameIsActive = true;
    private final int[] gameState = new int[25];
    private final int[][] winningPositions = {
            {0, 1, 2}, {1, 2, 3}, {2, 3, 4},
            {5, 6, 7}, {6, 7, 8}, {7, 8, 9},
            {10, 11, 12}, {11, 12, 13}, {12, 13, 14},
            {15, 16, 17}, {16, 17, 18}, {17, 18, 19},
            {20, 21, 22}, {21, 22, 23}, {22, 23, 24},
            {0, 5, 10}, {1, 6, 11}, {2, 7, 12}, {3, 8, 13}, {4, 9, 14},
            {5, 10, 15}, {6, 11, 16}, {7, 12, 17}, {8, 13, 18}, {9, 14, 19},
            {10, 15, 20}, {11, 16, 21}, {12, 17, 22}, {13, 18, 23}, {14, 19, 24},
            {0, 6, 12}, {1, 7, 13}, {2, 8, 14}, {3, 7, 11}, {4, 8, 12},
            {5, 11, 17}, {6, 12, 18}, {7, 13, 19}, {8, 14, 20}, {9, 13, 17},
            {10, 14, 18}, {11, 15, 19}, {12, 16, 20}, {16, 12, 8}, {17, 13, 9},
            {18, 14, 10}, {19, 13, 7}, {20, 14, 8}, {21, 15, 9}, {22, 16, 10},
            {23, 17, 11}, {24, 18, 12}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerTurn = findViewById(R.id.player_turn);
        winnerMessage = findViewById(R.id.winnerMessage); // Initialize winnerMessage TextView

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        // Initialize gameState
        // 2 represents unplayed position
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        // Set up the grid and set onClick listeners
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                final int row = i;
                final int col = j;
                ImageView imageView = (ImageView) gridLayout.getChildAt(i * 5 + j);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (gameIsActive) {
                            ImageView counter = (ImageView) v;
                            int tappedCounter = Integer.parseInt(counter.getTag().toString());

                            if (gameState[tappedCounter] == 2) {
                                if (player1Turn) {
                                    counter.setImageResource(R.drawable.yellow);
                                    gameState[tappedCounter] = 1; // Player 1's move
                                } else {
                                    counter.setImageResource(R.drawable.red);
                                    gameState[tappedCounter] = 0; // Player 2's move
                                }
                                movesCount++;
                                checkForWinner();
                                player1Turn = !player1Turn;
                                playerTurn.setText(player1Turn ? "Player 1's Turn" : "Player 2's Turn");
                                if (movesCount == 25 && gameIsActive) {
                                    playerTurn.setText("It's a draw!");
                                    gameIsActive = false;
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    private void checkForWinner() {
        for (int[] winningPosition : winningPositions) {
            int pos1 = winningPosition[0];
            int pos2 = winningPosition[1];
            int pos3 = winningPosition[2];

            if (gameState[pos1] != 2 &&
                    gameState[pos1] == gameState[pos2] &&
                    gameState[pos2] == gameState[pos3]) {
                gameIsActive = false;
                String winnerMessageText = (gameState[pos1] == 1) ? "Player 1 wins!" : "Player 2 wins!";
                winnerMessage.setText(winnerMessageText); // Update the winner message
                winnerMessage.setVisibility(View.VISIBLE); // Make the winner message visible
                findViewById(R.id.playAgainLayout).setVisibility(View.VISIBLE); // Set visibility to visible
                return;
            }
        }
    }
    public void playAgain(View view) {
        resetGame(view); // Call resetGame method to reset the game
        findViewById(R.id.playAgainLayout).setVisibility(View.INVISIBLE); // Hide the playAgainLayout
    }
    public void resetGame(View view) {
        player1Turn = true;
        gameIsActive = true;
        movesCount = 0;
        playerTurn.setText("Player 1's Turn");
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }
}