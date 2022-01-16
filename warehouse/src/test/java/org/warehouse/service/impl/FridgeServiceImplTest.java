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
import org.warehouse.dao.FridgeDao;
import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.FridgeModelDto;
import org.warehouse.entity.device.Fridge;
import org.warehouse.entity.devicemodel.FridgeModel;
import org.warehouse.exception.EntityAlreadyExistException;
import org.warehouse.exception.EntityNotExistException;
import org.warehouse.service.DeviceSorter;
import org.warehouse.service.FridgeService;
import org.warehouse.util.CreatorTestEntities;

class FridgeServiceImplTest {

    private static FridgeService fridgeService;
    private static FridgeDao fridgeDaoMock;
    private static DeviceSorter<Fridge> deviceSorter;

    @BeforeAll
    static void init() {
        fridgeDaoMock = createFridgeDaoMock();
        deviceSorter = new DeviceSorter<Fridge>();
        fridgeService = new FridgeServiceImpl(fridgeDaoMock, deviceSorter);
    }

    @Test
    void addDeviceShoulSaveDeviceToDatabaseWhenItNotExistYet() {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("test");
        deviceDto.setCountryOfManufacture("test");
        deviceDto.setCompany("test");
        deviceDto.setInstallment(true);
        deviceDto.setOnlineOrder(true);
        Fridge fridgeFromDto = Fridge.builder()                                
                                .withName(deviceDto.getName())
                                .withCountryOfManufacture(deviceDto.getCountryOfManufacture())
                                .withCompany(deviceDto.getCompany())
                                .withOnlineOrder(deviceDto.getOnlineOrder())
                                .withInstallment(deviceDto.getInstallment())
                                .build();
        fridgeService.addDevice(deviceDto);
        verify(fridgeDaoMock).save(fridgeFromDto);
    }
    
    @Test
    void addDeviceShouldThrowEntityAlreadyExistExceptionWhenInputDeviceExistInDatabase() {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("MacFridge");
        assertThatThrownBy(() -> fridgeService.addDevice(deviceDto))
                .isInstanceOf(EntityAlreadyExistException.class);
    }
    
    @Test
    void findByNameShouldThrowEntityNotExistExceptionWhenDeviceNotExistInDatabase() {
        assertThatThrownBy(() -> fridgeService.findByName("notexistFridge"))
            .isInstanceOf(EntityNotExistException.class).hasMessage("Device with name notexistFridge not found!");
    }
    
    @Test
    void findByNameShouldReturnExpectedFridgeWhenExist() {
        Fridge expected = CreatorTestEntities.createFridges().get(0);
        assertThat(fridgeService.findByName("MacFridge")).isEqualTo(expected);
    }
    
    @Test
    void findAllDevicesShouldReturnExpectedFridges() {
        List<Fridge> expected = CreatorTestEntities.createFridges();
        assertThat(fridgeService.findAllDevices()).isEqualTo(expected);
    }
    
    @Test
    void findAllByColorShouldReturnExpectedFridges() {        
        List<Fridge> expected = new ArrayList<>();
        Fridge fridge = CreatorTestEntities.createFridges().get(0);
        fridge.removeModel(CreatorTestEntities.createFridgeModels().get(0));
        expected.add(fridge);
        fridge = CreatorTestEntities.createFridges().get(1);
        fridge.removeModel(CreatorTestEntities.createFridgeModels().get(3));
        expected.add(fridge);
        assertThat(fridgeService.findAllByColor("White")).isEqualTo(expected);
    }
    
    @Test
    void findAllByColorShouldReturnEmptyListWhenColorNotExist() {
        assertThat(fridgeService.findAllByColor("Green")).isEmpty();
    }
    
    @Test
    void findAllByColorShouldReturnAllFridgesWhenColorNull() {
        List<Fridge> expected = CreatorTestEntities.createFridges();
        assertThat(fridgeService.findAllByColor(null)).isEqualTo(expected);
    }
    
    @Test
    void findAllByColorShouldReturnAllFridgesWhenColorEmpty() {
        List<Fridge> expected = CreatorTestEntities.createFridges();
        assertThat(fridgeService.findAllByColor("")).isEqualTo(expected);
    }
    
    @Test
    void findAllByCostShouldReturnExpectedFridges() {        
        List<Fridge> expected = new ArrayList<>();
        Fridge fridge = CreatorTestEntities.createFridges().get(0);
        fridge.removeModel(CreatorTestEntities.createFridgeModels().get(1));
        expected.add(fridge);
        fridge = CreatorTestEntities.createFridges().get(1);
        fridge.removeModel(CreatorTestEntities.createFridgeModels().get(2));
        expected.add(fridge);
        assertThat(fridgeService.findAllByCost(25000, 50000)).isEqualTo(expected);
    }
    
