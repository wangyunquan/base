package com.buswe.base.dao.springdata;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public abstract interface BaseRepository<T, ID extends Serializable>
  extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>
{
  public abstract EntityManager getEntityManager();
  
  public abstract Class<T> getDomainClass();
  
  public abstract List<T> findList(Collection<String> paramCollection);
}
