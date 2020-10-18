package es.udc.fi.dc.fd.model.persistence;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import es.udc.fi.dc.fd.model.User;

@Entity(name = "User")
@Table(name = "user")

public class UserEntity implements User {

	@Transient
	private static final long serialVersionUID = 1328776989450853491L;

	/**
	 * Entity's ID.
	 */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id = -1;

	/**
	 * Name of the entity.
	 * <p>
	 * This is to have additional data apart from the id, to be used on the tests.
	 */
	@Id
	@Column(name = "username", nullable = false, unique = true)
	private String username = "";

	@Column(name = "password", nullable = false, unique = false)
	private String password = "";

	@Column(name = "name", nullable = false, unique = false)
	private String name = "";

	@Column(name = "first_Lastname", nullable = false, unique = false)
	private String firstLastname = "";

	@Column(name = "second_Lastname", nullable = true, unique = false)
	private String secondLastname = "";

	@Column(name = "city", nullable = false, unique = true)
	private String city = "";
	
	@OneToMany(mappedBy="userA")   
    private Set<AdEntity> ads = new HashSet<AdEntity>(0);

	public UserEntity() {

	}

	public UserEntity(String username, String password, String name, String firstLastname, String secondLastname,
			String city, Set<AdEntity> ads) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.firstLastname = firstLastname;
		this.secondLastname = secondLastname;
		this.city = city;
		this.ads = ads;
	}
	
	public UserEntity(String username, String password, String name, String firstLastname, String secondLastname,
			String city) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.firstLastname = firstLastname;
		this.secondLastname = secondLastname;
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
	public String getUsername() {
		return username;
	}

	@Override
	public void setUsername(final String value) {
		username = checkNotNull(value, "Received a null pointer as username");
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
	public String getFirstLastname() {
		return firstLastname;
	}

	@Override
	public void setFirstLastname(final String value) {
		firstLastname = checkNotNull(value, "Received a null pointer as first Lastname");
	}

	@Override
	public String getSecondLastname() {
		return secondLastname;
	}

	@Override
	public void setSecondLastname(final String value) {
		secondLastname = value;
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
	public Set<AdEntity> getAds() {
		return ads;
	}
	
	@Override
	public void setAds(Set<AdEntity> ads) {
		this.ads = ads;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", username=" + username + ", name=" + name + ", firstLastname=" + firstLastname
				+ ", secondLastname=" + secondLastname + ", city=" + city + "]";
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
