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
		
		String cmdString = "weblogo -f input.fa -F png -o right_logo.png -U probability -s large -A dna -t PAM";
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
		
		String cmdString = "weblogo -f input1.fa -F png -o left_logo.png -U probability -s large -A dna -t PAM";
		try { 
		Runtime.getRuntime().exec(cmdString);
		} catch (IOException e) { 
		resultPanel.Error("Weblogo_create_ERROR");
		e.printStackTrace(); 
		}
			

	}


	public static void logos(ArrayList<String> left,ArrayList<String> right){
		File leftLogo = new File("left_pam.png");
		File rightLogo = new File("right_pam.png");
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

//public static void download() {
//	try {
//		Thread.sleep(2000);
//	} catch (InterruptedException e) {
//		e.printStackTrace();
//	}
//	String s=System.getProperty("user.dir").substring(0,System.getProperty("user.dir").lastIndexOf("\\"));
//	File filePath = new File(s+Programm.name_of_directory);
//    filePath.mkdir();
//	
//	File f1 = new File(System.getProperty("user.dir")+"\\output.png");
//	File f2 = new File(filePath+"output.png");
//	f1.renameTo(f2);
//	File f3 = new File(System.getProperty("user.dir")+"\\spacer.txt");
//	File f4 = new File(filePath+"spacer.txt");
//	f3.renameTo(f4);
//	
//}

	private static void show_weblogo () {
		BufferedImage imgleft=null;
		BufferedImage imgright=null;
		try {
			 imgleft = ImageIO.read(new File("left_pam.png"));
			 imgright = ImageIO.read(new File("right_pam.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		resultPanel.leftPam.setIcon(new ImageIcon(resultPanel.resizeImage(imgleft,340,300)));
		resultPanel.rightPam.setIcon(new ImageIcon(resultPanel.resizeImage(imgright,340,300)));
	}
	
			
}
