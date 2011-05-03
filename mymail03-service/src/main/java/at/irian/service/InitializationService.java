package at.irian.service;

import at.irian.domain.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class InitializationService {
    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    private void init() {
        initProviders();
    }

    private void initProviders() {
        for (int i = 0; i < 100; i++) {
            Mail mail = MailBuilder.buildMail();
            em.persist(mail);
        }
    }

}
