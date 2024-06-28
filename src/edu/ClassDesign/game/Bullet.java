package edu.ClassDesign.game;

public class Bullet extends FlyingObject {
    private int speed=1; //定义子弹速度

    public Bullet(int x,int y){
        image=ShootGame.bullet;
        width=image.getWidth();
        height=image.getHeight();
        this.x=x; //根据英雄机坐标来获取位置
        this.y=y; //根据英雄机坐标来获取位置
    }

    public void step() {
        y-=speed;
    }

    public boolean outOfBounds(){
        return this.y<=-ShootGame.HEIGHT; //子弹超过窗口
    }
}
