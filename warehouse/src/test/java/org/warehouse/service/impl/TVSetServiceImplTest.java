package org.warehouse.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.warehouse.dao.TVSetDao;
import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.TVSetModelDto;
import org.warehouse.entity.device.TVSet;
import org.warehouse.entity.devicemodel.TVSetModel;
import org.warehouse.exception.EntityAlreadyExistException;
import org.warehouse.exception.EntityNotExistException;
import org.warehouse.service.DeviceSorter;
import org.warehouse.service.TVSetService;
import org.warehouse.util.CreatorTestEntities;

class TVSetServiceImplTest {

    private static TVSetService tvSetService;
    private static TVSetDao tvSetDaoMock;
    private static DeviceSorter<TVSet> deviceSorter;

    @BeforeAll
    static void init() {
        tvSetDaoMock = createTVSetDaoMock();
        deviceSorter = new DeviceSorter<TVSet>();
        tvSetService = new TVSetServiceImpl(tvSetDaoMock, deviceSorter);
    }

    @Test
    void addDeviceShoulSaveDeviceToDatabaseWhenItNotExistYet() {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("test");
        deviceDto.setCountryOfManufacture("test");
        deviceDto.setCompany("test");
        deviceDto.setInstallment(true);
        deviceDto.setOnlineOrder(true);
        TVSet tvSetFromDto = TVSet.builder()                                
                                .withName(deviceDto.getName())
                                .withCountryOfManufacture(deviceDto.getCountryOfManufacture())
                                .withCompany(deviceDto.getCompany())
                                .withOnlineOrder(deviceDto.getOnlineOrder())
                                .withInstallment(deviceDto.getInstallment())
                                .build();
        tvSetService.addDevice(deviceDto);
        verify(tvSetDaoMock).save(tvSetFromDto);
    }
    
    @Test
    void addDeviceShouldThrowEntityAlreadyExistExceptionWhenInputDeviceExistInDatabase() {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("MacTV");
        assertThatThrownBy(() -> tvSetService.addDevice(deviceDto))
                .isInstanceOf(EntityAlreadyExistException.class);
    }
    
    @Test
    void findByNameShouldThrowEntityNotExistExceptionWhenDeviceNotExistInDatabase() {
        assertThatThrownBy(() -> tvSetService.findByName("notexisttv"))
            .isInstanceOf(EntityNotExistException.class).hasMessage("Device with name notexisttv not found!");
    }
    
    @Test
    void findByNameShouldReturnExpectedTVSetWhenExist() {
        TVSet expected = CreatorTestEntities.createTVSets().get(0);
        assertThat(tvSetService.findByName("MacTV")).isEqualTo(expected);
    }
    
    @Test
    void findAllDevicesShouldReturnExpectedTVSets() {
        List<TVSet> expected = CreatorTestEntities.createTVSets();
        assertThat(tvSetService.findAllDevices()).isEqualTo(expected);
    }
    
    @Test
    void findAllByColorShouldReturnExpectedTVSets() {        
        List<TVSet> expected = new ArrayList<>();
        TVSet tvSet = CreatorTestEntities.createTVSets().get(0);
        tvSet.removeModel(CreatorTestEntities.createTVSetModels().get(0));
        expected.add(tvSet);
        tvSet = CreatorTestEntities.createTVSets().get(1);
        tvSet.removeModel(CreatorTestEntities.createTVSetModels().get(3));
        expected.add(tvSet);
        assertThat(tvSetService.findAllByColor("White")).isEqualTo(expected);
    }
    
    @Test
    void findAllByColorShouldReturnEmptyListWhenColorNotExist() {
        assertThat(tvSetService.findAllByColor("Green")).isEmpty();
    }
    
    @Test
    void findAllByColorShouldReturnAllTVSetsWhenColorNull() {
        List<TVSet> expected = CreatorTestEntities.createTVSets();
        assertThat(tvSetService.findAllByColor(null)).isEqualTo(expected);
    }
    
    @Test
    void findAllByColorShouldReturnAllTVSetsWhenColorEmpty() {
        List<TVSet> expected = CreatorTestEntities.createTVSets();
        assertThat(tvSetService.findAllByColor("")).isEqualTo(expected);
    }
    
    @Test
    void findAllByCostShouldReturnExpectedTVSets() {        
        List<TVSet> expected = new ArrayList<>();
        TVSet tvSet = CreatorTestEntities.createTVSets().get(0);
        tvSet.removeModel(CreatorTestEntities.createTVSetModels().get(1));
        expected.add(tvSet);
        tvSet = CreatorTestEntities.createTVSets().get(1);
        tvSet.removeModel(CreatorTestEntities.createTVSetModels().get(2));
        expected.add(tvSet);
        assertThat(tvSetService.findAllByCost(25000, 50000)).isEqualTo(expected);
    }
    
    @Test
    void findAllByCostShouldReturnEmptyListWhenCostNotExists() {
        assertThat(tvSetService.findAllByCost(2000000, 0)).isEmpty();
    }
    
