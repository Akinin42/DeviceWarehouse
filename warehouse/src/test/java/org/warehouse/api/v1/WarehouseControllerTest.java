package org.warehouse.api.v1;

import static org.hamcrest.CoreMatchers.is;
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
import org.warehouse.entity.device.Device;
import org.warehouse.service.WarehouseService;
import org.warehouse.util.CreatorTestEntities;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class WarehouseControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Mock
    private WarehouseService warehouseServiceMock;

    private WarehouseController warehouseController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        mapper = new ObjectMapper();
        warehouseController = new WarehouseController(warehouseServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(warehouseController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
    }

    @Test
    void testFindAllDevices() throws Exception {
        List<Device> devices = new ArrayList<>(CreatorTestEntities.createComputers());
        when(warehouseServiceMock.findAllDevices()).thenReturn(devices);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/warehouse")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacBook")))
                .andExpect(jsonPath("$[1].name", is("MacBook2")))
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }
    
    @Test
    void testFindAllDevicesOrderedByName() throws Exception {
        List<Device> devices = new ArrayList<>(CreatorTestEntities.createComputers());
        when(warehouseServiceMock.findAllDevicesOrderedByName()).thenReturn(devices);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/warehouse/nameorder")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].name", is("MacBook")))
        .andExpect(jsonPath("$[1].name", is("MacBook2")))
        .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }
    
    @Test
    void testFindAllDevicesOrderedByCost() throws Exception {
        List<Device> devices = new ArrayList<>(CreatorTestEntities.createComputers());
        when(warehouseServiceMock.findAllDevicesOrderedByCost()).thenReturn(devices);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/warehouse/costorder")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].name", is("MacBook")))
        .andExpect(jsonPath("$[1].name", is("MacBook2")))
        .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }
}
