import java.io.InputStream;

public class Config {
	public static final int screenWidth = 240;
	public static final int screenHeight = 320;

	public static final int ballWidth = 12;
	public static final int ballHeight = 12;

	public static final int longBarWidth = 60;
	public static final int longBarHeight = 7;
	public static final int barWidth = 40;
	public static final int barHeight = 7;
	public static final int shortBarWidth = 20;
	public static final int shortBarHeight = 7;

	public static final int brickWidth = 18;
	public static final int brickHeight = 10;
	public static final int countNum = 100;
	public static final int defaultMaxLevel = 3;
	public static final int maxScoreNum = 10;
	public static final String maxLevel = "maxLevelDB";
	public static final String level = "levelDB";
	public static final String record = "recordDB";
	public static final String download1 = "http://localhost:8080/BreakBrickServer/Update?lastLevel=";
	public static final String download = "http://118.209.29.64:8080/BreakBrickServer/Update?lastLevel=";

	public static final String topScore1 = "http://localhost:8080/BreakBrickServer/Top10";
	public static final String topScore = "http://118.209.29.64:8080/BreakBrickServer/Top10";
	public static final int slowSpeed = 15;
	public static final int normalSpeed = 5;
	public static final int fastSpeed = 2;
	public static int speed = 5;
	public static boolean landscape = false;
	public static String playerName = "Someone";
	public static final String downloadServer = "/Config.txt";
	public static final String top10Server = "/Config.txt";

	private InputStream in;

	public String getDownloadServer(String filepath) {
		String temp = this.getServer(filepath,"download");
		if (temp == null || temp == "") {
			return download;
		} else {
			return temp;
		}
	}

	private String getServer(String filepath,String serverName) {
		in = this.getClass().getResourceAsStream(filepath);
		String temp = this.readLine().trim();
		while (temp != null) {
			if (temp.startsWith(serverName.trim())) {
				StringTokenizer st1 = new StringTokenizer(temp, "#");
				st1.nextToken();
				return st1.nextToken();
			}
			temp=this.readLine().trim();
		}
		return null;
	}
	
	public String getTop10Server(String filepath) {
		String temp = this.getServer(filepath,"topScore");
		if (temp == null || temp == "") {
			return topScore;
		} else {
			return temp;
		}
	}

	private String readLine() {
		try {
			StringBuffer buffer = new StringBuffer();
			boolean isEndOfFile = false;
			if (in != null) {
				while (true) {
					int ch = in.read() & 0xFF;
					if (ch == 0xD) { // '13' '\n'
					} else if (ch == 0xA) {
						break;
					} else if (ch == 0xFF) {
						isEndOfFile = true;
						break;
					} else {
						buffer.append((char) ch);
					}
				}
			}
			if (isEndOfFile) {
				if (buffer.length() > 0) {
					return buffer.toString();
				} else {
					return null;
				}
			}
			return buffer.toString();
		} catch (Exception e) {
			return null;
		}
	}

}
