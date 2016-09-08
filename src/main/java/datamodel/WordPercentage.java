package datamodel;

public class WordPercentage {
	
	private String word;
	private double percentage;
	
	public WordPercentage(String word, double percentage) {
		this.word = word;
		this.percentage = percentage;
	}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
}
