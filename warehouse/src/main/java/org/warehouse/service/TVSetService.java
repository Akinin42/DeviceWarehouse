package org.warehouse.service;

import org.warehouse.entity.device.TVSet;
import org.warehouse.entity.devicemodel.TVSetModel;

public interface TVSetService extends DeviceService<TVSet, TVSetModel> {

    TVSet findAvailabilityByNameAndCategoryAndTechnology(String deviceName, String category,
            String technology, boolean availability);
}
