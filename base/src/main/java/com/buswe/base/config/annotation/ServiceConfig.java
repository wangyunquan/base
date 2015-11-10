package com.buswe.base.config.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages={"com.buswe"}, excludeFilters={@org.springframework.context.annotation.ComponentScan.Filter({org.springframework.stereotype.Controller.class})})
@EnableTransactionManagement
@EnableScheduling
@EnableAspectJAutoProxy
@EnableAsync
public class ServiceConfig {}
