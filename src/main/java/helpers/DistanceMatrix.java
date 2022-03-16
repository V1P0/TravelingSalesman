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

/**
 * Travelling salesman problem in the form of a distance matrix
 */
public class DistanceMatrix implements TSPdata {
    /**
     * matrix itself
     */
    public double[][] matrix;
    String matrixToString;

    /**
     * enum for creating random distance matrix
     */
    public enum types{
        SYMMETRIC,
        ASYMMETRIC;
    }

    /**
     * loading distance matrix from a xml file
     * @param file xml file
     */
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

    /**
     * generates a random distance matrix of a given type
     */
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

    /**
     * saves the problem as a file using standard java serialization
     * the file should end with .dm extension
     * @param fileName
     */
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

    /**
     * loads the problem from a file with saved using standard java serialization
     * @param file
     * @return
     */
    public static DistanceMatrix load(File file){
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

    /**
     * calculates the cost to travel over given path
     * @param path ex 1, 6, 3, 5, 4
     * @return calculated cost
     */
    public double cost(int... path){
        double sum = 0;
        for(int i = 0; i< path.length; i++){
            sum+=matrix[path[i]][path[(i+1)%path.length]];
        }
        return sum;
    }

    public String getOutput() {
        return this.matrixToString;
    }

}
