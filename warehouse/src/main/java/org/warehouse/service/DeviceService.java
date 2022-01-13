package org.warehouse.service;

import java.util.List;

import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.ModelDto;

public interface DeviceService<E, T> {

    void addDevice(DeviceDto deviceDto);

    List<E> findAllDevices();

    void addModelForDevice(String deviceName, ModelDto modelDto);
    
    List<T> findAllModelsForDevice(String deviceName);
    
    List<T> findAllModels();
}
