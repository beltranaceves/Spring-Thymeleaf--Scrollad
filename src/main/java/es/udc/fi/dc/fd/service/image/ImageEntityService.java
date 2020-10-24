package es.udc.fi.dc.fd.service.image;

import org.springframework.data.domain.Pageable;

import es.udc.fi.dc.fd.model.Image;
import es.udc.fi.dc.fd.model.persistence.AdEntity;
import es.udc.fi.dc.fd.model.persistence.ImageEntity;

public interface ImageEntityService {


    public Image add(final ImageEntity entity);


    public Image findById(final Integer identifier);
    
    
    public Image findByAd(final AdEntity adEntity);
    
    
    public Iterable<ImageEntity> getAllEntities();
    
    

    public Iterable<ImageEntity> getEntities (final Pageable page);
    

    public void remove(final ImageEntity imageEntity);
}
