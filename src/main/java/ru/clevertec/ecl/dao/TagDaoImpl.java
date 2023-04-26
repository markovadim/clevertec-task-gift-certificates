package ru.clevertec.ecl.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.entities.Tag;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagDaoImpl implements TagDao {

    private final SessionFactory sessionFactory;

    @Override
    public List<Tag> findAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Tag> list = session.createQuery("FROM Tag").getResultList();
            session.getTransaction().commit();
            return list;
        }
    }

    @Override
    public Optional<Tag> findById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Optional<Tag> tag = session.byId(Tag.class).loadOptional(id);
            session.getTransaction().commit();
            return tag;
        }
    }

    @Override
    public void add(Tag tag) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(tag);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (sessionFactory.getCurrentSession().getTransaction() != null) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }
        }
    }

    @Transactional
    @Override
    public void update(long id, Tag updatedTag) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Tag tag = session.get(Tag.class, id);
            tag.setName(updatedTag.getName());
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
            session.remove(session.get(Tag.class, id));
            session.getTransaction().commit();
        } catch (Exception e) {
            if (sessionFactory.getCurrentSession().getTransaction() != null) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }
        }
    }
}
