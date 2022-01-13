package org.warehouse.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@EqualsAndHashCode
public class ModelDto {
    
    private Integer id;
    private String name;
    private String serialNumber;
    private String colour;
    private Integer lengthMm;
    private Integer widthMm;
    private Integer heightMm;
    private Integer cost;
    private Boolean availability;
}
