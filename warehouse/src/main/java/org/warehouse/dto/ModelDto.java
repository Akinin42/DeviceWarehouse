package org.warehouse.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String serialNumber;
    
    @NotBlank
    private String colour;
    
    @Min(1)
    private Integer lengthMm;
    
    @Min(1)
    private Integer widthMm;
    
    @Min(1)
    private Integer heightMm;
    
    @Min(1)
    private Integer cost;
    
    @NotNull
    private Boolean availability;
}
