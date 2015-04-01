import java.io.InputStream;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;


public class Music implements Runnable{
	private String filename;
	Player pla;
	InputStream is;
	boolean isplay;
	public  Music(String fliename1){
		this.filename=fliename1;
		new Thread(this).start();
		
	}
	public void run() {
		this.loadMusic();
		pla.setLoopCount(-1);
	}
	public void loadMusic(){
		is=this.getClass().getResourceAsStream("/"+filename);
		try{
		pla = Manager.createPlayer(is, "audio/midi");
		}catch(Exception e){
		}
	}
	public void setLoop(){
		
	}
	public void setVol(int i){
		VolumeControl vc=(VolumeControl) pla.getControl("VolumeControl");
		vc.setLevel(i);
	}
	public void stop(){
			try {
				pla.stop();
			} catch (MediaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.isplay=false;
	}
	public void play(){
		try {
			pla.realize();
			pla.prefetch();
			pla.start();
		} catch (MediaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.isplay=true;
	}
	public void rewind(){
		try {
			pla.setMediaTime(0);
		} catch (MediaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  static void main(){
		Music music=new Music("music.mid");
		music.play();	
	}

}
