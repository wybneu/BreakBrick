
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.*;
import javax.microedition.midlet.MIDletStateChangeException;
import java.util.*;


public class SnowBackground extends Canvas {
	private int w,h;
	private  int[] stars;
	private  Random generator=new Random();
	private boolean pause;
	private Timer timer=new Timer();
	private FieldMover mover=new FieldMover();
	public SnowBackground(){
		pause=true;
		h=this.getHeight();
		w=this.getWidth();
		stars=new int[h];
		for (int i=0;i<h;i++){
			stars[i]=-1;
		}
		timer.schedule(mover, 100,100);
	}
	public void stop(){
		pause=false;
	}
	public void start(){
		pause=true;
	}
	public void scroll(){
		for(int i=h-1;i>0;i--){
			stars[i]=stars[i-1];
		}
		stars[0]=Math.abs(generator.nextInt()% w)*2;
		if(stars[0]>w){
			stars[0]=-1;
		}
		repaint();
	}
	protected void paint(Graphics g) {
		g.setColor(0,0,0);
		g.fillRect(0, 0, w, h);
		g.setColor(255,250,255);
		for(int y=0;y<h;y++){
			int x=stars[y];
			if(x==-1){
				
				continue;
			}
			g.drawLine(x, y, x+2, y+2);
		}
	}
	
	class FieldMover extends TimerTask{

		public void run() {
			while (pause)
				scroll();
		}
		
	}
	
}
