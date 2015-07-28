package com.buswe.moudle.core.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.base.constants.ClassType;
import com.buswe.base.dao.MatchType;
import com.buswe.base.dao.PropertyFilter;
import com.buswe.base.dao.QueryHelper;
import com.buswe.base.service.ServiceException;
import com.buswe.moudle.core.dao.CodeTypeDao;
import com.buswe.moudle.core.dao.CodeValueDao;
import com.buswe.moudle.core.entity.CodeType;
import com.buswe.moudle.core.entity.CodeValue;
import com.buswe.moudle.core.service.AppCodeService;

@Service("appCodeService")
@Transactional("jpaTransaction")
public class AppCodeServiceImpl
  implements AppCodeService
{
  @Resource
  private CodeValueDao codeValueDao;
  @Resource
  private CodeTypeDao codeTypeDao;
  @Resource
  ConversionService conversionService;
  
  public Set<CodeValue> getAllCodeValue(String type)
  {
    Specification<CodeType> spec = QueryHelper.filter("type", MatchType.EQ, type);
    CodeType codeType = (CodeType)this.codeTypeDao.findOne(spec);
    if (codeType == null) {
      throw new ServiceException("此码表不存在，请检查码表类型是否有此值：" + type);
    }
    String valueType = codeType.getValueType();
    Set<CodeValue> codeValues = codeType.getCodeValues();
    for (CodeValue value : codeValues) {
      if (value.getEnable().booleanValue())
      {
        String valueString = value.getStringValue();
        if (valueType.contains("string"))
        {
          value.setValue(valueString);
        }
        else
        {
          Class clazz = ClassType.valueOf(valueType).getValue();
          value.setValue(this.conversionService.convert(valueString, clazz));
        }
      }
    }
    return codeValues;
  }
  
  public CodeValue saveCodeValue(CodeValue entity)
  {
    CodeType type = (CodeType)this.codeTypeDao.findOne(entity.getCodeType().getId());
    entity.setCodeType(type);
    entity = (CodeValue)this.codeValueDao.save(entity);
    return entity;
  }
  
  public void deleteCodeValue(String id)
  {
    this.codeValueDao.delete(id);
  }
  
  public CodeValue getCodeValue(String id)
  {
    return (CodeValue)this.codeValueDao.findOne(id);
  }
  
  public List<CodeValue> getCodeValues(String typeId)
  {
    return this.codeValueDao.findByCodeType(typeId);
  }
  
  public CodeType getCodeType(String typeId)
  {
    return (CodeType)this.codeTypeDao.findOne(typeId);
  }
  
  public CodeType saveCodeType(CodeType entity)
  {
    return (CodeType)this.codeTypeDao.save(entity);
  }
  
  public void deleteCodeType(String typeId)
  {
    this.codeTypeDao.delete(typeId);
  }
  
  public Page<CodeType> findPage(Pageable page, Collection<PropertyFilter> filters)
  {
    Specification<CodeType> specification = QueryHelper.bySearchFilter(filters);
    return this.codeTypeDao.findAll(specification, page);
  }
}
