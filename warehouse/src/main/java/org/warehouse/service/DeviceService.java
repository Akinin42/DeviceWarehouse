package org.warehouse.service;

import java.util.List;

import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.ModelDto;

public interface DeviceService<E, T> {

    void addDevice(DeviceDto deviceDto);
    
    E findByName(String deviceName);

    List<E> findAllDevices();    
  
    List<E> findAllByColor(String color);
    
    List<E> findAllByCost(int minCost, int maxCost);
    
    List<E> findAllByAvailability();

    void addModelForDevice(String deviceName, ModelDto modelDto);
}
