package org.warehouse.service;

import java.util.Optional;

import org.warehouse.entity.device.VacuumCleaner;
import org.warehouse.entity.devicemodel.VacuumCleanerModel;

public interface VacuumCleanerService extends DeviceService<VacuumCleaner, VacuumCleanerModel> {

    Optional<VacuumCleaner> findAvailabilityByNameAndAmountAndModes(String deviceName, int amountLitres,
            int numberOfModes, boolean availability);
}
