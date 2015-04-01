
public class Pages {
	private BreakOutMIDlet b;
	private Menu m;
	private SnowBackground l;
	private static Pages instance;
	public Pages(BreakOutMIDlet b,Menu m,  SnowBackground lost) {
		this.b=b;
		this.m=m;
		this.l=lost;
		instance=this;
	}
	public static Pages getInstance(){
		return instance;
	}
	public BreakOutMIDlet getB() {
		return b;
	}

	public void setB(BreakOutMIDlet b) {
		this.b = b;
	}

	public Menu getM() {
		return m;
	}

	public void setM(Menu m) {
		this.m = m;
	}
	

	public SnowBackground getL() {
		return l;
	}
	public void setL(SnowBackground l) {
		this.l = l;
	}

}
