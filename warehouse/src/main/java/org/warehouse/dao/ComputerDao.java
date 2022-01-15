package org.warehouse.dao;

import org.springframework.stereotype.Repository;
import org.warehouse.entity.device.Computer;

@Repository
public interface ComputerDao extends DeviceDao<Computer> {
}
