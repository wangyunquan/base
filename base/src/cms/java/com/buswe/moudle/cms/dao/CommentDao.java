package com.buswe.moudle.cms.dao;

import java.util.List;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.moudle.cms.entity.Comment;

public abstract interface CommentDao
  extends BaseRepository<Comment, String>
{
  public abstract List<Comment> findByArticleId(String paramString);
}
