import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WebLogo {

	/*��������� ����������� ������� � ����� ������
	 * @param s ���� � ������
	 */
	private static void logo(ArrayList<String> s) {//��������� �������
		try(FileWriter writer = new FileWriter("input.fa", false))
		{
			String h = "";
			for(int i=0;i<s.size();i++) {
				h+=s.get(i)+"\n";
			}
			writer.write(h);
			System.out.println(h);
			writer.flush();
		}
		catch(IOException ex){
			resultPanel.Error("input.fa ERROR");
			System.out.println(ex.getMessage());
		}
		System.out.println("qwert");
		String cmdString = "weblogo -f input.fa -F png -o src/res/logos/right_logo.png -U probability -s large -A dna -t RIGHT_PAM";
		try {
			Runtime.getRuntime().exec(cmdString);
		} catch (IOException e) {
			e.printStackTrace();
			resultPanel.Error("Weblogo_create_ERROR");
		}
	}
	private static void logo1(ArrayList<String> s) {//��������� �������
		try(FileWriter writer = new FileWriter("input1.fa", false))
		{
			String h = "";
			for(int i=0;i<s.size();i++) {
				h+=s.get(i)+"\n";
			}
			writer.write(h);
			System.out.println(h);
			writer.flush();
		}
		catch(IOException ex){
			resultPanel.Error("input.fa ERROR");
			System.out.println(ex.getMessage());
		}
		System.out.println("qwert");
		String cmdString = "weblogo -f input1.fa -F png -o src/res/logos/left_logo.png -U probability -s large -A dna -t LEFT_PAM";
		try {
			Runtime.getRuntime().exec(cmdString);
		} catch (IOException e) {
			resultPanel.Error("Weblogo_create_ERROR");
			e.printStackTrace();
		}


	}


	public static void logos(ArrayList<String> left,ArrayList<String> right){
		resultPanel.Status("creating WEBLOGO");
		File leftLogo = new File("src/res/logos/left_logo.png");
		File rightLogo = new File("src/res/logos/right_logo.png");
		if(leftLogo.exists()){
			leftLogo.delete();
		}
		if(rightLogo.exists()){
			rightLogo.delete();
		}
		logo1(left);
		logo(right);
		show_weblogo();
	}


	private static void show_weblogo () {
		BufferedImage imgleft=null;
		BufferedImage imgright=null;
		try {
			try {
				while(!new File("src/res/logos/left_logo.png").exists()&&!new File("src/res/logos/right_logo.png").exists()){
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}


			imgleft = ImageIO.read(new File("src/res/logos/left_logo.png"));
			imgright = ImageIO.read(new File("src/res/logos/right_logo.png"));

		} catch (IOException  e) {
			e.printStackTrace();
		}
		resultPanel.leftPam.setIcon(new ImageIcon(resultPanel.resizeImage(imgleft,340,300)));
		resultPanel.rightPam.setIcon(new ImageIcon(resultPanel.resizeImage(imgright,340,300)));
	}


}
