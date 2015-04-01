

public class Record {
	private String name;
	private int score;
	public Record(String name,int score){
		this.name=name;
		this.score=score;
	}
	public Record(){
		this.name="";
		this.score=0;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
}
