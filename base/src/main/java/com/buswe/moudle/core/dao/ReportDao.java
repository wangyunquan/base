package com.buswe.moudle.core.dao;

import com.buswe.base.dao.springdata.BaseRepository;
import com.buswe.moudle.core.entity.Report;

public abstract interface ReportDao
  extends BaseRepository<Report, String>
{}
