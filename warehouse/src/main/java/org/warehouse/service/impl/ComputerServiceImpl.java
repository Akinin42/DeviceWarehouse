package org.warehouse.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.warehouse.dao.ComputerDao;
import org.warehouse.dto.ComputerModelDto;
import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.ModelDto;
import org.warehouse.entity.Size;
import org.warehouse.entity.device.Computer;
import org.warehouse.entity.devicemodel.ComputerModel;
import org.warehouse.entity.devicemodel.Model;
import org.warehouse.service.ComputerService;
import org.warehouse.util.DeviceSorter;

@Service
@Transactional
public class ComputerServiceImpl extends DeviceServiceImpl<Computer, ComputerModel> implements ComputerService {

    protected final ComputerDao computerDao;

    public ComputerServiceImpl(ComputerDao computerDao, DeviceSorter<Computer> deviceSorter) {
        super(computerDao, deviceSorter);
        this.computerDao = computerDao;
    }
    
    @Override
    public List<Computer> findAllByCategory(String category) {
        List<Computer> computers = computerDao.findAll();
        List<Computer> result = new ArrayList<>();
        for (Computer computer : computers) {
            List<Model> models = new ArrayList<>(getModelsForDevice(computer.getName()));
            filterByCategory(models, category);
            if (!models.isEmpty()) {
                result.add(createResultDevice(computer, models));
            }
        }
        return result;
    }

    @Override
    public List<Computer> findAllByProcessor(String processor) {
        List<Computer> computers = computerDao.findAll();
        List<Computer> result = new ArrayList<>();
        for (Computer computer : computers) {
            List<Model> models = new ArrayList<>(getModelsForDevice(computer.getName()));
            filterByProcessor(models, processor);
            if (!models.isEmpty()) {
                result.add(createResultDevice(computer, models));
            }
        }
        return result;
    }

    private void filterByCategory(List<Model> models, String category) {
        if (category != null && !category.isEmpty()) {
            for (Model model : new ArrayList<>(models)) {
                ComputerModel computerModel = (ComputerModel) model;
                if (!computerModel.getCategory().equals(category)) {
                    models.remove(model);
                }
            }
        }
    }

    private void filterByProcessor(List<Model> models, String processor) {
        if (processor != null && !processor.isEmpty()) {
            for (Model model : new ArrayList<>(models)) {
                ComputerModel computerModel = (ComputerModel) model;
                if (!computerModel.getProcessor().equals(processor)) {
                    models.remove(model);
                }
            }
        }
    }

    @Override
    protected Computer mapDtoToEntity(DeviceDto deviceDto) {
        return Computer.builder()
                .withId(deviceDto.getId())
                .withName(deviceDto.getName())
                .withCountryOfManufacture(deviceDto.getCountryOfManufacture())
                .withCompany(deviceDto.getCompany())
                .withOnlineOrder(deviceDto.getOnlineOrder())
                .withInstallment(deviceDto.getInstallment())
                .build();
    }

    @Override
    protected ComputerModel mapModelDtoToEntity(ModelDto modelDto) {
        ComputerModelDto tvsetModelDto = (ComputerModelDto) modelDto;
        Size size = Size.builder()
                .withLengthMm(modelDto.getLengthMm())
                .withWidthMm(modelDto.getWidthMm())
                .withHeightMm(modelDto.getHeightMm())
                .build();
        return ComputerModel.builder()
                .withId(tvsetModelDto.getId())
                .withName(tvsetModelDto.getName())
                .withSerialNumber(tvsetModelDto.getSerialNumber())
                .withColor(tvsetModelDto.getColour())
                .withSize(size)
                .withCost(tvsetModelDto.getCost())
                .withAvailability(tvsetModelDto.getAvailability())
                .withCategory(tvsetModelDto.getCategory())
                .withProcessor(tvsetModelDto.getProcessor())
                .build();
    }

    @Override
    protected Computer createResultDevice(Computer device, List<Model> models) {
        return Computer.builder()
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
