package org.warehouse.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.warehouse.entity.device.Device;
import org.warehouse.entity.devicemodel.Model;

@Component
public class DeviceSorter<E extends Device> {    
    
    public List<E> sortByCost(List<E> devices) {
        List<E> sortedDevices = new ArrayList<>();
        int minCost = Integer.MAX_VALUE;
        E cheapestDevice = null;
        devices = deleteDeviceWithouModels(devices);
        while (!devices.isEmpty()) {
            for (E device : devices) {
                if (device.getModels().isEmpty()) {
                    devices.remove(device);
                }
                for (Model model : device.getModels()) {
                    if (model.getCost() <= minCost) {
                        minCost = model.getCost();
                        cheapestDevice = device;
                    }
                }
            }
            if (cheapestDevice != null) {
                sortModelByCost(cheapestDevice);
            }
            sortedDevices.add(cheapestDevice);
            devices.remove(cheapestDevice);
            minCost = Integer.MAX_VALUE;
        }
        return sortedDevices;
    }

    private List<E> deleteDeviceWithouModels(List<E> devices) {
        List<E> result = new ArrayList<>();
        for (E device : devices) {
            if (!device.getModels().isEmpty()) {
                result.add(device);
            }
        }
        return result;
    }

    private void sortModelByCost(E device) {
        List<Model> deviceModels = new ArrayList<>(device.getModels());
        List<Model> sortedModels = new ArrayList<>();
        int minCost = Integer.MAX_VALUE;
        Model cheapestModel = null;
        while (!deviceModels.isEmpty()) {
            for (Model model : deviceModels) {
                if (model.getCost() <= minCost) {
                    minCost = model.getCost();
                    cheapestModel = model;
                }
            }
            sortedModels.add(cheapestModel);
            deviceModels.remove(cheapestModel);
            minCost = Integer.MAX_VALUE;
        }
        device.setModels(sortedModels);
    }
}
