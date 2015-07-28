package com.buswe.moudle.cms.search;

import java.io.File;

import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

public class FSDirectoryFactoryBean
  implements FactoryBean<FSDirectory>, InitializingBean, DisposableBean
{
  private FSDirectory directory;
  private Resource location;
  
  public FSDirectory getObject()
    throws Exception
  {
    return this.directory;
  }
  
  public Class<FSDirectory> getObjectType()
  {
    return FSDirectory.class;
  }
  
  public void afterPropertiesSet()
    throws Exception
  {
    if (this.location == null) {
      throw new BeanInitializationException(
        "Must specify a location property");
    }
    File locationFile = this.location.getFile();
    boolean locationExists = locationFile.exists();
    if ((locationExists) && (!locationFile.isDirectory())) {
      throw new BeanInitializationException(
        "location must be a directory");
    }
    
   //  this.directory = FSDirectory.open(new java.nio.file.Path(locationFile));
  }
  
  public void destroy()
    throws Exception
  {
    if (this.directory != null) {
      this.directory.close();
    }
  }
  
  public boolean isSingleton()
  {
    return true;
  }
  
  public void setLocation(Resource location)
  {
    this.location = location;
  }
}
