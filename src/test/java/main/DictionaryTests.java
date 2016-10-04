package main;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import analyze.Dictionary;
import datamodel.SentimentType;
import datamodel.WordModel;
import training.TrainModel;

public class DictionaryTests {

	@Before
	public void initTestCases() {
		Dictionary.initializeDictionary();

		Dictionary.addWord("cangur", SentimentType.NEGATIVE);
		Dictionary.addWord("cangur", SentimentType.NEGATIVE);
		Dictionary.addWord("cangur", SentimentType.POSITIVE);

		Dictionary.addWord("cantina", SentimentType.POSITIVE);

		Dictionary.addWord("colina", SentimentType.NEGATIVE);
		Dictionary.addWord("colina", SentimentType.POSITIVE);

		Dictionary.addWord("cireasa", SentimentType.NEGATIVE);
		Dictionary.addWord("cireasa", SentimentType.NEGATIVE);
		Dictionary.addWord("cireasa", SentimentType.NEGATIVE);

		Dictionary.addWord("cangur", SentimentType.NEGATIVE);

	}

	@Test
	public void addWordToDictionaryTestLength() {
		assertEquals(4, Dictionary.getWordsOfLetter("c").size());
	}

	@Test
	public void addWordToDictionaryTestSameWords() {
		List<WordModel> wordsOfLetter = Dictionary.getWordsOfLetter("c");

		assertEquals("cangur", wordsOfLetter.get(0).getWord());
		assertEquals("cantina", wordsOfLetter.get(1).getWord());
		assertEquals("colina", wordsOfLetter.get(2).getWord());
		assertEquals("cireasa", wordsOfLetter.get(3).getWord());
	}

	@Test
	public void addWordToDictionaryTestNumbers() {
		List<WordModel> wordsOfLetter = Dictionary.getWordsOfLetter("c");

		// cangur
		assertEquals(3, wordsOfLetter.get(0).getNegCases());
		assertEquals(1, wordsOfLetter.get(0).getPosCases());

		// cantina
		assertEquals(1, wordsOfLetter.get(1).getPosCases());

		// colina
		assertEquals(1, wordsOfLetter.get(2).getPosCases());
		assertEquals(1, wordsOfLetter.get(2).getNegCases());

		// cireasa
		assertEquals(3, wordsOfLetter.get(3).getNegCases());
	}

	@Test
	public void serializeDictionaryTestExist() {
		Dictionary.serializeDictionary("dictionaryTest.xml");
		boolean file = false;
		File f = new File("dictionaryTest.xml");
		if (f.exists() && !f.isDirectory()) {
			file = true;
		}
		assertEquals(true, file);
	}

	@Test
	public void serlializeDictionaryTestCheckWords() {
		boolean check = true;
		
		Dictionary.serializeDictionary("dictionaryTest.xml");
		
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document doc = documentBuilder.parse(new File("dictionaryTest.xml"));
			
			// get <entry> elements inside <c> tag
			NodeList childNodes = doc.getElementsByTagName("c").item(0).getChildNodes();
			
			if(!childNodes.item(0).getFirstChild().getTextContent().equals("cangur") ||
			   !childNodes.item(1).getFirstChild().getTextContent().equals("cantina") ||
			   !childNodes.item(2).getFirstChild().getTextContent().equals("colina") ||
			   !childNodes.item(3).getFirstChild().getTextContent().equals("cireasa") ){
				check = false;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		assertEquals(true, check);
	}
	
	@Test
	public void loadDictionaryContentTest(){
		
		Dictionary.serializeDictionary("dictionaryTest.xml");
		Dictionary.loadDictionary("dictionaryTest.xml");
		boolean loadedWell = false;
		
		List<WordModel> wordsBeginWithC = Dictionary.getDictionary().get("c");
		
		for(WordModel wm:wordsBeginWithC){
			if(wm.getWord().equals("cireasa") || wm.getWord().equals("cangur") || wm.getWord().equals("colina") || wm.getWord().equals("cantina")){
				loadedWell = true;
			}
		}
		
		assertEquals(true, loadedWell);
	}
}
