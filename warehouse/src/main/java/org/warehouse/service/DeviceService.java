package org.warehouse.service;

import java.util.List;
import java.util.Optional;

import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.ModelDto;

public interface DeviceService<E, T> {

    void addDevice(DeviceDto deviceDto);
    
    Optional<E> findByName(String deviceName);

    List<E> findAllDevices();
    
    Optional<E> findByNameAndColourAndCost(String deviceName, String colour, int minCost, int maxCost);

    void addModelForDevice(String deviceName, ModelDto modelDto);
    
    List<T> findAllModelsForDevice(String deviceName);
    
    List<T> findAllModels();
}
