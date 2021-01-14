package es.udc.fi.dc.fd.test.unit.model.dto;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.dto.UserDto;

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })
public final class UserDtoTest {

	public UserDtoTest() {
		super();
	}

	@Test
	public void DtoTest() {
		UserDto userDto = new UserDto();
		
		String password = "contraseña";
		userDto.setPassword(password);
		
		String token = "asdaiusyhdi2u342908342";
		userDto.setToken(token);
		
		String username = "username";
		userDto.setUsername(username);
		
		
		assertEquals(userDto.getPassword(), password);
		assertEquals(userDto.getToken(), token);
		assertEquals(userDto.getUsername(), username);
		
		username = "usenamr2";
		
		password = "admin";
		
		UserDto userDto2 = new UserDto(username, password);
		
		assertNotEquals(userDto, userDto2);
		assertNotEquals(userDto.hashCode(), userDto2.hashCode());
	}


}
;