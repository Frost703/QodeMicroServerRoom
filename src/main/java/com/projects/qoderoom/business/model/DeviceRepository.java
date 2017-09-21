package com.projects.qoderoom.business.model;

import java.util.List;

public interface DeviceRepository {
    List<Device> getAll();
    List<Device> getAllWithReviewsGreaterThen(int amount);
    List<Device> getAllWithPriceLessThen(double amount);
    Device get(long id);

    void put(Device device);
    void putAll(List<Device> devices);

    Device update(Device updated);

    Device delete(Device toDelete);
    Device delete(long id);
}
