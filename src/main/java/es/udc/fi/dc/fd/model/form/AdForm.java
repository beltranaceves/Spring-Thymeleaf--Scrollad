package es.udc.fi.dc.fd.model.form;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;


public final class AdForm implements Serializable {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = 1328776989450853491L;

    
    @NotEmpty
    private String title;
    
    @NotEmpty
    private String description;
    
    @NotEmpty
    private String userA;
    
    //@NotEmpty
    //private FileInputStream image;
    
    @NotEmpty
    private String image;

    
    public AdForm() {
        super();
    }

    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((userA == null) ? 0 : userA.hashCode());
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
		
		AdForm other = (AdForm) obj;
		
		if (description == null) {
			if (other.description != null)
				return false;
			
		} else if (!description.equals(other.description))
			return false;
		
		if (image == null) {
			if (other.image != null)
				return false;
			
		} else if (!image.equals(other.image))
			return false;
		
		if (title == null) {
			if (other.title != null)
				return false;
			
		} else if (!title.equals(other.title))
			return false;
		
		if (userA == null) {
			if (other.userA != null)
				return false;
			
		} else if (!userA.equals(other.userA))
			return false;
		
		return true;
	}


	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	//public FileInputStream getImage() {
	//	return image;
	//}
	
	public String getImage() {
		return image;
	}

	public String getUser() {
		return userA;
	}

	
	public void setTitle(final String title) {
		this.title = checkNotNull(title, "Received a null pointer as name");
	}
	
	public void setDescription(final String description) {
		this.description = checkNotNull(description, "Received a null pointer as name");
	}
	
	//public void setImage(final FileInputStream image) {
	//	this.image = checkNotNull(image, "Received a null pointer as name");
	//}
	
	public void setImage(final String image) {
		this.image = checkNotNull(image, "Received a null pointer as name");
	}
	
	public void setUser(final String userA) {
		this.userA = checkNotNull(userA, "Received a null pointer as name");
	}


	@Override
	public String toString() {
		return "AdForm [title=" + title + ", description=" + description + ", user=" + userA + ", image=" + image + "]";
	}


}
