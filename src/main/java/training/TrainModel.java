package training;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import analyze.Dictionary;
import analyze.Normalization;
import datamodel.SentimentType;
import utilities.LoggerProducer;

public class TrainModel {
	
	@Inject
	private static final Logger logger = Logger.getLogger(LoggerProducer.class);
	
	public static void trainModel(String sourceFilePath){
		String result, message, line;
		BufferedReader br = null;
		
		try{
			ClassLoader classLoader = TrainModel.class.getClassLoader();
			File file = new File(classLoader.getResource(sourceFilePath).getFile());
			
			br = new BufferedReader(new FileReader(file));
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
					logger.error("IOException: ",ex);
				}
			}
		}
	}
}
