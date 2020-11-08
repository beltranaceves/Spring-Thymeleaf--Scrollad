package es.udc.fi.dc.fd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import es.udc.fi.dc.fd.model.persistence.AdEntity;

public interface User extends Serializable {

	Integer getId();

	void setId(final Integer value);

	String getUsername();

	void setUsername(final String value);

	String getPassword();

	void setPassword(final String value);

	String getName();

	void setName(final String value);

	String getFirstLastname();

	void setFirstLastname(final String value);

	String getSecondLastname();

	void setSecondLastname(final String value);

	String getCity();

	void setCity(final String value);

	List<String> getFollowed();

	void setFollowed(final ArrayList<String> value);

	Set<AdEntity> getAds();

	void setAds(Set<AdEntity> ads);

}