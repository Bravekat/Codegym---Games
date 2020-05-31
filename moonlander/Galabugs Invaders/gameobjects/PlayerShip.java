package com.codegym.games.spaceinvaders.gameobjects;
import com.codegym.games.spaceinvaders.*;
import java.util.*;


public class PlayerShip extends Ship {
    private Direction direction = Direction.UP;
    
    @Override
    public Bullet fire(){
        if (!isAlive){
            return null;
        }
        return new Bullet(x + 2, y - ShapeMatrix.BULLET.length, Direction.UP);
    }
    
    @Override
    public void kill(){
        if (!isAlive){
            return;
        }
        else {
            isAlive = false;
        }
        super.setAnimatedView(false, ShapeMatrix.KILL_PLAYER_ANIMATION_FIRST,
                                    ShapeMatrix.KILL_PLAYER_ANIMATION_SECOND,
                                    ShapeMatrix.KILL_PLAYER_ANIMATION_THIRD,
                                    ShapeMatrix.DEAD_PLAYER);
    }
    
    public PlayerShip(){
        super(SpaceInvadersGame.WIDTH / 2.0, SpaceInvadersGame.HEIGHT - ShapeMatrix.PLAYER.length - 1);
        setStaticView(ShapeMatrix.PLAYER);
    }
    
    public void checkHit(List<Bullet> bullets){
        if (bullets.size() == 0 || isAlive == false){
            return;
        }
        for (Bullet bullet : bullets){
            if (bullet.isAlive && isCollision(bullet)){
            kill();
            bullet.kill();
            }
        }
    }
    
    public void setDirection(Direction newDirection){
        if(newDirection != Direction.DOWN){
            direction = newDirection;
        }
    }
    
    public Direction getDirection(){
        return direction;
    }
    
    public void move(){
        if (isAlive){
            if (x <= 0) {
                x = 0;                
            }
            else if (x + width > SpaceInvadersGame.WIDTH -1){
                x = SpaceInvadersGame.WIDTH - width;
            }
            else if (direction == Direction.LEFT){
                x -= 1;
            }
            else if (direction == Direction.RIGHT){
                x += 1;
            }
        }
    }
    
    public void win(){
        setStaticView(ShapeMatrix.WIN_PLAYER);
    }
}