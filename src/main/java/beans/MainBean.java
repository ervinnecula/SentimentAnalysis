package beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import analyze.Dictionary;
import service.VideoService;
import training.TrainModel;

@ManagedBean
@SessionScoped
public class MainBean {

	@ManagedProperty(value = "#{videoService}")
	private VideoService vs;
	private String video;
	private String text;
	private boolean firstTime = true;
	
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
	
	public void getResponseVideo() {
		setVideo(vs.getResponseVideo(text));
		firstTime=false;
	}
	
	public String getVideo() {
		if(Dictionary.getDictionary() == null){
			Dictionary.loadDictionary("dictionary.xml");
		}
		return video;
	}
	
	@PostConstruct
	public void checkDictionaryBuilt() {
		if(firstTime == true){
			if(Dictionary.getDictionary() == null){
				Dictionary.initializeDictionary();
				TrainModel.trainModel("finaltraining.csv");
				Dictionary.serializeDictionary("dictionaray.xml");
			}
			video = vs.getInitialVideo();
			firstTime = false;
		}
	}

	public void setVideo(String video) {
		this.video = video;
	}

}