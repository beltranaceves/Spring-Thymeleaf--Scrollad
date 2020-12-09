package es.udc.fi.dc.fd.service.order;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import es.udc.fi.dc.fd.model.Order;
import es.udc.fi.dc.fd.model.User;
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
	public Iterable<OrderEntity> getEntitiesByUser(User user) {
		return orderRepository.findByUser(user, Sort.by(Sort.Direction.DESC, "date"));
	}
	
	@Override
	public boolean checkForm(final OrderForm orderForm) {
		checkNotNull(orderForm.getAddress(), "Received a null pointer as a address");
		checkNotNull(orderForm.getCreditCard(), "Received a null pointer as credit card");
		return false;
	}

	

}
