package org.warehouse.service;

import java.util.Optional;

import org.warehouse.entity.Phone;
import org.warehouse.entity.PhoneModel;

public interface PhoneService extends DeviceService<Phone, PhoneModel> {

    Optional<Phone> findAvailabilityByNameAndMemoryAndCameras(String deviceName, int memory,
            int numberOfCameras, boolean availability);
}
