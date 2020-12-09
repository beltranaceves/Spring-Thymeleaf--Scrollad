package es.udc.fi.dc.fd.service.order;

import es.udc.fi.dc.fd.model.Order;
import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.form.OrderForm;
import es.udc.fi.dc.fd.model.persistence.OrderEntity;

public interface OrderService {
	
	public Order addOrder(final OrderEntity order);
	
	public OrderEntity findById(final Integer identifier);
	
	public Iterable<OrderEntity> getEntitiesByUser(final User user);
	
	public boolean checkForm(final OrderForm orderForm);

}
