package org.warehouse.service;

import java.util.Optional;

import org.warehouse.entity.device.Fridge;
import org.warehouse.entity.devicemodel.FridgeModel;

public interface FridgeService extends DeviceService<Fridge, FridgeModel> {

    Optional<Fridge> findAvailabilityByNameAndDoorsAndCompressor(String deviceName, int numberOfDoor,
            String compressor, boolean availability);
}
