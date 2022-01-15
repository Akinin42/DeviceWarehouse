package org.warehouse.service;

import java.util.List;

import org.warehouse.entity.device.Computer;
import org.warehouse.entity.devicemodel.ComputerModel;

public interface ComputerService extends DeviceService<Computer, ComputerModel> {

    List<Computer> findAllByCategory(String category);
    
    List<Computer> findAllByProcessor(String processor);
}
