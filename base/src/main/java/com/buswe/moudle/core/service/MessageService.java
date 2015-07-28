package com.buswe.moudle.core.service;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;

import com.buswe.moudle.core.entity.Messages;

public abstract interface MessageService
{
  public abstract Page<Messages> getSendMessage(Specifications paramSpecifications, Pageable paramPageable);
  
  public abstract Page<Messages> recevieMessage(Specifications paramSpecifications, Pageable paramPageable);
  
  public abstract void sendMessage(Messages paramMessages);
  
  public abstract void saveMessage(Messages paramMessages);
  
  public abstract Integer queryUnreadMessage();
  
  public abstract Messages readMessage(String paramString);
  
  public abstract void deleteRecevieMessage(String paramString);
  
  public abstract void deleteSendMessage(String paramString);
  
  public abstract void deleteAttachements(Collection paramCollection);
}
