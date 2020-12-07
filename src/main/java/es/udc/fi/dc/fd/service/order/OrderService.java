package es.udc.fi.dc.fd.service.order;

import java.util.List;

import org.springframework.data.domain.Pageable;

import es.udc.fi.dc.fd.model.Order;
import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.form.OrderForm;
import es.udc.fi.dc.fd.model.persistence.OrderEntity;

public interface OrderService {
	
	public Order addOrder(final OrderEntity order);
	
	public OrderEntity findById(final Integer identifier);
	
	public void deleteById(final Integer identifier);
	
	public List<OrderEntity> getAllEntities();

	public List<OrderEntity> getEntities(final Pageable page);

	public Iterable<OrderEntity> getEntitiesByUser(final User user);

	public void remove(final OrderEntity order);
	
	public boolean checkForm(final OrderForm orderForm);

}
