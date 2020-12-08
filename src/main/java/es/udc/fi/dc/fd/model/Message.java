package es.udc.fi.dc.fd.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import es.udc.fi.dc.fd.model.persistence.UserEntity;

public interface Message extends Serializable {

	public Integer getId();

	public String getText();

	public UserEntity getSender();

	public UserEntity getReceiver();

	public LocalDateTime getDate();

	public boolean isSeen();

	public void setText(String text);

	public void setSender(UserEntity sender);

	public void setReceiver(UserEntity receiver);

	public void setDate(LocalDateTime date);

	public void setSeen(boolean seen);

}