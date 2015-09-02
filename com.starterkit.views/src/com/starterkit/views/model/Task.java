package com.starterkit.views.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Task {
	private Long id;
	private String name;
	private String status;
	private String date;
	private String info;
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	public Task() {

	}

	public Task(Long id, String name, String status, String date, String info) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.date = date;
		this.info = info;
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		propertyChangeSupport.firePropertyChange("id", this.id, this.id = id);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name){
		propertyChangeSupport.firePropertyChange("name", this.name, this.name = name);
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status){
		propertyChangeSupport.firePropertyChange("status", this.status, this.status = status);
	}

	public String getDate() {
		return date;
	}
	
	public void setDate(String date){
		propertyChangeSupport.firePropertyChange("date", this.date, this.date = date);
	}

	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info){
		propertyChangeSupport.firePropertyChange("info", this.info, this.info = info);
	}

}
