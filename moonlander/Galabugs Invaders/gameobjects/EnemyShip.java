package com.codegym.games.spaceinvaders.gameobjects;
import com.codegym.games.spaceinvaders.*;


public class EnemyShip extends Ship{
    public int score = 15;
    
    public EnemyShip(double x, double y){
        super(x,y);
        setStaticView(ShapeMatrix.ENEMY);
    }
    
    @Override
    public Bullet fire(){
        return new Bullet(x + 1, y + height, Direction.DOWN);
    }
    
    @Override
    public void kill(){
        if (isAlive){
            isAlive = false;
            super.setAnimatedView(false, ShapeMatrix.KILL_ENEMY_ANIMATION_FIRST,
                                        ShapeMatrix.KILL_ENEMY_ANIMATION_SECOND,
                                        ShapeMatrix.KILL_ENEMY_ANIMATION_THIRD);
        }
    }
    
    public void move(Direction direction, double speed){
        if (direction == direction.RIGHT){
            x += speed;
        }
        if (direction == direction.LEFT){
            x -= speed;
        }
        if (direction == direction.DOWN){
            y += 2;
        }
    }
}