package org.warehouse.dao;

import org.springframework.stereotype.Repository;
import org.warehouse.entity.device.VacuumCleaner;

@Repository
public interface VacuumCleanerDao extends DeviceDao<VacuumCleaner> {
}
