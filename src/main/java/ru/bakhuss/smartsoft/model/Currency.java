package ru.bakhuss.smartsoft.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    Long id;
    @Version
    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    Integer version;
    Integer numCode;
    String charCode;
    String name;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "{id:" + getId() +
                ";numCode:" + getNumCode() +
                ";charCode:" + getCharCode() +
                ";name:" + getName() +
                "}";
    }
}