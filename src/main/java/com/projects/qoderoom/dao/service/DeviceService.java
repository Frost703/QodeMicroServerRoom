package com.projects.qoderoom.dao.service;

import com.projects.qoderoom.business.model.Device;
import com.projects.qoderoom.business.model.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DeviceService {

    @Autowired
    private DeviceRepository dao;

    public void put(Device device){
        dao.put(device);
    }

    public Device get(long id){
        return dao.get(id);
    }

    public Device delete(Device device){
        return dao.delete(device);
    }

    public Device update(Device device){
        return dao.update(device);
    }
}
