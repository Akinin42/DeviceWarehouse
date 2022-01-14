package org.warehouse.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.warehouse.dao.DeviceDao;
import org.warehouse.entity.Device;
import org.warehouse.entity.Model;
import org.warehouse.service.WarehouseService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

    private final DeviceDao<Device> deviceDao;

    @Override
    public List<Device> findAllDevices() {
        return deviceDao.findAllWarehouse();
    }

    @Override
    public List<Device> findAllDevicesOrderedByName() {
        return deviceDao.findAllOrderedWarehouseByName();
    }

    @Override
    public List<Device> findAllDevicesOrderedByCost() {
        List<Device> devices = new ArrayList<>(deviceDao.findAllWarehouse());
        return sortByCost(devices);
    }

    private List<Device> sortByCost(List<Device> devices) {
        List<Device> sortedDevices = new ArrayList<>();
        int minCost = Integer.MAX_VALUE;
        Device cheapestDevice = null;
        while (!devices.isEmpty()) {
            for (Device device : devices) {
                for (Model model : device.getModels()) {
                    if (model.getCost() <= minCost) {
                        minCost = model.getCost();
                        cheapestDevice = device;
                    }
                }
            }
            sortedDevices.add(cheapestDevice);
            devices.remove(cheapestDevice);
            minCost = Integer.MAX_VALUE;
        }
        return sortedDevices;
    }
}
