package es.udc.fi.dc.fd.service;

import es.udc.fi.dc.fd.model.form.JwtInfo;

public interface JwtGenerator {

	String generate(JwtInfo info);

	JwtInfo getInfo(String token);

}
