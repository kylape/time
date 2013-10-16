package com.redhat.gss.time.cdi;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Simple resource producer for the {@link EntityManager}.
 */
public class EntityManagerProducer {
    @Produces
    @Default
    @PersistenceContext(type=PersistenceContextType.TRANSACTION)
    private EntityManager em;
}
