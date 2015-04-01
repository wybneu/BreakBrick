//import java.io.IOException;
//import java.util.Enumeration;
import java.util.Random;

//import javax.microedition.io.Connector;
//import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;

public class Games extends GameCanvas implements Runnable, CommandListener {
	private int level;
	private Random randomG = new Random();
	private GameBK background;
	private LocalRecord record;
	private int score;
	private Brick[][] bricks;
	private Bar bar;
	int barlength = 1; // 0 short 1 normal 2 long
	private Bar normalBar;
	private Bar longBar;
	private Bar shortBar;
	private Ball ball[] = new Ball[3];
	private int ballNum;
	private boolean isGamePause;
	private int rank;
	private String information;
	private boolean isGameFinish;
	private Command cmdExit;
	private Command cmdPause;
	private Command cmdStart;
	private int maxLevelNum = 0;
	private boolean isLevelFinish;
	private int count = 0;
	private int highScore[];
	private int next;
	private boolean isQuit = false;
	private String extraLevel[];
	private int ballx = Config.screenWidth / 2;
	private int bally = Config.screenHeight / 2;
	int speedx = -2;
	int speedy = -2;
	Form f = new Form("");
	StringItem y;
	StringItem s = new StringItem(null,
			"Your score is on top 10, Do you want to update your score?");
	Command yes = new Command("yes", Command.CANCEL, 1);
	Command no = new Command("no", Command.SCREEN, 1);

	Image bufImage;
	Graphics bufG;
	Graphics g;
	int screenX;
	int screenY;
	boolean ballfile=false;

	public Games(int startLevel) {
		super(true);
		highScore = new int[Config.maxScoreNum];
		record = new LocalRecord();
		if (record.status) {
			maxLevelNum = record.getMaxLevelNum();
			for (int i = 0; i < Config.maxScoreNum; i++) {
				highScore[i] = record.highScore[i];
			}
			extraLevel = record.extraLevel;
		} else {
			maxLevelNum = Config.defaultMaxLevel;
			for (int i = 0; i < Config.maxScoreNum; i++) {
				highScore[i] = 0;
			}
			extraLevel = null;
		}
		score = 0;
		ballNum = 4;
		rank = 11;
		level = startLevel;
		getRank();
		isGamePause = true;
		isGameFinish = false;
		isLevelFinish = false;
		try {
			Image backGroundImage;
			if (Config.landscape) {
				backGroundImage = Image.createImage("/gamebk1.png");
				background = new GameBK(backGroundImage, Config.screenHeight,
						Config.screenWidth);
			} else {
				backGroundImage = Image.createImage("/gamebk.png");
				background = new GameBK(backGroundImage, Config.screenWidth,
						Config.screenHeight);
			}

			Image barImg = Image.createImage("/bar.png");
			Image longBarImg = Image.createImage("/longbar.png");
			Image shortBarImg = Image.createImage("/shortbar.png");
			this.normalBar = new Bar(barImg, Config.barWidth, Config.barHeight);
			this.longBar = new Bar(longBarImg, Config.longBarWidth,
					Config.longBarHeight);
			this.shortBar = new Bar(shortBarImg, Config.shortBarWidth,
					Config.shortBarHeight);
			this.bar = normalBar;
			Image ballImg = Image.createImage("/ball.png");
			for (int i = 0; i < ball.length; i++)
				this.ball[i] = new Ball(ballImg, Config.ballWidth,
						Config.ballHeight);
		} catch (Exception localException) {
		}
		loadLevel(level);
		// ******
		if (Config.landscape) {
			bufImage = Image.createImage(this.getHeight(), this.getWidth());
			bufG = bufImage.getGraphics();
			g = bufG;
			screenX = this.getHeight();
			screenY = this.getWidth();
		} else {
			screenX = this.getWidth();
			screenY = this.getHeight();
			g = getGraphics();
		}
		// ******
		Reset();
		if (ballfile){
			this.ball[0].setPosition(ballx, bally);
			this.ball[0].setSpeed(speedx, speedy);
			ballfile=false;
		}
		cmdExit = new Command("Exit", Command.EXIT, 1);
		cmdPause = new Command("Pause", Command.SCREEN, 1);
		cmdStart = new Command("Start", Command.SCREEN, 1);
		this.addCommand(cmdExit);
		this.addCommand(cmdPause);

		this.setCommandListener(this);

		Start();
	}

