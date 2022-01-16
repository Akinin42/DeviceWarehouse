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
import org.warehouse.dao.ComputerDao;
import org.warehouse.dto.ComputerModelDto;
import org.warehouse.dto.DeviceDto;
import org.warehouse.entity.device.Computer;
import org.warehouse.entity.devicemodel.ComputerModel;
import org.warehouse.exception.EntityAlreadyExistException;
import org.warehouse.exception.EntityNotExistException;
import org.warehouse.service.ComputerService;
import org.warehouse.service.DeviceSorter;
import org.warehouse.util.CreatorTestEntities;

class ComputerServiceImplTest {

    private static ComputerService computerService;
    private static ComputerDao computerDaoMock;
    private static DeviceSorter<Computer> deviceSorter;

    @BeforeAll
    static void init() {
        computerDaoMock = createComputerDaoMock();
        deviceSorter = new DeviceSorter<Computer>();
        computerService = new ComputerServiceImpl(computerDaoMock, deviceSorter);
    }

    @Test
    void addDeviceShoulSaveDeviceToDatabaseWhenItNotExistYet() {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("test");
        deviceDto.setCountryOfManufacture("test");
        deviceDto.setCompany("test");
        deviceDto.setInstallment(true);
        deviceDto.setOnlineOrder(true);
        Computer computerFromDto = Computer.builder()                                
                                .withName(deviceDto.getName())
                                .withCountryOfManufacture(deviceDto.getCountryOfManufacture())
                                .withCompany(deviceDto.getCompany())
                                .withOnlineOrder(deviceDto.getOnlineOrder())
                                .withInstallment(deviceDto.getInstallment())
                                .build();
        computerService.addDevice(deviceDto);
        verify(computerDaoMock).save(computerFromDto);
    }
    
    @Test
    void addDeviceShouldThrowEntityAlreadyExistExceptionWhenInputDeviceExistInDatabase() {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("MacBook");
        assertThatThrownBy(() -> computerService.addDevice(deviceDto))
                .isInstanceOf(EntityAlreadyExistException.class);
    }
    
    @Test
    void findByNameShouldThrowEntityNotExistExceptionWhenDeviceNotExistInDatabase() {
        assertThatThrownBy(() -> computerService.findByName("notexistcomputer"))
            .isInstanceOf(EntityNotExistException.class).hasMessage("Device with name notexistcomputer not found!");
    }
    
    @Test
    void findByNameShouldReturnExpectedComputerWhenExist() {
        Computer expected = CreatorTestEntities.createComputers().get(0);
        assertThat(computerService.findByName("MacBook")).isEqualTo(expected);
    }
    
    @Test
    void findAllDevicesShouldReturnExpectedComputers() {
        List<Computer> expected = CreatorTestEntities.createComputers();
        assertThat(computerService.findAllDevices()).isEqualTo(expected);
    }
    
    @Test
    void findAllByColorShouldReturnExpectedComputers() {        
        List<Computer> expected = new ArrayList<>();
        Computer computer = CreatorTestEntities.createComputers().get(0);
        computer.removeModel(CreatorTestEntities.createComputerModels().get(0));
        expected.add(computer);
        computer = CreatorTestEntities.createComputers().get(1);
        computer.removeModel(CreatorTestEntities.createComputerModels().get(3));
        expected.add(computer);
        assertThat(computerService.findAllByColor("White")).isEqualTo(expected);
    }
    
    @Test
    void findAllByColorShouldReturnEmptyListWhenColorNotExist() {
        assertThat(computerService.findAllByColor("Green")).isEmpty();
    }
    
    @Test
    void findAllByColorShouldReturnAllComputersWhenColorNull() {
        List<Computer> expected = CreatorTestEntities.createComputers();
        assertThat(computerService.findAllByColor(null)).isEqualTo(expected);
    }
    
    @Test
    void findAllByColorShouldReturnAllComputersWhenColorEmpty() {
        List<Computer> expected = CreatorTestEntities.createComputers();
        assertThat(computerService.findAllByColor("")).isEqualTo(expected);
    }
    
    @Test
    void findAllByCostShouldReturnExpectedComputers() {        
        List<Computer> expected = new ArrayList<>();
        Computer computer = CreatorTestEntities.createComputers().get(0);
        computer.removeModel(CreatorTestEntities.createComputerModels().get(1));
        expected.add(computer);
        computer = CreatorTestEntities.createComputers().get(1);
        computer.removeModel(CreatorTestEntities.createComputerModels().get(2));
        expected.add(computer);
        assertThat(computerService.findAllByCost(25000, 50000)).isEqualTo(expected);
    }
    
    @Test
    void findAllByCostShouldReturnEmptyListWhenCostNotExists() {
        assertThat(computerService.findAllByCost(2000000, 0)).isEmpty();
    }
    
