package es.udc.fi.dc.fd.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;

public interface Order extends Serializable {
	
	public Integer getId();
	
	public void setId(Integer id);
	
	public String getCreditCard();

	public void setCreditCard(String creditCard);

	public LocalDateTime getDate();
	
	public void setDate(LocalDateTime date);

	public String getAddress();

	public void setAddress(String address);

	public UserEntity getUser();

	public void setUser(UserEntity user);

	public AdEntity getAd();

	public void setAd(AdEntity ad);
}
