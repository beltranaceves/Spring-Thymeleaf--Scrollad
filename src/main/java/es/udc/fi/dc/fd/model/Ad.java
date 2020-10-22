package es.udc.fi.dc.fd.model;

import java.io.Serializable;

import com.lowagie.text.pdf.codec.Base64;

import es.udc.fi.dc.fd.model.persistence.UserEntity;

public interface Ad extends Serializable {

	public Integer getId();

	public String getTitle();

	public String getDescription();

	public byte[] getImage();

	public UserEntity getUserA();
	
	public String getImageBase64();

	public void setId(final Integer value);

	public void setTitle(final String value);

	public void setDescription(final String value);

	public void setImage(final byte[] value);

	public void setUserA(final UserEntity value);
	
	public void convertAndLoadImageBase64();
	
	public void setImageBase64(final String value);

}
