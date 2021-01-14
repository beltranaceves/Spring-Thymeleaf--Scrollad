package es.udc.fi.dc.fd.test.controller.user;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import es.udc.fi.dc.fd.controller.user.UserRateController;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.ad.AdEntityService;
import es.udc.fi.dc.fd.service.like.LikeService;
import es.udc.fi.dc.fd.service.user.UserService;

@RunWith(JUnitPlatform.class)
public class TestUserRateController {

	private MockMvc mockMvc;

	public TestUserRateController() {
		super();
	}

	@BeforeEach
	public final void initMockTest() {

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/templates/view/");
		viewResolver.setSuffix(".html");

		final UserService userService;
		final AdEntityService adEntityService;
		final LikeService likedAdService;

		UserEntity userEntity = new UserEntity("user", "user", "user", "user", "user", "user", false, 0, (double) 0, 0);
		UserEntity user1 = new UserEntity("user1", "user1", "user1", "user1", "user1", "user1", false, 1, (double) 5,
				5);

		userService = Mockito.mock(UserService.class);
		adEntityService = Mockito.mock(AdEntityService.class);
		likedAdService = Mockito.mock(LikeService.class);

		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);

		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(userEntity);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(userService.rateUser(Matchers.anyString(), Matchers.anyString(), Matchers.anyInt()))
				.thenReturn(userEntity);
		Mockito.when(userService.findByUsername("user1")).thenReturn(user1);
		mockMvc = MockMvcBuilders.standaloneSetup(new UserRateController(userService, adEntityService, likedAdService))
				.setViewResolvers(viewResolver).build();
	}

	@Test
	public void testSaveScore() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/user/score").param("rated", "user1").param("score", "5")
				.param("returnUri", "/");
		ResultActions result = mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	public void testSaveScore2() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.post("/user/score").param("rated", "user1").param("score", "5")
				.param("returnUri", "list");

		ResultActions result = mockMvc.perform(request).andExpect(status().isOk());

	}
}
