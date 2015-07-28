package com.buswe.moudle.core.dao;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.moudle.core.entity.Operation;

public abstract interface OperationDao
  extends BaseRepository<Operation, String>
{}
