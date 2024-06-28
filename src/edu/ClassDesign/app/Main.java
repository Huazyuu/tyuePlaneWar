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

        // 设置 JFrame
        JFrame frame = new JFrame("PLANE WAR");
        frame.setSize(400, 654);
        frame.setLocationRelativeTo(null);
        frame.setTitle("飞机大战课设");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 添加 CardPanel 到 JFrame
        frame.add(cardPanel);

        // 添加 Start 页面到 CardPanel
        Start start = new Start();
        cardPanel.add(start.getMainPanel());

        // 显示 JFrame
        frame.setVisible(true);

        // 加载并播放背景音乐
        playBackgroundMusic();
    }

    public static void playBackgroundMusic() {
        try {
            File soundFile = new File("src/assets/sound/bullet.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);  // 持续循环播放
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
