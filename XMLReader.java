package btgk2;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlReader {
    public List<Student> readStudentsFromFile(String filePath) {
        List<Student> students = new ArrayList<>();
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("student");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    int id = Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent());
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    String address = eElement.getElementsByTagName("address").item(0).getTextContent();
                    String dob = eElement.getElementsByTagName("dateOfBirth").item(0).getTextContent();

                    students.add(new Student(id, name, address, dob));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
}