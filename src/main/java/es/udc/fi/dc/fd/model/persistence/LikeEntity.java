package es.udc.fi.dc.fd.model.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "likes")
@Table(name = "likes")
public class LikeEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1328776989450853491L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@ManyToOne(targetEntity = UserEntity.class, optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "user")
	private UserEntity user;

	@ManyToOne(targetEntity = AdEntity.class, optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "adLiked")
	private AdEntity adLiked;

	public LikeEntity() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public AdEntity getAdLiked() {
		return adLiked;
	}

	public void setAdLiked(AdEntity adLiked) {
		this.adLiked = adLiked;
	}

	public LikeEntity(UserEntity user, AdEntity adLiked) {
		super();
		this.user = user;
		this.adLiked = adLiked;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adLiked == null) ? 0 : adLiked.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		LikeEntity other = (LikeEntity) obj;
		if (adLiked == null) {
			if (other.adLiked != null)
				return false;
		} else if (!adLiked.equals(other.adLiked))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
