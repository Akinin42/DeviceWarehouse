package org.warehouse.service;

import java.util.Optional;

import org.warehouse.entity.VacuumCleaner;
import org.warehouse.entity.VacuumCleanerModel;

public interface VacuumCleanerService extends DeviceService<VacuumCleaner, VacuumCleanerModel> {

    Optional<VacuumCleaner> findAvailabilityByNameAndAmountAndModes(String deviceName, int amountLitres,
            int numberOfModes, boolean availability);
}
