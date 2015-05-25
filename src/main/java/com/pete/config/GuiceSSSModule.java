package com.pete.config;

import java.util.HashSet;
import java.util.Set;

import com.google.api.server.spi.guice.GuiceSystemServiceServletModule;
import com.pete.crawler.Crawler;
import com.pete.crawler.POMCrawler;
import com.pete.endpoint.Constants;
import com.pete.endpoint.Maven;

import dao.POMDAO;
import dao.POMDAOHTTP;

/**
 * Main configuration class for Guice.
 * Here we bind the interfaces to the implementation classes.
 * @author peter
 *
 */
public class GuiceSSSModule extends GuiceSystemServiceServletModule{


  @Override
  protected void configureServlets() {
    super.configureServlets();

    Set<Class<?>> serviceClasses = new HashSet<Class<?>>();
    serviceClasses.add(Maven.class);
    bind(POMDAO.class).to(POMDAOHTTP.class);
    bind(Crawler.class).to(POMCrawler.class);

    this.serveGuiceSystemServiceServlet(Constants.ENDPOINT_URL, serviceClasses);
  }
}
