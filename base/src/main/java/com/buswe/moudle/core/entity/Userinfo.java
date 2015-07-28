package com.buswe.moudle.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="base_userinfo")
public class Userinfo
  extends IdEntity
{
  private static final long serialVersionUID = 3872638362338872894L;
  @Column(name="lastlogin_ip", length=20)
  private String lastLoginIp;
  @Column(name="lastlogin_time")
  private Date lastLoginTime;
  @Column(name="phone", length=30)
  private String phone;
  @Column(name="mobilephone", length=20)
  private String mobilePhone;
  @Column(name="address", length=50)
  private String address;
  @Column(name="qq")
  private String qqNumber;
  @Column(name="logintotal", length=8)
  private Integer logintotal;
  @Column(name="photo", length=50)
  private String photo;
  @Column(name="totaltime", length=9)
  private Integer totalOnline;
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="userbasec_id")
  private UserBasic userBasic;
  
  public String getLastLoginIp()
  {
    return this.lastLoginIp;
  }
  
  public void setLastLoginIp(String lastLoginIp)
  {
    this.lastLoginIp = lastLoginIp;
  }
  
  public Date getLastLoginTime()
  {
    return this.lastLoginTime;
  }
  
  public void setLastLoginTime(Date lastLoginTime)
  {
    this.lastLoginTime = lastLoginTime;
  }
  
  public String getPhone()
  {
    return this.phone;
  }
  
  public void setPhone(String phone)
  {
    this.phone = phone;
  }
  
  public String getMobilePhone()
  {
    return this.mobilePhone;
  }
  
  public void setMobilePhone(String mobilePhone)
  {
    this.mobilePhone = mobilePhone;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String address)
  {
    this.address = address;
  }
  
  public String getQqNumber()
  {
    return this.qqNumber;
  }
  
  public void setQqNumber(String qqNumber)
  {
    this.qqNumber = qqNumber;
  }
  
  public Integer getLogintotal()
  {
    return this.logintotal;
  }
  
  public void setLogintotal(Integer logintotal)
  {
    this.logintotal = logintotal;
  }
  
  public String getPhoto()
  {
    return this.photo;
  }
  
  public void setPhoto(String photo)
  {
    this.photo = photo;
  }
  
  public Integer getTotalOnline()
  {
    return this.totalOnline;
  }
  
  public void setTotalOnline(Integer totalOnline)
  {
    this.totalOnline = totalOnline;
  }
  
  public UserBasic getUserBasic()
  {
    return this.userBasic;
  }
  
  public void setUserBasic(UserBasic userBasic)
  {
    this.userBasic = userBasic;
  }
}
