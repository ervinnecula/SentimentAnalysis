package service;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped	
public class VideoService {
	
	private static final String HAPPY_VIDEO = "videos/test.mp4";
	
	public String getResponseVideo(){
		//TODO
		return HAPPY_VIDEO;
	}
	
}
