import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Program {
	public static String GENOME;
	public static boolean wide_search=false,show_weblogo=false,download_results =false;//В разработке
	public static String name_of_directory="new";
	private final static String btn1 = "\nEnter\n",btn2 = "Find proteins",chck1="Wide search",chck2="Download results",chck3="Show WebLogo";
	private static JFrame w = new JFrame("CRISPR ANALYSER");
	private static SecThread SecondThread;
	private static JTextArea area;
	public static void main(String[] args) {	//главный метод
		window();
	}


	public static void window() {
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.CENTER));
		Image img = null;
		try {
			img = ImageIO.read(new File("src/PAM_PIC.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
			Panel.ERROR_LIST.add("IMAGE_ERROR");
		}

		area = new JTextArea(4,36);
		JScrollPane scroll = new JScrollPane(area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		p.add(scroll);
		p.add(createButton(605, 0, 80, 80,btn1));
		p.add(createJCheckBox(10, 50, 101, 20,chck1));
		p.add(createJCheckBox(250, 50, 125, 20,chck2));
		p.add(createJCheckBox(480, 50, 125, 20,chck3));
		p.add(createButton(10, 10, 10, 10,btn2));
		w.add(p);
		w.setIconImage(img);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setSize(540,150);
		w.setResizable(false);
		w.setVisible(true);
	}


	private static JButton createButton(int x,int y,int width,int height,String Name){
		JButton b = new JButton(Name);
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (Name){
					case btn1:

						window_with_panel(area.getText());
						w.setVisible(false);
						break;
					case btn2:
						Programm.main(null);
						w.setVisible(false);
						break;
				}
			}
		});
		b.setBounds(x, y, width, height);
		return  b;
	}


	private static JCheckBox createJCheckBox(int x,int y,int width,int height,String Name){
		JCheckBox chbox = new JCheckBox(Name);
		chbox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				switch (Name){
					case chck2:
						download_results =!download_results;
						if(download_results) {
							name_of_directory=JOptionPane.showInputDialog("Insert name of new directory");
						}
						break;
					case chck1:
						wide_search=!wide_search;
						break;
					case chck3:
						show_weblogo=!show_weblogo;
						break;
				}
			}
		});
		chbox.setBounds(x,y,width,height);
		return chbox;
	}



	public static void window_with_panel(String s) {// окно с отображением результата
		GENOME=s;
		SecondThread = new SecThread();	
		SecondThread.start();	
		//Panel.REZIM=1;
		JFrame w=new JFrame("RESULT");
		Image img = null;
		try {
			img = ImageIO.read(new File("src/PAM_PIC.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
			Panel.ERROR_LIST.add("IMAGE_ERROR");
		}
		w.setIconImage(img);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setSize(700,700);
		w.setResizable(false);
		Panel p = new Panel();
		w.addMouseWheelListener(p);
		w.add(p);
		w.setVisible(true);
		
	}

}



class SecThread extends Thread//класс отвечающий за многопоточность
{
	@Override
	public void run()	
	{
		Find_Repeats.start(Program.GENOME);
	}
}
