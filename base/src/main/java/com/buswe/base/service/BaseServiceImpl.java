package com.buswe.base.service;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.base.dao.PropertyFilter;
import com.buswe.base.dao.QueryHelper;
import com.buswe.base.dao.springdata.BaseRepository;

@Transactional("jpaTransaction")
public abstract class BaseServiceImpl<T>
  implements BaseService<T>
{
  protected Logger logger = LoggerFactory.getLogger(getClass());
  protected ApplicationEventPublisher applicationEventPublisher;
  
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
  {
    this.applicationEventPublisher = applicationEventPublisher;
  }
  
  public abstract BaseRepository<T, String> getDao();
  
  public T save(T entity)
  {
    return getDao().save(entity);
  }
  
  public T get(String id)
  {
    return getDao().findOne(id);
  }
  
  public List<T> findAll()
  {
    return getDao().findAll();
  }
  
  public void delete(String id)
  {
    getDao().delete(id);
  }
  
  public List<T> findList(Collection<PropertyFilter> filters)
  {
    Specification specification = QueryHelper.bySearchFilter(filters);
    return getDao().findAll(specification);
  }
  
  public Page<T> findPage(Pageable page, Collection<PropertyFilter> filters)
  {
    Specification<T> specification = QueryHelper.bySearchFilter(filters);
    return getDao().findAll(specification, page);
  }
  
  public Page<T> findPage(Pageable page, Collection<PropertyFilter> filters, String... fetchProperties)
  {
    Specification specification = QueryHelper.bySearchFilter(filters);
    return getDao().findAll(specification, page);
  }
}
