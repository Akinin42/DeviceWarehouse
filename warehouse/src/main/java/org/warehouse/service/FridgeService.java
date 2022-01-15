package org.warehouse.service;

import java.util.List;

import org.warehouse.entity.device.Fridge;
import org.warehouse.entity.devicemodel.FridgeModel;

public interface FridgeService extends DeviceService<Fridge, FridgeModel> {
    
    List<Fridge> findAllByDoors(int numberOfDoor);
    
    List<Fridge> findAllByCompressor(String compressor);
}
