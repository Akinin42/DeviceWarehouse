package org.warehouse.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.warehouse.entity.device.Device;
import org.warehouse.entity.device.Phone;
import org.warehouse.util.CreatorTestEntities;

class DeviceSorterTest {

    private static DeviceSorter<Device> deviceSorter;
    
    @BeforeAll
    static void init() {        
        deviceSorter = new DeviceSorter<Device>();        
    }
    
    @Test
    void sortByCostShouldReturnExpectedList() {
        List<Device> expected = new ArrayList<>();
        Phone device = CreatorTestEntities.createPhones().get(0);
        device.removeModel(CreatorTestEntities.createPhoneModels().get(1));
        expected.add(device);
        device = CreatorTestEntities.createPhones().get(1);
        device.removeModel(CreatorTestEntities.createPhoneModels().get(2));
        device.addModel(CreatorTestEntities.createPhoneModels().get(2));
        expected.add(device);
        List<Device> listForSort = new ArrayList<>();        
        Phone deviceWithOneModel = CreatorTestEntities.createPhones().get(0);
        deviceWithOneModel.removeModel(CreatorTestEntities.createPhoneModels().get(1));
        Phone deviceWithoutModel = CreatorTestEntities.createPhones().get(1);
        deviceWithoutModel.removeModel(CreatorTestEntities.createPhoneModels().get(2));
        deviceWithoutModel.removeModel(CreatorTestEntities.createPhoneModels().get(3));
        listForSort.add(deviceWithOneModel);
        listForSort.add(CreatorTestEntities.createPhones().get(1));
        listForSort.add(deviceWithoutModel);
        assertThat(deviceSorter.sortByCost(listForSort)).isEqualTo(expected);
    }
}
