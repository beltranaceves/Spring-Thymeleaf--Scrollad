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

package es.udc.fi.dc.fd.service.user;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.persistence.UserEntity;
import es.udc.fi.dc.fd.repository.UserEntityRepository;

/**
 * Implementation of the user service
 * 
 *
 */
@Service
@Transactional
public class UserEntityService implements UserService {

	private final UserEntityRepository userRepository;
	


	@Autowired
	public UserEntityService(final UserEntityRepository repository) {
		super();

		userRepository = checkNotNull(repository, "received a null pointer as repo");
	}

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public final User add(final UserEntity entity) {
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		return userRepository.save(entity);

	}

	@Override
	public final UserEntity findByUsername(final String username) {

		final UserEntity user;

		checkNotNull(username, "Received a null pointer as username");

		Optional<UserEntity> result = userRepository.findByUsername(username);

		if (!result.isPresent()) {

			user = new UserEntity();
			System.out.println("No se ha encontrado ningun usuario");

		} else {

			user = result.get();
		}

		return user;
	}
	
	
	public UserEntity findById(final Integer id) {
		return userRepository.findById(id).get();
	}

	@Override
	public UserEntity followAndUnfollow(final String entity,final String followed) {
		
		final UserEntity userEntity;
		
		Optional<UserEntity> result = userRepository.findByUsername(entity);
		
		if (!result.isPresent()) {
			userEntity = new UserEntity();
			System.out.println("No se ha encontrado el usuario logeado");
			
		} else {
			
			userEntity = findByUsername(entity);
			
			Set<String> followers = userEntity.getFollowed();
			if (followers.contains(followed)) {
				followers.remove(followed);
				userEntity.setFollowed(followers);
				userRepository.save(userEntity);
			} else if (!followers.contains(followed)) {
				followers.add(followed);
				userEntity.setFollowed(followers);
				userRepository.save(userEntity);
			}
		}	
		
		return userEntity;
	}

	@Override
	public UserEntity rateUser(final String entity,final String rated,final Integer score) {
		
		final UserEntity userLoged;
		final UserEntity ratedUser;
		Integer count;
		Integer sumScore;
		Double average;
		
		
		Optional<UserEntity> resultUserEntity = userRepository.findByUsername(entity);
		Optional<UserEntity> resultRatedUser = userRepository.findByUsername(rated);
		
		if (!resultUserEntity.isPresent() || !resultRatedUser.isPresent()) {
			
			userLoged = new UserEntity();
			System.out.println("No se ha encontrado el usuario logeado o el usuario a quien puntuar");
			
		} else {
			
			userLoged = findByUsername(entity);
			ratedUser = findByUsername(rated);
			
			Set<String> scoredUserList = userLoged.getScored();
			if (!scoredUserList.contains(rated)) {
				scoredUserList.add(rated);
				userLoged.setScored(scoredUserList);
				userRepository.save(userLoged);
				
				count= ratedUser.getScoreCount();
				count = count + 1;
				ratedUser.setScoreCount(count);
				userRepository.save(ratedUser);
				
				sumScore= ratedUser.getSumScore();
				sumScore= sumScore + score;
				ratedUser.setSumScore(sumScore);
				userRepository.save(ratedUser);
				
				average= ratedUser.getAverageScore();
				average= Double.valueOf(sumScore)/count;
				ratedUser.setAverageScore(average);
				userRepository.save(ratedUser);
			}
		}	
		
		return userLoged;
	}
	
	public Boolean isPremiumUser(final Integer id) {
		UserEntity user = findById(id);
		
		return user.getIsPremium();
	}
	
	public void updateIsPremiumUserByUserId(final Integer id, final Boolean value) {
		Optional<UserEntity> userEntity = userRepository.findById(id);
		if (userEntity.isPresent()) {
			userEntity.get().setIsPremium(value);;
			userRepository.save(userEntity.get());
		}
	}

}