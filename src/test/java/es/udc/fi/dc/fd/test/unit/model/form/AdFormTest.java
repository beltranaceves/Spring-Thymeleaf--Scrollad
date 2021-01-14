package es.udc.fi.dc.fd.test.unit.model.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.form.AdForm;

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })
public class AdFormTest {

	public AdFormTest() {
		super();
	}

	@Test
	public void FormTest() {
		AdForm adForm = new AdForm();

		String title = "title";
		String description = "description";

		String title2 = "title2";
		String description2 = "description2";

		adForm.setTitle(title);
		adForm.setDescription(description);

		assertEquals(adForm.getTitle(), title);
		assertEquals(adForm.getDescription(), description);

		AdForm adForm2 = adForm;
		assertEquals(adForm, adForm2);

		assertEquals(adForm.toString(), adForm2.toString());
		assertEquals(adForm.hashCode(), adForm2.hashCode());
		assertEquals(adForm, adForm);
		assertEquals(adForm, adForm2);
		assertTrue(adForm.equals(adForm2));

		AdForm adForm3 = new AdForm();
		adForm3.setTitle(title2);
		adForm3.setDescription(description2);

		assertFalse(adForm.equals(adForm3));
		assertFalse(adForm.equals(1));
	}

}
