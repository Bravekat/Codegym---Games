package com.codegym.games.snake;
import com.codegym.engine.cell.*;


public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private static final int GOAL = 28;
    
    private Snake snake;
    private Apple apple;
    private int turnDelay;
    private int score;
    private boolean isGameStopped;
    
    @Override
    public void initialize(){
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }
    
    @Override
    public void onTurn(int turn){
        snake.move(apple);
        if (!apple.isAlive) {
            score += 5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
            createNewApple();
        }
        if (!snake.isAlive){
            gameOver();
        }
        if (GOAL < snake.getLength()){
            win();
        }
        drawScene();
    }
    
    @Override
    public void onKeyPress(Key key){
        if (key == Key.LEFT){
            snake.setDirection(Direction.LEFT);
        }
        else if (key == Key.RIGHT){
            snake.setDirection(Direction.RIGHT);
        }
        else if (key == Key.UP){
            snake.setDirection(Direction.UP);
        }
        else if (key == Key.DOWN){
            snake.setDirection(Direction.DOWN);
        }
        else if (key == Key.SPACE && isGameStopped == true){
            createGame();
        }
    }
    
    private void createGame(){
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        snake.draw(this);
        turnDelay = 300;
        setTurnTimer(turnDelay);
        createNewApple();
        isGameStopped = false;
        score = 0;
        setScore(score);
        
        drawScene();
    }
    
    private void drawScene(){
        for (int i = 0; i < HEIGHT; i++){
            for (int j = 0; j < WIDTH; j++){
                setCellValueEx(i, j, Color.DARKSEAGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }
    
    private void createNewApple(){
        int height = getRandomNumber(HEIGHT);
        int width = getRandomNumber(WIDTH);
        apple = new Apple(height, width);
        if (snake.checkCollision(apple)){
            createNewApple();
        }
    }
    
    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.RED, "Game Over", Color.WHITE, 36);
        
    }
    
    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.GREEN, "You're Winner", Color.WHITE, 36);
    }
}