	public Games() {
		this(1);
		// super(true);
		// highScore = new int[Config.maxScoreNum];
		// record = new LocalRecord();
		// if (record.status) {
		// maxLevelNum = record.getMaxLevelNum();
		// for (int i = 0; i < Config.maxScoreNum; i++) {
		// highScore[i] = record.highScore[i];
		// }
		// extraLevel = record.extraLevel;
		// } else {
		// maxLevelNum = Config.defaultMaxLevel;
		// for (int i = 0; i < Config.maxScoreNum; i++) {
		// highScore[i] = 0;
		// }
		// extraLevel = null;
		// }
		// score = 0;
		// ballNum = 4;
		// rank = 11;
		// level = 1;
		// getRank();
		// isGamePause = true;
		// isGameFinish = false;
		// isLevelFinish = false;
		// try {
		//
		// Image backGroundImage;
		// if (Config.landscape) {
		// backGroundImage = Image.createImage("/gamebk1.png");
		// background = new GameBK(backGroundImage, Config.screenHeight,
		// Config.screenWidth);
		// }else{
		// backGroundImage = Image.createImage("/gamebk.png");
		// background = new GameBK(backGroundImage, Config.screenWidth,
		// Config.screenHeight);
		// }
		// Image barImg = Image.createImage("/bar.png");
		// Image longBarImg = Image.createImage("/longbar.png");
		// Image shortBarImg = Image.createImage("/shortbar.png");
		// this.normalBar = new Bar(barImg, Config.barWidth, Config.barHeight);
		// this.longBar = new Bar(longBarImg, Config.longBarWidth,
		// Config.longBarHeight);
		// this.shortBar = new Bar(shortBarImg, Config.shortBarWidth,
		// Config.shortBarHeight);
		// this.bar = normalBar;
		// Image ballImg = Image.createImage("/ball.png");
		// for (int i = 0; i < ball.length; i++)
		// this.ball[i] = new Ball(ballImg, Config.ballWidth,
		// Config.ballHeight);
		// } catch (Exception localException) {
		// }
		// loadLevel(level);
		// // ******
		// if (Config.landscape) {
		// bufImage = Image.createImage(this.getHeight(), this.getWidth());
		// bufG = bufImage.getGraphics();
		// g = bufG;
		// screenX = this.getHeight();
		// screenY = this.getWidth();
		// } else {
		// screenX = this.getWidth();
		// screenY = this.getHeight();
		// g = getGraphics();
		// }
		// // ******
		// Reset();
		// cmdExit = new Command("Exit", Command.EXIT, 1);
		// cmdPause = new Command("Pause", Command.SCREEN, 1);
		// cmdStart = new Command("Start", Command.SCREEN, 1);
		// this.addCommand(cmdExit);
		// this.addCommand(cmdPause);
		//
		// this.setCommandListener(this);
		//
		// Start();
	}

	public void nextLevel() {
		ballNum = 4;
		this.barlength = 1;
		level += 1;
		loadLevel(level);
		this.isLevelFinish = false;
		isGamePause = true;
		Reset();
		if (ballfile){
			this.ball[0].setPosition(ballx, bally);
			this.ball[0].setSpeed(ballx, bally);
			ballfile=false;
		}
		
	};

