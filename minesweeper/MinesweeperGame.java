package com.codegym.games.minesweeper;

import com.codegym.engine.cell.Color;
import com.codegym.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {
    private static final int SIDE = 9;
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";
    
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField;
    private int countFlags;
    private int countClosedTiles = SIDE * SIDE;
    private int score;
    private boolean isGameStopped;

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
    }
    
    @Override
    public void onMouseLeftClick(int x, int y){
        if (isGameStopped){
            restart();
            return;
        }
        this.openTile(x, y);
    }
    
    @Override
    public void onMouseRightClick(int x, int y){
        markTile(x, y);
    }

    private void createGame() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                boolean isMine = getRandomNumber(10) < 1;
                if (isMine) {
                    countMinesOnField++;
                }
                gameField[y][x] = new GameObject(x, y, isMine);
                setCellColor(x, y, Color.ORANGE);
                setCellValue(x, y, "");

            }
        }
        countFlags = countMinesOnField;
        countMineNeighbors();
    }

    private List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<>();
        for (int y = gameObject.y - 1; y <= gameObject.y + 1; y++) {
            for (int x = gameObject.x - 1; x <= gameObject.x + 1; x++) {
                if (y < 0 || y >= SIDE) {
                    continue;
                }
                if (x < 0 || x >= SIDE) {
                    continue;
                }
                if (gameField[y][x] == gameObject) {
                    continue;
                }
                result.add(gameField[y][x]);
            }
        }
        return result;
    }
    
    private void countMineNeighbors() {
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                if (!gameField[i][j].isMine) {
                    for(GameObject g:getNeighbors(gameField[i][j])){
                        if(g.isMine){
                            gameField[i][j].countMineNeighbors++ ;
                        }
                    }
                }
            }
        }
    }
    
    private void openTile(int x, int y){
        if (gameField[y][x].isOpen || gameField[y][x].isFlag || isGameStopped){
            return;
        }
        
        gameField[y][x].isOpen = true;
        countClosedTiles--;
        setCellColor(x, y, Color.GREEN);
        
        if (gameField[y][x].isOpen == true && gameField[y][x].isMine == false){
            score = score + 5;
            setScore(score);
        }
        
        if (countClosedTiles == countMinesOnField && gameField[y][x].isMine == false){
            win();
        }
        
        if (!gameField[y][x].isMine && gameField[y][x].countMineNeighbors == 0) {
            setCellValue(gameField[y][x].x, gameField[y][x].y, "");
        
        }
        
        if (gameField[y][x].isMine) {
        setCellValueEx(x, y, Color.RED, MINE);
        gameOver();
        } 
        
        else if (gameField[y][x].countMineNeighbors == 0 && !gameField[y][x].isMine) {
            for (GameObject gameObject : getNeighbors(gameField[y][x])) {
                if (!gameObject.isOpen) {
                    openTile(gameObject.x, gameObject.y);
                    if (gameObject.countMineNeighbors != 0 && !gameField[y][x].isMine) {
                        setCellNumber(gameObject.x, gameObject.y, gameObject.countMineNeighbors);
                    }
                }
            }
        } else {
            setCellNumber(x, y, gameField[y][x].countMineNeighbors);
        }
    }
    
    private void markTile(int x, int y){
        if (isGameStopped){
            return;
        }
        
        else if (gameField[y][x].isOpen == true){
            return;
        }
        
        else if (countFlags == 0 && gameField[y][x].isFlag == false){
            return;
        }
        
        else if (gameField[y][x].isFlag == false){
            countFlags--;
            setCellValue(x, y, FLAG);
            setCellColor(x, y, Color.YELLOW);
            gameField[y][x].isFlag = true;
        }
        
        else if (gameField[y][x].isFlag == true && countFlags > 0){
            countFlags++;
            setCellValue(x, y, "");
            setCellColor(x, y, Color.ORANGE);
            gameField[y][x].isFlag = false;
        }
    }
    
    private void gameOver(){
        isGameStopped = true;
        showMessageDialog(Color.RED,"Game Over", Color.WHITE, 101);
    }
    
    private void win(){
        isGameStopped = true;
        showMessageDialog(Color.GREEN, "You're Winner", Color.WHITE, 1);
    }
    
    private void restart(){
        isGameStopped = false;
        countClosedTiles = SIDE * SIDE;
        score = 0;
        countMinesOnField = 0;
        setScore(score);
        createGame();
    }
}