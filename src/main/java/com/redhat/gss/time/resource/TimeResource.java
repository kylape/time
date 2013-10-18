package com.redhat.gss.time.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.FormParam;

import com.redhat.gss.time.model.Action;
import com.redhat.gss.time.model.TimeEntry;
import com.redhat.gss.time.model.User;

import org.jboss.logging.Logger;

@Path("/")
@Stateless
public class TimeResource
{
  private static Logger log = Logger.getLogger(TimeResource.class);

  @Inject
  private EntityManager em;

  @POST
  @Path("user")
  @Produces("application/json")
  @Consumes("application/json")
  public long addUser(User user)
  {
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
  public long addAction(Action action)
  {
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
  public long addTimeEntry(TimeEntry entry)
  {
    Date currentTime = new Date();

    if(entry == null)
      throw new IllegalArgumentException("Time entry data not formatted correctly");

    if(em.contains(entry))
      throw new IllegalArgumentException("Time entry already added");

    if(entry.getUser() == null || entry.getUser().getId() == 0L)
      throw new IllegalArgumentException("Invalid user ID");

    if(entry.getAction() == null || entry.getAction().getId() == 0L)
      throw new IllegalArgumentException("Invalid action ID");

    if(entry.getStart() == null)
      entry.setStart(currentTime);

    //TODO: Think about adding back-dated entries

    //Automatically stop an active entry if it appears the new entry
    //starts after the active one
    TimeEntry activeEntry = getActiveUserEntry(entry.getUser());
    if(activeEntry != null && 
       activeEntry.getStart().getTime() < entry.getStart().getTime())
    {
      activeEntry.setEnd(currentTime);
      em.persist(activeEntry);
    }

    em.persist(entry);

    return entry.getId();
  }

  @GET
  @Consumes("application/json")
  @Produces("application/json")
  public TimeEntry getActiveUserEntry(User user)
  {
    TypedQuery<TimeEntry> query = em.createQuery(
      "from TimeEntry e where e.user = ?1 and e.end is null", 
      TimeEntry.class
    );
    query.setParameter(1, user);
    List<TimeEntry> list = query.getResultList();

    if(list.size() > 1)
      throw new IllegalStateException(
        "Invalid number of active entries: " + list.size());

    if(list.size() == 0)
      return null;
    else
      return list.get(0);
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
  @Consumes("application/json")
  public void stopUser(User user)
  {
    TimeEntry active = getActiveUserEntry(user);
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
