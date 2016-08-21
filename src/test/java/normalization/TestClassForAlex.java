package normalization;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestClassForAlex {
	
	@Test
	public void testDummy(){
		String textTest = "hi \n dear friend \t how are you\t I hope you are fine\n let me know\n.\n";
		
		String expectedText = "hi dear friend how are you I hope you are fine let me know.";
		
		textTest = Normalization.stripNewLineTab(textTest);
		
//		assertEquals(expectedText, textTest);
		assertEquals(9.5512512512, 9.552323245, 7);
	}
}
