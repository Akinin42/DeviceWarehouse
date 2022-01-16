package org.warehouse.entity.device;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.warehouse.entity.devicemodel.Model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SuperBuilder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@EqualsAndHashCode
public abstract class Device {

    /*
     * generator added for test if you use data.sql
     */
    @Id
    @Column(name = "device_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generatorForTests")
    @SequenceGenerator(name = "generatorForTests", initialValue = 100)
    private Integer id;

    private String name;
    private String countryOfManufacture;
    private String company;
    private Boolean onlineOrder;
    private Boolean installment;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "models_to_devices", joinColumns = @JoinColumn(name = "device_id"), inverseJoinColumns = @JoinColumn(name = "model_id"))
    private List<Model> models;

    public void addModel(Model model) {
        this.models.add(model);
    }

    public void removeModel(Model model) {
        this.models.remove(model);
    }
}
