import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Programm {
    public static int width=400,height=250;
    public static void main(String[] args) {
        window();

    }

    public static void window() {
        JFrame w = new JFrame();
        JPanel p = new JPanel();
        JLabel l = new JLabel("Enter your CRISPR_IDs");
        JTextArea area = new JTextArea(5,30);
        JButton b1= new JButton("Enter");
        JButton b2 = new JButton("?");
        JScrollPane scroll = new JScrollPane(area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        p.setLayout(new FlowLayout());
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FindProtein.input(area.getText());
                w.setVisible(false);
            }
        });
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setBounds(100,100,width,height);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p.add(l);
        p.add(scroll);
        p.add(b1);
        p.add(b2);
        w.add(p);
        w.setResizable(false);
        w.setVisible(true);
    }
}