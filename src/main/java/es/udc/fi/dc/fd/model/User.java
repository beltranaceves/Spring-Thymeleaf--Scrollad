package es.udc.fi.dc.fd.model;

import java.io.Serializable;
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
	
	
	Double getAverageScore();
	
	void setAverageScore(final Double value);
	
	Integer getSumScore();
	
	void setSumScore(final Integer value);
	
	Set<String> getScored();
	
	void setScored(final Set<String> value);
	
	Integer getScoreCount();
	
	void setScoreCount(final Integer value);

	
	Set<String> getFollowed();

	void setFollowed(final Set<String> value);

	Set<AdEntity> getAds();

	void setAds(Set<AdEntity> ads);

}