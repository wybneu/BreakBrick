import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.TextField;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.midlet.MIDletStateChangeException;

class Menu extends Canvas implements CommandListener {
	private Image image;
	private Image image1;
	private Image image2;
	private Command exit;
	private Command enter;
	private int menu;
	Music music;
	private boolean isSet = false;
	private int set = 0;
	private LocalRecord record;
	Form form1;
	Command setName;
	TextField name;
	Command cls;
	Image bufImage;
	Graphics bufG;

	public Menu() {
		form1 = new Form("player Name");
		setName = new Command("setName", Command.SCREEN, 1);
		cls = new Command("Cls", Command.CANCEL, 1);
		name = new TextField("Input the name", "Someone", 20, TextField.ANY);
		form1.append(name);
		form1.addCommand(setName);
		form1.addCommand(cls);
		form1.setCommandListener(this);
		music = new Music("music.mid");
		// music.stop();
		menu = 0;
		exit = new Command("Exit", Command.EXIT, 1);
		enter = new Command("Music", Command.SCREEN, 1);
		try {
			image1 = Image.createImage("/background.png");
			image2 = Image.createImage(image1, 0, 0, image1.getWidth(),
					image1.getHeight(), Sprite.TRANS_ROT90);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.addCommand(exit);
		this.addCommand(enter);
		this.setCommandListener(this);
	}

	protected void paint(Graphics g1) {
		int x = this.getWidth();
		int y = this.getHeight();
		Graphics g;
		if (Config.landscape) {
			bufImage = Image.createImage(this.getHeight(), this.getWidth());
			bufG = bufImage.getGraphics();
			g = bufG;
			image = image2;
			x = this.getHeight();
			y = this.getWidth();
		} else {
			g = g1;
			image = image1;
		}
		if (this.isSet) {

			g.drawImage(image, 0, 0, Graphics.LEFT | Graphics.TOP);

			g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD
					| Font.STYLE_ITALIC, Font.SIZE_LARGE));
			g.drawString("Game Setting", x / 2, y / 2 - 40, Graphics.BASELINE
					| Graphics.HCENTER);

			g.setFont(Font.getFont(Font.FACE_PROPORTIONAL,
					Font.STYLE_UNDERLINED, Font.SIZE_MEDIUM));
			if (set == 0) {
				g.setColor(255, 0, 0);
			}
			g.drawString("Player name: " + Config.playerName, x / 2,
					y / 2 - 20, Graphics.BASELINE | Graphics.HCENTER);
			g.setColor(0, 0, 0);
			if (set == 1) {
				g.setColor(255, 0, 0);
			}
			g.drawString("Reset All", x / 2, y / 2, Graphics.BASELINE
					| Graphics.HCENTER);
			g.setColor(0, 0, 0);
			if (set == 2) {
				g.setColor(255, 0, 0);
			}
			g.drawString("portrait & landscape: "
					+ (!Config.landscape ? "Portrait" : "Landscape"), x / 2,
					y / 2 + 20, Graphics.BASELINE | Graphics.HCENTER);
			g.setColor(0, 0, 0);
			if (set == 4) {
				g.setColor(255, 0, 0);
			}
			g.drawString("Back", x / 2, y / 2 + 60, Graphics.BASELINE
					| Graphics.HCENTER);
			g.setColor(0, 0, 0);
			if (set == 3) {
				g.setColor(255, 0, 0);
			}
			String s;
			if (Config.speed == Config.fastSpeed)
				s = "Fast";
			else if (Config.speed == Config.normalSpeed)
				s = "Normal";
			else
				s = "slow";
			g.drawString("Speed: " + s, x / 2, y / 2 + 40, Graphics.BASELINE
					| Graphics.HCENTER);
			g.setColor(0, 0, 0);

		} else {

			g.drawImage(image, 0, 0, Graphics.LEFT | Graphics.TOP);

			g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD
					| Font.STYLE_ITALIC, Font.SIZE_LARGE));
			g.drawString("Brick Break Game", x / 2, y / 2 - 40,
					Graphics.BASELINE | Graphics.HCENTER);

			g.setFont(Font.getFont(Font.FACE_PROPORTIONAL,
					Font.STYLE_UNDERLINED, Font.SIZE_MEDIUM));
			if (menu == 0) {
				g.setColor(255, 0, 0);
			}
			g.drawString("New Game", x / 2, y / 2 - 20, Graphics.BASELINE
					| Graphics.HCENTER);
			g.setColor(0, 0, 0);
			if (menu == 1) {
				g.setColor(255, 0, 0);
			}
			g.drawString("Level Select", x / 2, y / 2, Graphics.BASELINE
					| Graphics.HCENTER);
			g.setColor(0, 0, 0);
			if (menu == 2) {
				g.setColor(255, 0, 0);
			}
			g.drawString("Download level", x / 2, y / 2 + 20, Graphics.BASELINE
					| Graphics.HCENTER);
			g.setColor(0, 0, 0);
			if (menu == 3) {
				g.setColor(255, 0, 0);
			}
			g.drawString("Score", x / 2, y / 2 + 40, Graphics.BASELINE
					| Graphics.HCENTER);
			g.setColor(0, 0, 0);
			if (menu == 4) {
				g.setColor(255, 0, 0);
			}
			g.drawString("Help", x / 2, y / 2 + 60, Graphics.BASELINE
					| Graphics.HCENTER);
			g.setColor(0, 0, 0);
			if (menu == 5) {
				g.setColor(255, 0, 0);
			}
			g.drawString("Setting", x / 2, y / 2 + 80, Graphics.BASELINE
					| Graphics.HCENTER);
		}

		if (Config.landscape) {
			g1.drawRegion(bufImage, 0, 0, bufImage.getWidth(),
					bufImage.getHeight(), Sprite.TRANS_ROT90, 0, 0,
					Graphics.LEFT | Graphics.TOP);
		}

	}

	public void keyReleased(int keyCode) {
		switch (this.getGameAction(keyCode)) {
		case LEFT:
		case KEY_NUM4:
			if (Config.landscape) {
				if (this.isSet) {
					if (set == 4) {
						set = 0;
					} else {
						set++;
					}
				} else {
					if (menu == 5) {
						menu = 0;
					} else {
						menu++;
					}
				}
				repaint();
				break;
			}
		case UP:
		case KEY_NUM2:		
			if (this.isSet) {
				if (set == 0) {
					set = 4;
				} else {
					set--;
				}
			} else {
				if (menu == 0) {
					menu = 5;
				} else {
					menu--;
				}
			}
			repaint();
			break;
			
		case RIGHT:
		case KEY_NUM6:
			if(Config.landscape){
				if (this.isSet) {
					if (set == 0) {
						set = 4;
					} else {
						set--;
					}
				} else {
					if (menu == 0) {
						menu = 5;
					} else {
						menu--;
					}
				}
				repaint();
				break;
			}
		case DOWN:
		case KEY_NUM8:
		
			if (this.isSet) {
				if (set == 4) {
					set = 0;
				} else {
					set++;
				}
			} else {
				if (menu == 5) {
					menu = 0;
				} else {
					menu++;
				}
			}
			repaint();
			break;
		case FIRE:
		case KEY_STAR:
		case KEY_NUM5:
			if (!this.isSet) {

				if (menu == 0) {
					Pages.getInstance().getB().getDisplay()
							.setCurrent(new Games());
				}
				if (menu == 1) {
					Pages.getInstance().getB().getDisplay()
							.setCurrent(new LevelChooser());
				}
				if (menu == 2) {
					Pages.getInstance().getB().getDisplay()
							.setCurrent(new Download());
				}
				if (menu == 3) {
					Pages.getInstance().getB().getDisplay()
							.setCurrent(new Result());
				}
				if (menu == 4) {
					Pages.getInstance().getB().getDisplay()
							.setCurrent(new Help());
				}
				if (menu == 5) {
					this.isSet = true;
					this.removeCommand(exit);
					repaint();
				}
			} else {
				if (set == 0) {
					Pages.getInstance().getB().getDisplay().setCurrent(form1);
				}
				if (set == 1) {
					record = new LocalRecord();
					record.reset();
					this.isSet = false;
					repaint();
				}
				if (set == 2) {
					Config.landscape = !Config.landscape;
					repaint();
				}
				if (set == 4) {
					this.isSet = false;
					repaint();
				}
				if (set == 3) {
					if (Config.speed == Config.fastSpeed)
						Config.speed = Config.normalSpeed;
					else if (Config.speed == Config.normalSpeed)
						Config.speed = Config.slowSpeed;
					else if (Config.speed == Config.slowSpeed)
						Config.speed = Config.fastSpeed;
					repaint();
				}

				if (!this.isSet)
					this.addCommand(exit);
			}
		}

	}

	public void commandAction(Command arg0, Displayable arg1) {
		if (arg0 == enter) {
			if (music.isplay) {
				music.stop();
			} else {
				music.play();
			}
		}
		if (arg0 == cls) {
			name.setString("");
		}

		if (arg0 == setName) {

			Config.playerName = name.getString();
			Pages.getInstance().getB().getDisplay()
					.setCurrent(Pages.getInstance().getM());
		}
		if (arg0 == exit) {

			Pages.getInstance().getB().notifyDestroyed();
		}
	}

}