package es.udc.fi.dc.fd.service.ad;

import java.util.List;

import org.springframework.data.domain.Pageable;

import es.udc.fi.dc.fd.model.Ad;
import es.udc.fi.dc.fd.model.User;
import es.udc.fi.dc.fd.model.form.AdForm;
import es.udc.fi.dc.fd.model.persistence.AdEntity;

public interface AdEntityService {


    public Ad add(final AdEntity entity);


    public AdEntity findById(final Integer identifier);
    
    
    public void deleteById(final Integer identifier);

    
    public List<AdEntity> getAllEntities();


    public List<AdEntity> getEntities (final Pageable page);
    
    
    public Iterable<AdEntity> getEntitiesByUser(final User user);
    

    public void remove(final AdEntity advertisement);
    
    
    public boolean checkForm(final AdForm adForm); 
    
    
    public void updateIsOnHoldById(final Integer adEntityId);

}
