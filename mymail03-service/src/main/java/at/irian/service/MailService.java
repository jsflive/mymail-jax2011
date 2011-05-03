package at.irian.service;

import at.irian.dao.MailQuery;
import at.irian.dao.MailRepository;
import at.irian.dao.MailSearchParameters;
import at.irian.dao.PagedSearchResult;
import at.irian.domain.Mail;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class MailService {
    @Inject
    private MailRepository mailRepository;

    public void fetchNewMail() {
        for (int i = 0; i < 3; i++) {
            Mail mail = MailBuilder.buildMail();
            // Was passiert mit save() hier? neue Transaktion oder selbe?
            mailRepository.save(mail);
        }
    }

    public void save(Mail customer) {
        mailRepository.save(customer);
    }

    public void delete(Mail customer) {
        mailRepository.delete(customer);
    }

    public PagedSearchResult<Mail> findAll(MailSearchParameters parameters) {
        MailQuery mailQuery = mailRepository.getMailQuery()
                .withFromLike(parameters.getFrom())
                .withSubjectLike(parameters.getSubject())
                .withPriorityIn(parameters.getPriorities())
                .setFirstResult(parameters.getFromIndex())
                .setMaxResults(parameters.getToIndex() - parameters.getFromIndex());
        return new PagedSearchResult<Mail>(mailQuery.getResultList(), mailQuery.getResultSize());
    }

    public Mail findById(long id) {
        return mailRepository.findById(id);
    }

}
