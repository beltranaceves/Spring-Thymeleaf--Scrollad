package es.udc.fi.dc.fd.model;

import java.io.FileInputStream;
import java.io.Serializable;

public interface Ad extends Serializable {

	public Integer getId();
	
	public String getTitle();
	
	public String getDescription();
	
	//public FileInputStream getImage();
	
	public String getImage();
	
	public String getUserA();
	
	
	public void setId(
			final Integer value);
	
	public void setTitle(
			final String value);
	
	public void setDescription(
			final String value);
	
	//public void setImage(
	//		final FileInputStream value);
	
	public void setImage(
			final String value);
	
	public void setUserA(
			final String value);
	
	
	
	
	
}
