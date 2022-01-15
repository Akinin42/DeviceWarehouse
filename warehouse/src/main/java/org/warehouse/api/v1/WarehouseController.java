package org.warehouse.api.v1;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.warehouse.entity.device.Device;
import org.warehouse.service.WarehouseService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    public List<Device> findAllDevices() {
        return warehouseService.findAllDevices();
    }
    
    @GetMapping("/nameorder")
    public List<Device> findAllDevicesOrderedByName() {
        return warehouseService.findAllDevicesOrderedByName();
    }
    
    @GetMapping("/costorder")
    public List<Device> findAllDevicesOrderedByCost() {
        return warehouseService.findAllDevicesOrderedByCost();
    }
}
