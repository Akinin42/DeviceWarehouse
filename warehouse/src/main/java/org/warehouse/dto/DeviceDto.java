package org.warehouse.dto;

import lombok.Data;

@Data
public class DeviceDto {
    
    private Integer id;
    private String name;
    private String countryOfManufacture;
    private String company;
    private Boolean onlineOrder;
    private Boolean installment;
}
