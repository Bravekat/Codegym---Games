package com.codegym.games.moonlander;
import com.codegym.engine.cell.*;
import com.codegym.engine.cell.Color;
import java.util.*;

public class Rocket extends GameObject {
    private double speedY = 0;
    private double speedX = 0;
    private double boost = 0.05;
    private double slowdown = boost / 10;
    
    private RocketFire downFire;
    private RocketFire leftFire;
    private RocketFire rightFire;
    
    public Rocket(double x, double y) {
        super(x, y, ShapeMatrix.ROCKET);
        List<int[][]> tempDOWN = new ArrayList<int[][]>();
        List<int[][]> tempSIDE = new ArrayList<int[][]>();
        tempDOWN.add(ShapeMatrix.FIRE_DOWN_1);
        tempDOWN.add(ShapeMatrix.FIRE_DOWN_2);
        tempDOWN.add(ShapeMatrix.FIRE_DOWN_3);
        tempSIDE.add(ShapeMatrix.FIRE_SIDE_1);
        tempSIDE.add(ShapeMatrix.FIRE_SIDE_2);

        downFire = new RocketFire(tempDOWN);
        leftFire = new RocketFire(tempSIDE);
        rightFire = new RocketFire(tempSIDE);
    }
    
    @Override
    public void draw(Game game){
        super.draw(game);
        downFire.draw(game);
        leftFire.draw(game);
        rightFire.draw(game);
    }
    
    public void move(boolean isUpPressed,boolean isLeftPressed,boolean isRightPressed)
    {
        if (isUpPressed)
        {
            speedY -= boost;
        }
        else
        {
            speedY += boost;
        }
        if (isLeftPressed)
        {
            speedX -= boost;
        }
        else if (isRightPressed)
        {
            speedX += boost;
        }
        else if (speedX > slowdown)
        {
            speedX -= slowdown;
        }
        else if (speedX < -slowdown)
        {
            speedX += slowdown;
        }
        else
        {
            speedX = 0;
        }
        y += speedY;
        x += speedX;
        checkBorders();
        switchFire(isUpPressed, isLeftPressed, isRightPressed);
    }

    private void checkBorders() {
        if (x < 0) {
            x = 0;
            speedX = 0;
        } else if (x + width > MoonLanderGame.WIDTH) {
            x = MoonLanderGame.WIDTH - width;
            speedX = 0;
        }
        if (y <= 0) {
            y = 0;
            speedY = 0;
        }
    }

    public boolean isStopped() {
        return speedY < 10 * boost;
    }

    public boolean isCollision(GameObject object) {
        int transparent = Color.NONE.ordinal();

        for (int matrixX = 0; matrixX < width; matrixX++) {
            for (int matrixY = 0; matrixY < height; matrixY++) {
                int objectX = matrixX + (int) x - (int) object.x;
                int objectY = matrixY + (int) y - (int) object.y;

                if (objectX < 0 || objectX >= object.width || objectY < 0 || objectY >= object.height) {
                    continue;
                }

                if (matrix[matrixY][matrixX] != transparent && object.matrix[objectY][objectX] != transparent) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void land(){
        y -= 1;
    }
    
    public void crash(){
        matrix = ShapeMatrix.ROCKET_CRASH;
    }
    
    private void switchFire(boolean isUpPressed, boolean isLeftPressed, boolean isRightPressed){
        if (isUpPressed){
            downFire.x = x + (width / 2);
            downFire.y = y + height;
            downFire.show();
        }
        else if (!isUpPressed){
            downFire.hide();
        }
        
        if (isLeftPressed){
            leftFire.x = x + width;
            leftFire.y = y + height;
            leftFire.show();
        }
        else if (!isLeftPressed){
            leftFire.hide();
        }
        
        if (isRightPressed){
            rightFire.x = x - ShapeMatrix.FIRE_SIDE_1[0].length;
            rightFire.y = y + height;
            rightFire.show();
        }
        else if (!isRightPressed){
            rightFire.hide();
        }
    }
}
