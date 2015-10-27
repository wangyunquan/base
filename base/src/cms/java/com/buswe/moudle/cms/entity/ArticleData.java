//package com.buswe.moudle.cms.entity;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.Lob;
//import javax.persistence.MapsId;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//import org.hibernate.search.annotations.Analyze;
//import org.hibernate.search.annotations.Analyzer;
//import org.hibernate.search.annotations.ContainedIn;
//import org.hibernate.search.annotations.Field;
//import org.hibernate.search.annotations.Index;
//import org.hibernate.search.annotations.Store;
//import org.wltea.analyzer.lucene.IKAnalyzer;
//
//@Entity
//@Table(name="cms_articledata")
//public class ArticleData
//{
//  @Id
//  private String id;
//  @MapsId
//  @OneToOne
//  @JoinColumn(name="id")
//  private Article article;
//  @Lob
//  @ContainedIn
//  @Field(index=Index.YES, store=Store.YES,analyzer = @Analyzer(impl = IKAnalyzer.class))
//  private String lobContent;
//  
//  public String getId()
//  {
//    return this.id;
//  }
//  
//  public void setId(String id)
//  {
//    this.id = id;
//  }
//  
//  public Article getArticle()
//  {
//    return this.article;
//  }
//  
//  public void setArticle(Article article)
//  {
//    this.article = article;
//  }
//  
//  public String getLobContent()
//  {
//    return this.lobContent;
//  }
//  
//  public void setLobContent(String lobContent)
//  {
//    this.lobContent = lobContent;
//  }
//}
