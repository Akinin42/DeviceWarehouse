package org.warehouse.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.warehouse.dao.TVSetDao;
import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.ModelDto;
import org.warehouse.dto.TVSetModelDto;
import org.warehouse.entity.Size;
import org.warehouse.entity.device.TVSet;
import org.warehouse.entity.devicemodel.Model;
import org.warehouse.entity.devicemodel.TVSetModel;
import org.warehouse.service.DeviceSorter;
import org.warehouse.service.TVSetService;

@Service
@Transactional
public class TVSetServiceImpl extends DeviceServiceImpl<TVSet, TVSetModel> implements TVSetService {

    protected final TVSetDao tvsetDao;

    public TVSetServiceImpl(TVSetDao tvsetDao, DeviceSorter<TVSet> deviceSorter) {
        super(tvsetDao, deviceSorter);
        this.tvsetDao = tvsetDao;
    }
    
    @Override
    public List<TVSet> findAllByCategory(String category) {
        List<TVSet> tvsets = tvsetDao.findAll();
        List<TVSet> result = new ArrayList<>();
        for (TVSet tvset : tvsets) {
            List<Model> models = new ArrayList<>(getModelsForDevice(tvset.getName()));
            filterByCategory(models, category);
            if (!models.isEmpty()) {
                result.add(createResultDevice(tvset, models));
            }
        }
        return result;
    }

    @Override
    public List<TVSet> findAllByTechnology(String technology) {
        List<TVSet> tvsets = tvsetDao.findAll();
        List<TVSet> result = new ArrayList<>();
        for (TVSet tvset : tvsets) {
            List<Model> models = new ArrayList<>(getModelsForDevice(tvset.getName()));
            filterByTechnology(models, technology);
            if (!models.isEmpty()) {
                result.add(createResultDevice(tvset, models));
            }
        }
        return result;
    }

    private void filterByCategory(List<Model> models, String category) {
        if (category != null && !category.isEmpty()) {
            for (Model model : new ArrayList<>(models)) {
                TVSetModel tvsetModel = (TVSetModel) model;
                if (!tvsetModel.getCategory().equals(category)) {
                    models.remove(model);
                }
            }
        }
    }

    private void filterByTechnology(List<Model> models, String technology) {
        if (technology != null && !technology.isEmpty()) {
            for (Model model : new ArrayList<>(models)) {
                TVSetModel tvsetModel = (TVSetModel) model;
                if (!tvsetModel.getTechnology().equals(technology)) {
                    models.remove(model);
                }
            }
        }
    }

    @Override
    protected TVSet mapDtoToEntity(DeviceDto deviceDto) {
        return TVSet.builder()
                .withId(deviceDto.getId())
                .withName(deviceDto.getName())
                .withCountryOfManufacture(deviceDto.getCountryOfManufacture())
                .withCompany(deviceDto.getCompany())
                .withOnlineOrder(deviceDto.getOnlineOrder())
                .withInstallment(deviceDto.getInstallment())
                .build();
    }

    @Override
    protected TVSetModel mapModelDtoToEntity(ModelDto modelDto) {
        TVSetModelDto tvsetModelDto = (TVSetModelDto) modelDto;
        Size size = Size.builder()
                .withLengthMm(modelDto.getLengthMm())
                .withWidthMm(modelDto.getWidthMm())
                .withHeightMm(modelDto.getHeightMm())
                .build();
        return TVSetModel.builder()
                .withId(tvsetModelDto.getId())
                .withName(tvsetModelDto.getName())
                .withSerialNumber(tvsetModelDto.getSerialNumber())
                .withColor(tvsetModelDto.getColour())
                .withSize(size)
                .withCost(tvsetModelDto.getCost())
                .withAvailability(tvsetModelDto.getAvailability())
                .withCategory(tvsetModelDto.getCategory())
                .withTechnology(tvsetModelDto.getTechnology())
                .build();
    }

    @Override
    protected TVSet createResultDevice(TVSet device, List<Model> models) {
        return TVSet.builder()
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
