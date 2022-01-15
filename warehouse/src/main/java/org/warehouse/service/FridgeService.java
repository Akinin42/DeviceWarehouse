package org.warehouse.service;

import org.warehouse.entity.device.Fridge;
import org.warehouse.entity.devicemodel.FridgeModel;

public interface FridgeService extends DeviceService<Fridge, FridgeModel> {

    Fridge findAvailabilityByNameAndDoorsAndCompressor(String deviceName, int numberOfDoor,
            String compressor, boolean availability);
}
