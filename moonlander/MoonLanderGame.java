package com.codegym.games.moonlander;

import com.codegym.engine.cell.*;

public class MoonLanderGame extends Game {
    public static final int WIDTH=64;
    public static final int HEIGHT=64;
    private Rocket rocket;
    private GameObject landscape;
    private boolean isUpPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed;
    private boolean isGameStopped;
    private GameObject platform;
    private int score;

   public void initialize(){
       setScreenSize(WIDTH,HEIGHT);
       createGame();
       showGrid(false);


   }
    private void createGame(){
        createGameObjects();
        setTurnTimer(50);
        isUpPressed=false;
        isLeftPressed=false;
        isRightPressed=false;
        isGameStopped=false;
        score = 1000;
        drawScene();
    }
    private void drawScene(){
        for(int i=0;i<WIDTH;i++)
            for(int j=0;j<HEIGHT;j++)
            {
                setCellColor(i,j,Color.BLACK);
            }
      rocket.draw(this);
            landscape.draw(this);
    }


    private void createGameObjects(){
        rocket=new Rocket(WIDTH/2,0);
        landscape=new GameObject(0,25,ShapeMatrix.LANDSCAPE);
        platform=new GameObject(23, MoonLanderGame.HEIGHT - 1, ShapeMatrix.PLATFORM);

    }

    @Override
    public void onTurn(int step)
    {
        rocket.move(isUpPressed, isLeftPressed, isRightPressed);
        check();
        if(score > 0){
            score -= 1;
        }
        setScore(score);
        drawScene();
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
       if((0<=x&&x<64)&&(0<=y&&y<64))
        super.setCellColor(x, y, color);

    }

    @Override
    public void onKeyPress(Key key) {
        if (key==Key.UP){
            isUpPressed=true;
        }
        else if (key==Key.LEFT){
            isLeftPressed=true;
            isRightPressed=false;
        }
        else if (key==Key.RIGHT){
            isLeftPressed=false;
            isRightPressed=true;
        }
        else if (key==Key.SPACE && isGameStopped){
            createGame();
        }
   }

    @Override
    public void onKeyReleased(Key key) {
        if(key==Key.UP)
            isUpPressed=false;
        if (key==Key.LEFT){
            isLeftPressed=false;
        }
        if (key==Key.RIGHT){
            isRightPressed=false;
        }

    }
    private void check(){
        if(rocket.isCollision(landscape))
            gameOver();
     
        if(rocket.isCollision(platform)&&rocket.isStopped())
             win();

    }
    private void win(){
        rocket.land();
        isGameStopped = true;
        showMessageDialog(Color.GREEN, "You're Winnar", Color.WHITE, 36);
        stopTurnTimer();
    }
    private void gameOver(){
        rocket.crash();
        isGameStopped = true;
        score = 0;
        showMessageDialog(Color.RED, "We are dead now because of you!", Color.WHITE, 26);
        stopTurnTimer();
    }

}

