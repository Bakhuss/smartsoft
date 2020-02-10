package ru.bakhuss.smartsoft.web.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyListFromCBRView {
    @XmlAttribute(name = "name")
    String name;

    @XmlAttribute(name = "Date")
    String date;

    @XmlElement(name = "Valute")
    List<CurrencyFromCBRView> views;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "{name:" + getName() +
                ";date:" + getDate() +
                ";size:" + (getViews() == null ? 0 : getViews().size()) +
                "}";
    }
}