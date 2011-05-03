package at.irian.dao;

import at.irian.domain.Mail;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class MailRepository {

    @PersistenceContext(unitName = "mymail")
    private EntityManager em;

    public MailQuery getMailQuery() {
        return new MailQuery(em);
    }

    public void save(Mail mail) {
        if (mail.isTransient()) {
            em.persist(mail);
        } else {
            em.merge(mail);
        }
    }

    public void delete(Mail mail) {
        mail = em.merge(mail);
        em.remove(mail);
    }

    public Mail findById(long id) {
        return em.find(Mail.class, id);
    }

}
