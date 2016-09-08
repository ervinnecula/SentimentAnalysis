package service;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import analyze.Analysis;

@ManagedBean
@SessionScoped	
public class VideoService {
	
	public String getResponseVideo(String sentence){
		Analysis.analyzeWordsSentence(sentence);
		
		return "test.mp4";
	}
	
}
