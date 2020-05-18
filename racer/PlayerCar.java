package com.codegym.games.racer;

import com.codegym.games.racer.road.RoadManager;

public class PlayerCar extends GameObject {
    public int speed = 1;
    
    private static int playerCarHeight = ShapeMatrix.PLAYER.length;
    private Direction direction;
    
    public PlayerCar(){
        super(RacerGame.WIDTH / 2 + 2, RacerGame.HEIGHT - playerCarHeight - 1, ShapeMatrix.PLAYER);
    }
    
    public void setDirection(Direction direction){
        this.direction = direction;
    }
    
    public Direction getDirection(){
        return direction;
    }
    
    public void move(){
        if (direction == Direction.LEFT){
            x -= 1;
        }
        else if (direction == Direction.RIGHT){
            x += 1;
        }
        if (RoadManager.LEFT_BORDER > x){
            x = RoadManager.LEFT_BORDER;            
        }
        else if (RoadManager.RIGHT_BORDER < x){
            x = RoadManager.RIGHT_BORDER;            
        }
    }
    public void stop(){
        matrix = ShapeMatrix.PLAYER_DEAD;
    }
}