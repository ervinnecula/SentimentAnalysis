package datamodel;

public class WordModel {
	private int posCases;
    private int negCases;
    private String word;

    public WordModel(String word, int posCases, int negCases) {
        this.word = word;
        this.posCases = posCases;
        this.negCases = negCases;  
    }
    
    public WordModel() {
		// TODO Auto-generated constructor stub
	}

	public String getWord(){
        return this.word;
    }
    
    public int getPosCases(){
        return this.posCases;
    }
    
    public int getNegCases(){
        return this.negCases;
    }
    
    public void setWord(String word){
        this.word = word;
    }
    
    public void setPosCases(int posCases){
        this.posCases = posCases;
    }
    
    public void setNegCases(int negCases){
        this.negCases = negCases;
    }
}
