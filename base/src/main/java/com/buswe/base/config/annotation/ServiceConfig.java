package com.buswe.base.config.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages={"com.buswe.**.service**"}, excludeFilters={@org.springframework.context.annotation.ComponentScan.Filter({org.springframework.stereotype.Controller.class})})
@EnableTransactionManagement
@EnableScheduling
@EnableAspectJAutoProxy
public class ServiceConfig {}
