package com.pete.config;

import java.util.HashSet;
import java.util.Set;

import com.google.api.server.spi.guice.GuiceSystemServiceServletModule;
import com.pete.crawler.MavenList;
import com.pete.crawler.ProjectBrowser;
import com.pete.crawler.MavenDetail;
import com.pete.crawler.ProjectList;
import com.pete.endpoint.Constants;
import com.pete.endpoint.ProjectHub;

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
    serviceClasses.add(ProjectHub.class);
    bind(POMDAO.class).to(POMDAOHTTP.class);
    bind(ProjectBrowser.class).to(MavenDetail.class);
    bind(ProjectList.class).to(MavenList.class);

    this.serveGuiceSystemServiceServlet(Constants.ENDPOINT_URL, serviceClasses);
  }
}
