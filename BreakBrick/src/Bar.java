import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;

public class Bar extends Sprite {
	int speed;
	int count;

	public Bar(Image paramImage, int paramInt1, int paramInt2) {
		super(paramImage, paramInt1, paramInt2);
		defineReferencePixel(paramInt1 / 2, paramInt2 / 2);
		speed = 0;
		count = 0;
	}

	public void Input(int paramInt1, int paramInt2) {
		int i = getRefPixelX();
		if ((paramInt1 & GameCanvas.LEFT_PRESSED) != 0) {
			i -= 1 ;
			i=i+speed;
			count++;
			if(count==5)
			{
				speed--;
				count=0;
			}
		}else if ((paramInt1 & GameCanvas.RIGHT_PRESSED) != 0) {
			i += 1;
			i=i+speed;
			count++;
			if(count==5)
			{
				speed++;
				count=0;
			}
		}else if ((paramInt1 & GameCanvas.UP_PRESSED) != 0) {
			i -= 1 ;
			i=i+speed;
			count++;
			if(count==5)
			{
				speed--;
				count=0;
			}
		}else if ((paramInt1 & GameCanvas.DOWN_PRESSED) != 0) {
			i += 1;
			i=i+speed;
			count++;
			if(count==5)
			{
				speed++;
				count=0;
			}
		}else{
			speed=0;
			count=0;
		}
		if (i < getWidth() / 2)
			i = getWidth() / 2;
		if (i > paramInt2 - getWidth() / 2)
			i = paramInt2 - getWidth() / 2;
		setRefPixelPosition(i, getRefPixelY());
	}

	public void Logic() {
	}
}