package helpers;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.geometry.Point2D;
import types.TSPdata;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;

/**
 * Travelling salesman problem in the form of a distance matrix
 */
public class DistanceMatrix implements TSPdata {
    /**
     * matrix itself
     */
    public int[][] matrix;
    String matrixToString;

    /**
     * enum for creating random distance matrix
     */
    public enum types {
        SYMMETRIC,
        ASYMMETRIC;
    }

    /**
     * loading distance matrix from a xml file
     * 
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

            matrix = new int[verticies][verticies];
            for (int[] row : matrix) {
                // Arrays.fill(row, Double.MAX_VALUE);
                Arrays.fill(row, -1);
            }

            for (int row = 0; row < verticies; row++) {
                Node node = list.item(row);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList edgeList = node.getChildNodes();
                    int edgeCount = edgeList.getLength();
                    for (int index = 0; index < edgeCount; index++) {
                        Node checkNode = edgeList.item(index);
                        if (!checkNode.getNodeName().equals("#text")) {
                            matrix[row][Integer.parseInt(checkNode.getTextContent())] = (int) (Double
                                    .parseDouble(checkNode.getAttributes().item(0).getNodeValue()) + 0.5);
                        }
                    }
                }
            }
            for (int[] row : matrix) {
                matrixToString += Arrays.toString(row);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * generates a random distance matrix of a given type
     */
    public DistanceMatrix(int size, types type) {
        matrix = new int[size][size];
        Random rand = new Random();
        if (type == types.ASYMMETRIC) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    if (i != j) {
                        matrix[i][j] = rand.nextInt();
                    } else {
                        matrix[i][j] = -1;
                    }

                }
            }
        } else {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = i; j < matrix.length; j++) {
                    if (i != j) {
                        matrix[i][j] = rand.nextInt();
                        matrix[j][i] = matrix[i][j];
                    } else {
                        matrix[i][j] = -1;
                    }
                }
            }
        }

    }

    public DistanceMatrix(Euclidean euc) {
        matrix = new int[euc.xPoints.length][euc.yPoints.length];
        for (int i = 0; i < euc.xPoints.length; i++) {
            Point2D point = new Point2D(euc.xPoints[i], euc.yPoints[i]);
            for (int j = i; j < euc.xPoints.length; j++) {
                if (i != j) {
                    matrix[i][j] = (int) point.distance(euc.xPoints[j], euc.yPoints[j]);
                    matrix[j][i] = matrix[i][j];
                } else {
                    matrix[i][j] = -1;
                }
            }
        }
    }

    /**
     * saves the problem as a file using standard java serialization
     * the file should end with .dm extension
     * 
     * @param fileName
     */
    public void save(String fileName) {
        File newFile = new File(fileName);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(newFile));
            oos.writeObject(this);
            oos.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * loads the problem from a file with saved using standard java serialization
     * 
     * @param file
     * @return
     */
    public static DistanceMatrix load(File file) {
        try {
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
     * 
     * @param path ex 1, 6, 3, 5, 4
     * @return calculated cost
     */
    public long cost(int... path) {
        long sum = 0;
        for (int i = 0; i < path.length; i++) {
            sum += matrix[path[i]][path[(i + 1) % path.length]];
        }
        return sum;
    }

    public long cost(List<Integer> path) {
        long sum = 0;
        for (int i = 0; i < path.size(); i++) {
            sum += matrix[path.get(i)][path.get((i + 1) % path.size())];
        }
        return sum;
    }

    public List<Integer> kRandom(int k) {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            arr.add(i);
        }
        ArrayList<Integer> res = new ArrayList<>(arr);
        double cost = Double.MAX_VALUE;
        for (int i = 0; i < k; i++) {
            Collections.shuffle(arr);
            double newCost = cost(arr);
            if (newCost < cost) {
                cost = newCost;
                res = new ArrayList<>(arr);
            }
        }
        return res;
    }

    public List<Integer> kRandomThreaded(int k) {
        final List<List<Integer>> bests = new LinkedList<>();
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            arr.add(i);
        }
        Thread[] fredy = new Thread[k];
        for (int i = 0; i < k; i++) {
            fredy[i] = new Thread(() -> {
                List<Integer> foo = new ArrayList<>(arr);
                Collections.shuffle(foo);
                synchronized (bests) {
                    bests.add(foo);
                }
            });
        }
        for (Thread x : fredy) {
            x.start();
        }
        try {
            for (Thread x : fredy) {
                x.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Integer> res = new ArrayList<>();
        double cost = Double.MAX_VALUE;
        for (int i = 0; i < k; i++) {
            double newCost = cost(bests.get(i));
            if (newCost < cost) {
                cost = newCost;
                res = bests.get(i);
            }
        }
        return res;
    }

    public List<Integer> nearest() {
        final List<List<Integer>> bests = new LinkedList<>();
        Thread[] fredy = new Thread[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int finalI = i;
            fredy[i] = new Thread(() -> {
                List<Integer> foo = nearest(finalI);
                synchronized (bests) {
                    bests.add(foo);
                }
            });
        }
        for (Thread x : fredy) {
            x.start();
        }
        try {
            for (Thread x : fredy) {
                x.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Integer> res = new ArrayList<>();
        double cost = Double.MAX_VALUE;
        for (int i = 0; i < matrix.length; i++) {
            double newCost = cost(bests.get(i));
            if (newCost < cost) {
                cost = newCost;
                res = bests.get(i);
            }
        }
        return res;
    }

    public List<Integer> nearest(int start) {
        List<Integer> result = new ArrayList<>();
        result.add(start);
        for (int i = 0; i < matrix.length - 1; i++) {
            long cost = Long.MAX_VALUE;
            int r = -1;
            for (int j = 0; j < matrix.length; j++) {
                if (result.contains(j))
                    continue;
                if (cost > matrix[result.get(i)][j]) {
                    cost = matrix[result.get(i)][j];
                    r = j;
                }
            }
            result.add(r);
        }
        return result;
    }

    public List<Integer> twoOptAcc(List<Integer> start) {
        for (int n = 0; n < matrix.length; n++) {
            for (int m = n + 1; m < matrix.length; m++) {
                int mm = (m + 1) % matrix.length;
                if (n == mm)
                    continue;
                int nn = ((n - 1) + matrix.length) % matrix.length;
                boolean skip = matrix[start.get(nn)][start.get(n)]
                        + matrix[start.get(m)][start.get(mm)] > matrix[start.get(nn)][start.get(m)]
                                + matrix[start.get(n)][start.get(mm)];
                if (skip) {
                    reversePart(start, n, m);
                    n = -1;
                    break;
                }
            }
        }
        return start;
    }

    public List<Integer> twoOpt(List<Integer> start) {
        for (int n = 0; n < matrix.length; n++) {
            for (int m = n + 1; m < matrix.length; m++) {
                List<Integer> candidate = new ArrayList<>(start);
                reversePart(candidate, n, m);
                if (cost(start) > cost(candidate)) {
                    start = candidate;
                    n = -1;
                    break;
                }
            }
        }
        return start;
    }

    public List<Integer> threeOpt(List<Integer> start) {
        List<Integer> tour = new ArrayList<>(start);
        for (int i = 1; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix.length; j++) {
                for (int k = j + 1; k < matrix.length; k++) {
                    List<Integer> candidate = new ArrayList<>(tour);
                    reversePart(candidate, i, k);
                    reversePart(candidate, j, k);
                    if (cost(candidate) < cost(tour)) {
                        tour = candidate;
                        i = 1;
                        j = 2;
                        k = 2;
                    }
                }
            }
        }
        return tour;
    }

    public List<Integer> threeOptAcc(List<Integer> start) {
        while (true) {
            int delta = 0;
            for (int i = 1; i <= start.size(); i++) {
                for (int j = i + 1; j <= start.size(); j++) {
                    for (int k = j + 1; k <= start.size(); k++) {
                        delta += swapThree(start, i, j, k);
                    }
                }
            }
            System.out.println(delta);
            if (delta >= 0) {
                // start = twoOptAcc(start);
                break;
            }
        }
        return start;
    }

    private int swapThree(List<Integer> newTour, int i, int j, int k) {
        int d0 = matrix[newTour.get(i - 1)][newTour.get(i)] + matrix[newTour.get(j - 1)][newTour.get(j)]
                + matrix[newTour.get(k - 1)][newTour.get(k % newTour.size())];

        int d1 = matrix[newTour.get(i - 1)][newTour.get(j - 1)] + matrix[newTour.get(i)][newTour.get(j)]
                + matrix[newTour.get(k - 1)][newTour.get(k % newTour.size())];

        int d2 = matrix[newTour.get(i - 1)][newTour.get(i)] + matrix[newTour.get(j - 1)][newTour.get(k - 1)]
                + matrix[newTour.get(j)][newTour.get(k % newTour.size())];

        int d3 = matrix[newTour.get(i - 1)][newTour.get(j)] + matrix[newTour.get(k - 1)][newTour.get(i)]
                + matrix[newTour.get(j - 1)][newTour.get(k % newTour.size())];

        int d4 = matrix[newTour.get(k % newTour.size())][newTour.get(i)] + matrix[newTour.get(j - 1)][newTour.get(j)]
                + matrix[newTour.get(k - 1)][newTour.get(i - 1)];

        if (d0 > d1) {
            Collections.reverse(newTour.subList(i, j));
            return -d0 + d1;
        }
        if (d0 > d2) {
            Collections.reverse(newTour.subList(j, k));
            return -d0 + d2;
        }
        if (d0 > d4) {
            Collections.reverse(newTour.subList(i, k));
            return -d0 + d4;
        }
        if (d0 > d3) {
            List<Integer> tmp = new ArrayList<>();
            tmp.addAll(newTour.subList(j, k));
            tmp.addAll(newTour.subList(i, j));
            for (int l = newTour.indexOf(newTour.get(i)); l < newTour.indexOf(newTour.get(k)); l++) {
                newTour.set(l, tmp.get(l - newTour.indexOf(newTour.get(i))));
            }

            return -d0 + d3;
        }

        return 0;
    }

    private void reversePart(List<Integer> list, int n, int k) {
        while (k > n) {
            Collections.swap(list, n, k);
            n++;
            k--;
        }
    }

    public boolean isSymmetric() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] != matrix[j][i])
                    return false;
            }
        }
        return true;
    }

    public String getOutput() {
        return this.matrixToString;
    }

}
