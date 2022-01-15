package org.warehouse.service;

import java.util.List;

import org.warehouse.entity.device.Device;

public interface WarehouseService {
    
    List<Device> findAllDevices();
    
    List<Device> findAllDevicesOrderedByName();
    
    List<Device> findAllDevicesOrderedByCost();
}
