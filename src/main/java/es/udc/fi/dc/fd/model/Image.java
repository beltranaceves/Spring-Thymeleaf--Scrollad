package es.udc.fi.dc.fd.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;

public interface Image extends Serializable {

	public Integer getId();
	
	public AdEntity getAd();

	public byte[] getImage();

	public String getImageBase64();

	public void setId(final Integer value);

	public void setAd(final AdEntity value);
	
	public void setImage(byte[] value);
	
	public void setImageBase64(final String value);
	
	public void convertAndLoadImageBase64();

}
