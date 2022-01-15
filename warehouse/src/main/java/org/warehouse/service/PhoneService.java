package org.warehouse.service;

import java.util.List;

import org.warehouse.entity.device.Phone;
import org.warehouse.entity.devicemodel.PhoneModel;

public interface PhoneService extends DeviceService<Phone, PhoneModel> {

    List<Phone> findAllByMemory(int memory);
    
    List<Phone> findAllByCameras(int numberOfCameras);
}
