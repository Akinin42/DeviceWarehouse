package org.warehouse.service;

import java.util.Optional;

import org.warehouse.entity.device.Phone;
import org.warehouse.entity.devicemodel.PhoneModel;

public interface PhoneService extends DeviceService<Phone, PhoneModel> {

    Optional<Phone> findAvailabilityByNameAndMemoryAndCameras(String deviceName, int memory,
            int numberOfCameras, boolean availability);
}
