/*
 * Business facade for Bouncer entity
 * Author: Jordan Kent
 * Date: June 23, 2023
 */
package cst8218.kent0037.bouncer.business;

import cst8218.kent0037.bouncer.entity.Bouncer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class BouncerFacade extends AbstractFacade<Bouncer> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BouncerFacade() {
        super(Bouncer.class);
    }
    
}
