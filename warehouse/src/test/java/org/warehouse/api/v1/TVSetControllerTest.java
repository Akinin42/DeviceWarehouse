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
import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.TVSetModelDto;
import org.warehouse.entity.device.TVSet;
import org.warehouse.exception.EntityAlreadyExistException;
import org.warehouse.exception.EntityNotExistException;
import org.warehouse.service.TVSetService;
import org.warehouse.util.CreatorTestEntities;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class TVSetControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Mock
    private TVSetService tvSetServiceMock;

    private TVSetController tvSetController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        mapper = new ObjectMapper();
        tvSetController = new TVSetController(tvSetServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(tvSetController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
    }

    @Test
    void testFindAllTVSets() throws Exception {
        List<TVSet> tvSets = CreatorTestEntities.createTVSets();
        when(tvSetServiceMock.findAllDevices()).thenReturn(tvSets);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/tvsets")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacTV")))
                .andExpect(jsonPath("$[1].name", is("MacTV2")))
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void testFindTVSetByName() throws Exception {
        TVSet tvSet = CreatorTestEntities.createTVSets().get(0);
        when(tvSetServiceMock.findByName("MacTV")).thenReturn(tvSet);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/tvsets/MacTV")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("MacTV")));
    }

    @Test
    void testFindTVSetByNameWhenEntityNotExist() throws Exception {
        doThrow(new EntityNotExistException("Device with name unexisteddevice not found!")).when(tvSetServiceMock)
                .findByName("unexisteddevice");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/tvsets/unexisteddevice")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isNotFound());
    }

    @Test
    void testFindAllTVSetsByColor() throws Exception {
        List<TVSet> tvSets = new ArrayList<>();
        TVSet tvSet = CreatorTestEntities.createTVSets().get(0);
        tvSet.removeModel(CreatorTestEntities.createTVSetModels().get(0));
        tvSets.add(tvSet);
        when(tvSetServiceMock.findAllByColor("White")).thenReturn(tvSets);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/tvsets/sortcolor/White")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacTV")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllTVSetsByCost() throws Exception {
        List<TVSet> tvSets = new ArrayList<>();
        TVSet tvSet = CreatorTestEntities.createTVSets().get(0);
        tvSet.removeModel(CreatorTestEntities.createTVSetModels().get(0));
        tvSets.add(tvSet);
        when(tvSetServiceMock.findAllByCost(0, 18000)).thenReturn(tvSets);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/tvsets/sortcost/?minCost=0&maxCost=18000")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacTV")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllTVSetsByAvailability() throws Exception {
        List<TVSet> tvSets = new ArrayList<>();
        TVSet tvSet = CreatorTestEntities.createTVSets().get(0);
        tvSet.removeModel(CreatorTestEntities.createTVSetModels().get(0));
        tvSets.add(tvSet);
        when(tvSetServiceMock.findAllByAvailability()).thenReturn(tvSets);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/tvsets/availability")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacTV")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllTVSetsByCategory() throws Exception {
        List<TVSet> tvSets = new ArrayList<>();
        TVSet tvSet = CreatorTestEntities.createTVSets().get(0);
        tvSet.removeModel(CreatorTestEntities.createTVSetModels().get(0));
        tvSets.add(tvSet);
        when(tvSetServiceMock.findAllByCategory("second")).thenReturn(tvSets);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/tvsets/sortcategory/second")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacTV")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllTVSetsByTechnology() throws Exception {
        List<TVSet> tvSets = new ArrayList<>();
        TVSet tvSet = CreatorTestEntities.createTVSets().get(0);
        tvSet.removeModel(CreatorTestEntities.createTVSetModels().get(0));
        tvSets.add(tvSet);
        when(tvSetServiceMock.findAllByTechnology("TFT")).thenReturn(tvSets);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/tvsets/sorttechnology/TFT")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacTV")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testAddTVSet() throws Exception {
        DeviceDto device = new DeviceDto();
        device.setName("test");
        device.setCountryOfManufacture("test");
        device.setCompany("test");
        device.setInstallment(true);
        device.setOnlineOrder(true);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/tvsets")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(device));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isCreated());
        verify(tvSetServiceMock).addDevice(device);
    }

    @Test
    void testAddTVSetWhenNameExist() throws Exception {
        DeviceDto device = new DeviceDto();
        device.setName("test");
        device.setCountryOfManufacture("test");
        device.setCompany("test");
        device.setInstallment(true);
        device.setOnlineOrder(true);
        doThrow(new EntityAlreadyExistException("Device witn name test already exists")).when(tvSetServiceMock)
                .addDevice(device);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/tvsets")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(device));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isBadRequest());
    }

    @Test
    void testAddTVSetWhenInvalidData() throws Exception {
        DeviceDto device = new DeviceDto();
        device.setName("test");
        device.setCountryOfManufacture("test");
        device.setCompany("   ");
        device.setInstallment(true);
        device.setOnlineOrder(true);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/tvsets")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(device));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isBadRequest());
    }

    @Test
    void testAddTVSetModelForDevice() throws Exception {
        TVSetModelDto tvSetDto = TVSetModelDto.builder()
                .withName("test")
                .withSerialNumber("test")
                .withColour("test")
                .withLengthMm(1)
                .withWidthMm(1)
                .withHeightMm(1)
                .withCost(10)
                .withAvailability(true)
                .withCategory("test")
                .withTechnology("test")
                .build();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/tvsets/models/MacTV")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(tvSetDto));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isCreated());
        verify(tvSetServiceMock).addModelForDevice("MacTV", tvSetDto);
    }

    @Test
    void testAddTVSetModelForDeviceWhenInvalidInputData() throws Exception {
        TVSetModelDto tvSetDto = TVSetModelDto.builder()
                .withName("test")
                .withSerialNumber("test")
                .withColour("  ")
                .withLengthMm(1)
                .withWidthMm(1)
                .withHeightMm(1)
                .withCost(10)
                .withAvailability(true)
                .withCategory("test")
                .withTechnology("test")
                .build();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/tvsets/models/MacTV")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(tvSetDto));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isBadRequest());
    }
}
