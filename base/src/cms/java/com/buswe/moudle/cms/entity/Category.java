package com.buswe.moudle.cms.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.buswe.base.domain.IdEntity;

@Entity
@Table(name="cms_category")
public class Category
  extends IdEntity
{
  private static final long serialVersionUID = 1L;
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="site_id")
  @NotFound(action=NotFoundAction.IGNORE)
  private Site site;
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="parent_id")
  @NotFound(action=NotFoundAction.IGNORE)
  private Category parent;
  private String module;
  private String name;
  private String displayName;
  private String image;
  private String href;
  private String target;
  private String desciption;
  private String keywords;
  private Integer sort;
  private String inMenu;
  private String inList;
  private String showModes;
  private String allowComment;
  private String delFlag = "0";
  private String templates;
  private Integer catSize = Integer.valueOf(20);
  @OneToMany(cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.REMOVE}, fetch=FetchType.LAZY, mappedBy="parent")
  @Where(clause="delFlag=0")
  @OrderBy("sort")
  @NotFound(action=NotFoundAction.IGNORE)
  private List<Category> childList = new ArrayList();
  
  public Site getSite()
  {
    return this.site;
  }
  
  public void setSite(Site site)
  {
    this.site = site;
  }
  
  public Integer getCatSize()
  {
    return this.catSize;
  }
  
  public void setCatSize(Integer catSize)
  {
    this.catSize = catSize;
  }
  
  public String getDisplayName()
  {
    return this.displayName;
  }
  
  public void setDisplayName(String displayName)
  {
    this.displayName = displayName;
  }
  
  public String getTemplates()
  {
    return this.templates;
  }
  
  public void setTemplates(String templates)
  {
    this.templates = templates;
  }
  
  public Category getParent()
  {
    return this.parent;
  }
  
  public void setParent(Category parent)
  {
    this.parent = parent;
  }
  
  public String getModule()
  {
    return this.module;
  }
  
  public void setModule(String module)
  {
    this.module = module;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getImage()
  {
    return this.image;
  }
  
  public void setImage(String image)
  {
    this.image = image;
  }
  
  public String getHref()
  {
    return this.href;
  }
  
  public void setHref(String href)
  {
    this.href = href;
  }
  
  public String getTarget()
  {
    return this.target;
  }
  
  public void setTarget(String target)
  {
    this.target = target;
  }
  
  public String getDesciption()
  {
    return this.desciption;
  }
  
  public void setDesciption(String desciption)
  {
    this.desciption = desciption;
  }
  
  public String getKeywords()
  {
    return this.keywords;
  }
  
  public void setKeywords(String keywords)
  {
    this.keywords = keywords;
  }
  
  public Integer getSort()
  {
    return this.sort;
  }
  
  public void setSort(Integer sort)
  {
    this.sort = sort;
  }
  
  public String getInMenu()
  {
    return this.inMenu;
  }
  
  public void setInMenu(String inMenu)
  {
    this.inMenu = inMenu;
  }
  
  public String getInList()
  {
    return this.inList;
  }
  
  public void setInList(String inList)
  {
    this.inList = inList;
  }
  
  public String getShowModes()
  {
    return this.showModes;
  }
  
  public void setShowModes(String showModes)
  {
    this.showModes = showModes;
  }
  
  public String getAllowComment()
  {
    return this.allowComment;
  }
  
  public void setAllowComment(String allowComment)
  {
    this.allowComment = allowComment;
  }
  
  public String getDelFlag()
  {
    return this.delFlag;
  }
  
  public void setDelFlag(String delFlag)
  {
    this.delFlag = delFlag;
  }
  
  public List<Category> getChildList()
  {
    return this.childList;
  }
  
  public void setChildList(List<Category> childList)
  {
    this.childList = childList;
  }
  
  @Transient
  public static boolean isRoot(Long id)
  {
    return (id != null) && (id.equals(Long.valueOf(1L)));
  }
}
