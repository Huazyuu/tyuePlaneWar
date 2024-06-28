package edu.ClassDesign.game;

import edu.ClassDesign.global.global;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShootGame extends JPanel {
    public static final int WIDTH = 400; //���崰�ڵĿ�
    public static final int HEIGHT = 654; //���崰�ڵĸ�
    // img
    public static BufferedImage background;
    public static BufferedImage airplane;
    public static BufferedImage airplane2;
    public static BufferedImage bulletgify;
    public static BufferedImage bomb;
    public static BufferedImage bullet;
    public static BufferedImage gameover;
    public static BufferedImage hero0;
    public static BufferedImage hero1;
    public static BufferedImage pause;
    public static BufferedImage start;

    public Hero hero = new Hero(); //����Ӣ�ۻ�
    public FlyingObject[] flyings = {}; //�л�����
    public Bullet[] bullets = {}; //�ӵ�����

    public static final int START = 0;
    public static final int RUNNING = 1;
    public static final int PAUSE = 2;
    public static final int GAME_OVER = 3;
    public static int status = START;


    /**
     * ���صл�����
     */
    public FlyingObject nextOne() {
        Random rand = new Random();
        int type = rand.nextInt(40);
        if (type <= 13) {
            return new BulletGift();
        } else if (13 < type && type <= 15) {
            return new Bomb();
        } else if (15 < type && type <= 30) {
            return new Airplane2();
        } else {
            return new Airplane();
        }
    }

    /**
     * �л��볡����
     */
    int flyEnteredIndex = 0;

    public void enterAction() {
        flyEnteredIndex++;
        if (flyEnteredIndex % 40 == 0) {
            FlyingObject fly = nextOne();//���յл�����
            flyings = Arrays.copyOf(flyings, flyings.length + 1);
            flyings[flyings.length - 1] = fly;
        }

    }

    /**
     * �������ƶ�����
     */
    public void stepAction() {
        hero.step();
        for (int i = 0; i < flyings.length; i++) {
            flyings[i].step();
        }

        for (int i = 0; i < bullets.length; i++) {
            bullets[i].step();
        }
    }

    /**
     * �����ӵ�����
     */
    int shootIndex = 0;

    public void shootAction() {
        shootIndex++;
        if (shootIndex % 30 == 0) {
            Bullet[] bs = hero.shoot();
            bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
            System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);

        }

    }

    /**
     * Խ��ɾ������
     */
    public void outOfBounds() {
        int index = 0, indexBullet = 0;
        FlyingObject[] fly = new FlyingObject[flyings.length];
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            if (!f.outOfBounds()) {//��Խ��
                fly[index] = f;
                index++;
            }
        }
        flyings = Arrays.copyOf(fly, index);//��Խ������鸳ֵ���л�����

        Bullet[] b = new Bullet[bullets.length];
        for (int j = 0; j < bullets.length; j++) {
            Bullet bb = bullets[j];
            if (!bb.outOfBounds()) {
                b[indexBullet] = bb;
                indexBullet++;
            }
        }
        bullets = Arrays.copyOf(b, indexBullet);//��Խ������鸳ֵ���ӵ�����
    }

    /**
     * ��ɱ�л��ý���
     */
    public void bangAction() {
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            bang(b);
        }

    }

    static int score = 0;//�÷ֳ�ʼΪ0

    public void bang(Bullet b) {
        int index = -1;
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            if (f.shootBy(b)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            FlyingObject one = flyings[index];
            if (one instanceof Enemy) {
                Enemy e = (Enemy) one;
                score += e.getScore();
            }
            if (one instanceof Award) {
                Award a = (Award) one;
                int type = a.getType();
                switch (type) {
                    case Award.DOUBLE_FIRE:
                        hero.addFire();
                        break;
                    case Award.LIFE:
                        hero.addLife();
                        break;
                    case Award.BOMB:
                        hero.addBomb();
                        break;
                }
            }
            //ɾ������ɱ�ĵл�
            FlyingObject t = flyings[index];
            flyings[index] = flyings[flyings.length - 1];
            flyings[flyings.length - 1] = t;
            flyings = Arrays.copyOf(flyings, flyings.length - 1);
        }
    }

    /**
     * Ӣ�ۻ���ײ����
     */
    public void hitAction() {
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            if (hero.hit(f)) {
                flyings = new FlyingObject[]{};
                bullets = new Bullet[]{};
                hero.deleteLife();
                hero.clearFire();
            }
        }

    }

    /**
     * �����Ϸ��������
     */
    public void checkGameover() {
        if (hero.getLift() <= 0) { //��Ϸ����
             global.user.setScore(score);
             System.out.println("score: " + global.user.getScore());
            status = GAME_OVER;
        }
    }

    /**
     * ��������ִ��
     */
    public void action() {
        MouseAdapter mouse = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (status == RUNNING) {//����״̬��ִ��
                    int x = e.getX();
                    int y = e.getY();
                    hero.move(x, y);
                }
            }

            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    switch (status) {
                        case START:
                            status = RUNNING;
                            break;
                        case GAME_OVER:

                            score = 0;
                            hero = new Hero();
                            flyings = new FlyingObject[0];
                            bullets = new Bullet[0];
                            status = START;
                            break;
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (status == RUNNING) {
                        // ִ��ը���߼������������Ļ�ϵĵ��ˣ�����ը��������
                        if (hero.getBomb() > 0) {
                            System.out.println("Bomb used!");
                            flyings = new FlyingObject[0];
                            hero.setBomb(hero.getBomb() - 1);
                        } else {
                            System.out.println("ը������");
                        }
                        // ʾ����������з�����
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (status == RUNNING) {
                    status = PAUSE;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (status == PAUSE) {
                    status = RUNNING;
                }
            }
        };
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);

        Timer time = new Timer(); //��ʱ������
        int intervel = 10; //ʱ�����Ժ���Ϊ��λ
        //TimerTask��һ��������
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                if (status == RUNNING) {
                    enterAction(); //�л��볡
                    stepAction(); //�������ƶ�
                    shootAction();//�ӵ�����
                    outOfBounds(); //Խ��ɾ��
                    bangAction(); //��ɱ�л�
                    hitAction(); //Ӣ�ۻ���ײ
                    checkGameover(); //�����Ϸ�Ƿ����
                }
                repaint(); //�ص�paint����
            }
        }, intervel, intervel);


    }

    /**
     * ��дpaint(���� g:���ʣ�  ����ͼ��
     */
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, null); //����ͼ
        paintHero(g); //��Ӣ�ۻ�����
        paintFlyingObjects(g);//���л�����
        paintBullets(g);//���ӵ�����
        paintScoreAndLife(g);//�������÷�
        paintStatus(g);//����Ϸ״̬
    }

    public void paintHero(Graphics g) {
        g.drawImage(hero.image, hero.x, hero.y, null);
    }

    public void paintFlyingObjects(Graphics g) {
        for (int i = 0; i < flyings.length; i++) {
            //g.drawImage(flyings[i].image, flyings[i].x, flyings[i].y, null);
            FlyingObject f = flyings[i];
            g.drawImage(f.image, f.x, f.y, null);
        }
    }

    public void paintBullets(Graphics g) {
        for (int i = 0; i < bullets.length; i++) {
            //g.drawImage(bullets[i].image, bullets[i].x, bullets[i].y, null);
            Bullet b = bullets[i];
            g.drawImage(b.image, b.x, b.y, null);
        }

    }

    public void paintScoreAndLife(Graphics g) {
        g.setColor(new Color(0xff00000));
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        g.drawString("Score:" + score, 10, 25);
        g.drawString("Life:" + hero.getLift(), 10, 55);
        g.drawString("bomb:" + hero.getBomb(), 10, 85);
        g.drawString("doubleFire:" + hero.getFire(), 10, 115);
    }

    public void paintStatus(Graphics g) {
        switch (status) {
            case START:
                g.drawImage(start, 0, 0, null);
                break;
            case PAUSE:
                g.drawImage(pause, 0, 0, null);
                break;
            case GAME_OVER:
                g.drawImage(gameover, 0, 0, null);
                break;
        }

    }

    public ShootGame() {
        utils.loadImg();
    }

    public static void GameInit() {
        utils.loadImg();
        JFrame frame = new JFrame("�ɻ���ս");
        ShootGame game = new ShootGame();
        frame.add(game);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); //�Զ�����paint����
        game.action();
    }
}
