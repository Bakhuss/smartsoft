package ru.bakhuss.smartsoft.service.xmlservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.bakhuss.smartsoft.web.view.CurrencyListFromCBRView;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class XmlParser {
    private final Logger log = LoggerFactory.getLogger(XmlParser.class);

    public CurrencyListFromCBRView parseXmlFromCBRToView() {
        CurrencyListFromCBRView currList = null;
        String url = "http://www.cbr.ru/scripts/XML_daily.asp";
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(CurrencyListFromCBRView.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            URL urlCbr = new URL(url);
            currList = (CurrencyListFromCBRView) unmarshaller.unmarshal(urlCbr);
        } catch (JAXBException e) {
            log.error("faild to parse xml");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            log.error("faild to connect to " + url);
            e.printStackTrace();
        }
        return currList;
    }
}