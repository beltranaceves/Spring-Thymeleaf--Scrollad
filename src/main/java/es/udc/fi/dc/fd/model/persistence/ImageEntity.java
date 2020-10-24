package es.udc.fi.dc.fd.model.persistence;

import static com.google.common.base.Preconditions.checkNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.lowagie.text.pdf.codec.Base64;

import es.udc.fi.dc.fd.model.Ad;
import es.udc.fi.dc.fd.model.Image;

@Entity(name = "Image")
@Table(name = "Images")

public class ImageEntity implements Image {

	@Transient
	private static final long serialVersionUID = 1328776989450853491L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = 
		    CascadeType.MERGE)
	@JoinColumn(name = "ad", nullable = false)
	private AdEntity ad;
	
	@Column(name = "image", nullable = false, unique = true)
	private byte[] image;

	@Transient
	private String imageBase64;

	public ImageEntity() {
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

		final ImageEntity other = (ImageEntity) obj;
		return Objects.equals(id, other.id);
	}

	public void setId(final Integer id) {
		this.id = checkNotNull(id, "Received a null pointer as identifier");
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] value) {
		this.image = value;
	}

	public AdEntity getAd() {
		return ad;
	}

	public void setAdEntity(final AdEntity adEntity) {
		this.ad = checkNotNull(adEntity, "Received a null pointer as username");
	}

	public String getImageBase64() {
		return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}
	
	public void convertAndLoadImageBase64() {
		this.setImageBase64(Base64.encodeBytes(this.image));
	}
	
	public void setAd(AdEntity value) {
		this.ad = value;
		
	}
	
	@Override
	public String toString() {
		return "ImageEntity [id=" + id + ", ad=" + ad + ", image=" + Arrays.toString(image) + ", imageBase64="
				+ imageBase64 + "]";
	}


}