    @Test
    void findAllByCostShouldReturnEmptyListWhenTheirNot() {
        assertThat(tvSetService.findAllByCost(0, 10)).isEmpty();
    }
    
    @Test
    void findAllByAvailabilityShouldReturnExpectedTVSets() {        
        List<TVSet> expected = new ArrayList<>();
        TVSet tvSet = CreatorTestEntities.createTVSets().get(0);
        tvSet.removeModel(CreatorTestEntities.createTVSetModels().get(1));
        expected.add(tvSet);
        assertThat(tvSetService.findAllByAvailability()).isEqualTo(expected);
    }
    
    @Test
    void findAllByCategoryShouldReturnExpectedTVSets() {        
        List<TVSet> expected = new ArrayList<>();
        TVSet tvSet = CreatorTestEntities.createTVSets().get(0);
        tvSet.removeModel(CreatorTestEntities.createTVSetModels().get(1));
        expected.add(tvSet);
        tvSet = CreatorTestEntities.createTVSets().get(1);
        tvSet.removeModel(CreatorTestEntities.createTVSetModels().get(3));
        expected.add(tvSet);
        assertThat(tvSetService.findAllByCategory("first")).isEqualTo(expected);
    }
    
    @Test
    void findAllByCategoryShouldReturnAllTVSetsWhenInputNull() {        
        List<TVSet> expected = CreatorTestEntities.createTVSets();
        assertThat(tvSetService.findAllByCategory(null)).isEqualTo(expected);
    }
    
    @Test
    void findAllByCategoryShouldReturnAllTVSetsWhenInputEmpty() {        
        List<TVSet> expected = CreatorTestEntities.createTVSets();
        assertThat(tvSetService.findAllByCategory("")).isEqualTo(expected);
    }
    
    @Test
    void findAllByCategoryShouldReturnEmptyListWhenInputNotExist() {
        assertThat(tvSetService.findAllByCategory("notexist category")).isEmpty();
    }
    
    @Test
    void findAllByTechnologyShouldReturnExpectedTVSets() {        
        List<TVSet> expected = new ArrayList<>();
        TVSet tvSet = CreatorTestEntities.createTVSets().get(0);
        tvSet.removeModel(CreatorTestEntities.createTVSetModels().get(1));
        expected.add(tvSet);
        tvSet = CreatorTestEntities.createTVSets().get(1);
        tvSet.removeModel(CreatorTestEntities.createTVSetModels().get(3));
        expected.add(tvSet);
        assertThat(tvSetService.findAllByTechnology("LED")).isEqualTo(expected);
    }
    
    @Test
    void findAllByProcessorShouldReturnAllTVSetsWhenInputNull() {        
        List<TVSet> expected = CreatorTestEntities.createTVSets();
        assertThat(tvSetService.findAllByTechnology(null)).isEqualTo(expected);
    }
    
    @Test
    void findAllByProcessorShouldReturnAllTVSetsWhenInputEmpty() {        
        List<TVSet> expected = CreatorTestEntities.createTVSets();
        assertThat(tvSetService.findAllByTechnology("")).isEqualTo(expected);
    }
    
    @Test
    void findAllByProcessorShouldReturnEmptyListWhenInputNotExist() {
        assertThat(tvSetService.findAllByTechnology("notexist technology")).isEmpty();
    }
    
    @Test
    void addModelToDeviceShoulSaveModelToDatabase() {
        TVSetDao tvSetDaoMock = createTVSetDaoMock();
        deviceSorter = new DeviceSorter<TVSet>();
        TVSetService tvSetService = new TVSetServiceImpl(tvSetDaoMock, deviceSorter);
        TVSetModelDto modelDto = TVSetModelDto.builder()
                    .withName("testModel")
                    .withLengthMm(100)
                    .withWidthMm(100)
                    .withHeightMm(100)
                    .build();
        TVSetModel tvSetModelFromDto = TVSetModel.builder()                                
                                .withName(modelDto.getName())
                                .withSize(CreatorTestEntities.createSize(100, 100, 100))
                                .build();
        TVSet tvSet = CreatorTestEntities.createTVSets().get(0);
        tvSet.addModel(tvSetModelFromDto);
        tvSetService.addModelForDevice("MacTV", modelDto);
        verify(tvSetDaoMock).save(tvSet);
    }
    
    private static TVSetDao createTVSetDaoMock() {
        TVSetDao tvsetDaoMock = mock(TVSetDao.class);
        when(tvsetDaoMock.findAll()).thenReturn(CreatorTestEntities.createTVSets());
        when(tvsetDaoMock.findByNameIgnoreCase("MacTV")).thenReturn(Optional.ofNullable(CreatorTestEntities.createTVSets().get(0)));
        when(tvsetDaoMock.findByNameIgnoreCase("MacTV2")).thenReturn(Optional.ofNullable(CreatorTestEntities.createTVSets().get(1)));
        return tvsetDaoMock;
    }
}
