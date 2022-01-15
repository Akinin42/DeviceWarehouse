package org.warehouse.service;

import java.util.List;

import org.warehouse.entity.device.TVSet;
import org.warehouse.entity.devicemodel.TVSetModel;

public interface TVSetService extends DeviceService<TVSet, TVSetModel> {

    List<TVSet> findAllByCategory(String category);

    List<TVSet> findAllByTechnology(String technology);
}
