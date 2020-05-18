package com.codegym.games.moonlander;
import java.util.*;
import com.codegym.engine.cell.*;


public class RocketFire extends GameObject {
    private List<int[][]> frameslist;
    private int frameIndex;
    private boolean isVisible;
    public RocketFire(List<int[][]> list){
        super(0,0,list.get(0));
        frameslist = list;
        frameIndex = 0;
        isVisible = false;
    }
    
    @Override
    public void draw(Game game){
        if (!isVisible){
            return;
        }
        nextFrame();
        super.draw(game);
    }
    
    private void nextFrame(){
        frameIndex += 1;
        if (frameIndex >= frameslist.size()){
            frameIndex = 0;
        }
        matrix = frameslist.get(frameIndex);
    }
    
    public void show(){
        isVisible = true;
    }
    
    public void hide(){
        isVisible = false;
    }
}