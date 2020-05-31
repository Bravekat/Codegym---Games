package com.codegym.games.spaceinvaders;
import com.codegym.games.spaceinvaders.gameobjects.*;
import com.codegym.engine.cell.*;
import java.util.*;

public class SpaceInvadersGame extends Game{
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public static final int DIFFICULTY = 5;
    private static final int PLAYER_BULLETS_MAX = 5;
    private List<Star> stars;
    private EnemyFleet enemyFleet;
    private List<Bullet> enemyBullets;
    private List<Bullet> playerBullets;
    private PlayerShip playerShip;
    private boolean isGameStopped;
    private int animationsCount;
    private int score;
    
    @Override
    public void initialize(){
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }
    
    @Override
    public void onTurn(int turn){
        moveSpaceObjects();
        check();
        Bullet newBullet = enemyFleet.fire(this);
        if (newBullet != null){
            enemyBullets.add(newBullet);
        }
        setScore(score);
        drawScene();
    }
    
    @Override
    public void onKeyPress(Key key){
        if(Key.SPACE == key && isGameStopped){
            createGame();
        }
        if (Key.SPACE == key){
            if (playerShip.fire() != null && PLAYER_BULLETS_MAX > playerBullets.size()){
                playerBullets.add(playerShip.fire());
            }
        }
        if(Key.LEFT == key){
            playerShip.setDirection(Direction.LEFT);
        }
        if(Key.RIGHT == key){
            playerShip.setDirection(Direction.RIGHT);
        }
    }
    
    @Override
    public void onKeyReleased(Key key){
        if (key.LEFT == key && Direction.LEFT == playerShip.getDirection()){
            playerShip.setDirection(Direction.UP);
        }
        if (key.RIGHT == key && Direction.RIGHT == playerShip.getDirection()){
            playerShip.setDirection(Direction.UP);
        }
    }
    @Override
    public void setCellValueEx(int x, int y, Color color, String text){
        if  (x >= HEIGHT || y >= WIDTH || x < 0 || y < 0){
            return;
        }
        super.setCellValueEx(x,y,color,text);
    }
    
    private void createGame(){
        enemyFleet = new EnemyFleet();
        createStars();
        setTurnTimer(40);
        enemyBullets = new ArrayList<Bullet>();
        playerBullets = new ArrayList<Bullet>();
        playerShip = new PlayerShip();
        isGameStopped = false;
        animationsCount = 0;
        score = 0;
        drawScene();
    }
    
    private void drawScene(){
        drawField();
        playerShip.draw(this);
        enemyFleet.draw(this);
        for (Bullet bullet : enemyBullets ){
            bullet.draw(this);
        }
        for (Bullet bullet : playerBullets){
            bullet.draw(this);
        }
    }
    
    private void drawField(){
        for (int y = 0; y < HEIGHT; y++){
            for (int x = 0; x < WIDTH; x++){
                setCellValueEx(x, y, Color.BLACK, "");
            }
        }
        
        for (Star obj : stars){
            obj.draw(this);
        }
    }
    
    private void createStars(){
        stars = new ArrayList<Star>();
        stars.add(new Star(5, 10));
        stars.add(new Star(10, 20));
        stars.add(new Star(15, 36));
        stars.add(new Star(23, 48));
        stars.add(new Star(33, 12));
        stars.add(new Star(45, 50));
        stars.add(new Star(19, 23));
        stars.add(new Star(50, 23));
    }
    
    private void moveSpaceObjects(){
        enemyFleet.move();
        for (Bullet bullet : enemyBullets ){
            bullet.move();
        }
        playerShip.move();
        for (Bullet bullet : playerBullets ){
            bullet.move();
        }
    }
    
    private void removeDeadBullets(){
        for (int i = enemyBullets.size() - 1; i >= 0; i--){
            if(enemyBullets.get(i).y >= HEIGHT - 1 || enemyBullets.get(i).isAlive == false){
                enemyBullets.remove(enemyBullets.get(i));
            }
        }
        for (int i = playerBullets.size() - 1; i >= 0; i--){
            if(playerBullets.get(i).y + playerBullets.get(i).height < 0 || playerBullets.get(i).isAlive == false){
                playerBullets.remove(playerBullets.get(i));
            }
        }
    }
    
    private void check(){
        playerShip.checkHit(enemyBullets);
        score += enemyFleet.checkHit(playerBullets);
        enemyFleet.deleteHiddenShips();
        if(enemyFleet.getShipCount() == 0){
            playerShip.win();
            stopGameWithDelay();
        }
        if(enemyFleet.getBottomBorder() >= playerShip.y){
            playerShip.kill();
        }
        removeDeadBullets();
        if(!playerShip.isAlive){
            stopGameWithDelay();
        }
    }
    
    private void stopGame(boolean isWin){
        isGameStopped = true;
        stopTurnTimer();
        if(isWin){
            showMessageDialog(Color.WHITE, "You're winnar", Color.GREEN, 24);
        }
        else {
            showMessageDialog(Color.WHITE, "YOU SUCK", Color.RED, 24);
        }
    }
    
    private void stopGameWithDelay(){
        animationsCount++;
        if (animationsCount >= 10){
            stopGame(playerShip.isAlive);
        }
    }
}