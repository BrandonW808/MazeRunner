package com.example.brandonward.mazerunner;

import android.graphics.Color;
import android.os.Handler;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * Created by BrandonWard on 2018-02-28.
 */

public class Cell {
    int x;
    int y;
    int NUM_ROWS = 12;
    int NUM_COLS = 10;

    ToggleButton[][] maze;
    SolveThread control;

    private boolean isWall = false;
    private boolean isVisited = false;
    private boolean inRoute = false;

    private ArrayList<Cell> neighbours = new ArrayList<Cell> ();

    public Cell(int i, int j, ToggleButton[][] buttons, SolveThread thread){
        maze = buttons;
        control = thread;
        x = i;
        y = j;
    }

    public void setNeighbours ()
    {
        //set the neighbour cells of this cell
        neighbours = new ArrayList<Cell> ();

        if (x != 0)
        {
            addNeighbour (x-1, y);
        }

        if (y != 0)
        {
            addNeighbour (x, y-1);
        }

        if (x < NUM_COLS - 1)
        {
            addNeighbour (x+1, y);
        }

        if (y < NUM_ROWS - 1)
        {
            addNeighbour (x, y+1);
        }
    }

    private void addNeighbour (int x, int y)
    {
        Cell neighbour = control.getCell(x, y);
        if (!neighbour.isWall ())
        {
            neighbours.add (neighbour);
        }
    }

    public boolean isWall ()
    {
        return isWall;
    }

    public boolean isVisited ()
    {
        return isVisited;
    }

    public void visited ()
    {
        //set the cell as visited, or processed
        isVisited = true;
    }

    public int getX ()
    {
        return x;
    }

    public int getY ()
    {
        return y;
    }

    public void setWall(){
        isWall = true;
    }

    public Cell getAnUnvisitedNeighbour ()
    {
        //This methood returns one of the unvisited neigbours of this cell
        //if there is one.
        //This method is useful for recursively searching the maze

        if (neighbours == null)
        {
            return null;
        }

        if (neighbours.size () == 0)
        {
            return null;
        }

        for (int i = 0; i < neighbours.size (); i++)
        {
            Cell next = (Cell) neighbours.get (i);
            if (!next.isVisited ())
            {
                return next;
            }
        }

        return null;
    }

    public boolean inRoute ()
    {
        //answer whether this cell in on a path from the start of finish cell
        return inRoute;
    }

    public void setInRoute()
    {
        //set this cell to be on the path from the start cell to the finish cell
        inRoute = true;
    }
}
