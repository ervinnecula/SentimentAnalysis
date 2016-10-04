package service;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import analyze.Analysis;
import datamodel.SentimentType;
import utilities.LoggerProducer;
import utilities.Utils;

@ManagedBean
@SessionScoped	
public class VideoService {
	
	@Inject
	private static final Logger logger = Logger.getLogger(LoggerProducer.class);
	
	public String getResponseVideo(String sentence){
		SentimentType st = Analysis.analyzeWordsSentence(sentence);
		String sourcePath = null, destinationPath = null;
		String videoName = null;
		
		try{
			ServletContext servletContext = (ServletContext) FacesContext
			        .getCurrentInstance().getExternalContext().getContext();
			
			sourcePath = servletContext.getRealPath("WEB-INF/classes/reactions/test.mp4");
			
			if(st == SentimentType.NEGATIVE){
				videoName = "sad.mp4";
			}
			if(st == SentimentType.NEUTRAL){
				videoName = "neutral.mp4";
			}
			if(st == SentimentType.POSITIVE){
				videoName = "happy.mp4";
			}
			
			destinationPath = sourcePath.substring(0,28) + videoName;
			
			File destinationFile = new File(destinationPath);
			if(destinationFile.exists()){
				destinationFile.delete();
			}
			
			Utils.copyFileUsingStream(new File(sourcePath), new File(destinationPath));
		
		}
		catch(IOException ex){
			logger.error(ex);
		}

		return videoName;
	}
	
	@PostConstruct
	public String getInitialVideo(){
		
		String sourcePath = null, destinationPath = null;
		
		try{
			ServletContext servletContext = (ServletContext) FacesContext
			        .getCurrentInstance().getExternalContext().getContext();
			
			sourcePath = servletContext.getRealPath("WEB-INF/classes/reactions/blink.mp4");
			destinationPath = sourcePath.substring(0,28) + "blink.mp4";
			
			Utils.copyFileUsingStream(new File(sourcePath), new File(destinationPath));
		
		}
		catch(IOException ex){
			logger.error(ex);
		}
		
		return "blink.mp4";
	}
	
}
