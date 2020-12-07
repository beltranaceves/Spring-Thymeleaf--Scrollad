package es.udc.fi.dc.fd.service.order;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import es.udc.fi.dc.fd.model.Order;
import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.form.AdForm;
import es.udc.fi.dc.fd.model.form.OrderForm;
import es.udc.fi.dc.fd.model.persistence.OrderEntity;
import es.udc.fi.dc.fd.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{
	
	private final OrderRepository orderRepository;
	
	@Autowired
	public OrderServiceImpl(final OrderRepository repository) {
		super();
		orderRepository = checkNotNull(repository, "Received a null pointer as repository");
	}
	
	@Override
	public Order addOrder(final OrderEntity order) {
		
		return orderRepository.save(order);
	}

	@Override
	public OrderEntity findById(Integer identifier) {
		final OrderEntity entity;
		checkNotNull(identifier, "Received a null pointer as identifier");
		Optional<OrderEntity> imageEntity = orderRepository.findById(identifier);
		if (imageEntity.isPresent()) {
			entity = imageEntity.get();
		} else {
			entity = new OrderEntity();
		}

		return entity;
	}

	@Override
	public void deleteById(Integer identifier) {
		orderRepository.deleteById(identifier);		
	}

	@Override
	public List<OrderEntity> getAllEntities() {
		Iterable<OrderEntity> orderEntities = orderRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
		List<OrderEntity> orderEntitiesList = new ArrayList<OrderEntity>();
		orderEntities.forEach((orderEntity) -> 
			orderEntitiesList.add(orderEntity)
		);
		return orderEntitiesList;
	}

	@Override
	public List<OrderEntity> getEntities(Pageable page) {
		Iterable<OrderEntity> orderEntities = orderRepository.findAll(page);
		List<OrderEntity> orderEntitiesList = new ArrayList<OrderEntity>();
		orderEntities.forEach((orderEntity) -> 
			orderEntitiesList.add(orderEntity)
		);
		return orderEntitiesList;
	}

	@Override
	public Iterable<OrderEntity> getEntitiesByUser(User user) {
		return orderRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));
	}

	@Override
	public void remove(final OrderEntity order) {
		orderRepository.delete(order);
	}
	
	@Override
	public boolean checkForm(final OrderForm orderForm) {
		checkNotNull(orderForm.getAddress(), "Received a null pointer as a address");
		checkNotNull(orderForm.getCreditCard(), "Received a null pointer as credit card");
		return false;
	}

	

}
