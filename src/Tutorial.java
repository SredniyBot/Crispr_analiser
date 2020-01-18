import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Tutorial {
    private int Number=1,maxNumber=2;
    private String Path;
   Tutorial(String Path){
       this.Path=Path;
       getFiles(Path);
   }

    public void showFrame(){
        JFrame w = new JFrame("Tutorial");
        JPanel p = new JPanel();
        JLabel jl = new JLabel(new ImageIcon(tutorialImage(Number)));
        JButton b1 = new JButton("<-");
        JButton b2 = new JButton("->");
        JLabel tf = new JLabel(Number+"/"+maxNumber);

        p.setLayout(new FlowLayout(FlowLayout.CENTER));
        b1.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 if(Number==1){
                     Number=maxNumber;
                 }else{
                     Number--;
                 }
                 tf.setText(Number+"/"+maxNumber);
                 jl.setIcon(new ImageIcon(tutorialImage(2)));
             }
         });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Number==maxNumber){
                    Number=1;
                }else{
                    Number++;
                }
                tf.setText(Number+"/"+maxNumber);
                jl.setIcon(new ImageIcon(tutorialImage(2)));
            }
        });
        p.add(jl);
        p.add("LEFT",b1);
        p.add(tf);
        p.add("LEFT",b2);
        w.add(p);
        w.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        w.setSize(600, 500);
        w.setResizable(false);
        w.setVisible(true);

        File f = new File("");
        f.exists();

    }
    public void getFiles(String Path){
       if(new File(Path+1).exists()) {
           while (new File(Path + (int)(Number+1)).exists()){
               maxNumber++;
               Number++;
           }
       }else{
           Path="src/";
       }

    }

    private  Image tutorialImage(int number){
        BufferedImage img=null;
        try {
            img = ImageIO.read(new File(Path+Number+".png")); // Считываем картинку
        } catch(IOException ioe) {

            System.out.println("Не нашел изображения"+Path);
        }
        return resizeImage(img,600,400);
    }
    private  BufferedImage resizeImage(BufferedImage image, int width, int height) {
        int type = (image.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage bi = new BufferedImage(width, height, type);
        Graphics2D graphics = bi.createGraphics();
        graphics.drawImage(image, 0, 0, width, height, null);
        graphics.dispose();
        return bi;
    }
}
