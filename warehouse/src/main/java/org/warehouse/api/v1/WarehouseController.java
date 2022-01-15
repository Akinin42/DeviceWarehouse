package org.warehouse.api.v1;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.warehouse.entity.device.Device;
import org.warehouse.service.WarehouseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/warehouse")
@Tag(name = "Warehouse", description = "search and sorting warehouse (all devices)")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    @Operation(summary = "Find everything devices in warehouse", 
        description = "Return everything devices in warehouse")
    public List<Device> findAllDevices() {
        return warehouseService.findAllDevices();
    }

    @GetMapping("/nameorder")
    @Operation(summary = "Find everything ordered ascending devices in warehouse by name", 
        description = "Return everything ordered ascending devices in warehouse by name")
    public List<Device> findAllDevicesOrderedByName() {
        return warehouseService.findAllDevicesOrderedByName();
    }

    @GetMapping("/costorder")
    @Operation(summary = "Find everything ordered ascending devices in warehouse by cost", 
        description = "Return everything ordered ascending devices in warehouse by cost")
    public List<Device> findAllDevicesOrderedByCost() {
        return warehouseService.findAllDevicesOrderedByCost();
    }
}
