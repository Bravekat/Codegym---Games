package com.codegym.games.spaceinvaders.gameobjects;
import java.util.*;
import com.codegym.engine.cell.*;
import com.codegym.engine.cell.Game;


public class Ship extends GameObject{
    private List<int[][]> frames;
    private int frameIndex;
    private boolean loopAnimation = false;
    
    @Override
    public void draw(Game game){
        super.draw(game);
        nextFrame();
    }
    
    /*public void setAnimatedView(int[][]... viewFrames){
        super.setMatrix(viewFrames[0]);
        frames = Arrays.asList(viewFrames);
        frameIndex = 0;
    }*/
    
    public boolean isAlive = true;
    
    public Ship(double x, double y){
        super(x,y);
    }
    public void setStaticView(int[][] viewFrame){
        super.setMatrix(viewFrame);
        frames = new ArrayList<int[][]>();
        frames.add(viewFrame);
        frameIndex = 0;
    }
    
    public Bullet fire(){
        return null;
    }
    
    public void kill(){
        isAlive = false;
    }
    
    public void nextFrame(){
        frameIndex++;
        if(frameIndex >= frames.size() && !loopAnimation){
            return;
        }
        if(frameIndex >= frames.size() && loopAnimation){
            frameIndex = 0;
        }
        matrix = frames.get(frameIndex);
    }
    
    public boolean isVisible(){
        if (!isAlive && frameIndex >= frames.size()){
            return false;
        }
        return true;
    }
    
    public void setAnimatedView(boolean isLoopAnimation, int[][]... viewFrames){
        loopAnimation = isLoopAnimation;
    }
}