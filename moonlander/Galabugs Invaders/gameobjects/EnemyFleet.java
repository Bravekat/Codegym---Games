package com.codegym.games.spaceinvaders.gameobjects;
import com.codegym.games.spaceinvaders.*;
import com.codegym.engine.cell.Game;
import com.codegym.engine.cell.*;
import java.util.*;


public class EnemyFleet {
    private static final int ROWS_COUNT = 3;
    private static final int COLUMNS_COUNT = 10;
    private static final int STEP = ShapeMatrix.ENEMY.length +1;
    private Direction direction = Direction.RIGHT;
    private List<EnemyShip> ships;
    
    public EnemyFleet(){
        createShips();
    }
    
    
    private void createShips(){
        ships = new ArrayList<EnemyShip>();
        for (int x = 0; x < COLUMNS_COUNT; x++){
            for (int y = 0; y < ROWS_COUNT; y++){
                ships.add(new EnemyShip(x * STEP, y * STEP + 12));
            }
        }
        ships.add(new Boss(STEP * COLUMNS_COUNT / 2 - ShapeMatrix.BOSS_ANIMATION_FIRST.length / 2 - 1, 5));
    }
    public void draw(Game game){
        for (EnemyShip enemyship : ships){
            enemyship.draw(game);
        }
    }
    private double getLeftBorder(){
        double tempX = 1000;
        for (EnemyShip enemyship : ships){
            if (enemyship.x < tempX)
            {
                tempX = enemyship.x;
            }
        }
        return tempX;
    }
    private double getRightBorder(){
        double tempX = 0;
        for (EnemyShip enemyship : ships){
            if (enemyship.x + enemyship.width > tempX)
            {
                tempX = enemyship.x + enemyship.width;
            }
        }
        return tempX;
    }
    
    private double getSpeed(){
        double tempSpeed = 2.0;
        if (tempSpeed > 3.0 / ships.size())
        {
            tempSpeed = 3.0 / ships.size();
        }
        return tempSpeed;
    }
    public void move(){
        if (!ships.isEmpty())
        {
            double speed = getSpeed();
            if (direction == Direction.LEFT && getLeftBorder() < 0)
            {
                direction = Direction.RIGHT;
                for (EnemyShip enemyship : ships)
                {
                    enemyship.move(Direction.DOWN, speed);
                }
            }
            else if (direction == Direction.RIGHT &&
            getRightBorder() > SpaceInvadersGame.WIDTH)
            {
                direction = Direction.LEFT;
                for (EnemyShip enemyship : ships)
                {
                    enemyship.move(Direction.DOWN, speed);
                }
            }
            else
            {
                for (EnemyShip enemyship : ships)
                {
                    enemyship.move(direction, speed);
                }
            }
        }
    }
    
    public Bullet fire(Game game){
        if (ships.isEmpty()){
            return null;
        }
        if (game.getRandomNumber(100 / SpaceInvadersGame.DIFFICULTY) > 0){
            return null;
        }
        return ships.get(game.getRandomNumber(ships.size())).fire();
    }
    
    public int checkHit(List<Bullet> bullets){
        if (bullets.isEmpty()){
            return 0;
        }
        int sum = 0;
        for (Bullet bullet : bullets){
            for (EnemyShip enemyship : ships){
                if(bullet.isCollision(enemyship)){
                    if(bullet.isAlive && enemyship.isAlive){
                        bullet.kill();
                        enemyship.kill();
                        sum += enemyship.score;
                    }
                }
            }
        }
        return sum;
    }
    
    public void deleteHiddenShips(){
        for (int i = ships.size() - 1; i >= 0; i--){
            if (!ships.get(i).isVisible()){
                ships.remove(i);
            }
        }
    }
    
    public double getBottomBorder(){
        double largest = 0;
        for (EnemyShip enemyShip : ships){
            if(enemyShip.y + enemyShip.height > largest){
                largest = enemyShip.y + enemyShip.height;
            }
        }
        return largest;
    }
    
    public int getShipCount(){
        return ships.size();
    }

}