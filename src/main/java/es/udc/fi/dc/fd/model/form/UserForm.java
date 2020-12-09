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
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

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
	private String username;

	@NotEmpty
	private String password;

	@NotEmpty
	private String name;

	@NotEmpty
	private String firstLastname;

	private String secondLastname;
	
	private Double averageScore;
	
	private Integer scoreCount;
	
	private Integer sumScore;

	@NotEmpty
	private String city;

	private Set<String> scored;
	
	private Set<String> followed;

	/**
	 * Constructs a DTO for the example entity form.
	 */
	public UserForm() {
		super();
	}

	public final String getUsername() {
		return username;
	}

	public final String getPassword() {
		return password;
	}

	public final String getName() {
		return name;
	}

	public final String getFirstLastname() {
		return firstLastname;
	}

	public final String getSecondLastname() {
		return secondLastname;
	}

	public final String getCity() {
		return city;
	}

	public final Double getAverageScore() {
		return averageScore;
	}
	
	public final Integer getScoreCount() {
		return scoreCount;
	}
	
	public final Integer getSumScore() {
		return sumScore;
	}

	public final Set<String> getScored() {
		return scored;
	}
	
	public final Set<String> getFollowed() {
		return followed;
	}

	/**
	 * Sets the value of the name field.
	 * 
	 * @param value the new value for the name field
	 */
	public final void setUsername(final String value) {
		username = checkNotNull(value, "Received a null pointer as Username");
	}

	public final void setPassword(final String value) {
		password = checkNotNull(value, "Received a null pointer as password");
	}

	public final void setName(final String value) {
		name = checkNotNull(value, "Received a null pointer as name");
	}

	public final void setFirstLastname(final String value) {
		firstLastname = checkNotNull(value, "Received a null pointer as first Lastname");
	}

	public final void setSecondLastname(final String value) {
		secondLastname = value;
	}

	public final void setCity(final String value) {
		city = checkNotNull(value, "Received a null pointer as city");
	}

	public final void setAverageScore(final Double value) {
		averageScore = checkNotNull(value, "Received a null pointer as averageScore");
	}
	
	public final void setScoreCount(final Integer value) {
		scoreCount = checkNotNull(value, "Received a null pointer as scoreCount");
	}
	
	public final void setSumScore(final Integer value) {
		sumScore = checkNotNull(value, "Received a null pointer as sumScore");
	}

	public final void setScored(final Set<String> value) {
		scored = value;
	}
	
	public final void setFollowed(final Set<String> value) {
		followed = value;
	}

	@Override
	public String toString() {
		return "UserForm [Username=" + username + ", name=" + name + ", firstLastname=" + firstLastname
				+ ", secondLastname=" + secondLastname + ", city=" + city + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, firstLastname, username, name, secondLastname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof UserForm))
			return false;
		UserForm other = (UserForm) obj;
		return Objects.equals(city, other.city) && Objects.equals(firstLastname, other.firstLastname)
				&& Objects.equals(username, other.username) && Objects.equals(name, other.name)
				&& Objects.equals(secondLastname, other.secondLastname);
	}

}
