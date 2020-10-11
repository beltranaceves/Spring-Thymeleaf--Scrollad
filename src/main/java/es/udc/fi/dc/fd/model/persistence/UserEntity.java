package es.udc.fi.dc.fd.model.persistence;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import es.udc.fi.dc.fd.model.User;

@Entity(name = "User")
@Table(name = "users")

public class UserEntity implements User {

	@Transient
	private static final long serialVersionUID = 1328776989450853491L;

	/**
	 * Entity's ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id = -1;

	/**
	 * Name of the entity.
	 * <p>
	 * This is to have additional data apart from the id, to be used on the tests.
	 */

	@Column(name = "login", nullable = false, unique = true)
	private String login = "";

	@Column(name = "password", nullable = false, unique = false)
	private String password = "";

	@Column(name = "name", nullable = false, unique = false)
	private String name = "";

	@Column(name = "first_surname", nullable = false, unique = false)
	private String firstSurname = "";

	@Column(name = "second_surname", nullable = true, unique = false)
	private String secondSurname = "";

	@Column(name = "city", nullable = false, unique = true)
	private String city = "";

	public UserEntity() {

	}

	public UserEntity(Integer id, String login, String password, String name, String firstSurname, String secondSurname,
			String city) {
		super();
		this.login = login;
		this.password = password;
		this.name = name;
		this.firstSurname = firstSurname;
		this.secondSurname = secondSurname;
		this.city = city;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(final Integer value) {
		id = checkNotNull(value, "Received a null pointer as identifier");
	}

	@Override
	public String getLogin() {
		return login;
	}

	@Override
	public void setLogin(final String value) {
		login = checkNotNull(value, "Received a null pointer as login");
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(final String value) {
		password = checkNotNull(value, "Received a null pointer as password");
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String value) {
		name = checkNotNull(value, "Received a null pointer as name");
	}

	@Override
	public String getFirstSurname() {
		return firstSurname;
	}

	@Override
	public void setFirstSurname(final String value) {
		firstSurname = checkNotNull(value, "Received a null pointer as first surname");
	}

	@Override
	public String getSecondSurname() {
		return secondSurname;
	}

	@Override
	public void setSecondSurname(final String value) {
		secondSurname = checkNotNull(value, "Received a null pointer as second surname");
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public void setCity(final String value) {
		city = checkNotNull(value, "Received a null pointer as city");
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", login=" + login + ", name=" + name + ", firstSurname=" + firstSurname
				+ ", secondSurname=" + secondSurname + ", city=" + city + "]";
	}

	@Override
	public final int hashCode() {
		return Objects.hash(id);
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

		final UserEntity other = (UserEntity) obj;
		return Objects.equals(id, other.id);
	}

}
