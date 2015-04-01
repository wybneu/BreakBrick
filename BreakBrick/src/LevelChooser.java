import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

class LevelChooser extends Canvas implements CommandListener {
	private Image image;
	private Command exit;
	private int menu;
	LocalRecord record;
	private int startDrawLevel;
	private int endDrawLevel;

	public LevelChooser() {
		startDrawLevel = 0;
		menu = 0;
		exit = new Command("Back", Command.EXIT, 1);
		try {
			image = Image.createImage("/background.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		record = new LocalRecord();
		getDrawLevel();
		this.addCommand(exit);
		this.setCommandListener(this);
//		System.out.println(record.maxLvl);
//		System.out.println(record.extraLevel.length);
	}

	public void getDrawLevel() {
		endDrawLevel = record.maxLvl;
		if ((record.maxLvl - this.startDrawLevel) > 4)
			this.endDrawLevel = this.startDrawLevel + 4;
	}

	protected void paint(Graphics g) {
		g.drawImage(image, 0, 0, Graphics.LEFT | Graphics.TOP);

		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD
				| Font.STYLE_ITALIC, Font.SIZE_LARGE));
		g.drawString("Choose your level", getWidth() / 2, getHeight() / 2 - 40,
				Graphics.BASELINE | Graphics.HCENTER);
		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_UNDERLINED,
				Font.SIZE_MEDIUM));
		getDrawLevel();
		for (int i = this.startDrawLevel; i < endDrawLevel; i++) {
			if (menu == i) {
				g.setColor(255, 0, 0);
			}
			g.drawString("Level " + (i + 1), getWidth() / 2, getHeight() / 2
					- 20 + 20 * (i-startDrawLevel), Graphics.BASELINE | Graphics.HCENTER);
			g.setColor(0, 0, 0);
		}
	}

	public void keyReleased(int keyCode) {
		switch (this.getGameAction(keyCode)) {
		case UP:
		case KEY_NUM2:
		case LEFT:
		case KEY_NUM4:
			if (menu == 0) {
				menu = record.maxLvl - 1;
				startDrawLevel = record.maxLvl - 3;
			} else {
				menu--;
				startDrawLevel = menu;
			}
			repaint();
			break;
		case DOWN:
		case KEY_NUM8:
		case RIGHT:
		case KEY_NUM6:
			if (menu == record.maxLvl - 1) {
				menu = 0;
				startDrawLevel = 0;
			} else {
				menu++;
				if (menu == 0)
					startDrawLevel = 0;
				else
					startDrawLevel = menu - 1;
			}
			repaint();
			break;
		case FIRE:
		case KEY_STAR:
		case KEY_NUM5:
			for (int i = 0; i < record.maxLvl; i++) {
				if (menu == i) {
					Pages.getInstance().getB().getDisplay()
							.setCurrent(new Games(i+1));
				}
			}
		}

	}

	public void commandAction(Command arg0, Displayable arg1) {
		if (arg0 == exit) {
			Pages.getInstance().getB().getDisplay()
					.setCurrent(Pages.getInstance().getM());
		}
	}

}