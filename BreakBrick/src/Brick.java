import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class Brick extends Sprite {
	public static final int BRICK_FIX = 0;
	public static final int BRICK_DROP = 1;
	public static final int BRICK_HIDE = 2;
	public static final int BRICK_STATE_NUM = 3;
	private int life = 0;

	public Brick(Image paramImage, int paramInt1, int paramInt2) {
		super(paramImage, paramInt1, paramInt2);
		defineReferencePixel(paramInt1 / 2, paramInt2 / 2);
		setState(2);
	}

	public int getState() {
		return this.life;
	}

	public void setState(int paramInt) {
		if ((paramInt < 0) || (paramInt >= 3))
			return;
		this.life = paramInt;
		switch (this.life) {
		case 0:
		case 1:
			setVisible(true);
			break;
		case 2:
			setVisible(false);
		}
	}

	public void Logic(int paramInt) {
		switch (this.life) {
		case 1:
			int i = getRefPixelY();
			setRefPixelPosition(getRefPixelX(), i += 2);
			if (i <= paramInt)
				return;
			setState(2);
		}
	}
	
}