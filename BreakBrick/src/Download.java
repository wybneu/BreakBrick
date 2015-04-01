import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDletStateChangeException;

class Download extends Canvas implements CommandListener {
	private Image image;
	private Command exit;
	private LocalRecord record;
	private int lastLevel;
	HttpRecord http;

	public Download() {
		exit = new Command("Back", Command.EXIT, 1);
		try {
			image = Image.createImage("/background.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.addCommand(exit);
		this.setCommandListener(this);
		record = new LocalRecord();
		lastLevel = record.maxLvl;
		http = new HttpRecord(lastLevel);
		
		
	}

	protected void paint(Graphics g) {
		g.drawImage(image, 0, 0, Graphics.LEFT | Graphics.TOP);

		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD
				| Font.STYLE_ITALIC, Font.SIZE_LARGE));
		g.drawString("Download More Levels", getWidth() / 2, getHeight() / 2 - 40,
				Graphics.BASELINE | Graphics.HCENTER);

		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_UNDERLINED,
				Font.SIZE_MEDIUM));
		
		g.setColor(230,230,230);		
		if (http == null) {
			g.drawRect(20,  getHeight() / 2, this.getWidth()-40,20 );
			g.fillRect(20,  getHeight() / 2, this.getWidth()/2-40,20);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		} else if(http.downloadstuts==0){
			g.drawString("No new Levels available!", getWidth() / 2, getHeight() / 2  +20,
					Graphics.BASELINE | Graphics.HCENTER);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(http.downloadstuts==1){
			g.drawRect(20,  getHeight() / 2, this.getWidth()-40,20 );
			g.fillRect(20,  getHeight() / 2, this.getWidth()/2,20);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}else if (http.downloadstuts==2){
			g.drawRect(20,  getHeight() / 2, this.getWidth()-40,20 );
			g.fillRect(20,  getHeight() / 2, this.getWidth()-40,20);
			record.setLevels(http.levels);
			record.setMaxLevelNum(http.totalLevel);
			g.drawString("Download success!", getWidth() / 2, getHeight() / 2  +40,
					Graphics.BASELINE | Graphics.HCENTER);
		}else if(http.downloadstuts==4){
			g.drawString("Failed to download!", getWidth() / 2, getHeight() / 2  +20,
					Graphics.BASELINE | Graphics.HCENTER);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			g.drawRect(20,  getHeight() / 2, this.getWidth()-40,20 );
			g.fillRect(20,  getHeight() / 2, this.getWidth()/2+20,20);
			repaint();
		}
	}

	public void commandAction(Command arg0, Displayable arg1) {
		if (arg0 == exit) {
			Pages.getInstance().getB().getDisplay().setCurrent(
					Pages.getInstance().getM());
		}
	}

}