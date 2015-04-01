import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class BreakOutMIDlet extends MIDlet implements CommandListener{
	private Menu menu;
	private  Display display = Display.getDisplay(this);
	private Pages page;
	private SnowBackground lost;
	
	public Display getDisplay(){
		return display;
	}
	public BreakOutMIDlet() {
		menu=new Menu();
		lost=new SnowBackground();
		page=new Pages(this,menu,lost);
		
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {

	}

	protected void pauseApp() {

	}

	protected void startApp() throws MIDletStateChangeException {
		display.setCurrent(menu);
	}

	public void commandAction(Command arg0, Displayable arg1) {
		
	}

}
