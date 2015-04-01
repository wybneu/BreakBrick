import java.io.IOException;
import java.io.InputStream;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.StringItem;

public class LevelLoder implements CommandListener {
	private Brick[][] brick;
	int brick_num = 0;
	int row_num = 0;
	int colum_num = 0;
	Form failed;
	Command cmdback;
	StringItem is;
	int barLength = 2;// 1 for short 2for normal 3 for long
	int ballX=Config.screenWidth/2;
	int ballY=Config.screenHeight/2;
	int ballSpeedX=-2;
	int ballSpeedY=-2;
	boolean load=false;

	public LevelLoder() {
		failed = new Form("failed");
		cmdback = new Command("back", Command.CANCEL, 1);
		failed.addCommand(cmdback);
		failed.setCommandListener(this);
		is = new StringItem(null, null);
		failed.append(is);

	}

	public void processLevelConfig(String filepath) {
		// read the config txt file
		
		String config = configLoader(filepath);
		setBrick(config);
		configProcess(config);
	}

	public void setBrick(String config) {
		try {
			load=false;
			StringTokenizer st1 = new StringTokenizer(config, "|");
			row_num = st1.countTokens();
			colum_num = 0;
			while (st1.hasMoreTokens()) {
				String s1 = st1.nextToken();
				StringTokenizer st2 = new StringTokenizer(s1, "/");
				if (st2.countTokens() > colum_num) {
					colum_num = st2.countTokens();
				}
			}
		} catch (Exception e) {
			is.setLabel("Failed to understand level files, please resteup or redownload level files for the game");
			Pages.getInstance().getB().getDisplay().setCurrent(failed);
		}
		if (row_num == 0 && colum_num == 0) {
			is.setLabel("Failed to understand level files, please resteup or redownload level files for the game");
			Pages.getInstance().getB().getDisplay().setCurrent(failed);
		}
		this.brick = new Brick[row_num][colum_num];
	}

	public void configProcess(String config) {
		// TODO Auto-generated method stub
		Image brickImage;
		try {
			brickImage = Image.createImage("/brick.png");

			if (!config.startsWith("level")) {
				StringTokenizer st1 = new StringTokenizer(config, "|");
				int row_index = 0;

				while (st1.hasMoreTokens()) {
					String s1 = st1.nextToken();
					StringTokenizer st2 = new StringTokenizer(s1, "/");
					int colum_index = 0;
					colum_num = st2.countTokens();

					while (st2.hasMoreTokens()) {
						String s2 = st2.nextToken();
						StringTokenizer st3 = new StringTokenizer(s2, ",");

						String s3 = st3.nextToken();
						String s4 = st3.nextToken();
						this.brick[row_index][colum_index] = new Brick(
								brickImage, Config.brickWidth,
								Config.brickHeight);
						this.brick[row_index][colum_index].setPosition(
								Integer.parseInt(s3), Integer.parseInt(s4));
						colum_index++;
					}
					row_index++;
				}
			} else {
				StringTokenizer st1 = new StringTokenizer(config, "\n");
				while(st1.hasMoreTokens()){
					String s=st1.nextToken();
					if(s.startsWith("bar")){
						StringTokenizer st2 = new StringTokenizer(s, ":");
						st2.nextToken();
						this.barLength=Integer.parseInt(st2.nextToken());
					}
					if(s.startsWith("ballX")){
						StringTokenizer st2 = new StringTokenizer(s, ":");
						st2.nextToken();
						this.ballX=Integer.parseInt(st2.nextToken());
					}
					if(s.startsWith("ballY")){
						StringTokenizer st2 = new StringTokenizer(s, ":");
						st2.nextToken();
						this.ballY=Integer.parseInt(st2.nextToken());
					}
					if(s.startsWith("ballSpeedX")){
						StringTokenizer st2 = new StringTokenizer(s, ":");
						st2.nextToken();
						this.ballSpeedX=Integer.parseInt(st2.nextToken());
					}
					if(s.startsWith("ballSpeedY")){
						StringTokenizer st2 = new StringTokenizer(s, ":");
						st2.nextToken();
						this.ballSpeedY=Integer.parseInt(st2.nextToken());
					}
					if(s.startsWith("brick")){
						StringTokenizer st2 = new StringTokenizer(s, ":");
						st2.nextToken();
						s=st2.nextToken();
						StringTokenizer st3 = new StringTokenizer(s, "|");
						int row_index = 0;

						while (st3.hasMoreTokens()) {
							String s1 = st3.nextToken();
							StringTokenizer st4 = new StringTokenizer(s1, "/");
							int colum_index = 0;
							colum_num = st4.countTokens();

							while (st4.hasMoreTokens()) {
								String s2 = st4.nextToken();
								StringTokenizer st5 = new StringTokenizer(s2, ",");

								String s3 = st5.nextToken();
								String s4 = st5.nextToken();
								this.brick[row_index][colum_index] = new Brick(
										brickImage, Config.brickWidth,
										Config.brickHeight);
								this.brick[row_index][colum_index].setPosition(
										Integer.parseInt(s3), Integer.parseInt(s4));
								colum_index++;
							}
							row_index++;
						}
					}
				}
				load=true;
			}

		} catch (Exception e) {
			is.setLabel("Failed to understand level files, please resteup or redownload level files for the game");
			Pages.getInstance().getB().getDisplay().setCurrent(failed);

		}
	}

	public String configLoader(String filepath) {
		// TODO Auto-generated method stub
		InputStream in = this.getClass().getResourceAsStream(filepath);
		try {
			StringBuffer stringBuffer = new StringBuffer();
			int nextChar = 0;
			while ((nextChar = in.read()) != -1) {
				stringBuffer.append((char) nextChar);

			}
			return stringBuffer.toString();
		} catch (Exception e) {
			is.setLabel("Failed to loading local files, please resteup the game");
			Pages.getInstance().getB().getDisplay().setCurrent(failed);
		}
		return null;
	}

	public Brick[][] getBrick() {
		return brick;
	}

	public void setBrick(Brick[][] brick) {
		this.brick = brick;
	}

	public void commandAction(Command arg0, Displayable arg1) {
		if (arg0 == cmdback) {
			Pages.getInstance().getB().getDisplay()
					.setCurrent(Pages.getInstance().getM());
		}

	}
}
