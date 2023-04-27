package model.adapter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

public class Json1 {
    String path = "C:\\Users\\Anamaria\\Desktop\\an3\\abc.json";
    List<String> users;

    public Json1(String p, List<String> l){
        this.path=p;
        this.users = l;
    }

    void convertToXML() throws IOException, ParserConfigurationException, TransformerException {
        System.out.println(path);
        File f = new File(path);
        if (f.exists()){
            InputStream is = new FileInputStream(path);
            String jsonTxt = IOUtils.toString(is, "UTF-8");

            JSONObject json = new JSONObject(jsonTxt);
            JSONArray a = json.getJSONArray("UTILIZATOR");
            String str = XML.toString(json);
            System.out.println(str);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = null;
            try {
                doc = builder.parse(new InputSource(new StringReader(str)));
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result =  new StreamResult(new File(path.substring(0, path.length()-4)+"xml"));
            transformer.transform(source, result);
        }
    }

    void convertToCSV(){
        JSONObject jsonObject;
        try {
            InputStream is = new FileInputStream(path);
            String jsonTxt = IOUtils.toString(is, "UTF-8");

            JSONObject json = new JSONObject(jsonTxt);
            JSONArray a = json.getJSONArray("UTILIZATOR");
            File file = new File(path.substring(0, path.length()-4) + "csv");
            String csvString = CDL.toString(a);
            FileUtils.writeStringToFile(file, csvString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}