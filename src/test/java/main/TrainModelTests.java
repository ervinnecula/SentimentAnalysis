package main;

import static org.junit.Assert.assertEquals;

import java.io.File;
import org.junit.Test;

import analyze.Dictionary;
import training.TrainModel;

public class TrainModelTests {

	@Test
	public void trainModelTestExist() {
		Dictionary.initializeDictionary();
		TrainModel.trainModel("finaltraining.csv");
		boolean dictionaryNotNull = false; 
		
		if(Dictionary.getDictionary() != null){
			dictionaryNotNull = true;
		}
		
		assertEquals(true, dictionaryNotNull);
	}
	
	@Test
	public void initializeTrainSerialize(){
		Dictionary.initializeDictionary();
		TrainModel.trainModel("finaltraining.csv");
		Dictionary.serializeDictionary("dictionaryTest.xml");
		boolean wellDone = false; 
		File f = new File("dictionaryTest.xml");
		
		if(Dictionary.getDictionary() != null && f.exists() && !f.isDirectory()){
			wellDone = true;
		}
		
		assertEquals(true, wellDone);
	}

}
