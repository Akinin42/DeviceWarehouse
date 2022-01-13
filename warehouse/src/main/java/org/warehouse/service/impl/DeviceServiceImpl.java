package org.warehouse.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.warehouse.dao.DeviceDao;
import org.warehouse.dao.ModelDao;
import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.ModelDto;
import org.warehouse.entity.Device;
import org.warehouse.entity.Model;
import org.warehouse.service.DeviceService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public abstract class DeviceServiceImpl<E extends Device, T extends Model> implements DeviceService<E, T> {

    private final DeviceDao<E> deviceDao;
    private final ModelDao<T> modelDao;

    @Override
    public void addDevice(DeviceDto deviceDto) {
        deviceDao.save(mapDtoToEntity(deviceDto));
    }

    @Override
    public List<E> findAllDevices() {
        return deviceDao.findAll();
    }
    
    @Override
    public void addModelForDevice(String deviceName, ModelDto modelDto) {        
        E device = deviceDao.findByName(deviceName).get();
        device.addModel(mapModelDtoToEntity(modelDto));
        deviceDao.save(device);
    }
    @Override
    public List<T> findAllModelsForDevice(String deviceName){        
        return (List<T>) deviceDao.findByName(deviceName).get().getModels();        
    }
    
    @Override
    public List<T> findAllModels(){
        return modelDao.findAll();
    }

    protected abstract E mapDtoToEntity(DeviceDto deviceDto);
    
    protected abstract T mapModelDtoToEntity(ModelDto modelDto);
}
