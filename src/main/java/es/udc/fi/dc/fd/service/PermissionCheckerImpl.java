package es.udc.fi.dc.fd.service;

import java.util.Optional;

import javax.management.InstanceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class PermissionCheckerImpl implements PermissionChecker {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void checkUserExists(Integer userId) throws InstanceNotFoundException {

		if (!userRepository.existsById(userId)) {
			throw new InstanceNotFoundException("project.entities.user");
		}

	}

	@Override
	public User checkUser(Integer userId) throws InstanceNotFoundException {

		Optional<UserEntity> user = userRepository.findById(userId);

		if (!user.isPresent()) {
			throw new InstanceNotFoundException("project.entities.user");
		}

		return user.get();

	}

}
