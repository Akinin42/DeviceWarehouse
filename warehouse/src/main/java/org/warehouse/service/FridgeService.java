package org.warehouse.service;

import java.util.Optional;

import org.warehouse.entity.Fridge;
import org.warehouse.entity.FridgeModel;

public interface FridgeService extends DeviceService<Fridge, FridgeModel> {

    Optional<Fridge> findAvailabilityByNameAndDoorsAndCompressor(String deviceName, int numberOfDoor,
            String compressor, boolean availability);
}
