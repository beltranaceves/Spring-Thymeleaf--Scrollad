/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2020 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package es.udc.fi.dc.fd.model.form;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.Objects;

import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.base.MoreObjects;

/**
 * Represents the form used for the creating and editing example entities.
 * <p>
 * This is a DTO, meant to allow communication between the view and the
 * controller, and mapping all the values from the form. Each of field in the
 * DTO matches a field in the form.
 * <p>
 * Includes Java validation annotations, for applying binding validation. This
 * way the controller will make sure it receives all the required data.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class UserForm implements Serializable {

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
	private String login;

	@NotEmpty
	private String password;

	@NotEmpty
	private String name;

	@NotEmpty
	private String firstSurname;

	private String secondSurname;

	@NotEmpty
	private String city;

	/**
	 * Constructs a DTO for the example entity form.
	 */
	public UserForm() {
		super();
	}

	public final String getLogin() {
		return login;
	}

	public final String getPassword() {
		return password;
	}

	public final String getName() {
		return name;
	}

	public final String getFirstSurname() {
		return firstSurname;
	}

	public final String getSecondSurname() {
		return secondSurname;
	}

	public final String getCity() {
		return city;
	}

	/**
	 * Sets the value of the name field.
	 * 
	 * @param value the new value for the name field
	 */
	public final void setLogin(final String value) {
		login = checkNotNull(value, "Received a null pointer as login");
	}

	public final void setPassword(final String value) {
		password = checkNotNull(value, "Received a null pointer as password");
	}

	public final void setName(final String value) {
		name = checkNotNull(value, "Received a null pointer as name");
	}

	public final void setFirstSurname(final String value) {
		firstSurname = checkNotNull(value, "Received a null pointer as first surname");
	}

	public final void setSecondSurname(final String value) {
		secondSurname = value;
	}

	public final void setCity(final String value) {
		city = checkNotNull(value, "Received a null pointer as city");
	}

	@Override
	public final String toString() {
		return MoreObjects.toStringHelper(this).add("name", name).toString();
	}

	@Override
	public final int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		final UserForm other = (UserForm) obj;
		return Objects.equals(name, other.name);
	}

}
