package edu.ClassDesign.game;

import java.util.Random;

/**
 * 类的描述：小蜜蜂类
 * @author wentao
 * @time Create in 14:25 2017/8/29 0029
 * @copyright Wuxi ,Ltd.copyright 2015-2025
 */
public class BulletGift extends FlyingObject implements Award {
    private int xSpeed=1; //定义X坐标移动速度
    private int ySpeed=1; //定义Y坐标移动速度
    private int awardType; //定义奖励类型

    public BulletGift(){
        image=ShootGame.bulletgify;
        width=image.getWidth();
        height=image.getHeight();
        Random rand=new Random();
        x=rand.nextInt(ShootGame.WIDTH-this.width);
        y= -this.height;
        awardType=rand.nextInt(2);

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