	// private void loadLevel(int level) {
	// Image brickImage;
	// try {
	// brickImage = Image.createImage("/brick.png");
	//
	// this.bricks = new Brick[5][8];
	// int i = screenX / 2 - 60;
	// int j = 20;
	// for (int k = 0; k < this.bricks.length; ++k) {
	// i = screenX / 2 - 60;
	// for (int l = 0; l < this.bricks[0].length; ++l) {
	// this.bricks[k][l] = new Brick(brickImage,
	// Config.brickWidth, Config.brickHeight);
	// this.bricks[k][l].setPosition(i, j);
	// i += Config.brickWidth;
	// }
	// j += Config.brickHeight;
	// }
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
	private void loadLevel(int level) {
		if (level <= 3) {
			String path = "/level/Level" + String.valueOf(level) + ".txt";
			String filepath = path.toString();
			LevelLoder newLevel = new LevelLoder();
			newLevel.processLevelConfig(filepath);
			this.bricks = newLevel.getBrick();

		} else {
			LevelLoder newLevel = new LevelLoder();
			newLevel.setBrick(this.extraLevel[level - 4]);
			newLevel.configProcess(this.extraLevel[level - 4]);
			this.bricks = newLevel.getBrick();
			this.barlength = newLevel.barLength - 1;
			this.ballx = newLevel.ballX;
			this.bally = newLevel.ballY;
			this.speedx = newLevel.ballSpeedX;
			this.speedy = newLevel.ballSpeedY;
			this.ballfile=newLevel.load;
		}
		for (int i = 0; i < this.bricks.length; i++) {
			for (int j = 0; j < this.bricks[i].length; j++) {
				if (!Config.landscape) {
					this.bricks[i][j]
							.setPosition(
									Integer.parseInt(this.bricks[i][j].getX()
											* this.getWidth()
											/ Config.screenWidth + ""),
									Integer.parseInt(this.bricks[i][j].getY()
											* this.getHeight()
											/ Config.screenHeight + ""));
				} else {
					this.bricks[i][j].setPosition(
							Integer.parseInt(this.bricks[i][j].getX()
									* this.getHeight() / Config.screenWidth
									+ ""),
							Integer.parseInt(this.bricks[i][j].getY()
									* this.getWidth() / Config.screenHeight
									+ ""));
				}
			}
		}
	}

	private void Reset() {
		for (int i = 0; i < this.bricks.length; ++i)
			for (int j = 0; j < this.bricks[0].length; ++j) {
				this.bricks[i][j].setState(0);
				int k = Math.abs(this.randomG.nextInt() % 3);
				this.bricks[i][j].setFrame(k);
			}
		ResetBarBall(0);

	}

	private void ResetBarBall(int index) {
		this.ballNum -= 1;
		if (this.ballNum <= 0) {
			this.isGameFinish = true;
			return;
		}
		ball[1].setVisible(false);
		ball[2].setVisible(false);
		this.normalBar.setRefPixelPosition(screenX / 2, screenY - 30);
		this.longBar.setRefPixelPosition(screenX / 2, screenY - 30);
		this.shortBar.setRefPixelPosition(screenX / 2, screenY - 30);
		this.bar.setRefPixelPosition(screenX / 2, screenY - 30);
		if (this.barlength == 0) {
			this.bar = shortBar;
		} else if (this.barlength == 1) {
			this.bar = normalBar;
		} else {
			this.bar = longBar;
		}

		this.ball[index].setSpeed(-2, -2);
		int i = this.bar.getRefPixelY() - this.bar.getHeight() / 2
				- this.ball[0].getHeight() / 2 - 2;
		this.ball[index].setRefPixelPosition(this.bar.getRefPixelX() - 5, i);
		this.ball[index].setVisible(true);
	}

	public void Start() {
		ball[1].setVisible(false);
		ball[2].setVisible(false);
		f.append(s);
		f.addCommand(yes);
		f.addCommand(no);
		f.setCommandListener(this);
		Thread localThread = new Thread(this);
		localThread.start();
	}

