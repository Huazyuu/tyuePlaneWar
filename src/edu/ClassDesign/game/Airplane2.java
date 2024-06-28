package edu.ClassDesign.game;

import java.util.Random;

public class Airplane2 extends FlyingObject implements Enemy{
    private int speed=1; //���Ƶл������ٶ�

    public Airplane2(){
        image=ShootGame.airplane2;
        width=image.getWidth();
        height=image.getHeight();
        Random rand=new Random();
        x=rand.nextInt(ShootGame.WIDTH-this.width);
        y= -this.height;

    }

    public int getScore(){
        return 2;
    }

    public void step() {
        y+=speed;
    }

    public boolean outOfBounds(){
        return this.y>=ShootGame.HEIGHT; //�л���������
    }
}
