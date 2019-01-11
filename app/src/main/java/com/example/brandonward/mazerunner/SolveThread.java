package com.example.brandonward.mazerunner;

import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by BrandonWard on 2018-02-27.
 */

public class SolveThread extends Thread {
    int NUM_ROWS = 10;
    int NUM_COLS = 12;
    Cell destination;
    Cell start;

    private boolean pathExists = true;

    private ToggleButton[][] maze;
    private Cell [][] cells;

    public boolean getPathExisting() { return pathExists; }

    public SolveThread(ToggleButton[][] buttons) {
        cells = new Cell[NUM_ROWS][NUM_COLS];
        maze = buttons;
        for (int i=1; i<NUM_ROWS+1; i++){
            for (int j=1; j<NUM_COLS+1; j++){
                cells[i-1][j-1] = new Cell(i-1, j-1, maze, this);
                maze[i-1][j-1].setEnabled(false);
                if(maze[i - 1][j - 1].getTextOn() == "D") {
                    destination = cells[i - 1][j - 1];
                }else if (maze[i - 1][j - 1].getTextOn() == "S"){
                    start = cells[i - 1][j - 1];
                }else if (maze[i - 1][j - 1].isChecked()){
                    cells[i-1][j-1].setWall();
                }
            }
        }

        for (int i=1; i<NUM_ROWS+1; i++){
            for (int j=1; j<NUM_COLS+1; j++){
                cells [i-1][j-1].setNeighbours();
            }
        }

        pathExists = findPathFrom(start);

    }

    public Cell getCell(int i, int j){
        return cells[i][j];
    }

    public void findPathFrom (ToggleButton start){
        start.setChecked(true);

    }

    public boolean findPathFrom (Cell aCell)
    {

        //Recursively determine if we can reach the finish cell from cell aCell
        //This method answers whether a route was found, and sets the cells along the
        //route, but invoking their setInRoute() method, so the route will be shown (in green)
        //when the maze is drawn

        aCell.visited(); //mark aCell as being visisted (so the problem will get smaller)

        //Basis Case
        //if aCell is the finish cell set it to be in the route and return true (we are done)

        //MISSING CODE --basis case
        if (aCell == destination)
        {
            aCell.setInRoute();
            return true;
        }
        //END BASIS CASE

        //Recursion
        Cell next = aCell.getAnUnvisitedNeighbour(); //get an unvisited neighbour of aCell

        //Check each of aCell's unvisited neigbours. If you find a neighbour n for which
        //findPathFrom(n) is true, set the neighbour to be in the route and return true (we are done).
        //otherwise check the remaining unvisited neighbours.

        //If no unvisited neighbour can be found that allows a path, return false ---there is no path
        //possible.

        //MISSING CODE ----recursive case
        while (next != null)
        {


            if (findPathFrom (next)) {
                //aCell.setNextCell (next);
                aCell.setInRoute();
                maze[aCell.getX()][aCell.getY()].setBackgroundColor(Color.BLUE);
                return true;
            }

            next = aCell.getAnUnvisitedNeighbour();
        }

        //END ---recursive case

        pathExists = false;
        Log.i(TAG, "No Path!");
        return false;

    }
}
