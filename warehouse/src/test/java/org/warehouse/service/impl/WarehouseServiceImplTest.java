package org.warehouse.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.warehouse.dao.DeviceDao;
import org.warehouse.entity.device.Device;
import org.warehouse.service.DeviceSorter;
import org.warehouse.service.WarehouseService;
import org.warehouse.util.CreatorTestEntities;

class WarehouseServiceImplTest {

    private static WarehouseService warehouseService;
    private static DeviceDao<Device> deviceDaoMock;
    private static DeviceSorter<Device> deviceSorter;

    @BeforeAll
    static void init() {
        deviceDaoMock = mock(DeviceDao.class);
        deviceSorter = new DeviceSorter<Device>();
        warehouseService = new WarehouseServiceImpl(deviceDaoMock, deviceSorter);
    }

    @Test
    void findAllDevicesShouldReturnExpectedDevices() {
        List<Device> expected = new ArrayList<>();
        expected.add(CreatorTestEntities.createComputers().get(1));
        when(deviceDaoMock.findAllWarehouse()).thenReturn(expected);
        assertThat(warehouseService.findAllDevices()).isEqualTo(expected);
    }

    @Test
    void findAllDevicesOrderedByNameShouldReturnExpectedDevices() {
        List<Device> expected = new ArrayList<>();
        expected.add(CreatorTestEntities.createPhones().get(1));
        when(deviceDaoMock.findAllOrderedWarehouseByName()).thenReturn(expected);
        assertThat(warehouseService.findAllDevicesOrderedByName()).isEqualTo(expected);
    }

    @Test
    void findAllDevicesOrderedByCostShouldReturnExpectedDevices() {
        List<Device> devices = new ArrayList<>();
        devices.add(CreatorTestEntities.createPhones().get(1));
        when(deviceDaoMock.findAllWarehouse()).thenReturn(devices);
        List<Device> expectedSortedDevices = new ArrayList<>();
        Device device = CreatorTestEntities.createPhones().get(1);
        device.removeModel(CreatorTestEntities.createPhoneModels().get(2));
        device.addModel(CreatorTestEntities.createPhoneModels().get(2));
        expectedSortedDevices.add(device);
        assertThat(warehouseService.findAllDevicesOrderedByCost()).isEqualTo(expectedSortedDevices);
    }
}
