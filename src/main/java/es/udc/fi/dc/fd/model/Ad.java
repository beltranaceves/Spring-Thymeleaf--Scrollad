package es.udc.fi.dc.fd.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import es.udc.fi.dc.fd.model.persistence.UserEntity;

public interface Ad extends Serializable {

	public Integer getId();

	public String getTitle();

	public String getDescription();
	
	public LocalDateTime getDate();

	public byte[] getImage();

	public UserEntity getUserA();
	
	public String getImageBase64();

	public void setId(final Integer value);

	public void setTitle(final String value);

	public void setDescription(final String value);
	
	public void setDate(final LocalDateTime value);

	public void setImage(final byte[] value);

	public void setUserA(final UserEntity value);
	
	public void convertAndLoadImageBase64();
	
	public void setImageBase64(final String value);

}
