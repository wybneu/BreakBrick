import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

public class LocalRecord {
	public int maxLvl;
	public int highScore[];
	public String extraLevel[];
	ByteArrayOutputStream bos;
	DataOutputStream dos;
	ByteArrayInputStream bis;
	DataInputStream dis;
	Byte[] temp;
	RecordStore rs;
	boolean status;

	public LocalRecord() {
		status = true;
		maxLvl = 3;
		highScore = new int[Config.maxScoreNum];
		for (int i = 0; i < highScore.length; i++)
			highScore[i] = 0;
		// level=new String[t];
		bos = new ByteArrayOutputStream();
		dos = new DataOutputStream(bos);

		load();
	}

	public void load() {
		load(Config.maxLevel);
		load(Config.record);
		load(Config.level);
	}

	public void load(String DBName) {
		try {
			rs = RecordStore.openRecordStore(DBName, true);
			if (!logic(DBName)) {
				status = false;
			}
			;
			rs.closeRecordStore();
		} catch (RecordStoreFullException e) {
			e.printStackTrace();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
	}

	public boolean logic(String DBName) {
		try {
			if (DBName.equals(Config.maxLevel)) {
				byte[] temp1 = rs.getRecord(1);
				bis = new ByteArrayInputStream(temp1);
				dis = new DataInputStream(bis);
				maxLvl = dis.readInt();
				bis.close();
				dis.close();
			}
		} catch (Exception e) {
			try {
				dos.writeInt(Config.defaultMaxLevel);
				byte[] temp1 = bos.toByteArray();
				rs.addRecord(temp1, 0, temp1.length);
				maxLvl = Config.defaultMaxLevel;
				dos.flush();
				bos.reset();
			} catch (Exception e1) {
				return false;
			}
		}
		try {

			if (DBName.equals(Config.record)) {
				int x = rs.getNumRecords();
				if (rs.getNumRecords() == Config.maxScoreNum) {
					for (int i = 0; i < Config.maxScoreNum; i++) {
						byte[] temp1 = rs.getRecord(i + 1);
						bis = new ByteArrayInputStream(temp1);
						dis = new DataInputStream(bis);
						highScore[i] = dis.readInt();
					}
					bis.close();
					dis.close();
				} else if ((rs.getNumRecords()) == 0) {
					for (int i = 0; i < Config.maxScoreNum; i++) {
						dos.writeInt(highScore[i]);
						byte[] temp1 = bos.toByteArray();
						rs.addRecord(temp1, 0, temp1.length);
						dos.flush();
						bos.reset();
					}
				} else {
					rs.closeRecordStore();
					RecordStore.deleteRecordStore(DBName);
					rs = RecordStore.openRecordStore(DBName, true);
					logic(DBName);
				}
			}
		} catch (Exception e) {
			return false;
		}

		try {

			if (DBName.equals(Config.level)) {
				int x = rs.getNumRecords();
				if (x == 0) {
					this.extraLevel = null;
					return true;
				} else {
					this.extraLevel = new String[x];
					for (int x2 = 0; x2 < this.extraLevel.length; x2++) {
						byte[] temp1 = rs.getRecord(x2 + 1);
						bis = new ByteArrayInputStream(temp1);
						dis = new DataInputStream(bis);
						extraLevel[x2] = dis.readUTF();
					}
				}
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public void setLevels(String levels[]) {
		try {
			rs = RecordStore.openRecordStore(Config.level, true);
		} catch (Exception e) {
			return;
		}
		try {
			for (int i = 0; i < levels.length; i++) {
				dos.writeUTF(levels[i]);
				byte[] temp1 = bos.toByteArray();
				rs.addRecord(temp1, 0, temp1.length);
				dos.flush();
				bos.reset();
			}
		} catch (Exception e) {

		}

	}

	public int getMaxLevelNum() {
		return this.maxLvl;
	}

	public boolean setMaxLevelNum(int i) {
		try {
			rs = RecordStore.openRecordStore(Config.maxLevel, true);
		} catch (Exception e) {
			return false;
		}
		try {

			dos.writeInt(i);
			byte[] temp1 = bos.toByteArray();
			rs.setRecord(1, temp1, 0, temp1.length);
			maxLvl = i;
			dos.flush();
			bos.reset();

		} catch (Exception e) {
			try {
				dos.writeInt(Config.defaultMaxLevel);
				byte[] temp1 = bos.toByteArray();
				rs.addRecord(temp1, 0, temp1.length);
				maxLvl = Config.defaultMaxLevel;
				dos.flush();
				bos.reset();
			} catch (Exception e1) {
				e1.printStackTrace();
				return false;
			}
		}

		try {
			rs.closeRecordStore();
		} catch (RecordStoreNotOpenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecordStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public void setRecord(int records[]) {
		try {
			rs = RecordStore.openRecordStore(Config.record, true);
		} catch (Exception e) {
			return;
		}
		try {
			if (rs.getNumRecords() == Config.maxScoreNum) {
				for (int i = 0; i < Config.maxScoreNum; i++) {
					dos.writeInt(records[i]);
					byte[] temp1 = bos.toByteArray();
					rs.setRecord(i + 1, temp1, 0, temp1.length);
					dos.flush();
					bos.reset();
				}
				bos.close();
				dos.close();
			} else if (rs.getNumRecords() == 0) {
				for (int i = 0; i < Config.maxScoreNum; i++) {
					dos.writeInt(records[i]);
					byte[] temp1 = bos.toByteArray();
					rs.addRecord(temp1, 0, temp1.length);
					dos.flush();
					bos.reset();
				}
			} else {
				rs.closeRecordStore();
				RecordStore.deleteRecordStore(Config.record);
				setRecord(records);
				return;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			rs.closeRecordStore();
		} catch (RecordStoreNotOpenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecordStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void reset(){
		int s[]=new int[10];
		this.setRecord(s);
		this.setMaxLevelNum(3);
		try {
			RecordStore.deleteRecordStore(Config.level);
		} catch (RecordStoreNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecordStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}