import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tutorial {


    public static void main(String[] args) {
        showFrame();
    }
    public static void showFrame(){
        BufferedImage img=null;
        try {
            img = ImageIO.read(new File("src/res/1.png")); // Считываем картинку
        } catch(IOException ioe) {
            JOptionPane.showConfirmDialog(null, "Что-то неправильно!\n" + ioe.toString(),
                    "Error!", JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }
        JLabel jl = new JLabel(new ImageIcon(resizeImage(img,600,400)));



        JFrame w = new JFrame();
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER));
        p.add(jl);
        JButton b1 = new JButton("<-");
        JLabel tf = new JLabel("1234");
        JButton b2 = new JButton("->");
        p.add("LEFT",b1);
        p.add(tf);
        p.add("LEFT",b2);
        w.add(p);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setSize(600, 600);
        w.setResizable(false);
        w.setVisible(true);
    }

    public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        int type = (image.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage bi = new BufferedImage(width, height, type);
        Graphics2D graphics = bi.createGraphics();
        graphics.drawImage(image, 0, 0, width, height, null);
        graphics.dispose();
        return bi;
    }
}
