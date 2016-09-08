package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import service.VideoService;

@ManagedBean
@SessionScoped
public class MainBean {

	@ManagedProperty(value = "#{videoService}")
	private VideoService vs;
	private String video;
	private String text;
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public VideoService getVs() {
		return vs;
	}
	
	public void setVs(VideoService vs) {
		this.vs = vs;
	}
	
	public void getResponseVideo(){
		setVideo(vs.getResponseVideo(text));
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

}