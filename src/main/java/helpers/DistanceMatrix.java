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
import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class DistanceMatrix implements TSPdata {
    public double[][] matrix;
    String matrixToString;

    public enum types{
        SYMMETRIC,
        ASYMMETRIC;
    }

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
    public DistanceMatrix(int size, types type){
        matrix = new double[size][size];
        Random rand = new Random();
        if(type == types.ASYMMETRIC){
            for(int i = 0; i< matrix.length; i++){
                for(int j = 0; j< matrix.length; j++){
                    if(i!=j){
                        matrix[i][j] = rand.nextDouble();
                    }else{
                        matrix[i][j] = -1;
                    }

                }
            }
        }else{
            for(int i = 0; i< matrix.length; i++){
                for(int j = i; j< matrix.length; j++){
                    if(i!=j){
                        matrix[i][j] = rand.nextDouble();
                        matrix[j][i] = matrix[i][j];
                    }else {
                        matrix[i][j] = -1;
                    }
                }
            }
        }
        for(double[] row : matrix){
            for(double x: row){
                System.out.print(x + ";");
            }
            System.out.println();
        }
    }

    public void save(String fileName){
        File newFile = new File(fileName);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(newFile));
            oos.writeObject(this);
            oos.close();
        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static DistanceMatrix load(String fileName){
        File file = new File(fileName);
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            DistanceMatrix dm = (DistanceMatrix) ois.readObject();
            ois.close();
            return dm;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getOutput() {
        return this.matrixToString;
    }

}
