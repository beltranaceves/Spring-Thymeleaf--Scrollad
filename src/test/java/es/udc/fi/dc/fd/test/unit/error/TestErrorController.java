package es.udc.fi.dc.fd.test.unit.error;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import es.udc.fi.dc.fd.controller.error.ErrorController;

@RunWith(JUnitPlatform.class)
public class TestErrorController {

	private MockMvc mockMvc;

	public TestErrorController() {
		super();
	}

	@BeforeEach
	public final void initMockTest() {

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/templates/view/");
		viewResolver.setSuffix(".html");

		mockMvc = MockMvcBuilders.standaloneSetup(new ErrorController()).setViewResolvers(viewResolver).build();
	}

	@Test
	public void testErrorController() {
		ErrorController errorController = new ErrorController();
		assertNotNull(errorController);
	}

	@Test
	public void test404Endpoint() throws Exception {
		final ResultActions result;

		result = mockMvc.perform(MockMvcRequestBuilders.get("/404")).andExpect(status().isOk());

	}
}
