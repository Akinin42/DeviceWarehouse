package org.warehouse.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DeviceDto {
    
    private Integer id;
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String countryOfManufacture;
    
    @NotBlank
    private String company;    
    
    @NotNull
    private Boolean onlineOrder;
    
    @NotNull
    private Boolean installment;
}
