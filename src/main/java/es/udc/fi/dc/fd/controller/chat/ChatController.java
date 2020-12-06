package es.udc.fi.dc.fd.controller.chat;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.udc.fi.dc.fd.model.form.MessageForm;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.chat.ChatService;
import es.udc.fi.dc.fd.service.user.UserService;

@Controller
@RequestMapping("/chat")
public class ChatController {

	private final ChatService chatService;
	private final UserService userService;

	@Autowired
	public ChatController(final ChatService service, final UserService service2) {
		super();
		chatService = checkNotNull(service, "Received a null pointer as chat service");
		userService = checkNotNull(service2, "Received a null pointer as user service");
	}

	@ModelAttribute(ChatViewConstants.BEAN_FORM)
	public MessageForm getMessageForm() {
		return new MessageForm();
	}

	@GetMapping
	public String showMessagesList(final ModelMap model,
			@RequestParam(value = "vendor", required = true) String username) {

		UserEntity vendor = userService.findByUsername(username);
		loadViewModelByVendor(model, vendor);

		return ChatViewConstants.VIEW_MESSAGES_LIST;
	}

	@PostMapping
	public String saveEntity(final ModelMap model,
			@ModelAttribute(ChatViewConstants.BEAN_FORM) @Valid final MessageForm form,
			final BindingResult bindingResult, final HttpServletResponse response) {
		final String path;
		final UserEntity vendor;

		if (bindingResult.hasErrors()) {
			// Invalid form data

			path = ChatViewConstants.VIEW_MESSAGES_LIST;

			// Marks the response as a bad request
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {

			vendor = userService.findByUsername(form.getVendor());
			chatService.send(form.getText(), getLoggedUser(), vendor);
			loadViewModelByVendor(model, vendor);
			path = ChatViewConstants.VIEW_MESSAGES_LIST;
		}

		return path;
	}

	private UserEntity getLoggedUser() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserEntity user = userService.findByUsername(auth.getName());
		return user;
	}

	private final void loadViewModelByVendor(final ModelMap model, final UserEntity user2) {

		UserEntity user1 = getLoggedUser();
		model.put(ChatViewConstants.MESSAGES, chatService.getAllMessagesBetween(user1, user2));
		model.put(ChatViewConstants.VENDOR, user2.getUsername());
	}

}