    @Test
    void findAllByCostShouldReturnEmptyListWhenCostNotExists() {
        assertThat(fridgeService.findAllByCost(2000000, 0)).isEmpty();
    }
    
    @Test
    void findAllByCostShouldReturnEmptyListWhenTheirNot() {
        assertThat(fridgeService.findAllByCost(0, 10)).isEmpty();
    }
    
    @Test
    void findAllByAvailabilityShouldReturnExpectedFridges() {        
        List<Fridge> expected = new ArrayList<>();
        Fridge fridge = CreatorTestEntities.createFridges().get(0);
        fridge.removeModel(CreatorTestEntities.createFridgeModels().get(1));
        expected.add(fridge);
        assertThat(fridgeService.findAllByAvailability()).isEqualTo(expected);
    }
    
    @Test
    void findAllByDoorsShouldReturnExpectedFridges() {        
        List<Fridge> expected = new ArrayList<>();
        Fridge fridge = CreatorTestEntities.createFridges().get(0);
        fridge.removeModel(CreatorTestEntities.createFridgeModels().get(0));
        expected.add(fridge);
        fridge = CreatorTestEntities.createFridges().get(1);
        fridge.removeModel(CreatorTestEntities.createFridgeModels().get(2));
        expected.add(fridge);
        assertThat(fridgeService.findAllByDoors(2)).isEqualTo(expected);
    }
    
    @Test
    void findAllByDoorsShouldReturnAllFridgesWhenInputZero() {        
        List<Fridge> expected = CreatorTestEntities.createFridges();
        assertThat(fridgeService.findAllByDoors(0)).isEqualTo(expected);
    }
    
    @Test
    void findAllByDoorsShouldReturnEmptyListWhenInputNotExist() {
        assertThat(fridgeService.findAllByDoors(8)).isEmpty();
    }
    
    @Test
    void findAllByCompressorShouldReturnExpectedFridges() {        
        List<Fridge> expected = new ArrayList<>();
        Fridge fridge = CreatorTestEntities.createFridges().get(0);
        fridge.removeModel(CreatorTestEntities.createFridgeModels().get(1));
        expected.add(fridge);
        fridge = CreatorTestEntities.createFridges().get(1);
        fridge.removeModel(CreatorTestEntities.createFridgeModels().get(3));
        expected.add(fridge);
        assertThat(fridgeService.findAllByCompressor("INV")).isEqualTo(expected);
    }
    
    @Test
    void findAllByCompressorShouldReturnAllFridgesWhenInputNull() {        
        List<Fridge> expected = CreatorTestEntities.createFridges();
        assertThat(fridgeService.findAllByCompressor(null)).isEqualTo(expected);
    }
    
    @Test
    void findAllByCompressorShouldReturnAllFridgesWhenInputEmpty() {        
        List<Fridge> expected = CreatorTestEntities.createFridges();
        assertThat(fridgeService.findAllByCompressor("")).isEqualTo(expected);
    }
    
    @Test
    void findAllByCompressorShouldReturnEmptyListWhenInputNotExist() {
        assertThat(fridgeService.findAllByCompressor("notexist compressor")).isEmpty();
    }
    
    @Test
    void addModelToDeviceShoulSaveModelToDatabase() {
        FridgeDao fridgeDaoMock = createFridgeDaoMock();
        deviceSorter = new DeviceSorter<Fridge>();
        FridgeService fridgeService = new FridgeServiceImpl(fridgeDaoMock, deviceSorter);
        FridgeModelDto modelDto = FridgeModelDto.builder()
                    .withName("testModel")
                    .withLengthMm(100)
                    .withWidthMm(100)
                    .withHeightMm(100)
                    .build();
        FridgeModel fridgeModelFromDto = FridgeModel.builder()                                
                                .withName(modelDto.getName())
                                .withSize(CreatorTestEntities.createSize(100, 100, 100))
                                .build();
        Fridge fridge = CreatorTestEntities.createFridges().get(0);
        fridge.addModel(fridgeModelFromDto);
        fridgeService.addModelForDevice("MacFridge", modelDto);
        verify(fridgeDaoMock).save(fridge);
    }
    
    private static FridgeDao createFridgeDaoMock() {
        FridgeDao fridgeDaoMock = mock(FridgeDao.class);
        when(fridgeDaoMock.findAll()).thenReturn(CreatorTestEntities.createFridges());
        when(fridgeDaoMock.findByNameIgnoreCase("MacFridge")).thenReturn(Optional.ofNullable(CreatorTestEntities.createFridges().get(0)));
        when(fridgeDaoMock.findByNameIgnoreCase("MacFridge2")).thenReturn(Optional.ofNullable(CreatorTestEntities.createFridges().get(1)));
        return fridgeDaoMock;
    }
}
