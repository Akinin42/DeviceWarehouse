package org.warehouse.service;

import java.util.List;

import org.warehouse.entity.device.VacuumCleaner;
import org.warehouse.entity.devicemodel.VacuumCleanerModel;

public interface VacuumCleanerService extends DeviceService<VacuumCleaner, VacuumCleanerModel> {

    List<VacuumCleaner> findAllByAmount(int amountLitres);

    List<VacuumCleaner> findAllByModes(int numberOfModes);
}
