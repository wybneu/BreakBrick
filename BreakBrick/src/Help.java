import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

class Help extends Canvas implements CommandListener {
	private int maxpages=3;
	private int currentPage=0;
	private Command exit;
	SnowBackground helpBK;
	public Help() {
		exit = new Command("Back", Command.EXIT, 1);
		this.addCommand(exit);
		this.setCommandListener(this);
		helpBK=new SnowBackground();
		helpBK.start();
	}

	protected void paint(Graphics g) {
		helpBK.paint(g);
		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD
				| Font.STYLE_ITALIC, Font.SIZE_LARGE));
		g.drawString("Instrucion", getWidth() / 2, getHeight() / 2 - 60,
				Graphics.BASELINE | Graphics.HCENTER);

		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_UNDERLINED,
				Font.SIZE_MEDIUM));
		g.drawString("Do not Let the ball below the bottom",  getWidth() / 2, getHeight() / 2 - 40,
				Graphics.BASELINE | Graphics.HCENTER);
		g.drawString("Use the ball to clear all the bricks", getWidth() / 2, getHeight() / 2 - 20,
				Graphics.BASELINE | Graphics.HCENTER);
		g.drawString("Yellow for extra life, Green for balls!", getWidth() / 2, getHeight() / 2,
				Graphics.BASELINE | Graphics.HCENTER);
		g.drawString("Black for random size of bar!", getWidth() / 2, getHeight() / 2+20,
				Graphics.BASELINE | Graphics.HCENTER);
		g.drawString("Yingbo Wang  ID: 349149", getWidth() / 2, getHeight() / 2+40,
				Graphics.BASELINE | Graphics.HCENTER);
		g.drawString("Danping Lei  ID: 382665", getWidth() / 2, getHeight() / 2+60,
				Graphics.BASELINE | Graphics.HCENTER);
		g.drawString("Xuechong Han ID: 369389", getWidth() / 2, getHeight() / 2+80,
				Graphics.BASELINE | Graphics.HCENTER);
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();

	}

	
	public void commandAction(Command arg0, Displayable arg1) {
			
		if (arg0 == exit) {
			helpBK.stop();
			Pages.getInstance().getB().getDisplay().setCurrent(Pages.getInstance().getM());
		}
	}

}