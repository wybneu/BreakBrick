import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class Ball extends Sprite {
	public int speedX = 0;
	public int speedY = 0;

	public Ball(Image paramImage, int x, int y) {
		super(paramImage, x, y);
		defineReferencePixel(x / 2, y / 2);
	}

	public void setSpeed(int x, int y) {
		this.speedX = x;
		this.speedY = y;
	}

	public void AddSpeed(int x, int y) {
		if ((this.speedX < 0) && (this.speedX - x < 0))
			this.speedX -= x;
		else if ((this.speedX > 0) && (this.speedX + x > 0))
			this.speedX += x;
		if ((this.speedY < 0) && (this.speedY - y < 0))
			this.speedY -= y;
		else if ((this.speedY > 0) && (this.speedY + y > 0))
			this.speedY += y;
	}

	public void Reflect(boolean dir) {
		if (dir)
			this.speedX = (-this.speedX);
		else
			this.speedY = (-this.speedY);
	}

	public void Logic(int x, int Y) {
		int i = getRefPixelX();
		int j = getRefPixelY();
		i += this.speedX;
		j += this.speedY;
		if (i < getWidth() / 2) {
			i = getWidth() / 2;
			Reflect(true);
		} else if (i > x - getWidth() / 2) {
			i = x - getWidth() / 2;
			Reflect(true);
		}
		if (j < getHeight() / 2) {
			j = getHeight() / 2;
			
				Reflect(false);
			
		} else if (j > Y) {
			setVisible(false);
		}
		setRefPixelPosition(i, j);
	}
}