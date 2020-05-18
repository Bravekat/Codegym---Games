package com.codegym.games.snake;
import com.codegym.engine.cell.*;
import java.util.ArrayList;
import java.util.List;


public class Snake {
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    
    private Direction direction = Direction.LEFT;
    
    private List<GameObject> snakeParts = new ArrayList<>();
    
    public Snake(int x, int y){
        GameObject first = new GameObject(x, y);
        GameObject second = new GameObject(x + 1, y);
        GameObject third = new GameObject(x + 2, y);
        
        snakeParts.add(first);
        snakeParts.add(second);
        snakeParts.add(third);
    }
    
    public void draw(Game game){
        for (GameObject obj: snakeParts){
            if (snakeParts.indexOf(obj) == 0){
                if (isAlive == true){
                    game.setCellValueEx(obj.x, obj.y, Color.NONE, HEAD_SIGN, Color.BLACK, 75);
                }
                else {
                    game.setCellValueEx(obj.x, obj.y, Color.NONE, HEAD_SIGN, Color.RED, 75);
                }
            }
            
            else {
                if (isAlive == true){
                    game.setCellValueEx(obj.x, obj.y, Color.NONE, BODY_SIGN, Color.BLACK, 75);
                }
                else {
                    game.setCellValueEx(obj.x, obj.y, Color.NONE, BODY_SIGN, Color.RED, 75);
                }
            }
        }
    }
    
    public void setDirection(Direction direction) {
        if(this.direction == Direction.LEFT && snakeParts.get(0).x == snakeParts.get(1).x){
            return;
        }
        else if(this.direction == Direction.RIGHT && snakeParts.get(0).x == snakeParts.get(1).x){
            return;
        }
        else if(this.direction == Direction.UP && snakeParts.get(0).y == snakeParts.get(1).y){
            return;
        }
        else if(this.direction == Direction.DOWN && snakeParts.get(0).y == snakeParts.get(1).y){
            return;
        }
        else {
            this.direction = direction;
        }
    }
    
    public void move(Apple apple){
        GameObject head = createNewHead();
        if(checkCollision(head)){
            isAlive = false;
            return;
        }
        if(head.x >= 0 && head.x < 15 && head.y >= 0 && head.y < 15){
            snakeParts.add(0, head);
            if(apple.x == head.x && apple.y == head.y){
                apple.isAlive = false;
                return;
            }
            else {
                removeTail();
            }
        }
    }
    
    public GameObject createNewHead(){
        GameObject newhead = snakeParts.get(0);
        if(direction == Direction.UP){
            return new GameObject(newhead.x, newhead.y - 1);
        }
        else if(direction == Direction.RIGHT){
            return new GameObject(newhead.x + 1, newhead.y);
        }
        else if(direction == Direction.DOWN){
            return new GameObject(newhead.x, newhead.y + 1);
        }
        else {
            return new GameObject(newhead.x - 1, newhead.y);
        }
    }
    
    public void removeTail(){
        snakeParts.remove(snakeParts.size() - 1);
    }
    
    public boolean checkCollision(GameObject gameobject){
        boolean collision = false;
        for(GameObject obj : snakeParts) {
            if(obj.x == gameobject.x && obj.y == gameobject.y) {
                collision = true;
            }
        }
        return collision;
    }
    
    public int getLength(){
        return snakeParts.size();
    }
}