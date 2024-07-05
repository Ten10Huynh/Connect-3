package spring2024.cs477.project1_thuynh31;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class PlayingAgainstComputer extends AppCompatActivity {

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
    // The tokens represent one a player goes (green), or the computer goes (red), or no one goes (yellow)
    int GreenToken;
    int RedToken;
    int YellowToken;
    TextView choiceMade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_against_computer);

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
        // This will be use determine which player (person/computer) has gone
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

        choiceMade = findViewById(R.id.choiceMade);
    }

    // A turn represents when a player goes, and then the computer. It is supposed to represent when 1 round has passed.
    // When the user clicks on a button, each button will calculate where the token is "dropped" via the column counter for each column.
    // The tag for that specific area (ImageView) is set to 1, to represent that the player has dropped the token in that area
    // If the column is full after a player has gone, then the button is disabled, preventing the button from being used again.
    // After a player turn, the findConnect3() is called, which checks if the player has created a match of 3 tokens in a row. If so, display the alert message.
    // If not, the computer shall go on its turn. The computerTurn is done via the computerTurn().
    // After the computer has gone, findConnect3() will be called again to check if the computer has created a match of 3 tokens in a row. If so, display the alert message.
    // If no match was found, then do nothing until the buttons are clicked again.
    // Lastly, if all the columns are filled, there was a tie and display the alert message
    public void Turn(View v) {
        int buttonNum = v.getId();
        // rowNum is used in findConnect3() to determine where the inserted token is
        int rowNum = 0;
        // colNum is used in findConnect3() to determine where the inserted token is
        int colNum = 0;
        int computerCol = 0;
        int computerRow = 0;

        // Button 1
        if (buttonNum == R.id.Button1) {
            if (col1Counter < 4) {
                grid[3 - col1Counter][0].setImageResource(GreenToken);
                grid[3 - col1Counter][0].setTag(1);
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
                grid[3 - col2Counter][1].setImageResource(GreenToken);
                grid[3 - col2Counter][1].setTag(1);
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
                grid[3 - col3Counter][2].setImageResource(GreenToken);
                grid[3 - col3Counter][2].setTag(1);
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
                grid[3 - col4Counter][3].setImageResource(GreenToken);
                grid[3 - col4Counter][3].setTag(1);
                rowNum = 3 - col4Counter;
                colNum = 3;
                col4Counter++;
                if (col4Counter == 4) {
                    button4.setEnabled(false);
                }
            }
        }

        // Check if the player has made any matches. If so, display message with title "You Won!! Wooo Hooo!".
        if (findConnect3(rowNum, colNum, 1)) {
            alertView("You Won!! Wooo Hoooo!!");

            // After a win, this resets each tag to -1, to prevent the computer from accidentally winning
            // This is due to the fact that the computer runs simultaneously with the findConnect3.
            for(int i = 0; i < 4; i++) {
                for(int j = 0; j < 4; j++) {
                   grid[i][j].setTag(-1);
                }
            }
        }

        // No matches were found, it is the computer's time to go
        computerCol = computerTurn();

        choiceMade.setText("You chose " + (colNum+1) + ". The computer chose " + (computerCol + 1) + ".");

        // These series of if statements are used to determine what row the computer token was inserted to.
        // The 4 is specifically used because 3 - the number of items in a column represent the next available space in that column.
        // Since after each "drop", the counter gets incremented, to find where the computer token was "dropped", you subtract one from the column counter
        if(computerCol == 0) {
            computerRow = 4 - col1Counter ;
        }
        else if(computerCol == 1) {
            computerRow = 4 - col2Counter;
        }
        else if(computerCol == 2) {
            computerRow = 4 - col3Counter;
        }
        else {
            computerRow = 4 - col4Counter;
        }

        // Check if the computer has made any matches. If so, display the message with title "The Computer Won! Better luck next time!"
        if(findConnect3(computerRow, computerCol, 2)) {
            alertView("The Computer Won! Better luck next time! ");

            // To make sure no one else can win.
            for(int i = 0; i < 4; i++) {
                for(int j = 0; j < 4; j++) {
                    grid[i][j].setTag(-1);
                }
            }
        }

        // No winners were found, and the grid is filled up
        if(col1Counter == 4 && col2Counter == 4 && col3Counter == 4 && col4Counter == 4) {
            alertView("It was a TIE, better get a suit");

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    grid[i][j].setTag(-1);
                }
            }
        }
    }

    // computerTurn() is very similar to Turn, the only difference is that if the column selected is full, then it runs computerTurn again to find a column that is not full.
    // computerTurn also sets the tag to be 2
    // The method also returns an int of the column number used. This is used in turn to calculate what box was the token inserted into, and then sent into findConnect3() to find matches.
    public int computerTurn() {
        Random rand = new Random();
        int randomNum = rand.nextInt(4);

        if (randomNum == 0) {
            if (col1Counter < 4) {
                grid[3 - col1Counter][0].setImageResource(RedToken);
                grid[3 - col1Counter][0].setTag(2);
                col1Counter++;
                if (col1Counter == 4) {
                    button1.setEnabled(false);
                }
            } else {
                computerTurn();
            }
        } else if (randomNum == 1) {
            if (col2Counter < 4) {
                grid[3 - col2Counter][1].setImageResource(RedToken);
                grid[3 - col2Counter][1].setTag(2);
                col2Counter++;
                if (col2Counter == 4) {
                    button2.setEnabled(false);
                }
            } else {
                computerTurn();
            }
        } else if (randomNum == 2) {
            if (col3Counter < 4) {
                grid[3 - col3Counter][2].setImageResource(RedToken);
                grid[3 - col3Counter][2].setTag(2);
                col3Counter++;
                if (col3Counter == 4) {
                    button3.setEnabled(false);
                }
            } else {
                computerTurn();
            }
        } else {
            if (col4Counter < 4) {
                grid[3 - col4Counter][3].setImageResource(RedToken);
                grid[3 - col4Counter][3].setTag(2);
                col4Counter++;
                if (col4Counter == 4) {
                    button4.setEnabled(false);
                }
            } else {
                computerTurn();
            }
        }
        return randomNum;
    }

    // findConnect3() finds if there are any matches that are 3 in a row.
    // It first starts off by finding any horizontal matches
    // Then it finds any vertical matches
    // Then the diagonals
    // If no matches were found, it returns false
    public boolean findConnect3(int row, int col, int tag) {
        // Check horizontal Connect 3.
        // If there exist a box on the left and right of the item and the tags are correct, then there is a Connect 3
        if(((col - 1) > -1) && ((col + 1) < 4)) {
            if((int) grid[row][col - 1].getTag() == tag && (int) grid[row][col + 1].getTag() == tag) {
                return true;
            }
        }
        // If there exist two boxes on the left of the item, and the tags are correct, then there is a Connect 3
        if(((col - 1) > -1) && ((col - 2) > -1)) {
            if((int) grid[row][col - 1].getTag() == tag && (int) grid[row][col - 2].getTag() == tag) {
                return true;
            }
        }
        // If there exist two boxes on the right of the item, and the tags are correct, then there is a Connect 3
        if(((col + 1) < 4) && ((col + 2) < 4)) {
            if((int) grid[row][col + 1].getTag() == tag && (int) grid[row][col + 2].getTag() == tag) {
                return true;
            }
        }

        // Check vertical Connect 3
        // If there exist a box above and below the item, and the tags are correct, then there is a Connect 3
        if(((row - 1) > -1) && ((row + 1) < 4)) {
            if((int) grid[row - 1][col].getTag() == tag && (int) grid[row + 1][col].getTag() == tag) {
                return true;
            }
        }
        // If there exist 2 boxes above the item, and the tags are correct, then there is a Connect 3
        if(((row - 1) > -1) && ((row - 2) > -1)) {
            if((int) grid[row - 1][col].getTag() == tag && (int) grid[row - 2][col].getTag() == tag) {
                return true;
            }
        }
        // If there exist 2 boxes below the item, and the tags are correct, then there is a Connect 3
        if(((row + 1) < 4) && ((row + 2) < 4)) {
            if((int) grid[row + 1][col].getTag() == tag && (int) grid[row + 2][col].getTag() == tag) {
                return true;
            }
        }

        // Check diagonal Connect 3, from bottom left to top right
        // If there exist one box to the bottom left of the item, and one box to the top right of the item, and the tags are correct, then there is a Connect 3
        if(((row + 1) < 4) && ((col - 1) > -1) && ((row - 1) > -1) && ((col + 1) < 4)) {
            if((int) grid[row + 1][col - 1].getTag() == tag && (int) grid[row - 1][col + 1].getTag() == tag) {
                return true;
            }
        }
        // If there exist two boxes that are diagonal (the bottom left) to the item, and the tags are correct, then there is a Connect 3
        if(((row + 1) < 4) && ((col - 1) > -1) && ((row + 2) < 4) && ((col - 2) > -1)) {
            if((int) grid[row + 1][col - 1].getTag() == tag && (int) grid[row + 2][col - 2].getTag() == tag) {
                return true;
            }
        }
        // If there exist two boxes that are diagonal (the top right) to the item, and the tags are correct, then there is a Connect 3
        if(((row - 1) > -1) && ((col + 1) < 4) && ((row - 2) > -1) && ((col + 2) < 4)) {
            if((int) grid[row - 1][col + 1].getTag() == tag && (int) grid[row - 2][col + 2].getTag() == tag) {
                return true;
            }
        }

        // Check diagonal Connect 3, from top left to bottom right
        // If there exist one box that is to the top left of the item, and one box to the bottom right of the item, and the tags are correct, then there is Connect 3
        if(((row - 1) > -1) && ((col - 1) > -1) && ((row + 1) < 4) && ((col + 1) < 4)) {
            if((int) grid[row - 1][col - 1].getTag() == tag && (int) grid[row + 1][col + 1].getTag() == tag) {
                return true;
            }
        }
        // If there exist two boxes that are diagonal (the top left) to the item , and the tags match, then there is Connect 3
        if(((row - 1) > -1) && ((col - 1) > -1) && ((row - 2) > -1) && ((col - 2) > -1)) {
            if((int) grid[row - 1][col - 1].getTag() == tag && (int) grid[row - 2][col - 2].getTag() == tag) {
                return true;
            }
        }
        // If there exist two boxes that are diagonal (the bottom right) to the item, and the tags match, then there is Connect 3.
        if(((row + 1) < 4) && ((col + 1) < 4) && ((row + 2) < 4) && ((col + 2) < 4)) {
            if((int) grid[row + 1][col + 1].getTag() == tag && (int) grid[row + 2][col + 2].getTag() == tag) {
                return true;
            }
        }

        //If there are no matches for Connect 3, then continue playing game
        return false;
    }

    // alertView displays a message whenever someone has won, or there was a tie
    // The user is then presented with the option to play again or stop playing altogether.
    private void alertView(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(PlayingAgainstComputer.this);
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
                    }
                }).show();
    }
}
