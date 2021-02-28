package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // use an array to store state of game. 2 represents empty
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    // create an array of arrays of winning combos
    int[][] winningPos = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7},
            {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    // yellow will be 0. red will be 1.
    int activePlayer = 0;

    String winner = "";

    int countDropped = 0;

    boolean gameActive = true;

    public void dropin(View view) {

        // can determine which square was tapped on by assigning the view
        // that is taken in as an argument to a variable of type ImageView
        ImageView counter = (ImageView) view;

        int tag = Integer.parseInt(counter.getTag().toString());

        // can only tap on square if its empty
        if (gameState[tag] == 2 && gameActive) {


            // change gamestate so we know which counter is in which position
            gameState[tag] = activePlayer;
            // move it off screen
            counter.setTranslationY(-1500);

            if (activePlayer == 0) {

                // set image view (which is initially blank - set it as blank
                // by removing the src of the image) to a resource from drawable.
                counter.setImageResource(R.drawable.yellow);

                // now that yellow has played, switch it to reds turn
                activePlayer = 1;

                countDropped+=1;
            } else {

                counter.setImageResource(R.drawable.red);


                // now that red has played, switch it to yellows turn
                activePlayer = 0;
                countDropped+=1;
            }

            counter.animate().translationYBy(1500).setDuration(300);

            // loop through all winning positions and check to see if theyve all
            // got same value and that theyre not equal to 2
            // // (if theyre all empty, no ones won)

            for (int[] values : winningPos) {

                if (gameState[values[0]] == gameState[values[1]] &&
                        gameState[values[1]] == gameState[values[2]]
                        && gameState[values[0]] != 2) {

                    gameActive = false;

                    if (gameState[values[0]] == 1) {
                        winner = "Red";
                    } else {
                        winner = "Yellow";
                    }

                    Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                    TextView winnerTextView = (TextView) findViewById(R.id.textView);

                    winnerTextView.setText(winner + " has won!");

                    winnerTextView.setVisibility(View.VISIBLE);
                    playAgainButton.setVisibility(View.VISIBLE);
                }
            }if(countDropped == 9 && winner == ""){
                gameActive = false;
                Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                TextView winnerTextView = (TextView) findViewById(R.id.textView);
                winnerTextView.setText("Drawn Match!");
                winnerTextView.setVisibility(View.VISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }

    }

    public void playAgain(View view) {
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        TextView winnerTextView = (TextView) findViewById(R.id.textView);

        winnerTextView.setText(winner + " has won!");

        winnerTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);

            // remove image src
            counter.setImageDrawable(null);

        }

        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }

        // yellow will be 0. red will be 1.
        activePlayer = 0;

        gameActive = true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}