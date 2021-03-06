package org.warehouse.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sizes")
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@EqualsAndHashCode
public class Size {
    
    /*
     * generator added for test if you use data.sql
     */
    @Id
    @Column(name = "size_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generatorForTests")
    @SequenceGenerator(name="generatorForTests", initialValue = 100)
    private Integer id;
    
    private Integer lengthMm;
    
    private Integer widthMm;
    
    private Integer heightMm;
}
