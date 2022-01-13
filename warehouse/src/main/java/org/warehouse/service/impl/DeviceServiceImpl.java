package org.warehouse.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Optional<E> findByName(String deviceName) {
        return Optional.ofNullable(deviceDao.findByNameIgnoreCase(deviceName).get());
    }

    @Override
    public List<E> findAllDevices() {
        return deviceDao.findAll();
    }

    @Override
    public Optional<E> findByNameAndColorAndCost(String deviceName, String color, int minCost, int maxCost) {
        E result = null;
        if (deviceDao.findByNameIgnoreCase(deviceName).isPresent()) {
            List<Model> models = new ArrayList<>(deviceDao.findByNameIgnoreCase(deviceName).get().getModels());
            filterByColor(models, color);
            filterByCost(models, minCost, maxCost);
            result = createResultDevice(deviceDao.findByNameIgnoreCase(deviceName).get(), models);

        }
        return Optional.ofNullable(result);
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
        if (minCost != 0 || maxCost != 0) {
            for (Model model : new ArrayList<>(models)) {
                if (model.getCost() < minCost || model.getCost() > maxCost) {
                    models.remove(model);
                }
            }
        }
    }

    @Override
    public void addModelForDevice(String deviceName, ModelDto modelDto) {
        if (deviceDao.findByNameIgnoreCase(deviceName).isPresent()) {
            E device = deviceDao.findByNameIgnoreCase(deviceName).get();
            device.addModel(mapModelDtoToEntity(modelDto));
            deviceDao.save(device);
        }
    }

    @Override
    public List<T> findAllModelsForDevice(String deviceName) {
        List<T> models = new ArrayList<>();
        if (deviceDao.findByNameIgnoreCase(deviceName.toLowerCase()).isPresent()) {
            models = (List<T>) deviceDao.findByNameIgnoreCase(deviceName.toLowerCase()).get().getModels();
        }
        return models;
    }

    @Override
    public List<T> findAllModels() {
        return modelDao.findAll();
    }

    protected abstract E mapDtoToEntity(DeviceDto deviceDto);

    protected abstract T mapModelDtoToEntity(ModelDto modelDto);

    protected abstract E createResultDevice(E device, List<Model> models);
}
