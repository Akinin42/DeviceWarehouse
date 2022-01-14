package org.warehouse.service;

import java.util.Optional;

import org.warehouse.entity.TVSet;
import org.warehouse.entity.TVSetModel;

public interface TVSetService extends DeviceService<TVSet, TVSetModel> {

    Optional<TVSet> findAvailabilityByNameAndCategoryAndTechnology(String deviceName, String category,
            String technology, boolean availability);
}
