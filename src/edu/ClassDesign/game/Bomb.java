package edu.ClassDesign.game;

import java.util.Random;

public class Bomb extends FlyingObject implements Award {
    private int xSpeed=1; //定义X坐标移动速度
    private int ySpeed=1; //定义Y坐标移动速度
    private int awardType; //定义奖励类型

    public Bomb(){
        image=ShootGame.bomb;
        width=image.getWidth();
        height=image.getHeight();
        Random rand=new Random();
        x=rand.nextInt(ShootGame.WIDTH-this.width);
        y= -this.height;
        awardType=2;

    }

    public int getType(){
        return awardType;
    }

    public void step() {
        x+=xSpeed;
        y+=ySpeed;
        if(x>=ShootGame.WIDTH-this.width){
            xSpeed=-1;
        }
        if(x<=0){
            xSpeed=1;
        }
    }

    public boolean outOfBounds(){
        return this.y>=ShootGame.HEIGHT;
    }
}
