package es.udc.fi.dc.fd.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;

@Transactional
public class SearchDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3747582962141968902L;

	private List<Integer> likesList;
	
	private UserEntity user;
	
	private List<String> cities;
	
	private Integer scoreCount;
	
	private Set<String> scoredUsers; 
	
	private Set<String> follows;
	
	private List<AdEntity> advertisements;

	private List<AdEntity> premiumAdvertisements;

	public SearchDto(List<Integer> likesList, Integer scoreCount, UserEntity user, List<String> cities,
			Set<String> scoredUsers, Set<String> follows, List<AdEntity> advertisements,
			List<AdEntity> premiumAdvertisements) {
		super();
		this.likesList = likesList;
		this.user = user;
		this.cities = cities;
		this.scoreCount = scoreCount;
		this.scoredUsers = scoredUsers;
		this.follows = follows;
		this.advertisements = advertisements;
		this.premiumAdvertisements = premiumAdvertisements;
	}

	public List<Integer> getLikesList() {
		return likesList;
	}

	public void setLikesList(List<Integer> likesList) {
		this.likesList = likesList;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	
	public List<String> getCities() {
		return cities;
	}

	public void setCities(List<String> cities) {
		this.cities = cities;
	}

	public Integer getScoreCount() {
		return scoreCount;
	}

	public void setScoreCount(Integer scoreCount) {
		this.scoreCount = scoreCount;
	}

	public Set<String> getScoredUsers() {
		return scoredUsers;
	}

	public void setScoredUsers(Set<String> scoredUsers) {
		this.scoredUsers = scoredUsers;
	}

	public Set<String> getFollows() {
		return follows;
	}

	public void setFollows(Set<String> follows) {
		this.follows = follows;
	}

	public List<AdEntity> getAdvertisements() {
		return advertisements;
	}

	public void setAdvertisements(List<AdEntity> advertisements) {
		this.advertisements = advertisements;
	}

	public List<AdEntity> getPremiumAdvertisements() {
		return premiumAdvertisements;
	}

	public void setPremiumAdvertisements(List<AdEntity> premiumAdvertisements) {
		this.premiumAdvertisements = premiumAdvertisements;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((advertisements == null) ? 0 : advertisements.hashCode());
		result = prime * result + ((cities == null) ? 0 : cities.hashCode());
		result = prime * result + ((follows == null) ? 0 : follows.hashCode());
		result = prime * result + ((likesList == null) ? 0 : likesList.hashCode());
		result = prime * result + ((premiumAdvertisements == null) ? 0 : premiumAdvertisements.hashCode());
		result = prime * result + ((scoreCount == null) ? 0 : scoreCount.hashCode());
		result = prime * result + ((scoredUsers == null) ? 0 : scoredUsers.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchDto other = (SearchDto) obj;
		if (advertisements == null) {
			if (other.advertisements != null)
				return false;
		} else if (!advertisements.equals(other.advertisements))
			return false;
		if (cities == null) {
			if (other.cities != null)
				return false;
		} else if (!cities.equals(other.cities))
			return false;
		if (follows == null) {
			if (other.follows != null)
				return false;
		} else if (!follows.equals(other.follows))
			return false;
		if (likesList == null) {
			if (other.likesList != null)
				return false;
		} else if (!likesList.equals(other.likesList))
			return false;
		if (premiumAdvertisements == null) {
			if (other.premiumAdvertisements != null)
				return false;
		} else if (!premiumAdvertisements.equals(other.premiumAdvertisements))
			return false;
		if (scoreCount == null) {
			if (other.scoreCount != null)
				return false;
		} else if (!scoreCount.equals(other.scoreCount))
			return false;
		if (scoredUsers == null) {
			if (other.scoredUsers != null)
				return false;
		} else if (!scoredUsers.equals(other.scoredUsers))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
}
