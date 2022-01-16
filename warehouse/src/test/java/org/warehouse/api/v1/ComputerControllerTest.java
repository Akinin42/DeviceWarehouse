package org.warehouse.api.v1;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.warehouse.GlobalExceptionHandler;
import org.warehouse.dto.ComputerModelDto;
import org.warehouse.dto.DeviceDto;
import org.warehouse.entity.device.Computer;
import org.warehouse.exception.EntityAlreadyExistException;
import org.warehouse.exception.EntityNotExistException;
import org.warehouse.service.ComputerService;
import org.warehouse.util.CreatorTestEntities;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class ComputerControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Mock
    private ComputerService computerServiceMock;

    private ComputerController computerController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        mapper = new ObjectMapper();
        computerController = new ComputerController(computerServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(computerController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
    }

    @Test
    void testFindAllComputers() throws Exception {
        List<Computer> computers = CreatorTestEntities.createComputers();
        when(computerServiceMock.findAllDevices()).thenReturn(computers);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/computers")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacBook")))
                .andExpect(jsonPath("$[1].name", is("MacBook2")))
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void testFindComputerByName() throws Exception {
        Computer computer = CreatorTestEntities.createComputers().get(0);
        when(computerServiceMock.findByName("MacBook")).thenReturn(computer);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/computers/MacBook")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("MacBook")));
    }

    @Test
    void testFindComputerByNameWhenEntityNotExist() throws Exception {
        doThrow(new EntityNotExistException("Device with name unexisteddevice not found!")).when(computerServiceMock)
                .findByName("unexisteddevice");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/computers/unexisteddevice")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isNotFound());
    }

    @Test
    void testFindAllComputerByColor() throws Exception {
        List<Computer> computers = new ArrayList<>();
        Computer computer = CreatorTestEntities.createComputers().get(0);
        computer.removeModel(CreatorTestEntities.createComputerModels().get(0));
        computers.add(computer);
        when(computerServiceMock.findAllByColor("White")).thenReturn(computers);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/computers/sortcolor/White")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacBook")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllComputersByCost() throws Exception {
        List<Computer> computers = new ArrayList<>();
        Computer computer = CreatorTestEntities.createComputers().get(0);
        computer.removeModel(CreatorTestEntities.createComputerModels().get(0));
        computers.add(computer);
        when(computerServiceMock.findAllByCost(0, 18000)).thenReturn(computers);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/computers/sortcost/?minCost=0&maxCost=18000")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacBook")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllComputersByAvailability() throws Exception {
        List<Computer> computers = new ArrayList<>();
        Computer computer = CreatorTestEntities.createComputers().get(0);
        computer.removeModel(CreatorTestEntities.createComputerModels().get(0));
        computers.add(computer);
        when(computerServiceMock.findAllByAvailability()).thenReturn(computers);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/computers/availability")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacBook")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllComputersByCategory() throws Exception {
        List<Computer> computers = new ArrayList<>();
        Computer computer = CreatorTestEntities.createComputers().get(0);
        computer.removeModel(CreatorTestEntities.createComputerModels().get(0));
        computers.add(computer);
        when(computerServiceMock.findAllByCategory("second")).thenReturn(computers);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/computers/sortcategory/second")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacBook")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllComputersByProcessor() throws Exception {
        List<Computer> computers = new ArrayList<>();
        Computer computer = CreatorTestEntities.createComputers().get(0);
        computer.removeModel(CreatorTestEntities.createComputerModels().get(0));
        computers.add(computer);
        when(computerServiceMock.findAllByProcessor("iCore")).thenReturn(computers);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/computers/sortprocessor/iCore")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacBook")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testAddComputer() throws Exception {
        DeviceDto device = new DeviceDto();
        device.setName("test");
        device.setCountryOfManufacture("test");
        device.setCompany("test");
        device.setInstallment(true);
        device.setOnlineOrder(true);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/computers")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(device));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isCreated());
        verify(computerServiceMock).addDevice(device);
    }

    @Test
    void testAddComputerWhenComputerWithNameExist() throws Exception {
        DeviceDto device = new DeviceDto();
        device.setName("test");
        device.setCountryOfManufacture("test");
        device.setCompany("test");
        device.setInstallment(true);
        device.setOnlineOrder(true);
        doThrow(new EntityAlreadyExistException("Device witn name test already exists")).when(computerServiceMock)
                .addDevice(device);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/computers")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(device));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isBadRequest());
    }

    @Test
    void testAddComputerWhenComputerWithInvalidData() throws Exception {
        DeviceDto device = new DeviceDto();
        device.setName("test");
        device.setCountryOfManufacture("  ");
        device.setCompany("test");
        device.setInstallment(true);
        device.setOnlineOrder(true);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/computers")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(device));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isBadRequest());
    }

    @Test
    void testAddComputerModelForDevice() throws Exception {
        ComputerModelDto computerDto = ComputerModelDto.builder()
                .withName("test")
                .withSerialNumber("test")
                .withColour("test")
                .withLengthMm(1)
                .withWidthMm(1)
                .withHeightMm(1)
                .withCost(10)
                .withAvailability(true)
                .withCategory("test")
                .withProcessor("test")
                .build();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/computers/models/MacBook")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(computerDto));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isCreated());
        verify(computerServiceMock).addModelForDevice("MacBook", computerDto);
    }

    @Test
    void testAddComputerModelForDeviceWhenInvalidInputData() throws Exception {
        ComputerModelDto computerDto = ComputerModelDto.builder()
                .withName("test")
                .withSerialNumber("  ")
                .withColour("test")
                .withLengthMm(1)
                .withWidthMm(1)
                .withHeightMm(1)
                .withCost(10)
                .withAvailability(true)
                .withCategory("test")
                .withProcessor("test")
                .build();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/computers/models/MacBook")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(computerDto));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isBadRequest());
    }
}
