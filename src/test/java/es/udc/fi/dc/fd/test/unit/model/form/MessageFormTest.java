package es.udc.fi.dc.fd.test.unit.model.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.form.MessageForm;

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })
public class MessageFormTest {
	
	public MessageFormTest() {
		super();
	}
	
	@Test
	public void FormTest() {
		
		MessageForm msgForm = new MessageForm();
		
		String text = "text";
		msgForm.setText(text);
		
		String vendor = "vendor";
		msgForm.setVendor(vendor);
		
		assertEquals(msgForm.getText(), text);
		assertEquals(msgForm.getVendor(), vendor);
		
		MessageForm msgForm2 = msgForm;
		assertEquals(msgForm, msgForm2);
		
		
		msgForm2 = new MessageForm();
		
		text = "text";
		msgForm2.setText(text);
		
		vendor = "vendor";
		msgForm2.setVendor(vendor);
		
		assertEquals(msgForm.toString(), msgForm2.toString());
		assertEquals(msgForm.hashCode(), msgForm2.hashCode());
		assertEquals(msgForm2, msgForm2);
		assertEquals(msgForm, msgForm2);

		msgForm2.setText("dasd");
		assertNotEquals(msgForm, msgForm2);
		
		msgForm2.setVendor("asdasd");
		assertNotEquals(msgForm, msgForm2);
		
		assertNotEquals(msgForm, 1);
		
		
	}

}
