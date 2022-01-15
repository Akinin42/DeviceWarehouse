package org.warehouse.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.warehouse.dao.DeviceDao;
import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.ModelDto;
import org.warehouse.entity.device.Device;
import org.warehouse.entity.devicemodel.Model;
import org.warehouse.exception.EntityAlreadyExistException;
import org.warehouse.exception.EntityNotExistException;
import org.warehouse.service.DeviceService;
import org.warehouse.service.DeviceSorter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public abstract class DeviceServiceImpl<E extends Device, T extends Model> implements DeviceService<E, T> {

    private final DeviceDao<E> deviceDao;
    private final DeviceSorter<E> deviceSorter;

    @Override
    public void addDevice(DeviceDto deviceDto) {
        if (deviceDao.findByNameIgnoreCase(deviceDto.getName()).isPresent()) {
            throw new EntityAlreadyExistException(
                    String.format("Device witn name %s already exists", deviceDto.getName()));
        }
        deviceDao.save(mapDtoToEntity(deviceDto));
        log.info(String.format("Device witn name %s saved to datadase", deviceDto.getName()));
    }

    @Override
    public E findByName(String deviceName) {
        checkDeviceExists(deviceName);
        return deviceDao.findByNameIgnoreCase(deviceName).get();
    }

    @Override
    public List<E> findAllDevices() {
        return deviceDao.findAll();
    }

    @Override
    public List<E> findAllByColor(String color) {
        List<E> devices = deviceDao.findAll();
        List<E> result = new ArrayList<>();
        for (E device : devices) {
            List<Model> models = new ArrayList<>(getModelsForDevice(device.getName()));
            filterByColor(models, color);
            if (!models.isEmpty()) {
                result.add(createResultDevice(device, models));
            }
        }
        return result;
    }
    
    @Override
    public List<E> findAllByCost(int minCost, int maxCost) {
        List<E> devices = deviceDao.findAll();
        List<E> result = new ArrayList<>();
        for (E device : devices) {
            List<Model> models = new ArrayList<>(getModelsForDevice(device.getName()));
            filterByCost(models, minCost, maxCost);
            if (!models.isEmpty()) {
                result.add(createResultDevice(device, models));
            }
        }
        return deviceSorter.sortByCost(result);
    }
    
    @Override
    public List<E> findAllByAvailability() {
        List<E> devices = deviceDao.findAll();
        List<E> result = new ArrayList<>();
        for (E device : devices) {
            List<Model> models = new ArrayList<>(getModelsForDevice(device.getName()));
            filterByAvailability(models);
            if (!models.isEmpty()) {
                result.add(createResultDevice(device, models));
            }
        }
        return result;
    }

    private void filterByColor(List<Model> models, String color) {
        if (color != null && !color.isEmpty()) {
            for (Model model : new ArrayList<>(models)) {
                if (!model.getColor().equals(color)) {
                    models.remove(model);
                }
            }
        }
    }

    private void filterByCost(List<Model> models, int minCost, int maxCost) {
        for (Model model : new ArrayList<>(models)) {
            if (model.getCost() < minCost || (model.getCost() > maxCost && maxCost != 0)) {
                models.remove(model);
            }
        }
    }
    
    private void filterByAvailability(List<Model> models) {
        for (Model model : new ArrayList<>(models)) {
            if (Boolean.FALSE.equals(model.getAvailability())) {
                models.remove(model);
            }
        }
    }

    @Override
    public void addModelForDevice(String deviceName, ModelDto modelDto) {
        checkDeviceExists(deviceName);
        E device = deviceDao.findByNameIgnoreCase(deviceName).get();
        device.addModel(mapModelDtoToEntity(modelDto));
        deviceDao.save(device);
        log.info(String.format("Model witn name %s added to device %s", modelDto.getName(), deviceName));
    }

    protected void checkDeviceExists(String deviceName) {
        if (!deviceDao.findByNameIgnoreCase(deviceName).isPresent()) {
            throw new EntityNotExistException(String.format("Device with name %s not found!", deviceName));
        }
    }

    protected List<Model> getModelsForDevice(String deviceName) {
        return deviceDao.findByNameIgnoreCase(deviceName).get().getModels();
    }

    protected abstract E mapDtoToEntity(DeviceDto deviceDto);

    protected abstract T mapModelDtoToEntity(ModelDto modelDto);

    protected abstract E createResultDevice(E device, List<Model> models);
}
