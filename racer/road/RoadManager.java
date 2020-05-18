package com.codegym.games.racer.road;
import com.codegym.engine.cell.*;
import com.codegym.games.racer.*;
import com.codegym.engine.cell.Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RoadManager {
    public static final int leftOffset = 1;
    public static final int rightOffset = 6;
    public static final int LEFT_BORDER = RacerGame.ROADSIDE_WIDTH - leftOffset;
    public static final int RIGHT_BORDER = RacerGame.WIDTH - LEFT_BORDER - rightOffset;
    private static final int FIRST_LANE_POSITION = 16;
    private static final int FOURTH_LANE_POSITION = 44;
    private static final int PLAYER_CAR_DISTANCE = 12;
    private List<RoadObject> items = new ArrayList<RoadObject>();
    private int passedCarsCount = 0;
    
    private RoadObject createRoadObject(RoadObjectType type, int x, int y){
        if (type == RoadObjectType.SPIKE){
            return new Spike(x,y);
        }
        else if (type == RoadObjectType.CAR){
            return new Car(type, x, y);
        }
        else if (type == RoadObjectType.DRUNK_CAR){
            return new MovingCar(x, y);
        }
        return null;
    }
    
    private void addRoadObject(RoadObjectType type, Game game){
        int x = game.getRandomNumber(FIRST_LANE_POSITION, FOURTH_LANE_POSITION);
        int y = -1 * RoadObject.getHeight(type);
        RoadObject roadobject = createRoadObject(type, x, y);
        if (roadobject != null && isRoadSpaceFree(roadobject)){
            items.add(roadobject);            
        }
    }
    
    private void generateRegularCar(Game game){
        int number = game.getRandomNumber(100);
        int carTypeNumber = game.getRandomNumber(4);
        if(number < 30){
            addRoadObject(RoadObjectType.values()[carTypeNumber], game);
        }
    }
    
    public void draw(Game game){
        for (RoadObject object : items)
        {
            object.draw(game);
        }
    }
    
    public void move(int boost){
        for (RoadObject object : items)
        {
            object.move(boost + object.speed, items);
        }
        deletePassedItems();
    }
    
    private boolean spikeExists(){
        for (int i = 0; i < items.size(); i++)
        {
            if (items.get(i).type == RoadObjectType.SPIKE){
                return true;
            }
        }
        return false;
    }
    
    private void generateSpike(Game game){
       int number = game.getRandomNumber(100);
       if (number < 10 && !spikeExists()){
           addRoadObject(RoadObjectType.SPIKE, game);
       }
    }
    
    public void generateNewRoadObjects(Game game){
        generateSpike(game);
        generateRegularCar(game);
        generateMovingCar(game);
    }
    
    private void deletePassedItems(){
        for (int i = items.size() - 1; i >= 0; i--)
        {
            if (items.get(i).y >= RacerGame.HEIGHT){
                if(items.get(i).type != RoadObjectType.SPIKE){
                    passedCarsCount += 1;
                }
                items.remove(i);
            }
        }
    }
    
    public boolean checkCrash(PlayerCar playercar){
        for (RoadObject object : items)
        {
            if (object.isCollision(playercar)){
                return true;
            }
        }
        return false;
    }
    
    private boolean isRoadSpaceFree(RoadObject object){
        for (RoadObject obj : items){
            if(obj.isCollisionWithDistance(object, PLAYER_CAR_DISTANCE)){
                return false;
            }
        }
        return true;
    }
    
    private boolean movingCarExists(){
        for (RoadObject object : items){
            if(object.type == RoadObjectType.DRUNK_CAR){
                return true;
            }
        }
        return false;
    }
    
    private void generateMovingCar(Game game){
        int number = game.getRandomNumber(100);
        if (number < 10 && !movingCarExists()){
            addRoadObject(RoadObjectType.DRUNK_CAR, game);
        }
    }
    
    public int getPassedCarsCount(){
        return passedCarsCount;
    }
}