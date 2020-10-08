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

import com.google.common.base.MoreObjects;

import es.udc.fi.dc.fd.model.User;

@Entity(name = "User")
@Table(name = "users")

public class UserEntity implements User{

	@Transient
    private static final long serialVersionUID = 1328776989450853491L;

    /**
     * Entity's ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer           id               = -1;

    /**
     * Name of the entity.
     * <p>
     * This is to have additional data apart from the id, to be used on the
     * tests.
     */
    
    @Column(name = "login", nullable = false, unique = true)
    private String            login             = "";
    
    @Column(name = "name", nullable = false, unique = true)
    private String            name             = "";
    
    @Column(name = "city", nullable = false, unique = true)
    private String            city             = "";
    
    public UserEntity() {
        super();
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

	@Override
    public Integer getId() {
		return id;
	}
	
	@Override
    public final int hashCode() {
        return Objects.hash(id);
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
        name = checkNotNull(value, "Received a null pointer as login");
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
	public String getCity() {
		return city;
	}

	@Override
    public void setCity(final String value) {
        name = checkNotNull(value, "Received a null pointer as city");
    }
    
	@Override
    public final String toString() {
        return MoreObjects.toStringHelper(this).add("entityId", id).toString();
    }
	
}
