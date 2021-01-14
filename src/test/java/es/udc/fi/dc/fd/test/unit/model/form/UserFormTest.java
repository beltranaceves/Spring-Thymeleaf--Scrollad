package es.udc.fi.dc.fd.test.unit.model.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.form.UserForm;

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })
public final class UserFormTest {

	public UserFormTest() {
		super();
	}

	@Test
	public void FormTest() {
		UserForm userForm = new UserForm();
		
		Double averageScore = 2.0;
		userForm.setAverageScore(averageScore);
		
		String city = "A Coruña";
		userForm.setCity(city);
		
		String firstLastName = "Aceves";
		userForm.setFirstLastname(firstLastName);
		
		Set<String> followed = new HashSet<String>();
		followed.add("viewer2");
		userForm.setFollowed(followed);
		
		String name = "Beltran";
		userForm.setName(name);
		
		String password = "ayuda";
		userForm.setPassword(password);
		
		Integer scoreCount = 1;
		userForm.setScoreCount(scoreCount);
		
		Set<String> scored = new HashSet<String>();
		followed.add("viewer2");
		userForm.setScored(scored);
		
		String secondLastName = "Gil";
		userForm.setSecondLastname(secondLastName);
		
		Integer sumScore = 1;
		userForm.setSumScore(sumScore);
		
		String username = "beltran";
		userForm.setUsername(username);
		
		assertEquals(userForm.getAverageScore(), averageScore);
		assertEquals(userForm.getCity(), city);
		assertEquals(userForm.getFirstLastname(), firstLastName);
		assertEquals(userForm.getFollowed(), followed);
		assertEquals(userForm.getName(), name);
		assertEquals(userForm.getPassword(), password);
		assertEquals(userForm.getScoreCount(), scoreCount);
		assertEquals(userForm.getScored(), scored);
		assertEquals(userForm.getSecondLastname(), secondLastName);
		assertEquals(userForm.getSumScore(), sumScore);
		assertEquals(userForm.getUsername(), username);
		
		
		UserForm userForm2 = userForm;
		assertEquals(userForm, userForm2);
		
		
		userForm2 = new UserForm();
		
		averageScore = 2.0;
		userForm2.setAverageScore(averageScore);
		
		city = "A Coruña";
		userForm2.setCity(city);
		
		firstLastName = "Aceves";
		userForm2.setFirstLastname(firstLastName);
		
		followed = new HashSet<String>();
		followed.add("viewer2");
		userForm2.setFollowed(followed);
		
		name = "Beltran";
		userForm2.setName(name);
		
		password = "ayuda";
		userForm2.setPassword(password);
		
		scoreCount = 1;
		userForm2.setScoreCount(scoreCount);
		
		scored = new HashSet<String>();
		followed.add("viewer2");
		userForm2.setScored(scored);
		
		secondLastName = "Gil";
		userForm2.setSecondLastname(secondLastName);
		
		sumScore = 1;
		userForm2.setSumScore(sumScore);
		
		username = "beltran";
		userForm2.setUsername(username);
		
		assertEquals(userForm.toString(), userForm2.toString());
		assertEquals(userForm.hashCode(), userForm2.hashCode());
		assertEquals(userForm, userForm);
		assertEquals(userForm, userForm2);
		
		
		
		
		userForm2.setCity("dasd");
		assertNotEquals(userForm, userForm2);
		
		userForm2.setFirstLastname("asdasd");
		assertNotEquals(userForm, userForm2);
		
		userForm2.setUsername("sdasdasd");
		assertNotEquals(userForm, userForm2);
		
		userForm2.setName("asdasdasd");
		assertNotEquals(userForm, userForm2);
		
		userForm2.setSecondLastname("asdasda");
		assertNotEquals(userForm, userForm2);
		
		assertNotEquals(userForm, 1);
		
		
	}


}
