package com.buswe.base.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.DocumentId;
import org.springframework.data.domain.Persistable;

@MappedSuperclass
public abstract class IdEntity
  implements Persistable<String>
{
  private static final long serialVersionUID = 4135637827653249198L;
  @Id
  @Column(unique=true, length=36, name="id")
  @GeneratedValue(generator="UIDGenerator")
  @GenericGenerator(name="UIDGenerator", strategy="uuid2")
  @DocumentId
  protected String id;
  @Version
  private Timestamp timestamp;
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public Timestamp getTimestamp()
  {
    return this.timestamp;
  }
  
  public void setTimestamp(Timestamp timestamp)
  {
    this.timestamp = timestamp;
  }
  
  public boolean isNew()
  {
    return StringUtils.isBlank(this.id);
  }
}
