package edu.ClassDesign.game;

import javax.imageio.ImageIO;
import java.io.IOException;

public class utils {

    public static void loadImg(){
        try {
            ShootGame.background= ImageIO.read(ShootGame .class.getResourceAsStream("Img/background.png")); //¶ÁÈ¡Í¬°üÍ¼Æ¬
            ShootGame.bulletgify= ImageIO.read(ShootGame .class.getResourceAsStream("Img/bullet_supply.png"));
            ShootGame.bomb= ImageIO.read(ShootGame .class.getResourceAsStream("Img/bomb_supply.png"));
            ShootGame.bullet= ImageIO.read(ShootGame .class.getResourceAsStream("Img/bullet.png"));
            ShootGame.gameover= ImageIO.read(ShootGame .class.getResourceAsStream("Img/gameover.png"));
            ShootGame.hero0= ImageIO.read(ShootGame .class.getResourceAsStream("Img/hero0.png"));
            ShootGame.hero1= ImageIO.read(ShootGame .class.getResourceAsStream("Img/hero1.png"));
            ShootGame.pause= ImageIO.read(ShootGame .class.getResourceAsStream("Img/pause.png"));
            ShootGame.start= ImageIO.read(ShootGame .class.getResourceAsStream("Img/start.png"));
            ShootGame.airplane= ImageIO.read(ShootGame .class.getResourceAsStream("Img/airplane.png"));
            ShootGame.airplane2= ImageIO.read(ShootGame .class.getResourceAsStream("Img/airplane2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
