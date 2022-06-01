package Genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Specimen implements Cloneable{
    List<Integer> result;
    long cost;
    int age;

    public Specimen(){
        age = 0;
    }

    //getters and setters

    public List<Integer> getResult() {
        return result;
    }

    public void setResult(List<Integer> result) {
        this.result = result;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void updateAge(){
        age++;
    }


    public static Specimen getRandomSpecimen(int[][] costMatrix){
        Specimen specimen = new Specimen();
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < costMatrix.length; i++){
            result.add(i);
        }
        Random r = new Random();
        for (int in = costMatrix.length - 1; in > 0; in--) {
            int jn = r.nextInt(in);
            Collections.swap(result, in, jn);
        }
        specimen.setResult(result);
        specimen.setCost(cost(result, costMatrix));
        return specimen;
    }

    public static long cost(List<Integer> path, int[][] matrix) {
        long sum = 0;
        for (int i = 0; i < path.size(); i++) {
            sum += matrix[path.get(i)][path.get((i + 1) % path.size())];
        }
        return sum;
    }

    public String toString(){
        return "Specimen: " + result + "\ncost: " + cost + " age: " + age;
    }

    @Override
    public Object clone(){
        Specimen ns = new Specimen();
        ns.setResult(new ArrayList<>(result));
        ns.setCost(cost);
        return ns;
    }
}
