package edu.ClassDesign.app;

import edu.ClassDesign.global.global;
import edu.ClassDesign.page.Start;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static final CardLayout cardLayout = new CardLayout(0, 0);
    public static final JPanel cardPanel = new JPanel(cardLayout);

    public static void main(String[] args) {
        System.out.println("Hello plane war");

        // ���� JFrame
        JFrame frame = new JFrame("PLANE WAR");
        frame.setSize(400, 654);
        frame.setLocationRelativeTo(null);
        frame.setTitle("�ɻ���ս����");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ��� CardPanel �� JFrame
        frame.add(cardPanel);

        // ��� Start ҳ�浽 CardPanel
        Start start = new Start();
        cardPanel.add(start.getMainPanel());

        // ��ʾ JFrame
        frame.setVisible(true);

        // ���ز����ű�������
        playBackgroundMusic();
    }

    public static void playBackgroundMusic() {
        try {
            File soundFile = new File("src/assets/sound/bullet.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);  // ����ѭ������
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
