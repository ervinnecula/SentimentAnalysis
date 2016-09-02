package training;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import datamodel.SentimentType;
import utilities.Dictionary;
import utilities.LoggerProducer;
import utilities.Normalization;

public class TrainModel {
	
	@Inject
	private static final Logger logger = Logger.getLogger(LoggerProducer.class);
	
	static public void trainModel(String sourceFilePath){
		String result, message, line;
		BufferedReader br = null;
		
		Dictionary.initializeDictionary();
		
		try{
			br = new BufferedReader(new FileReader(sourceFilePath));
			while(null != (line = br.readLine())){
				result = line.substring(0,1);
				message = line.substring(2, line.length());
				
				message = Normalization.normalizeMessage(message);
				
				if(!message.equals("")){
					for(String word: message.split(" ")){
						if(result.equals("0")){
							Dictionary.addWord(word, SentimentType.NEGATIVE);
						}
						else{
							Dictionary.addWord(word, SentimentType.POSITIVE);
						}
					}
				}
			}
	
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(br != null){
				try{
					br.close();
				}catch(IOException ex){
					logger.warn("IOException: ",ex);
				}
			}
		}
		
		Dictionary.serializeDictionary("dictionary.xml");
	}
}
