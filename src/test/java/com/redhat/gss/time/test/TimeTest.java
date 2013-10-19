package com.redhat.gss.time.test;

import java.net.URISyntaxException;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;

import org.junit.Before;
import org.junit.Test;

import com.redhat.gss.time.resource.TimeResource;

public class TimeTest
{
  Dispatcher dispatcher;

  @Before
  public void setup() throws Exception
  {
    dispatcher = MockDispatcherFactory.createDispatcher();
    POJOResourceFactory resourceFactory = new POJOResourceFactory(TimeResource.class);
    dispatcher.getRegistry().addResourceFactory(resourceFactory);
  }

  @Test
  public void test() throws Exception
  {
    System.out.println(makeRequest("/user"));
  }

  private int makeRequest(String uri) throws URISyntaxException 
  {
    MockHttpRequest request = MockHttpRequest.get(uri);
    MockHttpResponse response = new MockHttpResponse();
    dispatcher.invoke(request, response);
    return response.getStatus();
  }
}
