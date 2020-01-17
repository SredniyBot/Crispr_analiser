import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WebLogo {

	/*Получение изобрадение веблого в корне проета
	 * @param s лист с памами
	*/
	public static void logo(ArrayList<String> s) {//получение веблого
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
	    	Panel.ERROR_LIST.add("input.fa ERROR");
	        System.out.println(ex.getMessage());
	    }
		
		String cmdString = "weblogo -f input.fa -F png -o right_logo.png -U probability -s large -A dna -t PAM";
		try { 
		Runtime.getRuntime().exec(cmdString); 
		} catch (IOException e) { 
		e.printStackTrace(); 
		Panel.ERROR_LIST.add("Weblogo_create_ERROR");
		}
			

	}
	public static void logo1(ArrayList<String> s) {//получение веблого
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
			Panel.ERROR_LIST.add("input.fa ERROR");
	        System.out.println(ex.getMessage());
	    }
		
		String cmdString = "weblogo -f input1.fa -F png -o left_logo.png -U probability -s large -A dna -t PAM";
		try { 
		Runtime.getRuntime().exec(cmdString);
		} catch (IOException e) { 
		Panel.ERROR_LIST.add("Weblogo_create_ERROR");
		e.printStackTrace(); 
		}
			

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

	public static void show_web_logo () {
		
	}
	
			
}
