package btgk2;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainClass {
    private static Document doc;

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            XmlReader xmlReader = new XmlReader();
            List<Student> students = xmlReader.readStudentsFromFile("C:\\Users\\ASUS\\IdeaProjects\\ktgk\\src\\bt\\Students.xml");

            List<Thread> threads = new ArrayList<>();

            for (Student student : students) {
                Thread t = new Thread(() -> {
                    String encryptedAge = AgeCalculator.calculateAndEncryptAge(student.getDateOfBirth());
                    int sumOfBirthDate = PrimeChecker.sumOfDigits(Integer.parseInt(student.getDateOfBirth().replace("-", "")));
                    boolean isSumPrime = PrimeChecker.isPrime(sumOfBirthDate);

                    writeResultToXml(student, encryptedAge, isSumPrime);
                });
                threads.add(t);
                t.start();
            }

            for (Thread t : threads) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static synchronized void writeResultToXml(Student student, String encryptedAge, boolean isPrime) {
        try {


            Element rootElement = doc.getDocumentElement();
            if (rootElement == null) {
                rootElement = doc.createElement("students");
                doc.appendChild(rootElement);
            }

            Element studentElement = doc.createElement("student");

            Element nameElement = doc.createElement("name");
            nameElement.appendChild(doc.createTextNode(student.getName()));
            studentElement.appendChild(nameElement);

            Element ageElement = doc.createElement("age");
            ageElement.appendChild(doc.createTextNode(encryptedAge));
            studentElement.appendChild(ageElement);

            Element isPrimeElement = doc.createElement("isPrime");
            isPrimeElement.appendChild(doc.createTextNode(String.valueOf(isPrime)));
            studentElement.appendChild(isPrimeElement);

            rootElement.appendChild(studentElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("kq.xml"));
            transformer.transform(source, result);


            System.out.println("Student Name: " + student.getName());
            System.out.println("Encrypted Age: " + encryptedAge);
            System.out.println("Is Sum of Birth Date Prime: " + isPrime);
            System.out.println("------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}