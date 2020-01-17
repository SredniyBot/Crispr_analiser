import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Panel extends JPanel implements MouseWheelListener{
	private static final long serialVersionUID = 1L;
public static int REZIM=0;
public static int shift;
public static boolean shift_needed=false;
public static String STATUS="Trying to find repeats and spacers";
public static ArrayList<String> ERROR_LIST = new ArrayList<String>();
	public void paint(Graphics g) {//метод отрисовки панели
		Color bgcolor = new Color(200,230,250);
		g.setColor(bgcolor);
		g.fillRect(0,0,1000,1000);
		ERROR_LIST_prtint(g);
		
		g.setColor(Color.BLACK);
		Font f = new Font("Ariel", Font.PLAIN, 12);
		Rectangle2D r = f.getStringBounds("Status: "+STATUS, new FontRenderContext(null, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT, RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT));
		g.drawString("Status: "+STATUS,(int)(670-r.getWidth()),650);
		if(REZIM==0) {
			g.drawString("Trying to find repeats and spacers",45,15);
		} else if(REZIM==10) {
			paint_repeats_and_spacers(Find_Repeats.zapolnenie_massiva(), g);
		}else if(REZIM==2) {
			g.drawString("Done",45,15);
		}
		
		repaint(1000);
	}
	
	
	
	/*
	 * отображение повторов и спейсеров
	 * @param casset лист с повторами-спейсерами в правильном порядке
	 */
	public static void paint_repeats_and_spacers(ArrayList<String> casset,Graphics g) {
		for(int i =0;i<casset.size();i++) {
			
			Font f = new Font("Ariel", Font.PLAIN, 12);
			Rectangle2D r = f.getStringBounds(casset.get(i), new FontRenderContext(null, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT, RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT));			
			if(shift<=(int)(casset.size()*r.getHeight()+2)-400) {
				shift=(int)(casset.size()*r.getHeight()+2)-400;
			}
			if((casset.size()-1)*r.getHeight()+2>400) {
				shift_needed=true;
			}
			if((15+r.getHeight()*i)+shift<400&&(15+r.getHeight()*i)+shift>=10) {
			if(i%2==0) {
			g.setColor(Color.YELLOW);
			g.fillRect(10,(int) (2+r.getHeight()*i)+shift,(int)r.getWidth(),(int)r.getHeight());
			g.setColor(Color.BLACK);
			g.drawString(casset.get(i),10,(int) (15+r.getHeight()*i)+shift);
			}else {
				
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect((int) (670-r.getWidth()),(int) (2+r.getHeight()*i)+shift,(int)r.getWidth(),(int)r.getHeight());
			g.setColor(Color.BLACK);
			g.drawString(casset.get(i),(int) (670-r.getWidth()),(int) (15+r.getHeight()*i)+shift);
			}
			}else {
				g.setColor(Color.BLACK);
				g.drawString("Scroll to see next",300,400);
			}
			
		}
		
		
		
		
	}


	
	public static void ERROR_LIST_prtint(Graphics g) {
	if(!ERROR_LIST.isEmpty()) {
			for(int i =0;i<ERROR_LIST.size();i++) {
				
				Font f = new Font("Ariel", Font.PLAIN, 6);
				Rectangle2D r = f.getStringBounds(ERROR_LIST.get(i), new FontRenderContext(null, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT, RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT));			
				if(shift<=(int)(ERROR_LIST.size()*r.getHeight()+2)-400) {
					shift=(int)(ERROR_LIST.size()*r.getHeight()+2)-400;
				}
				
				g.setColor(Color.RED);
				g.drawString(ERROR_LIST.get(i),2,(int) (650-r.getHeight()*i));
			}
	}
	}
	
	

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(shift_needed) {
		shift+=e.getWheelRotation()*2;
		if(shift>10) {
			shift=10;
		}
	}
	}
}
