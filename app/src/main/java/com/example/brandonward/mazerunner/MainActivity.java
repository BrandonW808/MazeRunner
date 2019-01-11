package com.example.brandonward.mazerunner;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import static android.content.ContentValues.TAG;
import static java.lang.Thread.State.TERMINATED;


public class MainActivity extends AppCompatActivity {

    int NUM_ROWS = 10;
    int NUM_COLS = 12;

    String id;
    ToggleButton[][] buttons;
    Boolean isDestinationSet = false;
    Boolean isStartSet = false;
    Button solveBtn;
    int[][] buttonIDs;

    SolveThread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        buttons = new ToggleButton[10][12];

        solveBtn = (Button) findViewById(R.id.submit);

        for (int i=1; i<NUM_ROWS+1; i++){
            for (int j=1; j<NUM_COLS+1; j++){
                String buttonID = "b" + i + j;
                String text = "" + i;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i-1][j-1] = (ToggleButton) findViewById(resID);
                buttons[i - 1][j - 1].setBackgroundColor(Color.WHITE);
                buttons[i - 1][j - 1].setText(text);
                buttons[i - 1][j - 1].setTextOff(text);
            }
        }

        for (int i=1; i<NUM_ROWS+1; i++){
            for (int j=1; j<NUM_COLS+1; j++){
                int finalJ = j;
                int finalI = i;
                buttons[i-1][j-1].setOnClickListener(v -> {
                    int k = finalI;
                    int l = finalJ;
                    String text = "" + k;
                    if (isStartSet != true && buttons[k - 1][l - 1].getTextOn() != "D"){
                        buttons[k - 1][l - 1].setBackgroundColor(Color.GREEN);
                        buttons[k - 1][l - 1].setTextOn("S");
                        buttons[k - 1][l - 1].setText("S");
                        isStartSet = true;
                    }else if (isDestinationSet != true && buttons[k - 1][l - 1].getTextOn() != "S") {
                        buttons[k - 1][l - 1].setBackgroundColor(Color.RED);
                        buttons[k - 1][l - 1].setTextOn("D");
                        buttons[k - 1][l - 1].setText("D");
                        isDestinationSet = true;
                    }else if (buttons[k - 1][l - 1].getTextOn() == "S"){
                        isStartSet = false;
                        buttons[k - 1][l - 1].setBackgroundColor(Color.WHITE);
                        buttons[k - 1][l - 1].setText(text);
                        buttons[k - 1][l - 1].setTextOn(text);
                    }else if (buttons[k - 1][l - 1].getTextOn() == "D"){
                        isDestinationSet = false;
                        buttons[k - 1][l - 1].setBackgroundColor(Color.WHITE);
                        buttons[k - 1][l - 1].setText(text);
                        buttons[k - 1][l - 1].setTextOn(text);
                    }else if (buttons[k-1][l-1].isChecked() && isDestinationSet == true && isStartSet == true){
                        buttons[k - 1][l - 1].setBackgroundColor(Color.BLACK);
                    }else {
                        buttons[k - 1][l - 1].setBackgroundColor(Color.WHITE);
                        buttons[k - 1][l - 1].setText(text);
                    }
                });
            }
        }

        solveBtn.setOnClickListener(v -> {

            for (int i=1; i<NUM_ROWS+1; i++){
                for (int j=1; j<NUM_COLS+1; j++){
                    buttons[i-1][j-1].setEnabled(false);
                    if(buttons[i - 1][j - 1].getText() != "D") {
                        buttons[i - 1][j - 1].setText("");
                    }
                }
            }
            mThread = new SolveThread(buttons);

            try {
                mThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!mThread.getPathExisting()){
                Toast.makeText(MainActivity.this,
                        R.string.no_path,
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this,
                        R.string.path,
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

}
