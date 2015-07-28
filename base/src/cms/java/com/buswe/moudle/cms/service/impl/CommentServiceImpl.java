package com.buswe.moudle.cms.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.base.service.BaseServiceImpl;
import com.buswe.moudle.cms.dao.CommentDao;
import com.buswe.moudle.cms.entity.Comment;
import com.buswe.moudle.cms.service.CommentService;

@Service
@Transactional("jpaTransaction")
public class CommentServiceImpl
  extends BaseServiceImpl<Comment>
  implements CommentService
{
  @Resource
  CommentDao commentDao;
  
  public BaseRepository<Comment, String> getDao()
  {
    return this.commentDao;
  }
}
