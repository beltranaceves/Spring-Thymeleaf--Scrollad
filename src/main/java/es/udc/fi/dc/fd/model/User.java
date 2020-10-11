package es.udc.fi.dc.fd.model;

import java.io.Serializable;

public interface User extends Serializable {

	Integer getId();

	void setId(final Integer value);

	String getLogin();

	void setLogin(final String value);

	String getPassword();

	void setPassword(final String value);

	String getName();

	void setName(final String value);

	String getFirstSurname();

	void setFirstSurname(final String value);

	String getSecondSurname();

	void setSecondSurname(final String value);

	String getCity();

	void setCity(final String value);

}