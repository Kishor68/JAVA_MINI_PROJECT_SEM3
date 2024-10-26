package Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyMenu extends JPanel {
    private AppInterface parent;

    public MyMenu(AppInterface p) {
        super(null);
        this.parent = p;
        setSize(900, 500);
        setBackground(Color.ORANGE);
        JButton x4 = new JButton("4x4 Puzzle");
        JButton x3 = new JButton("3x3 Puzzle");
        JButton co = new JButton("PvP mode");
        JButton ex = new JButton("Exit");
        x4.setBounds(300, 100, 300, 50);
        x3.setBounds(300, 170, 300, 50);
        co.setBounds(300, 240, 300, 50);
        ex.setBounds(300, 310, 300, 50);
        add(x4);
        add(x3);
        add(co);
        add(ex);
        ex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.destroy();
            }
        });
        x4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.call4x4();
            }
        });
        x3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.call3x3();
            }
        });
        co.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.callComp();
            }
        });
    }
}
