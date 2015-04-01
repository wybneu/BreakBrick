import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.SocketConnection;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;

public class HttpRecord extends Canvas implements Runnable {
	private byte[] s;
	private String s1 = "";
	private HttpConnection hc;
	private DataInputStream dis;
	private DataOutputStream dos;
	public boolean isConnected;
	public Record record[];
	private String url = null;
	private boolean isNewRecord = false;
	public Record newRecord;
	private int newRecordRank = Config.maxScoreNum + 1;
	private boolean isDownload = false;
	private String downloadUrl = null;
	public int lastLevel = 0;
	public String levels[];
	public  int totalLevel=0;
	public int downloadstuts=-1;
	public int currentDownload=0;
	public Config cf;
	
	public void setRank(int i) {
		this.newRecordRank = i;
	}

	public int getRank() {
		return this.newRecordRank;
	}

	public HttpRecord(int last) {
		
		lastLevel = last;
		totalLevel=this.lastLevel;
		this.isDownload = true;
		Load();
	}

	public HttpRecord() {
		this.isNewRecord = false;
		Load();
		System.out.print(isConnected);
	}

	public HttpRecord(String newName, int newScore) {
		this.newRecord = new Record(newName, newScore);
		this.isNewRecord = true;
		Load();
		System.out.print(isConnected);
	}

	public boolean Load() {
		if (this.isDownload == true) {
			this.isConnected = false;
			
		} else {
			this.isConnected = false;
			s = new byte[20];
			record = new Record[Config.maxScoreNum];
			for (int i = 0; i < Config.maxScoreNum; i++) {
				record[i] = new Record();
			}
		}
		cf=new Config();
		try {
			// this.run();
			new Thread(this).start();
		} catch (Exception e) {
			return false;

		}
		return true;
	}

	public void run() {
		if (this.isDownload) {
			url=cf.getDownloadServer(Config.downloadServer)+this.lastLevel;
			System.out.println(url);
			try{
			hc = (HttpConnection) Connector.open(url);
			hc.setRequestMethod(hc.POST);
			hc.setRequestProperty("user-agent",
					"profile/midp-1.0 configuration/cldc-1.0");
			hc.setRequestProperty("content-language", "en-us");
			dis = new DataInputStream(hc.openInputStream());
			dos = new DataOutputStream(hc.openOutputStream());
			
			String temps="";
					temps=dis.readUTF();
			if(temps.equals("NOUPDATE")){
				this.downloadstuts=0;//no update
			}else{
				this.downloadstuts=1;//downloading
				this.totalLevel=Integer.parseInt(temps);
				levels=new String[totalLevel-lastLevel];			
				for(int i=0;i<totalLevel-lastLevel;i++){
					levels[i]="";
					levels[i]=dis.readUTF();
					System.out.println(levels[i]);
					this.currentDownload=lastLevel+i+1;
				}
				this.downloadstuts=2;//downloaded
			};			
			}catch(Exception e){
				System.out.println(e);
				this.downloadstuts=4;//download failed
				isConnected = false;
				return;
			}
			isConnected = true;
		} else {
			if (!this.isNewRecord) {
				url = cf.getTop10Server(Config.top10Server);
			} else {
				url = cf.getTop10Server(Config.top10Server) + "?name=" + this.newRecord.getName()
						+ "&score=" + this.newRecord.getScore();
			}
			try {
				hc = (HttpConnection) Connector.open(url);
				hc.setRequestMethod(hc.POST);
				hc.setRequestProperty("user-agent",
						"profile/midp-1.0 configuration/cldc-1.0");
				hc.setRequestProperty("content-language", "en-us");
				dis = new DataInputStream(hc.openInputStream());
				dos = new DataOutputStream(hc.openOutputStream());

				if (!this.isNewRecord) {
					for (int i = 0; i < Config.maxScoreNum; i++) {
						// dis.read(s);
						// s1 = new String(s);
						s1 = dis.readUTF();

						record[i].setName(s1);
						// dis.read(s);
						s1 = dis.readUTF();
						// s1 = new String(s);
						record[i].setScore(Integer.parseInt(s1));
					}
				} else {
					newRecordRank = Integer.parseInt(dis.readUTF());
					for (int i = 0; i < Config.maxScoreNum; i++) {
						// dis.read(s);
						// s1 = new String(s);
						s1 = dis.readUTF();
						record[i].setName(s1);
						// dis.read(s);
						s1 = dis.readUTF();
						// s1 = new String(s);
						record[i].setScore(Integer.parseInt(s1));
					}
				}

			} catch (Exception e) {
				System.out.println(e);
				isConnected = false;
				return;
			}
			isConnected = true;
		}
	}

	public void getScore() {

	}

	protected void paint(Graphics g) {
		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_UNDERLINED,
				Font.SIZE_MEDIUM));
		for (int i = 0; i < Config.maxScoreNum; i++) {
			g.setColor(0, 0, 0);
			if (i == newRecordRank) {
				g.setColor(255, 0, 0);
			}
			g.drawString(record[i].getName(), getWidth() / 2 - 40, getHeight()
					/ 4 + 20 * (i + 1), Graphics.BASELINE | Graphics.HCENTER);
			g.drawString(record[i].getScore() + "", getWidth() / 2 + 40,
					getHeight() / 4 + 20 * (i + 1), Graphics.BASELINE
							| Graphics.HCENTER);

		}

	}

}