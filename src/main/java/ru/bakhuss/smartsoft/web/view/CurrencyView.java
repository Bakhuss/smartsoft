package ru.bakhuss.smartsoft.web.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(value = NON_NULL)
public class CurrencyView {
    String id;
    String numCode;
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
