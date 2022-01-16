package org.warehouse.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.warehouse.dao.VacuumCleanerDao;
import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.ModelDto;
import org.warehouse.dto.VacuumCleanerModelDto;
import org.warehouse.entity.Size;
import org.warehouse.entity.device.VacuumCleaner;
import org.warehouse.entity.devicemodel.Model;
import org.warehouse.entity.devicemodel.VacuumCleanerModel;
import org.warehouse.service.DeviceSorter;
import org.warehouse.service.VacuumCleanerService;

@Service
@Transactional
public class VacuumCleanerServiceImpl extends DeviceServiceImpl<VacuumCleaner, VacuumCleanerModel> implements VacuumCleanerService {

    protected final VacuumCleanerDao vacuumCleanerDao;

    public VacuumCleanerServiceImpl(VacuumCleanerDao vacuumCleanerDao, DeviceSorter<VacuumCleaner> deviceSorter) {
        super(vacuumCleanerDao, deviceSorter);
        this.vacuumCleanerDao = vacuumCleanerDao;
    }
    
    @Override
    public List<VacuumCleaner> findAllByAmount(int amountLitres) {
        List<VacuumCleaner> vacuumCleaners = vacuumCleanerDao.findAll();
        List<VacuumCleaner> result = new ArrayList<>();
        for (VacuumCleaner vacuumCleaner : vacuumCleaners) {
            List<Model> models = new ArrayList<>(getModelsForDevice(vacuumCleaner.getName()));
            filterByAmount(models, amountLitres);
            if (!models.isEmpty()) {
                result.add(createResultDevice(vacuumCleaner, models));
            }
        }
        return result;
    }

    @Override
    public List<VacuumCleaner> findAllByModes(int numberOfModes) {
        List<VacuumCleaner> vacuumCleaners = vacuumCleanerDao.findAll();
        List<VacuumCleaner> result = new ArrayList<>();
        for (VacuumCleaner vacuumCleaner : vacuumCleaners) {
            List<Model> models = new ArrayList<>(getModelsForDevice(vacuumCleaner.getName()));
            filterByModes(models, numberOfModes);
            if (!models.isEmpty()) {
                result.add(createResultDevice(vacuumCleaner, models));
            }
        }
        return result;
    }
    
    private void filterByAmount(List<Model> models, int amountLitres) {
        if (amountLitres != 0) {
            for (Model model : new ArrayList<>(models)) {
                VacuumCleanerModel vacuumCleanerModel = (VacuumCleanerModel) model;
                if (vacuumCleanerModel.getAmountLitres() < amountLitres) {
                    models.remove(model);
                }
            }
        }
    }

    private void filterByModes(List<Model> models, int numberOfModes) {
        for (Model model : new ArrayList<>(models)) {
            VacuumCleanerModel vacuumCleanerModel = (VacuumCleanerModel) model;
            if (vacuumCleanerModel.getNumberModes() < numberOfModes) {
                models.remove(model);
            }
        }
    }

    @Override
    protected VacuumCleaner mapDtoToEntity(DeviceDto deviceDto) {
        return VacuumCleaner.builder()                
                .withName(deviceDto.getName())
                .withCountryOfManufacture(deviceDto.getCountryOfManufacture())
                .withCompany(deviceDto.getCompany())
                .withOnlineOrder(deviceDto.getOnlineOrder())
                .withInstallment(deviceDto.getInstallment())
                .build();
    }

    @Override
    protected VacuumCleanerModel mapModelDtoToEntity(ModelDto modelDto) {
        VacuumCleanerModelDto vacuumCleanerModelDto = (VacuumCleanerModelDto) modelDto;
        Size size = Size.builder()
                .withLengthMm(modelDto.getLengthMm())
                .withWidthMm(modelDto.getWidthMm())
                .withHeightMm(modelDto.getHeightMm())
                .build();
        return VacuumCleanerModel.builder()                
                .withName(vacuumCleanerModelDto.getName())
                .withSerialNumber(vacuumCleanerModelDto.getSerialNumber())
                .withColor(vacuumCleanerModelDto.getColour())
                .withSize(size)
                .withCost(vacuumCleanerModelDto.getCost())
                .withAvailability(vacuumCleanerModelDto.getAvailability())
                .withAmountLitres(vacuumCleanerModelDto.getAmountLitres())
                .withNumberModes(vacuumCleanerModelDto.getNumberModes())
                .build();
    }

    @Override
    protected VacuumCleaner createResultDevice(VacuumCleaner device, List<Model> models) {
        return VacuumCleaner.builder()
                .withId(device.getId())
                .withName(device.getName())
                .withCountryOfManufacture(device.getCountryOfManufacture())
                .withCompany(device.getCompany())
                .withOnlineOrder(device.getOnlineOrder())
                .withInstallment(device.getInstallment())
                .withModels(models)
                .build();
    }
}
