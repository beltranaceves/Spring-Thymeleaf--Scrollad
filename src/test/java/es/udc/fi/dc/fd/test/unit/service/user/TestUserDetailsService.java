package es.udc.fi.dc.fd.test.unit.service.user;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.user.UserService;

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })
public final class TestUserDetailsService {

	public TestUserDetailsService() {
		super();
	}

	@Autowired
	private UserService userService;

	@Autowired
	private UserDetailsService userDetailsService;

	private UserEntity createUserToTest(String username) {
		return new UserEntity(username, username, username, username, username, "city", 1, 3.0, 3);
	}

	@Test
	public void loadByUsernameTest() {
		User user = userService.add(createUserToTest("anton"));
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

		assertNotNull(userDetails);
	}

	@Test
	public void loadByUsername() {
		org.junit.jupiter.api.Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			userDetailsService.loadUserByUsername("noexisto");
		});
	}

}
