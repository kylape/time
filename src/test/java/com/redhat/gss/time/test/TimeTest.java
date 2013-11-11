package com.redhat.gss.time.test;

import java.net.URISyntaxException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;

import com.redhat.gss.time.resource.TimeResource;

@RunWith(Arquillian.class)
public class TimeTest
{
  @Deployment(testable = false)
  public static WebArchive createDeployment()
  {
    System.out.println("Creating deployment...");
    return ShrinkWrap.create(WebArchive.class, "time.war")
      .addPackage("com/redhat/gss/time/model")
      .addPackage("com/redhat/gss/time/resource")
      .addPackage("com/redhat/gss/time/cdi")
      .addAsWebInfResource("WEB-INF/beans.xml")
      .addAsWebInfResource("WEB-INF/h2-ds.xml")
      .addAsResource("META-INF/persistence.xml")
      .setWebXML("WEB-INF/web.xml");
  }

  @Test
  public void test() throws Exception
  {
    System.out.println("BOOM TEST");
  }
}
