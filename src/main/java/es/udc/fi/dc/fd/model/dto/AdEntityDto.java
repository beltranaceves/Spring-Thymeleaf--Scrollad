package es.udc.fi.dc.fd.model.dto;

import static com.google.common.base.Preconditions.checkNotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import es.udc.fi.dc.fd.model.persistence.ImageEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;

public class AdEntityDto {
	
	private Integer id;

	private String title = "";

	private String description = "";

	private String date;

	private Double price;

	private UserEntity userA;

	private List<ImageEntity> images;

	private Boolean isOnHold;
	
	private Boolean isSold;

	public AdEntityDto() {
		super();
	}

	public AdEntityDto(List<ImageEntity> images, Integer id, String title, String description, LocalDateTime date, Double price, UserEntity userA, Boolean isOnHold, Boolean isSold) {
		super();
		this.images = images;
		this.id = id;
		this.title = title;
		this.description = description;
		this.date = date.toString();
		this.price = price;
		this.userA = userA;
		this.isOnHold = isOnHold;
		this.isSold = isSold;
	}

	private String prepareStringDate(String dateString) {
		String[] separatedDate = dateString.split("T");
		String[] separatedTime = separatedDate[1].split(":");
		String fecha = separatedDate[0];
		String hora = separatedTime[0];
		String minuto = separatedTime[1];
		return fecha+ " " + hora + ":" + minuto;
	}

	public Integer getId() {
		return id;
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

	public Boolean getIsOnHold() {
		return isOnHold;
	}
		
	public Boolean getIsSold() {
		return isSold;
	}

	public void setDescription(final String description) {
		this.description = checkNotNull(description, "Received a null pointer as description");
	}

	public String getDate() {
		return date;
	}

	public void setDate(final String date) {
		checkNotNull(date, "Received a null pointer as date");
		this.date = prepareStringDate(date.toString());
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	public void setIsOnHold(final Boolean isOnHold) {
		this.isOnHold = isOnHold;
	}

	public void setIsSold(Boolean isSold) {
		this.isSold = isSold;
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

		final AdEntityDto other = (AdEntityDto) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "AdEntity [id=" + id + ", title=" + title + ", description=" + description + ", date=" + date
				+ ", price=" + price + ", userA=" + userA + ", images=" + images + ", isOnHold=" + isOnHold + "]";
	}

}
