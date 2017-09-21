package com.projects.qoderoom.dao;

import com.projects.qoderoom.business.model.Device;
import com.projects.qoderoom.business.model.DeviceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


/**
 * Provides access to database for device objects
 */
@Transactional
@Repository("deviceRepository")
public class DeviceDAO implements DeviceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Device> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Device> cq = cb.createQuery(Device.class);
        Root<Device> rootEntry = cq.from(Device.class);
        CriteriaQuery<Device> all = cq.select(rootEntry);
        TypedQuery<Device> allQuery = entityManager.createQuery(all);

        return allQuery.getResultList();
    }

    @Override
    public List<Device> getAllWithReviewsGreaterThen(int amount) {
        return entityManager.createQuery("select d from Device d where reviews > :amount").setParameter("amount", amount).getResultList();
    }

    @Override
    public List<Device> getAllWithPriceLessThen(double amount) {
        return entityManager.createQuery("select d from Device d where price < :amount").setParameter("amount", amount).getResultList();
    }

    @Override
    public Device get(long id) {
        return entityManager.find(Device.class, id);
    }

    @Override
    public void put(Device device) {
        entityManager.persist(device);
    }

    public void putAll(List<Device> devices) {
        for(Device d : devices){
            put(d);
        }
    }

    @Override
    public Device update(Device updated) {
        entityManager.merge(updated);

        return updated;
    }

    @Override
    public Device delete(Device toDelete) {
        entityManager.remove(toDelete);

        return toDelete;
    }

    @Override
    public Device delete(long id) {
        Device toDelete = get(id);
        delete(toDelete);

        return toDelete;
    }
}
