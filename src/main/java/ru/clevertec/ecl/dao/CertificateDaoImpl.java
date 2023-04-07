package ru.clevertec.ecl.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entities.Certificate;
import ru.clevertec.ecl.exceptions.CertificateNotFoundException;
import ru.clevertec.ecl.services.TagService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CertificateDaoImpl implements CertificateDao {

    private final SessionFactory sessionFactory;

    @Override
    public List<Certificate> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Certificate> list = session.createQuery("FROM Certificate").getResultList();
            list.forEach(c -> Hibernate.initialize(c.getTags()));
            session.getTransaction().commit();
            return list;
        }
    }

    @Override
    public Optional<Certificate> findById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Optional<Certificate> certificate = session.byId(Certificate.class).loadOptional(id);
            Hibernate.initialize(certificate.get().getTags());
            session.getTransaction().commit();
            return certificate;
        } catch (NoSuchElementException e) {
            throw new CertificateNotFoundException(id);
        }
    }

    @Override
    public void add(Certificate certificate) {
        certificate.setCreateDate(LocalDateTime.now());
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            certificate.getTags().forEach(session::save);
            session.save(certificate);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (sessionFactory.getCurrentSession().getTransaction() != null) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }
        }
    }

    @Override
    public void update(long id, Certificate updatedCertificate) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Certificate certificate = session.get(Certificate.class, id);
            certificate.setName(updatedCertificate.getName());
            certificate.setDescription(updatedCertificate.getDescription());
            certificate.setPrice(updatedCertificate.getPrice());
            certificate.setDuration(updatedCertificate.getDuration());
            certificate.setLastUpdateDate(LocalDateTime.now());
            updatedCertificate.getTags().forEach(session::saveOrUpdate);
            certificate.setTags(updatedCertificate.getTags());
            session.getTransaction().commit();
        } catch (Exception e) {
            if (sessionFactory.getCurrentSession().getTransaction() != null) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }
        }
    }

    @Override
    public void deleteById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(session.get(Certificate.class, id));
            session.getTransaction().commit();
        } catch (Exception e) {
            if (sessionFactory.getCurrentSession().getTransaction() != null) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }
        }
    }

    public List<Certificate> findByFilter(String name, String description, double minPrice, double maxPrice) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Certificate> query = session.createQuery("FROM Certificate WHERE name = :name or description like :description or (price>=:minPrice and price<=:maxPrice)");
            query.setParameter("name", name);
            query.setParameter("description", "%" + description + "%");
            query.setParameter("minPrice", minPrice);
            query.setParameter("maxPrice", maxPrice);
            session.getTransaction().commit();
            return query.getResultList();
        }
    }
}