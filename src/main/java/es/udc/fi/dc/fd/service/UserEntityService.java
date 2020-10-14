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

package es.udc.fi.dc.fd.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.exceptions.IncorrectLoginException;
import es.udc.fi.dc.fd.model.exceptions.IncorrectPasswordException;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.repository.UserRepository;

/**
 * Implementation of the user service
 * 
 *
 */
@Service
@Transactional
public class UserEntityService implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserEntityService(final UserRepository repository) {
		super();

		userRepository = checkNotNull(repository, "Received a null pointer as repository");
	}

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public final User add(final UserEntity entity) {
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		return userRepository.save(entity);

	}

	@Override
	@Transactional(readOnly = true)
	public User login(String login, String password) throws IncorrectLoginException, IncorrectPasswordException {

		Optional<User> user = userRepository.findByLogin(login);

		if (!user.isPresent()) {
			throw new IncorrectLoginException("El usuario '" + login + "' no existe");
		}

		if (!passwordEncoder.matches(password, user.get().getPassword())) {
			throw new IncorrectPasswordException("La contraseña no es correcta");
		}

		return user.get();

	}

}