package helpers;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import types.TSPdata;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class DistanceMatrix implements TSPdata {
    double[][] matrix;
    String matrixToString;

    public DistanceMatrix(File file) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("vertex");
            int verticies = list.getLength();

            matrix = new double[verticies][verticies];
            for (double[] row : matrix) {
                // Arrays.fill(row, Double.MAX_VALUE);
                Arrays.fill(row, -1.0);
            }

            for (int row = 0; row < verticies; row++) {
                Node node = list.item(row);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList edgeList = node.getChildNodes();
                    int edgeCount = edgeList.getLength();
                    for (int index = 0; index < edgeCount; index++) {
                        Node checkNode = edgeList.item(index);
                        if (!checkNode.getNodeName().equals("#text")) {
                            matrix[row][Integer.parseInt(checkNode.getTextContent())] = Double
                                    .parseDouble(checkNode.getAttributes().item(0).getNodeValue());
                        }
                    }
                }
            }
            for (double[] row : matrix) {
                matrixToString += Arrays.toString(row);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public String getOutput() {
        return this.matrixToString;
    }

}
