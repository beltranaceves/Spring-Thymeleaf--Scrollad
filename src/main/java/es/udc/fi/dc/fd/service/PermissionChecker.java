package es.udc.fi.dc.fd.service;

import javax.management.InstanceNotFoundException;

import es.udc.fi.dc.fd.model.User;

public interface PermissionChecker {

	public void checkUserExists(Integer userId) throws InstanceNotFoundException;

	public User checkUser(Integer userId) throws InstanceNotFoundException;

}
