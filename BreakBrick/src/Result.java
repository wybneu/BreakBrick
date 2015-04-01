import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

class Result extends Canvas implements CommandListener {
	private Image image;
	private Command exit;
	private Command enter;
	public HttpRecord score;
	public LocalRecord record;
	public Result() {

		exit = new Command("back", Command.EXIT, 1);
		enter = new Command("ReStart", Command.SCREEN, 1);
		try {
			image = Image.createImage("/background.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.addCommand(exit);
		this.addCommand(enter);
		this.setCommandListener(this);
		
		score = new HttpRecord();
		this.record=new LocalRecord();
	}

	public Result(String name, int i,LocalRecord record1) {
		this.record=record1;
		exit = new Command("back", Command.EXIT, 1);
		enter = new Command("ReStart", Command.SCREEN, 1);
		try {
			image = Image.createImage("/background.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.addCommand(exit);
		this.addCommand(enter);
		this.setCommandListener(this);

		score = new HttpRecord(name, i);
	}

	protected void paint(Graphics g) {
		g.drawImage(image, 0, 0, Graphics.LEFT | Graphics.TOP);
		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD
				| Font.STYLE_ITALIC, Font.SIZE_LARGE));
		g.drawString("Score", getWidth() / 2, getHeight() / 4,
				Graphics.BASELINE | Graphics.HCENTER);
		long s1 = System.currentTimeMillis();
		boolean painted = false;
		while ((System.currentTimeMillis() - s1) < 100) {
			if (score.isConnected == true) {
				score.paint(g);
				painted = true;
				int records[]=new int[Config.maxScoreNum];
				for(int i1=0;i1<Config.maxScoreNum;i1++){
					records[i1]=score.record[i1].getScore();
				}
				record.setRecord(records);
				break;
			}
		}
		if (painted == false) {
			g.drawString("Disconnected Try again!", getWidth() / 2,
					getHeight() / 4 + 20, Graphics.BASELINE | Graphics.HCENTER);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if ((System.currentTimeMillis() - s1) > 3000)
				return;
			repaint();
		}

	}

	public void commandAction(Command arg0, Displayable arg1) {
		if (arg0 == enter) {
			Pages.getInstance().getB().getDisplay().setCurrent(new Games());
		}
		if (arg0 == exit) {
			Pages.getInstance().getB().getDisplay()
					.setCurrent(Pages.getInstance().getM());
		}
	}

}