    @Test
    void findAllByCostShouldReturnEmptyListWhenTheirNot() {
        assertThat(computerService.findAllByCost(0, 10)).isEmpty();
    }
    
    @Test
    void findAllByAvailabilityShouldReturnExpectedComputers() {        
        List<Computer> expected = new ArrayList<>();
        Computer computer = CreatorTestEntities.createComputers().get(0);
        computer.removeModel(CreatorTestEntities.createComputerModels().get(1));
        expected.add(computer);
        assertThat(computerService.findAllByAvailability()).isEqualTo(expected);
    }
    
    @Test
    void findAllByCategoryShouldReturnExpectedComputers() {        
        List<Computer> expected = new ArrayList<>();
        Computer computer = CreatorTestEntities.createComputers().get(0);
        computer.removeModel(CreatorTestEntities.createComputerModels().get(1));
        expected.add(computer);
        computer = CreatorTestEntities.createComputers().get(1);
        computer.removeModel(CreatorTestEntities.createComputerModels().get(3));
        expected.add(computer);
        assertThat(computerService.findAllByCategory("first")).isEqualTo(expected);
    }
    
    @Test
    void findAllByCategoryShouldReturnAllComputersWhenInputNull() {        
        List<Computer> expected = CreatorTestEntities.createComputers();
        assertThat(computerService.findAllByCategory(null)).isEqualTo(expected);
    }
    
    @Test
    void findAllByCategoryShouldReturnAllComputersWhenInputEmpty() {        
        List<Computer> expected = CreatorTestEntities.createComputers();
        assertThat(computerService.findAllByCategory("")).isEqualTo(expected);
    }
    
    @Test
    void findAllByCategoryShouldReturnEmptyListWhenInputNotExist() {
        assertThat(computerService.findAllByCategory("notexist category")).isEmpty();
    }
    
    @Test
    void findAllByProcessorShouldReturnExpectedComputers() {        
        List<Computer> expected = new ArrayList<>();
        Computer computer = CreatorTestEntities.createComputers().get(0);
        computer.removeModel(CreatorTestEntities.createComputerModels().get(1));
        expected.add(computer);
        computer = CreatorTestEntities.createComputers().get(1);
        computer.removeModel(CreatorTestEntities.createComputerModels().get(3));
        expected.add(computer);
        assertThat(computerService.findAllByProcessor("atlon")).isEqualTo(expected);
    }
    
    @Test
    void findAllByProcessorShouldReturnAllComputersWhenInputNull() {        
        List<Computer> expected = CreatorTestEntities.createComputers();
        assertThat(computerService.findAllByProcessor(null)).isEqualTo(expected);
    }
    
    @Test
    void findAllByProcessorShouldReturnAllComputersWhenInputEmpty() {        
        List<Computer> expected = CreatorTestEntities.createComputers();
        assertThat(computerService.findAllByProcessor("")).isEqualTo(expected);
    }
    
    @Test
    void findAllByProcessorShouldReturnEmptyListWhenInputNotExist() {
        assertThat(computerService.findAllByProcessor("notexist processor")).isEmpty();
    }
    
    @Test
    void addModelToDeviceShoulSaveModelToDatabase() {
        ComputerDao computerDaoMock = createComputerDaoMock();
        deviceSorter = new DeviceSorter<Computer>();
        ComputerService computerService = new ComputerServiceImpl(computerDaoMock, deviceSorter);
        ComputerModelDto modelDto = ComputerModelDto.builder()
                    .withName("testModel")
                    .withLengthMm(100)
                    .withWidthMm(100)
                    .withHeightMm(100)
                    .build();
        ComputerModel computerModelFromDto = ComputerModel.builder()                                
                                .withName(modelDto.getName())
                                .withSize(CreatorTestEntities.createSize(100, 100, 100))
                                .build();
        Computer computer = CreatorTestEntities.createComputers().get(0);
        computer.addModel(computerModelFromDto);
        computerService.addModelForDevice("MacBook", modelDto);
        verify(computerDaoMock).save(computer);
    }
    
    private static ComputerDao createComputerDaoMock() {
        ComputerDao computerDaoMock = mock(ComputerDao.class);
        when(computerDaoMock.findAll()).thenReturn(CreatorTestEntities.createComputers());
        when(computerDaoMock.findByNameIgnoreCase("MacBook")).thenReturn(Optional.ofNullable(CreatorTestEntities.createComputers().get(0)));
        when(computerDaoMock.findByNameIgnoreCase("MacBook2")).thenReturn(Optional.ofNullable(CreatorTestEntities.createComputers().get(1)));
        return computerDaoMock;
    }
}
