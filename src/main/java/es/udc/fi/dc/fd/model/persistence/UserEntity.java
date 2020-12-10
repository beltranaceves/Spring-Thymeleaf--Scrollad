package es.udc.fi.dc.fd.model.persistence;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.common.collect.Sets;

import es.udc.fi.dc.fd.model.User;

@Entity(name = "User")
@Table(name = "user")
@SecondaryTable(name = "userScored")
@SecondaryTable(name = "userFollowed")

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
	
	@Column(name = "isPremium", nullable = false)
	private Boolean isPremium = false;

	@Column(name = "scoreCount", nullable = false, unique = true)
	private Integer scoreCount = 0;
	
	@Column(name = "sumScore", nullable = false, unique = true)
	private Integer sumScore = 0;
	
	@Column(name = "averageScore", nullable = false, unique = true)
	private Double averageScore = 0.0;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "userScored", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"))
	@Column(name = "scoredUser", nullable = false)
	private Set<String> scoredUser = Sets.newHashSet();
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "userFollowed", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"))
	@Column(name = "followedUser", nullable = false)
	private Set<String> followedUser = Sets.newHashSet();

	@OneToMany(mappedBy = "userA")
	private Set<AdEntity> ads = new HashSet<AdEntity>(0);

	public UserEntity() {
		super();
	}

	public UserEntity(String username, String password, String name, String firstLastname, String secondLastname,
			String city,Integer scoreCount,Double averageScore,Set<String> scoredUser, Set<AdEntity> ads, Set<String> followed) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.firstLastname = firstLastname;
		this.secondLastname = secondLastname;
		this.city = city;
		this.scoreCount=scoreCount;
		this.averageScore=averageScore;
		this.scoredUser=scoredUser;
		this.ads = ads;
		this.followedUser = followed;
	}

	public UserEntity(String username, String password, String name, String firstLastname, String secondLastname,
			String city,Integer scoreCount,Double averageScore,Integer sumScore) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.firstLastname = firstLastname;
		this.secondLastname = secondLastname;
		this.city = city;
		this.scoreCount=scoreCount;
		this.averageScore=averageScore;
		this.sumScore=sumScore;
	}
	
	public UserEntity(String username, String password, String name, String firstLastname, String secondLastname,
			String city,Boolean isPremium, Integer scoreCount,Double averageScore,Integer sumScore) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.firstLastname = firstLastname;
		this.secondLastname = secondLastname;
		this.city = city;
		this.isPremium = isPremium;
		this.scoreCount=scoreCount;
		this.averageScore=averageScore;
		this.sumScore=sumScore;
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
	public Boolean getIsPremium() {
		return isPremium;
	}

	@Override
	public void setIsPremium(final Boolean value) {
		isPremium = checkNotNull(value, "Received a null pointer as city");
	}
	
	@Override
	public Double getAverageScore() {
		return averageScore;
	}

	@Override
	public void setAverageScore(final Double value) {
		averageScore = checkNotNull(value, "Received a null pointer as averageScore");
	}
	
	@Override
	public Integer getSumScore() {
		return sumScore;
	}

	@Override
	public void setSumScore(final Integer value) {
		sumScore = checkNotNull(value, "Received a null pointer as sumScore");
	}
	
	@Override
	public Integer getScoreCount() {
		return scoreCount;
	}

	@Override
	public void setScoreCount(final Integer value) {
		scoreCount = checkNotNull(value, "Received a null pointer as scoreCount");
	}
	
	@Override
	public Set<String> getFollowed() {
		return followedUser;
	}

	@Override
	public void setFollowed(final Set<String> value) {
		followedUser = value;
	}

	@Override
	public Set<String> getScored() {
		return scoredUser;
	}

	@Override
	public void setScored(final Set<String> value) {
		scoredUser = value;
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
