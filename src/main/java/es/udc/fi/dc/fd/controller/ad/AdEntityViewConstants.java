package es.udc.fi.dc.fd.controller.ad;


public final class AdEntityViewConstants {

    /**
     * Form bean parameter name.
     */
    public static final String BEAN_FORM        = "form";

    /**
     * Entities parameter name.
     */
    public static final String PARAM_ENTITIES   = "advertisements";
    
    /**
     * Entity parameter name.
     */
    public static final String PARAM_ENTITY   = "advertisement";

    /**
     * Name for the entity form.
     */
    public static final String VIEW_ENTITY_FORM = "advertisement/form";

    /**
     * Name for the entities view using AJAX.
     */
    public static final String VIEW_ENTITY_LIST_AJAX = "advertisement/listAjax";

    /**
     * Name for the entities view.
     */
    public static final String VIEW_ENTITY_LIST = "advertisement/list";
    
    /**
     * Name for the entities view by user.
     */
    public static final String VIEW_ENTITY_LIST_BY_USER = "advertisement/listByUser";
    
    /**
     * Name for the ad upload success.
     */
    public static final String VIEW_AD_SUCCESS = "advertisement/success";
    
    /**
     * Name for the ad deletion success.
     */
    public static final String DELETE_AD_SUCCESS = "advertisement/deleteSuccess";

    /**
     * Name for the exception view.
     */
    public static final String VIEW_EXCEPTION   = "exception";
    
    /**
     * Name for the isOnHold value change success view.
     */
    public static final String ISONHOLD_CHANGE_SUCCESS= "advertisement/isOnHoldSuccess";
    /**
     * Private constructor to avoid initialization.
     */
    private AdEntityViewConstants() {
        super();
    }

}