	public void run() {
		while (!isGameFinish && !isQuit) {
			try {
				Thread.sleep(Config.speed);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Input();
			Logic();
			Paint();
		}

	}

	public void setPause() {
		if (this.isGamePause == true)
			this.isGamePause = false;
		else
			this.isGamePause = true;
	}

	public void setPause(boolean p) {
		this.isGamePause = p;
	}

	public void Input() {

		int i = getKeyStates();

		if (i == GameCanvas.FIRE_PRESSED || i == GameCanvas.LEFT_PRESSED
				|| i == GameCanvas.RIGHT_PRESSED) {
			if (this.isLevelFinish) {
				nextLevel();
			}
			;
			this.setPause(false);
		}
		if (isGamePause) {

		} else {

			this.bar.Input(i, screenX);
		}
	}

	public void Logic() {
		if (isGamePause || isGameFinish) {
			this.removeCommand(cmdPause);
			if (!isGameFinish) {
				this.addCommand(cmdStart);
			}

		} else {
			this.removeCommand(cmdStart);
			this.addCommand(cmdPause);
		}
		if (this.isLevelFinish)
			return;
		if (!isGamePause) {
			this.bar.Logic();
			for (int i = 0; i < ball.length; i++)
				this.ball[i].Logic(screenX, screenY);
			int i = 1;
			for (int j = 0; j < this.bricks.length; ++j)
				for (int k = 0; k < this.bricks[0].length; ++k) {
					this.bricks[j][k].Logic(screenY);
					if (this.bricks[j][k].getState() != 2)
						i = 0;
				}
			if (i != 0) {
				this.isLevelFinish = true;
				this.setPause(true);
				if (this.level >= this.maxLevelNum) {
					this.isGameFinish = true;
				}
				return;
			}
			Collides();
			for (int f = 0; f < ball.length; f++) {
				if (this.ball[f].isVisible())
					return;
			}
			ResetBarBall(0);

		}

	}

	public void Collides() {
		for (int i = 0; i < ball.length; i++) {
			if (this.ball[i].collidesWith(this.bar, false)) {
				this.ball[i].Reflect(false);
				this.ball[i].setSpeed(this.ball[i].speedX + this.bar.speed,
						this.ball[i].speedY);
			}
		}
		for (int i = 0; i < this.bricks.length; ++i)
			for (int j = 0; j < this.bricks[0].length; ++j)
				if (this.bricks[i][j].getState() == 0) {
					for (int z = 0; z < ball.length; z++) {
						if (this.bricks[i][j].collidesWith(this.ball[z], false)) {
							int k = this.ball[z].getRefPixelX();
							int l = this.bricks[i][j].getX();
							int i1 = this.bricks[i][j].getX()
									+ this.bricks[i][j].getWidth();
							if ((k > l) && (k < i1))
								this.ball[z].Reflect(false);
							else
								this.ball[z].Reflect(true);
							int i2 = this.randomG.nextInt() % 3;
							if (i2 == 0)
								this.bricks[i][j].setState(1);
							else
								this.bricks[i][j].setState(2);
							// int i3 = this.score.getCurRecord();
							int i3 = this.score;
							this.score = i3 + 5;
							getRank();
							// this.score.setCurRecord(i3 += 5);
						}
					}
				} else if ((this.bricks[i][j].getState() == 1)
						&& (this.bricks[i][j].collidesWith(this.bar, false))) {
					switch (this.bricks[i][j].getFrame()) {
					case 0:
						this.ballNum += 1;
						break;
					case 1:
						int i2;
						do {
							i2 = this.randomG.nextInt() % 3;
						} while (i2 == this.barlength);

						if (i2 == 0) {
							this.shortBar.setRefPixelPosition(
									this.bar.getRefPixelX(),
									this.bar.getRefPixelY());
							this.bar = this.shortBar;
							this.barlength = 0;
						} else if (i2 == 1) {
							this.normalBar.setRefPixelPosition(
									this.bar.getRefPixelX(),
									this.bar.getRefPixelY());
							this.bar = this.normalBar;
							this.barlength = 1;
						} else {
							this.longBar.setRefPixelPosition(
									this.bar.getRefPixelX(),
									this.bar.getRefPixelY());
							this.bar = this.longBar;
							this.barlength = 2;
						}
						break;
					case 2:
						int ix = this.bar.getRefPixelY() - this.bar.getHeight()
								/ 2 - this.ball[0].getWidth() / 2 - 2;
						int temp;
						for (int ballx = 0; ballx < ball.length; ballx++) {
							if (!ball[ballx].isVisible()) {
								for (temp = 0; temp < ball.length; temp++) {
									if (ball[temp].isVisible()) {
										break;
									}
									temp = 0;
								}
								ball[ballx].speedX = -ball[temp].speedX;
								ball[ballx].speedY = -ball[temp].speedY;
								this.ball[ballx].setRefPixelPosition(
										this.ball[temp].getX(),
										this.ball[temp].getY());
								ball[ballx].setVisible(true);
								break;
							}
						}

					}
					this.bricks[i][j].setState(2);
				}
	}

	public void Paint() {

		// ******
		// ********
		g.setColor(0);
		g.fillRect(0, 0, screenX, screenY);
		g.setColor(-1);
		if (!isGameFinish) {
			background.paint(g);
			for (int i = 0; i < this.bricks.length; ++i)
				for (int j = 0; j < this.bricks[0].length; ++j)
					this.bricks[i][j].paint(g);
			for (int i = 0; i < ball.length; i++) {
				this.ball[i].paint(g);
			}
			this.bar.paint(g);
			information = "Level: " + this.level + "  Score: " + this.score
					+ "  next: " + this.next + "  life: " + this.ballNum
					+ "  Rank: ";
			if (this.rank > 10) {
				information = information + "-";
			} else {
				information = information + this.rank;
			}
			g.drawString(information.toString(), 5, screenY - 20, 0);
			if (isLevelFinish) {
				g.drawString("Level Clear!", screenX / 2 - 25, screenY / 2, 0);
				g.drawString("5 or OK to start!", screenX / 2 - 30,
						screenY / 2 + 20, 0);
			} else if (isGamePause) {
				g.drawString("Game Paused!", screenX / 2 - 25, screenY / 2, 0);
				g.drawString("5 or OK to start!", screenX / 2 - 30,
						screenY / 2 + 20, 0);
			}
		} else {
			if (rank < 11) {
				Pages.getInstance().getB().getDisplay().setCurrent(f);
			} else {
				background.paint(g);
				g.drawString("You failed to Top 10", screenX / 2 - 25,
						screenY / 2, 0);
			}

		}
		if (Config.landscape) {
			this.getGraphics().drawRegion(bufImage, 0, 0, screenX, screenY,
					Sprite.TRANS_ROT90, 0, 0, Graphics.LEFT | Graphics.TOP);
		}
		flushGraphics();

	}

	public void commandAction(Command c, Displayable g) {
		if (c == cmdExit) {
			this.setPause(true);
			this.isQuit = true;
			Pages.getInstance().getB().getDisplay()
					.setCurrent(Pages.getInstance().getM());
		}
		if (c == cmdPause) {
			this.setPause();
		}
		if (c == cmdStart) {
			this.setPause(false);
		}
		if (c == yes) {
			this.setPause(true);
			this.isQuit = true;
			Pages.getInstance()
					.getB()
					.getDisplay()
					.setCurrent(
							new Result(Config.playerName, this.score, record));
		}
		if (c == no) {
			this.setPause(true);
			this.isQuit = true;
			Pages.getInstance().getB().getDisplay()
					.setCurrent(Pages.getInstance().getM());
		}

	}

	// public int getlevelNum() {
	//
	// levelNum = 0;
	// FileConnection fc;
	// try {
	// fc = (FileConnection) Connector.open("file:///level/", Connector.READ);
	//
	// Enumeration enums = fc.list();
	// while (enums.hasMoreElements()) {
	// levelNum++;
	// }
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// System.out.println(levelNum);
	// return levelNum;
	// }

	public void getRank() {
		for (int i = Config.maxScoreNum - 1; i >= 0; i--) {
			if (this.record.highScore[i] > this.score) {
				this.next = this.record.highScore[i];
				this.rank = i + 2;
				return;
			}
		}
		this.next = 0;
		this.rank = 1;

	}
}