import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import java.util.*;


public class GameBK extends Sprite {
	public GameBK(Image paramImage, int paramInt1, int paramInt2) {
		super(paramImage, paramInt1, paramInt2);
		defineReferencePixel(paramInt1 / 2, paramInt2 / 2);
	}
}
