package es.udc.fi.dc.fd.model.persistence;

import static com.google.common.base.Preconditions.checkNotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import es.udc.fi.dc.fd.model.Ad;

@Entity(name = "Ad")
@Table(name = "advertisement")

public class AdEntity implements Ad {

	@Transient
	private static final long serialVersionUID = 1328776989450853491L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@Column(name = "title", nullable = false, unique = true)
	private String title = "";

	@Column(name = "description", nullable = false, unique = true)
	private String description = "";

	@Column(name = "date", nullable = false, unique = true)
	private LocalDateTime date;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "userA", nullable = false)
	private UserEntity userA;

	@OneToMany(mappedBy = "ad", fetch = FetchType.EAGER)
	private List<ImageEntity> images;

	public AdEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		final AdEntity other = (AdEntity) obj;
		return Objects.equals(id, other.id);
	}

	public void setId(final Integer id) {
		this.id = checkNotNull(id, "Received a null pointer as identifier");
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = checkNotNull(title, "Received a null pointer as title");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = checkNotNull(description, "Received a null pointer as description");
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(final LocalDateTime date) {
		this.date = checkNotNull(date, "Received a null pointer as date");
	}

	public UserEntity getUserA() {
		return userA;
	}

	public void setUserA(final UserEntity userA) {
		this.userA = checkNotNull(userA, "Received a null pointer as username");
	}

	public List<ImageEntity> getImages() {
		return images;
	}

	public void setImages(List<ImageEntity> images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "AdEntity [id=" + id + ", title=" + title + ", description=" + description + ", userA="
				+ userA.getUsername() + "]";
	}

}
