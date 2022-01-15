package org.warehouse.service;

import java.util.List;

import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.ModelDto;

public interface DeviceService<E, T> {

    void addDevice(DeviceDto deviceDto);
    
    E findByName(String deviceName);

    List<E> findAllDevices();
    
    E findByNameAndColorAndCost(String deviceName, String color, int minCost, int maxCost);

    void addModelForDevice(String deviceName, ModelDto modelDto);
}
