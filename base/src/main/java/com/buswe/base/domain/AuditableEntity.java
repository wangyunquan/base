package com.buswe.base.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;
import org.springframework.data.domain.Auditable;

import com.buswe.moudle.core.entity.UserBasic;

@MappedSuperclass
public abstract class AuditableEntity
  extends IdEntity
  implements Auditable<UserBasic, String>
{
  private static final long serialVersionUID = 5718183941573890588L;
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="creat_user")
  private UserBasic createdBy;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="creat_date")
  private Date createdDate;
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="last_modify_user")
  private UserBasic lastModifiedBy;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="last_modify_date")
  private Date lastModifiedDate;
  
  public UserBasic getCreatedBy()
  {
    return this.createdBy;
  }
  
  public void setCreatedBy(UserBasic createdBy)
  {
    this.createdBy = createdBy;
  }
  
  public DateTime getCreatedDate()
  {
    return this.createdDate == null ? null : new DateTime(this.createdDate);
  }
  
  public void setCreatedDate(DateTime createdDate)
  {
    this.createdDate = (createdDate == null ? null : createdDate.toDate());
  }
  
  public UserBasic getLastModifiedBy()
  {
    return this.lastModifiedBy;
  }
  
  public void setLastModifiedBy(UserBasic lastModifiedBy)
  {
    this.lastModifiedBy = lastModifiedBy;
  }
  
  public DateTime getLastModifiedDate()
  {
    return this.lastModifiedDate == null ? null : new DateTime(this.lastModifiedDate);
  }
  
  public void setLastModifiedDate(DateTime lastModifiedDate)
  {
    this.lastModifiedDate = (lastModifiedDate == null ? null : lastModifiedDate.toDate());
  }
}
