package org.simplebank.repository.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.simplebank.domain.Customer;
import org.simplebank.exception.UserException;
import org.simplebank.repository.CustomerRepository;
import org.simplebank.repository.RepositorySessionFactory;

import java.util.Collection;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {
    private final SessionFactory sessionFactory;
    private static final Logger log = Logger.getLogger(CustomerRepositoryImpl.class);

    public CustomerRepositoryImpl() {
        RepositorySessionFactory hibernateH2SessionFactory = new HibernateH2SessionFactory();
        sessionFactory = hibernateH2SessionFactory.getSessionFactory();
    }

    @Override
    public Collection<Customer> getCustomers() throws UserException {
        Session session = sessionFactory.openSession();
        List<Customer> customers;
        try {
            customers = session.createQuery("FROM Customer").list();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new UserException("Problem getting all customers info");
        } finally {
            session.close();
        }
        return customers;
    }
}
