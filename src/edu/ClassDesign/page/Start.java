package edu.ClassDesign.page;


import edu.ClassDesign.app.ImgLoad;
import edu.ClassDesign.dao.User;
import edu.ClassDesign.dao.UserDao;
import edu.ClassDesign.game.ShootGame;
import edu.ClassDesign.global.global;
import edu.ClassDesign.utils.SetTextFont;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class Start {
    private JPanel mainPanel;
    private JPanel loginPanel;
    private JPanel registerPanel;
    private JPanel rankPanel;


    private DefaultTableModel model;


    private UserDao userDao = new UserDao();

    public Start() {
        mainPanel = new JPanel(new CardLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImgLoad.bgImg01, 0, 0, getWidth(), getHeight(), this);
            }
        };

        loginPanel = initLogin();
        registerPanel = initRegister();
        rankPanel = initRank();


        mainPanel.add(loginPanel, "LoginPanel");
        mainPanel.add(registerPanel, "RegisterPanel");
        mainPanel.add(rankPanel, "RankPanel");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private JPanel initLogin() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setOpaque(false);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("��¼");
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 40));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        loginPanel.add(titleLabel, constraints);

        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        JLabel usernameLabel = SetTextFont.setFont("�û���:");
        loginPanel.add(usernameLabel, constraints);

        JTextField loginUsernameField = new JTextField(20);
        constraints.gridx = 1;
        loginPanel.add(loginUsernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        JLabel passwordLabel = SetTextFont.setFont("�� ��:");
        loginPanel.add(passwordLabel, constraints);

        JPasswordField loginPasswordField = new JPasswordField(20);
        constraints.gridx = 1;
        loginPanel.add(loginPasswordField, constraints);

        JButton loginButton = new JButton("��¼");
        loginButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(loginButton, constraints);

        loginButton.addActionListener(e -> {
            String username = loginUsernameField.getText();
            String password = new String(loginPasswordField.getPassword());

            try {
                User user = userDao.getUser(username);
                if (user != null && user.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(loginPanel, "��¼�ɹ�����ʼ��Ϸ!!!!");
                    global.user.setUsername(username);
                    System.out.println("��ǰ�û�: " + global.user.getUsername());
                    global.user.setScore(0);
                    ShootGame.GameInit();

                } else {
                    JOptionPane.showMessageDialog(loginPanel, "�û������������");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(loginPanel, "���ݿ����");
            }
        });

        JButton toRegisterButton = new JButton("ȥע��");
        toRegisterButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
        constraints.gridy = 4;
        loginPanel.add(toRegisterButton, constraints);

        toRegisterButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "RegisterPanel");
        });

        JButton toScoreButton = new JButton("���а�");
        toScoreButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
        constraints.gridy = 5;
        loginPanel.add(toScoreButton, constraints);

        toScoreButton.addActionListener(e -> {
            try {
                userDao.UpdateScore();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            updateRank();

            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "RankPanel");
        });

        return loginPanel;
    }

    private JPanel initRegister() {
        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setOpaque(false);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("ע��");
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 40));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        registerPanel.add(titleLabel, constraints);

        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        JLabel usernameLabel = SetTextFont.setFont("�û���:");
        registerPanel.add(usernameLabel, constraints);

        JTextField registerUsernameField = new JTextField(20);
        constraints.gridx = 1;
        registerPanel.add(registerUsernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        JLabel passwordLabel = SetTextFont.setFont("�� ��:");
        registerPanel.add(passwordLabel, constraints);

        JPasswordField registerPasswordField = new JPasswordField(20);
        constraints.gridx = 1;
        registerPanel.add(registerPasswordField, constraints);

        JButton registerButton = new JButton("ע��");
        registerButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        registerPanel.add(registerButton, constraints);

        registerButton.addActionListener(e -> {
            String username = registerUsernameField.getText();
            String password = new String(registerPasswordField.getPassword());

            try {
                if (userDao.getUser(username) != null) {
                    JOptionPane.showMessageDialog(registerPanel, "�û����Ѵ��ڣ�");
                } else {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setScore(0); // Default score
                    userDao.addUser(user);
                    JOptionPane.showMessageDialog(registerPanel, "ע��ɹ���");
                    CardLayout cl = (CardLayout) mainPanel.getLayout();
                    cl.show(mainPanel, "LoginPanel");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(registerPanel, "���ݿ����");
            }
        });

        JButton toLoginButton = new JButton("ȥ��½");
        toLoginButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
        constraints.gridy = 4;
        registerPanel.add(toLoginButton, constraints);

        toLoginButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "LoginPanel");
        });

        return registerPanel;
    }

    private JPanel initRank() {
        JPanel scorePanel = new JPanel(new BorderLayout());
        scorePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("���а�", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 40));
        scorePanel.add(titleLabel, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"����", "�û���", "����"}, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scorePanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("����");
        backButton.addActionListener(e -> {

            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "LoginPanel");
        });
        scorePanel.add(backButton, BorderLayout.SOUTH);

        return scorePanel;
    }

    private void updateRank() {
        try {
            List<User> users = userDao.getTopUsers();
            model.setRowCount(0); // Clear existing rows
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                model.addRow(new Object[]{i + 1, user.getUsername(), user.getScore()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rankPanel, "�޷��������а�");
        }
    }
}
