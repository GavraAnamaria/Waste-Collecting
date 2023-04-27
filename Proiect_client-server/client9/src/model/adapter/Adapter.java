package model.adapter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Adapter  implements IDataAdapter {
    private Json1 json;

    public Adapter(Json1 json){
        this.json = json;
    }

    public void convert()  {
        try {
            json.convertToXML();
        } catch (IOException | ParserConfigurationException | TransformerException e) {
            throw new RuntimeException(e);
        }
        json.convertToCSV();
    }
}
