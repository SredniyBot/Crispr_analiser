import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import javax.imageio.ImageIO;
import javax.swing.*;

public class resultPanel extends JPanel{
	private static String REPEATS_N_SPACERS="";
	private static JTextArea REPEATS_N_SPACERS_AREA;
	public static JLabel leftPam,rightPam,Status=new JLabel();

	resultPanel(JTextArea REPEATS_N_SPACERS_AREA){
		resultPanel.REPEATS_N_SPACERS_AREA=REPEATS_N_SPACERS_AREA;
		Status("Trying to find repeats and spacers");
		try {
			leftPam = new JLabel();
			rightPam = new JLabel();
			leftPam.setIcon(new ImageIcon(
					resizeImage((BufferedImage)ImageIO.read(
							new File("src/res/icons/2.png")),330,300)));
			rightPam.setIcon(new ImageIcon(
					resizeImage((BufferedImage)ImageIO.read(
							new File("src/res/icons/2.png")),330,300)));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * ����������� �������� � ���������
	 * @param casset ���� � ���������-���������� � ���������� �������
	 */
	public static void paint_repeats_and_spacers(ArrayList<String> casset) {
		for(int i =0;i<casset.size();i++) {
			if (i % 2 != 0) {
				for (int l = 120; l > casset.get(i).length(); l--) REPEATS_N_SPACERS += " ";
			}
			REPEATS_N_SPACERS+=casset.get(i)+"\n";
		}
		REPEATS_N_SPACERS_AREA.setText(REPEATS_N_SPACERS);
	}

	public static void Status(String st){
		Status.setText(st);
	}

	public static void Error(String message){
		JOptionPane.showMessageDialog(null,message,"ERROR",0);
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
