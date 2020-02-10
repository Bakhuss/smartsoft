package ru.bakhuss.smartsoft.web.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Valute")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyFromCBRView {
    @XmlAttribute(name = "ID")
    String id;
    String NumCode;
    String CharCode;
    String Nominal;
    String Name;
    String Value;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "{id:" + getId() +
                ";NumCode:" + getNumCode() +
                ";CharCode:" + getCharCode() +
                ";Nominal:" + getNominal() +
                ";Name:" + getName() +
                ";Value:" + getValue() +
                "}";
    }
}