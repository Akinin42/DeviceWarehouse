package org.warehouse.service;

import java.util.Optional;

import org.warehouse.entity.device.TVSet;
import org.warehouse.entity.devicemodel.TVSetModel;

public interface TVSetService extends DeviceService<TVSet, TVSetModel> {

    Optional<TVSet> findAvailabilityByNameAndCategoryAndTechnology(String deviceName, String category,
            String technology, boolean availability);
}
