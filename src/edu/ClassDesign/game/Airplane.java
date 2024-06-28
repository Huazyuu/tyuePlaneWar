package edu.ClassDesign.game;

import java.util.Random;

public class Airplane extends FlyingObject implements Enemy{
    private int speed=1; //控制敌机下落速度
    public Airplane(){
        image=ShootGame.airplane;
        width=image.getWidth();
        height=image.getHeight();
        Random rand=new Random();
        x=rand.nextInt(ShootGame.WIDTH-this.width);
        y= -this.height;

    }
    public int getScore(){
        return 1;
    }
    public void step() {
        y+=speed;
    }
    public boolean outOfBounds(){
        return this.y>=ShootGame.HEIGHT; //敌机超过窗口
    }
}
