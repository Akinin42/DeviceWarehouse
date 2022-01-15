package org.warehouse.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.warehouse.dao.DeviceDao;
import org.warehouse.entity.device.Device;
import org.warehouse.service.WarehouseService;
import org.warehouse.util.DeviceSorter;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

    private final DeviceDao<Device> deviceDao;
    private final DeviceSorter<Device> deviceSorter;

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
        return deviceSorter.sortByCost(devices);
    }
}
