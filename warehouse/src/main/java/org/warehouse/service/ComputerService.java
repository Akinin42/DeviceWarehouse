package org.warehouse.service;

import java.util.Optional;

import org.warehouse.entity.device.Computer;
import org.warehouse.entity.devicemodel.ComputerModel;

public interface ComputerService extends DeviceService<Computer, ComputerModel> {

    Optional<Computer> findAvailabilityByNameAndCategoryAndProcessor(String deviceName, String category,
            String processor, boolean availability);
}
