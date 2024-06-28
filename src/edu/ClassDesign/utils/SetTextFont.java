package edu.ClassDesign.utils;

import javax.swing.*;
import java.awt.*;

public  class SetTextFont {
    static Font labelFont = new Font("MicroSoft YaHei", Font.BOLD, 20);
    public static JLabel setFont(String content){
       JLabel label=new JLabel(content);
       label.setFont(labelFont);
       return label;
    }
}
