package com.codegym.games.spaceinvaders.gameobjects;
import com.codegym.games.spaceinvaders.*;

public class Boss extends EnemyShip{
    private int frameCount = 0;
    
    public Boss(double x,double y){
        super(x,y);
        setAnimatedView(true, ShapeMatrix.BOSS_ANIMATION_FIRST,
                            ShapeMatrix.BOSS_ANIMATION_SECOND);
        score = 100;
    }
    
    @Override
    public void nextFrame(){
        frameCount++;
        if(!isAlive || frameCount % 10 == 0){
            super.nextFrame();
        }
    }
    
    @Override
    public Bullet fire(){
        if(!isAlive){
            return null;
        }
        if(matrix == ShapeMatrix.BOSS_ANIMATION_FIRST){
            return new Bullet(x + 6, y + height, Direction.DOWN);
        }
        if(matrix != ShapeMatrix.BOSS_ANIMATION_FIRST){
            return new Bullet(x, y + height, Direction.DOWN);
        }
        return null;
    }
    
    @Override
    public void kill(){
        if(!isAlive){
            return;
        }
        isAlive = false;
        super.setAnimatedView(  false, ShapeMatrix.KILL_BOSS_ANIMATION_FIRST,
                                        ShapeMatrix.KILL_BOSS_ANIMATION_SECOND,
                                        ShapeMatrix.KILL_BOSS_ANIMATION_THIRD);
    }
}