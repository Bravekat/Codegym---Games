package com.codegym.games.game2048;
import com.codegym.engine.cell.*;
import java.util.*;

public class Game2048 extends Game {
    private static final int SIDE = 4;
    private int[][] gameField = new int[SIDE][SIDE];
    private boolean isGameStopped = false;
    private int score = 0;
    
    @Override
    public void initialize(){
        setScreenSize(SIDE, SIDE);
        createGame();
        
        drawScene();
    }
    
    @Override
    public void onKeyPress(Key key){
        if (!isGameStopped)
        {
            if (canUserMove())
            {
                if (key == Key.LEFT){
                    moveLeft();
                    drawScene();
                }
                else if (key == Key.RIGHT){
                    moveRight();
                    drawScene();
                }
                else if (key == Key.UP){
                    moveUp();
                    drawScene();
                }
                else if (key == Key.DOWN){
                    moveDown();
                    drawScene();
                }
                else if (key == Key.SPACE){
                    isGameStopped = !isGameStopped;
                    drawScene();
                    showMessageDialog(Color.RED,"Stopped", Color.YELLOW, 20);
                }
            }
            else
            {
                gameOver();
            }
        }
        else if (key == Key.SPACE)
        {
            isGameStopped = false;
            score = 0;
            setScore(score);
            createGame();
            drawScene();
        }
    }
    
    private void createGame(){
        gameField = new int[SIDE][SIDE];
        createNewNumber();
        createNewNumber();
        
    }
    
    private void drawScene(){
        for (int i = 0; SIDE > i; i++){
            for (int j = 0; SIDE > j; j++){
                setCellColoredNumber(i, j, gameField[j][i]);
            }
        }
    }
    
    private void createNewNumber(){
        if (getMaxTileValue() >= 2048)
        {
            win();  
        }
        int sidex = getRandomNumber(SIDE);
        int sidey = getRandomNumber(SIDE);
        int rand = getRandomNumber(10);
        if (gameField[sidex][sidey] == 0){
            if (rand == 9){
                gameField[sidex][sidey] = 4;
            }
            else {
                gameField[sidex][sidey] = 2;
            }
        }
        else {
            createNewNumber();
        }
    }
    
    private Color getColorByValue(int value){
        if (value == 2){
            return Color.PINK;
        }
        else if (value == 4){
            return Color.DARKBLUE;
        }
        else if (value == 8){
            return Color.BLUE;
        }
        else if (value == 16){
            return Color.LIGHTBLUE;
        }
        else if (value == 32){
            return Color.DARKGREEN;
        }
        else if (value == 64){
            return Color.GREEN;
        }
        else if (value == 128){
            return Color.ORANGE;
        }
        else if (value == 256){
            return Color.DARKORANGE;
        }
        else if (value == 512){
            return Color.RED;
        }
        else if (value == 1024){
            return Color.LIGHTPINK;
        }
        else if (value == 2048){
            return Color.PURPLE;
        }
        else {
            return Color.WHITE;
        }
    }
    
    private void setCellColoredNumber(int x, int y, int value){
        if (value != 0) {
            setCellValueEx(x, y, getColorByValue(value), Integer.toString(value));
        } 
        else 
        {
            setCellValueEx(x, y, getColorByValue(value), "");
        }
    }
    
    private boolean compressRow(int[] row){
        int temp = 0;
        boolean shifted = false;
            for(int a=0; a < SIDE - 1 ; a++){
                for(int b = 0; b < SIDE - 1 ; b++){
                    if(row[b] == 0 && row[b+1] !=0){
                        shifted = true;
                        for( int c=b ; c < SIDE-1 ; c++){
                        temp = row[c+1];
                        row[c+1] = row[c];
                        row[c] = temp;
                        }
                    }
                }
            }
        return shifted;   
    }
    
    private boolean mergeRow(int[] row){
    boolean moved = false;
    for (int i=0; i< row.length-1;i++){
        if ((row[i] == row[i+1])&&(row[i]!=0)){
            row[i] = 2*row[i];
            score += row[i];
            setScore(score);
            row[i+1] = 0;
            return true;
            }
        }
        return moved;
    }
    
    private void moveLeft(){
        boolean counter = false;
        
        for(int[] row : gameField) {
            
            boolean compressed = compressRow(row);
            boolean merged = mergeRow(row);
            
            if((merged || compressed) && !counter) {
                
                createNewNumber();
                counter = true;
            }
            compressed = compressRow(row);
        }
    }
    private void moveRight(){
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
    }
    private void moveUp(){
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
    }
    private void moveDown(){
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
    }
    private void rotateClockwise(){
        //int[][] newGameField = new int [SIDE][SIDE];
        for (int i = 0; i < SIDE / 2; i++)
        {
            for (int j = i; j < SIDE - i - 1; j++)
            {
                int temp = gameField[i][j];
                gameField[i][j] = gameField[SIDE - 1 - j][i];
                gameField[SIDE - 1 - j][i] = gameField[SIDE - 1 - i][SIDE - 1 - j];
                gameField[SIDE - 1 - i][SIDE - 1 - j] = gameField[j][SIDE - 1 - i];
                gameField[j][SIDE - 1 - i] = temp;
            }
        }
    }
    private int getMaxTileValue(){
        int highestValue = 0;
        for (int i = 0; i < SIDE; i++)
        {
            for (int j = 0; j < SIDE; j++)
            {
                if (gameField[i][j] > highestValue)
                {
                    highestValue = gameField[i][j];
                }
            }
        }
        return highestValue;
    }
    private void win(){
        isGameStopped = true;
        showMessageDialog(Color.GREEN,"you're Winnah!", Color.YELLOW, 20);
    }
    
    private boolean canUserMove()
    {
        int sameNumber = 0;
        for (int i = 0; i < SIDE; i++)
        {
            for (int j = 0; j < SIDE; j++)
            {
                sameNumber = gameField[i][j];
                if (i < 3)
                {
                    if (sameNumber == gameField[i+1][j])
                        {
                            return true;
                        }
                }
                if (j < 3)
                {
                    if (sameNumber == gameField[i][j +1])
                    {
                        return true;
                    }

                }
                if (gameField[i][j] == 0)
                {
                    return true;
                }
            }
        }
        return false;
    }
    private void gameOver()
    {
        isGameStopped = true;
        showMessageDialog(Color.RED,"GAME OVEW", Color.YELLOW, 20);
    }
}
