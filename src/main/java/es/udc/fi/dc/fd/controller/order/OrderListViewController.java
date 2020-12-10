/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2020 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package es.udc.fi.dc.fd.controller.order;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.service.order.OrderService;
import es.udc.fi.dc.fd.service.user.UserService;

@Controller
@RequestMapping("/order")
public class OrderListViewController {

	private final OrderService orderEntityService;

	private final UserService userEntityService;

	@Autowired
	public OrderListViewController(final OrderService orderService,	
			final UserService userService) {
		super();

		orderEntityService = checkNotNull(orderService, "Received a null pointer as service");

		userEntityService = checkNotNull(userService, "Received a null pointer as service");

	}

	public UserEntity getLoggedUser(final ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserEntity user = userEntityService.findByUsername(auth.getName());

		return user;
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	private final void loadViewModel(final ModelMap model) {

		model.put(OrderViewConstants.PARAM_ENTITIES, orderEntityService.getEntitiesByUser(getLoggedUser(model)));
	}

	@GetMapping(path = "/list")
	public String showOrderListByUser(final ModelMap model) {
		// Loads required data into the model
		loadViewModel(model);

		return OrderViewConstants.VIEW_ENTITY_LIST;
	}

}
