package com.codegym.games.racer;
import com.codegym.engine.cell.*;
import com.codegym.games.racer.road.*;


public class RacerGame extends Game {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public static final int CENTER_X = WIDTH / 2;
    public static final int ROADSIDE_WIDTH = 14;
    private static final int RACE_GOAL_CARS_COUNT = 40;
    private int score;
    private boolean isGameStopped;
    private RoadMarking roadMarking;
    private PlayerCar player;
    private RoadManager roadManager;
    private FinishLine finishLine;
    private ProgressBar progressBar;

    @Override
    public void initialize(){
        setScreenSize(WIDTH,HEIGHT);
        showGrid(false);
        createGame();
    }
    
    @Override
    public void setCellColor(int x, int y, Color color){
        if(x < 0 || x >= WIDTH){
            return;
        }
        if(y < 0 || y >= WIDTH){
            return;
        }
        super.setCellColor(x, y, color);
    }
    
    @Override
    public void onTurn(int turn){
        if (roadManager.checkCrash(player)){
            gameOver();
            drawScene();
            return;
        }
        if (roadManager.getPassedCarsCount() >= RACE_GOAL_CARS_COUNT){
            finishLine.show();
        }
        if (finishLine.isCrossed(player)){
            win();
            drawScene();
            return;
        }
        score -= 5;
        setScore(score);
        moveAll();
        roadManager.generateNewRoadObjects(this);
        drawScene();
    }
    
    @Override
    public void onKeyPress(Key key){
        if(key == Key.UP){
            player.speed = 2;
        }
        else if (key == Key.RIGHT){
            player.setDirection(Direction.RIGHT);
        }
        else if (key == Key.LEFT){
            player.setDirection(Direction.LEFT);
        }
        else if (key == Key.SPACE && isGameStopped == true){
            createGame();
        }
    }
    
    @Override
    public void onKeyReleased(Key key){
        if (key == Key.UP){
            player.speed = 1;
        }
        else if (key == Key.LEFT && player.getDirection() == Direction.LEFT){
            player.setDirection(Direction.NONE);
        }
        else if (key == Key.RIGHT && player.getDirection() == Direction.RIGHT){
            player.setDirection(Direction.NONE);
        }
    }
    
    private void createGame(){
        score = 3500;
        roadMarking = new RoadMarking();
        player = new PlayerCar();
        roadManager = new RoadManager();
        finishLine = new FinishLine();
        progressBar = new ProgressBar(RACE_GOAL_CARS_COUNT);
        isGameStopped = false;
        setTurnTimer(40);
        drawScene();
    }
    
    private void drawScene(){
        drawField();
        roadManager.draw(this);
        roadMarking.draw(this);
        player.draw(this);
        finishLine.draw(this);
        progressBar.draw(this);
        
    }
    
    private void drawField(){
        for (int y = 0; y < HEIGHT; y++)
        {
            for (int x = 0; x < WIDTH; x++){
                if(x == CENTER_X){
                    setCellColor(CENTER_X, y, Color.WHITE);
                }
                else if(x >= ROADSIDE_WIDTH && x < (WIDTH - ROADSIDE_WIDTH)){
                    setCellColor(x, y, Color.DIMGREY);
                }
                else {
                    setCellColor(x,y,Color.GREEN);
                }
            }
        }
    }
    
    private void moveAll(){
        roadMarking.move(player.speed);
        roadManager.move(player.speed);
        finishLine.move(player.speed);
        progressBar.move(roadManager.getPassedCarsCount());
        player.move();

    }
    
    private void gameOver(){
        isGameStopped = true;
        showMessageDialog(Color.RED, "You're loser", Color.WHITE, 26);
        stopTurnTimer();
        player.stop();
    }
    
    private void win(){
        isGameStopped = true;
        showMessageDialog(Color.GREEN, "You are Numbah 1!", Color.YELLOW, 36);
        stopTurnTimer();
    }
}