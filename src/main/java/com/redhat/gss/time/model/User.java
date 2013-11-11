package com.redhat.gss.time.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Users")
public class User
{
  @Id
  @GeneratedValue
  private Long id = null;
  private String firstName = null;
  private String lastName = null;
  private String email = null;

  public User() {}
  
  public User(long id)
  {
    this.id = id;
  }

  public User(String first, String last, String email)
  {
    this.firstName = first;
    this.lastName = last;
    this.email = email;
  }

  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public String getLastName()
  {
    return this.lastName;
  }
  
  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }
  
  public String getFirstName()
  {
    return this.firstName;
  }
  
  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
}
