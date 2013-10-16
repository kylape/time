package com.redhat.gss.time.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.redhat.gss.time.model.Action;
import com.redhat.gss.time.model.TimeEntry;
import com.redhat.gss.time.model.User;

@Path("/")
public class TimeResource
{
  @Inject
  private EntityManager em;

  @POST
  @Path("user")
  @Produces("application/json")
  @Consumes("application/json")
  public long addUser(String first, String last, String email)
  {
    User user = new User(first, last, email);
    if(em.contains(user))
    {
      em.merge(user);
    }
    else
    {
      em.persist(user);
    }

    return user.getId();
  }

  @POST
  @Path("action")
  @Produces("application/json")
  @Consumes("application/json")
  public long addAction(String name)
  {
    Action action = new Action(name);
    if(em.contains(action))
    {
      em.merge(action);
    }
    else
    {
      em.persist(action);
    }

    return action.getId();
  }

  @POST
  @Path("entry")
  @Produces("application/json")
  @Consumes("application/json")
  public long addTimeEntry(long userId, long actionId, Date start, Date stop)
  {
    Date currentTime = new Date();

    if(userId == 0L || actionId == 0L)
    {
      throw new IllegalArgumentException("Must provide userId and actionId");
    }

    User user = em.find(User.class, userId);
    if(user == null)
    {
      throw new IllegalArgumentException("Invalid user ID");
    }

    Action action = em.find(Action.class, actionId);
    if(action == null)
    {
      throw new IllegalArgumentException("Invalid action ID");
    }

    TimeEntry entry = new TimeEntry(user, action, start, stop);
    if(start == null)
    {
      entry.setStart(currentTime);
    }

    if(em.contains(entry))
    {
      em.merge(entry);
    }
    else
    {
      if(start != null)
      {
        stopUser(userId);
      }
      em.persist(entry);
    }

    return entry.getId();
  }

  @GET
  @Consumes("application/json")
  @Produces("application/json")
  public TimeEntry getActiveUserEntry(long userId)
  {
    TypedQuery<TimeEntry> query = em.createQuery(
      "select e from TimeEntry where e.user = ?1 and e.stop is null", 
      TimeEntry.class
    );
    query.setParameter(1, userId);
    return query.getSingleResult();
  }

  @POST
  @Path("entry/{id}/stop")
  public void stopTimeEntry(@PathParam("id") long id)
  {
    TimeEntry entry = em.find(TimeEntry.class, id);
    if(entry == null)
    {
      throw new IllegalArgumentException("ID not found");
    }
    entry.setEnd(new Date());
    em.persist(entry);
  }

  @POST
  @Path("user/{id}/stop")
  public void stopUser(@PathParam("id") long id)
  {
    TimeEntry active = getActiveUserEntry(id);
    if(active != null)
    {
      active.setEnd(new Date());
      em.persist(active);
    }
  }

  @GET
  @Path("user/{id}")
  @Produces("application/json")
  public User getUser(@PathParam("id") long id)
  {
    return em.find(User.class, id);
  }

  @GET
  @Path("user/list")
  @Produces("application/json")
  public List<User> getAllUsers()
  {
    TypedQuery<User> q = em.createQuery("select u from User", User.class);
    return q.getResultList();
  }

  @GET
  @Path("action/{id}")
  @Produces("application/json")
  public Action getAction(@PathParam("id") long id)
  {
    return em.find(Action.class, id);
  }

  @GET
  @Path("user/list")
  @Produces("application/json")
  public List<Action> getAllActions()
  {
    TypedQuery<Action> q = em.createQuery("select u from Action", Action.class);
    return q.getResultList();
  }

  @GET
  @Path("entry/{id}")
  @Produces("application/json")
  public TimeEntry getTimeEntry(@PathParam("id") long id)
  {
    return em.find(TimeEntry.class, id);
  }

  @GET
  @Path("entry")
  @Produces("application/json")
  @Consumes("application/json")
  public List<TimeEntry> getEntries(
    @QueryParam("user") long userId, 
    @QueryParam("fromEpoch") long fromEpoch,
    @QueryParam("toEpoch") long toEpoch,
    @QueryParam("from") Date fromDate,
    @QueryParam("to") Date toDate,
    @QueryParam("action") String actionId)
  {
    return new ArrayList<TimeEntry>();
  }
}
