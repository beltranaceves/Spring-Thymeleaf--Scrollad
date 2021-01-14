package es.udc.fi.dc.fd.test.unit.model.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.dto.AdEntityDto;
import es.udc.fi.dc.fd.model.dto.SearchDto;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.ImageEntity;
import es.udc.fi.dc.fd.model.persistence.UserEntity;

@RunWith(JUnitPlatform.class)
@SpringJUnitConfig
@Transactional
@Rollback
@ContextConfiguration(locations = { "classpath:context/application-context.xml" })
@TestPropertySource({ "classpath:config/persistence-access.properties" })
public final class SearchDtoTest {

	public SearchDtoTest() {
		super();
	}

	@Test
	public void DtoTest() {
		SearchDto searchDto = new SearchDto();
		
		
		List<AdEntityDto> advertisements = new ArrayList<AdEntityDto>();
		searchDto.setAdvertisements(advertisements);
		
		List<String> cities = new ArrayList<String>();
		cities.add("A Coruña");
		searchDto.setCities(cities);
		
		Set<String> follows = new HashSet<String>();
		follows.add("viewer2");
		searchDto.setFollows(follows);
		
		List<Integer> likesList = new ArrayList<Integer>();
		likesList.add(2);
		searchDto.setLikesList(likesList);
		
		List<AdEntityDto> premiumAdvertisements = new ArrayList<AdEntityDto>();
		searchDto.setPremiumAdvertisements(premiumAdvertisements);
		
		Integer scoreCount = 1;
		searchDto.setScoreCount(scoreCount);
		
		Set<String> scoredUsers= new HashSet<String>();
		scoredUsers.add("viewer2");
		searchDto.setScoredUsers(scoredUsers);
		
		UserEntity user = new UserEntity();
		searchDto.setUser(user);
		
		assertEquals(searchDto.getAdvertisements(), advertisements);
		assertEquals(searchDto.getCities(), cities);
		assertEquals(searchDto.getFollows(), follows);
		assertEquals(searchDto.getLikesList(), likesList);
		assertEquals(searchDto.getPremiumAdvertisements(), premiumAdvertisements);
		assertEquals(searchDto.getScoreCount(), scoreCount);
		assertEquals(searchDto.getScoredUsers(), scoredUsers);
		assertEquals(searchDto.getUser(), user);
		
		
		AdEntityDto adEntityDto = new AdEntityDto();
		String stringDate = "2020-10-10T18:34:34.043";
		String description = "Esta es la descripcion";
		Integer integer = 12;
		List<ImageEntity>  images = new ArrayList<ImageEntity>();
		Boolean isOnHold = false;
		Boolean isSold = false;
		Double price = 10.0;
		String title = "Test";	
		UserEntity userA = new UserEntity();
		AdEntity adEntity = new AdEntity(title, description, LocalDateTime.now(), price, userA, isOnHold, isSold);
		
		List<AdEntity> adEntityList = new ArrayList<AdEntity>();
		adEntityList.add(adEntity);
		scoreCount = 2;
		SearchDto searchDto2 = new SearchDto(likesList, scoreCount, user, cities, scoredUsers, follows, adEntityList, adEntityList);
		
		assertNotEquals(searchDto, searchDto2);
	}


}
;