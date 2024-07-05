package spring2024.cs477.project1_thuynh31;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayingAgainstPartner extends AppCompatActivity {

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    // The grid is used to navigate through the "Connect 3" matrix (aka the image views)
    ImageView[][] grid = new ImageView[4][4];
    // The counter variables are used to help keep track of how many items are in each column
    int col1Counter;
    int col2Counter;
    int col3Counter;
    int col4Counter;
    // The tokens represent different players or the default board. Player 1 is green, Player 2 is red, or no one goes (yellow)
    int GreenToken;
    int RedToken;
    int YellowToken;
    // This boolean is used to determine if player 1 is playing or player 2. Player 1 only plays when it is set to true.
    Boolean Player1;
    TextView playerChoice;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_against_partner);

        button1 = findViewById(R.id.Button1);
        button2 = findViewById(R.id.Button2);
        button3 = findViewById(R.id.Button3);
        button4 = findViewById(R.id.Button4);

        // Assigning each ImageView to an item in the grid to easily access the ImageView
        grid[0][0] = findViewById(R.id.imageButton1);
        grid[0][1] = findViewById(R.id.imageButton2);
        grid[0][2] = findViewById(R.id.imageButton3);
        grid[0][3] = findViewById(R.id.imageButton4);
        grid[1][0] = findViewById(R.id.imageButton5);
        grid[1][1] = findViewById(R.id.imageButton6);
        grid[1][2] = findViewById(R.id.imageButton7);
        grid[1][3] = findViewById(R.id.imageButton8);
        grid[2][0] = findViewById(R.id.imageButton9);
        grid[2][1] = findViewById(R.id.imageButton10);
        grid[2][2] = findViewById(R.id.imageButton11);
        grid[2][3] = findViewById(R.id.imageButton12);
        grid[3][0] = findViewById(R.id.imageButton13);
        grid[3][1] = findViewById(R.id.imageButton14);
        grid[3][2] = findViewById(R.id.imageButton15);
        grid[3][3] = findViewById(R.id.imageButton16);

        // Setting all the tags for each image view to -1
        // This will be use determine which player (Player 1/ Player 2) has gone
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                grid[i][j].setTag(-1);
            }
        }

        col1Counter = 0;
        col2Counter = 0;
        col3Counter = 0;
        col4Counter = 0;

        GreenToken = R.drawable.greenstar;
        RedToken = R.drawable.redstar;
        YellowToken = R.drawable.yellowstar;

        Player1 = true;

        playerChoice = findViewById(R.id.playerChoice);
    }

    // A turn represents when a player goes. Since each player makes their own moves, to distinguish from player 1 and player 2, the boolean is flipped to true and false to indicate who is playing.
    // When one user clicks on a button, each button will calculate where the token is "dropped" via the column counter for each column.
    // The tag for that specific area (ImageView) is set to 1 for Player 1 and 2 for Player 2, to represent that the player has dropped the token in that column
    // If the column is full after a player has gone, then the button is disabled, preventing the button from being used again.
    // After a player turn, the findConnect3() is called, which checks if the player has created a match of 3 tokens in a row. If so, display the alert message.
    // If not, the program waits until another button is clicked again.
    // Lastly, if all the columns are filled, there was a tie and display the alert message. There is no winner :(
    public void Turn(View v) {
        int buttonNum = v.getId();
        int rowNum = 0;
        int colNum = 0;

        // Button 1
        if (buttonNum == R.id.Button1) {
            if (col1Counter < 4) {
                if(Player1) {
                    grid[3 - col1Counter][0].setImageResource(GreenToken);
                    grid[3 - col1Counter][0].setTag(1);
                }
                else {
                    grid[3 - col1Counter][0].setImageResource(RedToken);
                    grid[3 - col1Counter][0].setTag(2);
                }
                rowNum = 3 - col1Counter;
                colNum = 0;
                col1Counter++;
                if (col1Counter == 4) {
                    button1.setEnabled(false);
                }
            }
            // Button 2
        } else if (buttonNum == R.id.Button2) {
            if (col2Counter < 4) {
                if(Player1) {
                    grid[3 - col2Counter][1].setImageResource(GreenToken);
                    grid[3 - col2Counter][1].setTag(1);
                }
                else {
                    grid[3 - col2Counter][1].setImageResource(RedToken);
                    grid[3 - col2Counter][1].setTag(2);
                }
                rowNum = 3 - col2Counter;
                colNum = 1;
                col2Counter++;
                if (col2Counter == 4) {
                    button2.setEnabled(false);
                }
            }
            // Button 3
        } else if (buttonNum == R.id.Button3) {
            if (col3Counter < 4) {
                if(Player1) {
                    grid[3 - col3Counter][2].setImageResource(GreenToken);
                    grid[3 - col3Counter][2].setTag(1);
                }
                else {
                    grid[3 - col3Counter][2].setImageResource(RedToken);
                    grid[3 - col3Counter][2].setTag(2);
                }
                rowNum = 3 - col3Counter;
                colNum = 2;
                col3Counter++;
                if (col3Counter == 4) {
                    button3.setEnabled(false);
                }
            }
            // Button 4
        } else if (buttonNum == R.id.Button4) {
            if (col4Counter < 4) {
                if(Player1) {
                    grid[3 - col4Counter][3].setImageResource(GreenToken);
                    grid[3 - col4Counter][3].setTag(1);
                }
                else {
                    grid[3 - col4Counter][3].setImageResource(RedToken);
                    grid[3 - col4Counter][3].setTag(2);
                }
                rowNum = 3 - col4Counter;
                colNum = 3;
                col4Counter++;
                if (col4Counter == 4) {
                    button4.setEnabled(false);
                }
            }
        }
        // Check if Player 1 made any matches
        if(Player1) {
            playerChoice.setText("Player 1 chose Button " + (colNum+1));
            if (findConnect3(rowNum, colNum, 1)) {
                alertView("Player 1 Won!! Wooo Hoooo!!");

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        grid[i][j].setTag(-1);
                    }
                }
            }
            // This is where Player 1's turn has officially ended, therefore next time the button is clicked, it will be player 2
            Player1 = false;
        }
        // Check if Player 2 made any matches
        else {
            playerChoice.setText("Player 2 chose Button " + (colNum+1));
            if (findConnect3(rowNum, colNum, 2)) {
                alertView("Player 2 Won!! Wooo Hoooo!!");

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        grid[i][j].setTag(-1);
                    }
                }
            }
            //Player 2's turn has officially ended, therefore making the next time a button is clicked to be Player 1
            Player1 = true;
        }

        // A tie was reached, no winners were found.
        if(col1Counter == 4 && col2Counter == 4 && col3Counter == 4 && col4Counter == 4) {
            alertView("It was a TIE, better get a suit");

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    grid[i][j].setTag(-1);
                }
            }
        }
    }


    // findConnect3() finds if there are any matches that are 3 in a row.
    // It first starts off by finding any horizontal matches
    // Then it finds any vertical matches
    // Then the diagonals
    // If no matches were found, it returns false
    public boolean findConnect3(int row, int col, int tag) {
        // Check horizontal Connect 3.
        // If there exist a box on the left and right of the item and the tags are correct, then there is a Connect 3
        if (((col - 1) > -1) && ((col + 1) < 4)) {
            if ((int) grid[row][col - 1].getTag() == tag && (int) grid[row][col + 1].getTag() == tag) {
                return true;
            }
        }
        // If there exist two boxes on the left of the item, and the tags are correct, then there is a Connect 3
        if (((col - 1) > -1) && ((col - 2) > -1)) {
            if ((int) grid[row][col - 1].getTag() == tag && (int) grid[row][col - 2].getTag() == tag) {
                return true;
            }
        }
        // If there exist two boxes on the right of the item, and the tags are correct, then there is a Connect 3
        if (((col + 1) < 4) && ((col + 2) < 4)) {
            if ((int) grid[row][col + 1].getTag() == tag && (int) grid[row][col + 2].getTag() == tag) {
                return true;
            }
        }

        // Check vertical Connect 3
        // If there exist a box above and below the item, and the tags are correct, then there is a Connect 3
        if (((row - 1) > -1) && ((row + 1) < 4)) {
            if ((int) grid[row - 1][col].getTag() == tag && (int) grid[row + 1][col].getTag() == tag) {
                return true;
            }
        }
        // If there exist 2 boxes above the item, and the tags are correct, then there is a Connect 3
        if (((row - 1) > -1) && ((row - 2) > -1)) {
            if ((int) grid[row - 1][col].getTag() == tag && (int) grid[row - 2][col].getTag() == tag) {
                return true;
            }
        }
        // If there exist 2 boxes below the item, and the tags are correct, then there is a Connect 3
        if (((row + 1) < 4) && ((row + 2) < 4)) {
            if ((int) grid[row + 1][col].getTag() == tag && (int) grid[row + 2][col].getTag() == tag) {
                return true;
            }
        }

        // Check diagonal Connect 3, from bottom left to top right
        // If there exist one box to the bottom left of the item, and one box to the top right of the item, and the tags are correct, then there is a Connect 3
        if (((row + 1) < 4) && ((col - 1) > -1) && ((row - 1) > -1) && ((col + 1) < 4)) {
            if ((int) grid[row + 1][col - 1].getTag() == tag && (int) grid[row - 1][col + 1].getTag() == tag) {
                return true;
            }
        }
        // If there exist two boxes that are diagonal (the bottom left) to the item, and the tags are correct, then there is a Connect 3
        if (((row + 1) < 4) && ((col - 1) > -1) && ((row + 2) < 4) && ((col - 2) > -1)) {
            if ((int) grid[row + 1][col - 1].getTag() == tag && (int) grid[row + 2][col - 2].getTag() == tag) {
                return true;
            }
        }
        // If there exist two boxes that are diagonal (the top right) to the item, and the tags are correct, then there is a Connect 3
        if (((row - 1) > -1) && ((col + 1) < 4) && ((row - 2) > -1) && ((col + 2) < 4)) {
            if ((int) grid[row - 1][col + 1].getTag() == tag && (int) grid[row - 2][col + 2].getTag() == tag) {
                return true;
            }
        }

        // Check diagonal Connect 3, from top left to bottom right
        // If there exist one box that is to the top left of the item, and one box to the bottom right of the item, and the tags are correct, then there is Connect 3
        if (((row - 1) > -1) && ((col - 1) > -1) && ((row + 1) < 4) && ((col + 1) < 4)) {
            if ((int) grid[row - 1][col - 1].getTag() == tag && (int) grid[row + 1][col + 1].getTag() == tag) {
                return true;
            }
        }
        // If there exist two boxes that are diagonal (the top left) to the item , and the tags match, then there is Connect 3
        if (((row - 1) > -1) && ((col - 1) > -1) && ((row - 2) > -1) && ((col - 2) > -1)) {
            if ((int) grid[row - 1][col - 1].getTag() == tag && (int) grid[row - 2][col - 2].getTag() == tag) {
                return true;
            }
        }
        // If there exist two boxes that are diagonal (the bottom right) to the item, and the tags match, then there is Connect 3.
        if (((row + 1) < 4) && ((col + 1) < 4) && ((row + 2) < 4) && ((col + 2) < 4)) {
            if ((int) grid[row + 1][col + 1].getTag() == tag && (int) grid[row + 2][col + 2].getTag() == tag) {
                return true;
            }
        }
        // No matches were found, there was not a Connect 3, continue playing game
        return false;
    }

    // alertView displays a message whenever someone has won, or there was a tie
    // The user is then presented with the option to play again or stop playing altogether.
    private void alertView(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(PlayingAgainstPartner.this);
        dialog.setTitle(message)
                .setIcon(R.drawable.ic_launcher_background)
                .setCancelable(false)
                .setMessage("Would you like to play again?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    // If the no button was pressed, return to starter page
                    public void onClick(DialogInterface dialoginterface, int i) {
                        finish();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    // If the yes button was pressed, reset everything back to the original board
                    // This includes the tags, the Yellow Tokens, the counters, and the buttons are all enabled again
                    public void onClick(DialogInterface dialoginterface, int i) {
                        for (int k = 0; k < 4; k++) {
                            for (int j = 0; j < 4; j++) {
                                grid[k][j].setImageResource(YellowToken);
                                grid[k][j].setTag(-1);
                            }
                        }
                        col1Counter = 0;
                        col2Counter = 0;
                        col3Counter = 0;
                        col4Counter = 0;

                        button1.setEnabled(true);
                        button2.setEnabled(true);
                        button3.setEnabled(true);
                        button4.setEnabled(true);

                        Player1 = true;
                    }
                }).show();
    }

}