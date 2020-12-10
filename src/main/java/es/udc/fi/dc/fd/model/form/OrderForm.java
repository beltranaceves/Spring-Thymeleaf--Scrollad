package es.udc.fi.dc.fd.model.form;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public final class OrderForm implements Serializable {
	
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = 1328776989450853491L;
	
	@NotEmpty
	private String creditCard;

	@NotEmpty
	private String address;
	
	private Integer adId;
	
	public OrderForm() {
		super();
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getAdId() {
		return adId;
	}

	public void setAdId(Integer adId) {
		this.adId = adId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adId == null) ? 0 : adId.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((creditCard == null) ? 0 : creditCard.hashCode());
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
		OrderForm other = (OrderForm) obj;
		if (adId == null) {
			if (other.adId != null)
				return false;
		} else if (!adId.equals(other.adId))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (creditCard == null) {
			if (other.creditCard != null)
				return false;
		} else if (!creditCard.equals(other.creditCard))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderForm [creditCard=" + creditCard + ", address=" + address + ", ad=" + adId + "]";
	}

}
