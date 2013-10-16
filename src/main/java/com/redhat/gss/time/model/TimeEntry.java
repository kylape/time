package com.redhat.gss.time.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

@Entity
@Table(name="TimeEntries")
public class TimeEntry
{
  @Id
  @GeneratedValue
  private Long id = null;

  @ManyToOne
  private User user = null;

  @ManyToOne
  private Action action = null;

  private Date start = null;
  private Date end = null;

  public TimeEntry(User user, Action action, Date start, Date stop)
  {
    this.user = user;
    this.action = action;
    this.start = start;
    this.end = end;
  }
  
  public Date getEnd()
  {
    return this.end;
  }
  
  public void setEnd(Date end)
  {
    this.end = end;
  }
  
  public Date getStart()
  {
    return this.start;
  }
  
  public void setStart(Date start)
  {
    this.start = start;
  }
  
  public Action getAction()
  {
    return this.action;
  }
  
  public void setAction(Action action)
  {
    this.action = action;
  }
  
  public User getUser()
  {
    return this.user;
  }
  
  public void setUser(User user)
  {
    this.user = user;
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
