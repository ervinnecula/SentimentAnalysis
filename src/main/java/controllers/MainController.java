package controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import functionality.FunctionalityBean;

@ManagedBean(name="hello")
@SessionScoped
public class MainController {
	
	@ManagedProperty(value="#{functionalityBean}")
	private FunctionalityBean fb;

	public FunctionalityBean getFb() {
		return fb;
	}

	public void setFb(FunctionalityBean fb) {
		this.fb = fb;
	}

	public String getTextFromFB(){
		return fb.getMessage();
	}
	
	public String goToSecondPage(){
		return "second";
	}
	
	public String goToFirstPage(){
		return "main";
	}
	
}