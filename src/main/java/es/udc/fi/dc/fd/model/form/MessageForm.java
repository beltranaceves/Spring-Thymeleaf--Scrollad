package es.udc.fi.dc.fd.model.form;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public final class MessageForm implements Serializable {

	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = 1328776989450853491L;

	/**
	 * Name field.
	 * <p>
	 * This is a required field and can't be empty.
	 */
	@NotEmpty
	private String text;

	@NotEmpty
	private String vendor;

	public MessageForm() {
		super();
	}

	public String getText() {
		return text;
	}

	public String getVendor() {
		return vendor;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((vendor == null) ? 0 : vendor.hashCode());
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
		MessageForm other = (MessageForm) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (vendor == null) {
			if (other.vendor != null)
				return false;
		} else if (!vendor.equals(other.vendor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MessageForm [text=" + text + ", vendor=" + vendor + "]";
	}

}
