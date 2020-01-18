import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

public class Program {
    public static String GENOME;
    public static boolean wide_search = false, show_weblogo = false, download_results = false;//� ����������
    public static String name_of_directory = "new";
    private final static String btn1 = "\nEnter\n", btn2 = "Find proteins",btn3="?", check1 = "Wide search", check2 = "Download results", check3 = "Show WebLogo";
    private static JFrame w = new JFrame("CRISPR ANALYSER");
    private static SecThread SecondThread;
    private static JTextArea area;
    public static void main(String[] args) {    //������� �����
        window();
    }

    private static void window() {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEADING));
        Image img = null;
        try {
            img = ImageIO.read(new File("src/PAM_PIC.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            Panel.ERROR_LIST.add("IMAGE_ERROR");
        }
        area = new JTextArea(4, 40);
        JScrollPane scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        p.add(scroll);
        p.add(createButton(605, 0, 80, 80, btn1));
        p.add(createJCheckBox(10, 50, 101, 20, check1));
        p.add(createJCheckBox(250, 50, 125, 20, check2));
        p.add(createJCheckBox(480, 50, 125, 20, check3));
        p.add(createButton(10, 10, 10, 10, btn2));
        p.add(createButton(10, 10, 10, 10, btn3));
        w.add(p);
        w.setIconImage(img);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setSize(560, 170);
        w.setResizable(false);
        w.setVisible(true);
    }


    private static JButton createButton(int x, int y, int width, int height, String Name) {
        JButton b = new JButton(Name);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (Name) {
                    case btn1:
                        if(Find_Repeats.rightData(area.getText())){
                            window_with_panel(area.getText());
                            w.setVisible(false);
                        }else {
                            JOptionPane.showMessageDialog(null,"Wrong input data.\nCheck tutorial.");
                        }

                        break;
                    case btn2:
                        Programm.main(null);
                        w.setVisible(false);
                        break;
                    case btn3:
                        Tutorial t = new Tutorial("src/res/tutorial2/");
                        t.showFrame();
                        break;
                }
            }
        });
        b.setBounds(x, y, width, height);
        return b;
    }


    private static JCheckBox createJCheckBox(int x, int y, int width, int height, String Name) {
        JCheckBox checkbox = new JCheckBox(Name);
        checkbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                switch (Name) {
                    case check2:
                        download_results = !download_results;
                        if (download_results) {
                            name_of_directory = JOptionPane.showInputDialog("Insert name of new directory");
                        }
                        break;
                    case check1:
                        wide_search = !wide_search;
                        break;
                    case check3:
                        show_weblogo = !show_weblogo;
                        break;
                }
            }
        });
        checkbox.setBounds(x, y, width, height);
        return checkbox;
    }


    private static void window_with_panel(String s) {// ���� � ������������ ����������
        GENOME = s;
        SecondThread = new SecThread();
        SecondThread.start();
        //Panel.REZIM=1;
        JFrame w = new JFrame("RESULT");
        Image img = null;
        try {
            img = ImageIO.read(new File("src/PAM_PIC.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            Panel.ERROR_LIST.add("IMAGE_ERROR");
        }
        w.setIconImage(img);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setSize(700, 700);
        w.setResizable(false);
        Panel p = new Panel();
        w.addMouseWheelListener(p);
        w.add(p);
        w.setVisible(true);

    }
}

class SecThread extends Thread {
    @Override
    public void run() {
        Find_Repeats.start(Program.GENOME);
    }
}
