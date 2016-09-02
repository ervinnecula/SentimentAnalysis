package main;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import utilities.*;

public class NormalizationTests {

	@Test
	public void stripNewLineTabTest() {
		String strippedText = Normalization.stripNewLineTab("hi \n dear friend \t how are you\t I hope you are fine\n let me know\n.\n");
		String desiredText = "hi dear friend how are you I hope you are fine let me know.";

		assertEquals(desiredText, strippedText);
	}

	@Test
	public void stripURLsTest() {
		String strippedText = Normalization.stripURLs("http://stackoverflow.com/questions/19025868/what-is-the-meaning-r-carriage-return-in-java-can-any-one-give-a-small-example"
													   + " hello "
													   + "http://olx.ro/oferte/q-seat-arosa/?search%5Bprivate_business%5D=private&page=2"
													   + " how arehttps://www.youtube.com/watch?v=3phVp93WZJ8");
		String desiredText = "hello how are";

		assertEquals(desiredText, strippedText);
		assertEquals("", Normalization.stripURLs("https://www.google.ro/"));
	}

	@Test
	public void stripHashtagsTest() {
		String strippedText = Normalization.stripHashtags("hi #dear #friend how#are you#fine1nigga?");
		String desiredText = "hi how you?";
		
		assertEquals(desiredText, strippedText);
	}
	
	@Test
	public void stripMentionsTest(){
		String strippedText = Normalization.stripHashtags("hi #dear #friend how#are you#fine1nigga?");
		String desiredText = "hi how you?";
		
		assertEquals(desiredText, strippedText);
	}
	
	@Test
	public void stripQuotAmpLtTest(){
		String strippedText = Normalization.stripQuotAmpLt("&amphi &ltfriend&quot &quothow&lt &amp you?");
		String desiredText = "hi friend how you?";
		
		assertEquals(desiredText, strippedText);
	}
	
	@Test
	public void correctApostrophesTest(){
		String textToCorrect = Normalization.correctApostrophes("’Hi ’dear’ frie’nd’");
		String desiredText = "'Hi 'dear' frie'nd'";
		
		assertEquals(desiredText, textToCorrect);
	}
	
	@Test
	public void stripNumbersTest(){
		String textToCorrect = Normalization.stripNumbers("hi189 11dear48 fri238end");
		String desiredText = "hi dear friend";
				
		assertEquals(desiredText, textToCorrect);
	}
	
	@Test
	public void correctDotCommaDoublePointsTest(){
		String textToCorrect = Normalization.correctDotCommaDoublePoints("hi ,dear .friend :a :asd .smth. good");
		String desiredText = "hi, dear. friend: a: asd. smth. good";
		
		assertEquals(desiredText, textToCorrect);
	}
	
	@Test
	public void removeQuotesApostrophesTest(){
		String textToCorrect = Normalization.removeQuotesApostrophes("hi'dear\"friend's'");
		String desiredText = "hi dear friend s ";
		
		assertEquals(desiredText,textToCorrect);
	}
	
	@Test
	public void replaceUnderscoreTest(){
		String textToCorrect = Normalization.replaceUnderscore("hi_dear _ friend_");
		String desiredText = "hi dear   friend ";
		
		assertEquals(desiredText,textToCorrect);
	}
	
	@Test
	public void stripInnerMultipleSpacesTest(){
		String textToCorrect = Normalization.stripMultipleSpaces("hi dear  friend    say hi           \n  ");
		String desiredText = "hi dear friend say hi";
		
		assertEquals(desiredText,textToCorrect);
	}
	
	@Test
	public void stripUnnecessaryWordsTest(){
		String textToCorrect = Normalization.stripUnnecessaryWords("hi an dear have friend when you");
		String desiredText = "hi  dear  friend  ";
		
		assertEquals(desiredText,textToCorrect);
	}
	
	@Test
	public void normalizeMessageTest(){
		String textToCorrect = Normalization.normalizeMessage("hi an dear have friend   when   you_fine'dear :stupid .but .works ,does12it? #sure #it @ervin http://checkthis.com");
		String desiredText = "hi dear friend fine dear: stupid. but. works, doesit?";
		
		assertEquals(desiredText, textToCorrect);
	}

}
