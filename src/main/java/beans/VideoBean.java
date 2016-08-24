package beans;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import service.VideoService;

@ManagedBean
@SessionScoped
public class VideoBean {

	@ManagedProperty(value = "#{videoService}")
	private VideoService vs;
	private String video = "videos/test.mp4";

	public String goToSecondPage() {
		return "second";
	}

	public String goToFirstPage() {
		return "main";
	}

	public VideoService getVs() {
		return vs;
	}

	public void setVs(VideoService vs) {
		this.vs = vs;
	}
	
	public void getResponseVideo(){
		setVideo(vs.getResponseVideo());
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

}