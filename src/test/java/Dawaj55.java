import Genetic.Crossovers.*;
import Genetic.Killers.*;
import Genetic.Mutators.*;
import Genetic.Population;
import helpers.DistanceMatrix;
import helpers.TSPLoader;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Dawaj55 {
    @Test
    public void fajnyTest() throws Exception {
        DistanceMatrix att = new DistanceMatrix(TSPLoader.returnScanner(new File("data/att532.tsp")));
        DistanceMatrix berlin = new DistanceMatrix(TSPLoader.returnScanner(new File("data/berlin52.tsp")));
        DistanceMatrix bier = new DistanceMatrix(TSPLoader.returnScanner(new File("data/bier127.tsp")));
        DistanceMatrix ch = new DistanceMatrix(TSPLoader.returnScanner(new File("data/ch130.tsp")));
        DistanceMatrix a = new DistanceMatrix(TSPLoader.returnScanner(new File("data/a280.tsp")));
        DistanceMatrix r = new DistanceMatrix(300, DistanceMatrix.types.SYMMETRIC);
        PrintWriter pwatt = new PrintWriter("att.csv");
        PrintWriter pwberlin = new PrintWriter("berlin.csv");
        PrintWriter pwbier = new PrintWriter("bier.csv");
        PrintWriter pwch = new PrintWriter("ch.csv");
        PrintWriter pwa = new PrintWriter("a.csv");
        PrintWriter pwr = new PrintWriter("r.csv");
        Crossover[] crossovers = new Crossover[] { new PMXCrossover(), new OXCrossover(), new CXCrossover(),
                new MOCCrossover() };
        Mutator[] mutators = new Mutator[] { new RandomSwapMutator(), new BestReverseMutator(), new TwoOptMutator(),
                new BagMutator(new RandomSwapMutator(), new BestReverseMutator(), new TwoOptMutator()) };
        Killer[] killers = new Killer[] { new NaturalKiller(), new PureCostStagnationKiller(), new RouletteKiller(),
                new TournamentKiller() };
        double[] probabilities = new double[] { 0.3, 0.4, 0.5 };
        int[] sizes = new int[] { 30, 50, 70 };
        long time = 20000;
        Thread t1 = new Thread(() -> {
            DistanceMatrix dm = att;
            PrintWriter pw = pwatt;
            for (Crossover c : crossovers) {
                for (Mutator m : mutators) {
                    for (Killer k : killers) {
                        for (double p : probabilities) {
                            for (int si : sizes) {
                                Population pop = Population.getRandomPopulation(si, dm.matrix);
                                long cost = dm.cost(dm.genetic(pop,
                                        m,
                                        c,
                                        k,
                                        p,
                                        time));
                                pw.println(c.getClass().getSimpleName() + "," + m.getClass().getSimpleName() + ","
                                        + k.getClass().getSimpleName() + "," + p + "," + si + "," + cost);
                                System.out.println(c.getClass().getSimpleName() + "," + m.getClass().getSimpleName()
                                        + "," + k.getClass().getSimpleName() + "," + p + "," + si + "," + cost);
                            }
                        }
                    }
                }
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            DistanceMatrix dm = berlin;
            PrintWriter pw = pwberlin;
            for (Crossover c : crossovers) {
                for (Mutator m : mutators) {
                    for (Killer k : killers) {
                        for (double p : probabilities) {
                            for (int si : sizes) {
                                Population pop = Population.getRandomPopulation(si, dm.matrix);
                                long cost = dm.cost(dm.genetic(pop,
                                        m,
                                        c,
                                        k,
                                        p,
                                        time));
                                pw.println(c.getClass().getSimpleName() + "," + m.getClass().getSimpleName() + ","
                                        + k.getClass().getSimpleName() + "," + p + "," + si + "," + cost);
                                System.out.println(c.getClass().getSimpleName() + "," + m.getClass().getSimpleName()
                                        + "," + k.getClass().getSimpleName() + "," + p + "," + si + "," + cost);
                            }
                        }
                    }
                }
            }
        });
        t2.start();
        Thread t3 = new Thread(() -> {
            DistanceMatrix dm = bier;
            PrintWriter pw = pwbier;
            for (Crossover c : crossovers) {
                for (Mutator m : mutators) {
                    for (Killer k : killers) {
                        for (double p : probabilities) {
                            for (int si : sizes) {
                                Population pop = Population.getRandomPopulation(si, dm.matrix);
                                long cost = dm.cost(dm.genetic(pop,
                                        m,
                                        c,
                                        k,
                                        p,
                                        time));
                                pw.println(c.getClass().getSimpleName() + "," + m.getClass().getSimpleName() + ","
                                        + k.getClass().getSimpleName() + "," + p + "," + si + "," + cost);
                                System.out.println(c.getClass().getSimpleName() + "," + m.getClass().getSimpleName()
                                        + "," + k.getClass().getSimpleName() + "," + p + "," + si + "," + cost);
                            }
                        }
                    }
                }
            }
        });
        t3.start();
        Thread t4 = new Thread(() -> {
            DistanceMatrix dm = ch;
            PrintWriter pw = pwch;
            for (Crossover c : crossovers) {
                for (Mutator m : mutators) {
                    for (Killer k : killers) {
                        for (double p : probabilities) {
                            for (int si : sizes) {
                                Population pop = Population.getRandomPopulation(si, dm.matrix);
                                long cost = dm.cost(dm.genetic(pop,
                                        m,
                                        c,
                                        k,
                                        p,
                                        time));
                                pw.println(c.getClass().getSimpleName() + "," + m.getClass().getSimpleName() + ","
                                        + k.getClass().getSimpleName() + "," + p + "," + si + "," + cost);
                                System.out.println(c.getClass().getSimpleName() + "," + m.getClass().getSimpleName()
                                        + "," + k.getClass().getSimpleName() + "," + p + "," + si + "," + cost);
                            }
                        }
                    }
                }
            }
        });
        t4.start();
        Thread t5 = new Thread(() -> {
            DistanceMatrix dm = a;
            PrintWriter pw = pwa;
            for (Crossover c : crossovers) {
                for (Mutator m : mutators) {
                    for (Killer k : killers) {
                        for (double p : probabilities) {
                            for (int si : sizes) {
                                Population pop = Population.getRandomPopulation(si, dm.matrix);
                                long cost = dm.cost(dm.genetic(pop,
                                        m,
                                        c,
                                        k,
                                        p,
                                        time));
                                pw.println(c.getClass().getSimpleName() + "," + m.getClass().getSimpleName() + ","
                                        + k.getClass().getSimpleName() + "," + p + "," + si + "," + cost);
                                System.out.println(c.getClass().getSimpleName() + "," + m.getClass().getSimpleName()
                                        + "," + k.getClass().getSimpleName() + "," + p + "," + si + "," + cost);
                            }
                        }
                    }
                }
            }
        });
        t5.start();
        Thread t6 = new Thread(() -> {
            DistanceMatrix dm = r;
            PrintWriter pw = pwr;
            for (Crossover c : crossovers) {
                for (Mutator m : mutators) {
                    for (Killer k : killers) {
                        for (double p : probabilities) {
                            for (int si : sizes) {
                                Population pop = Population.getRandomPopulation(si, dm.matrix);
                                long cost = dm.cost(dm.genetic(pop,
                                        m,
                                        c,
                                        k,
                                        p,
                                        time));
                                pw.println(c.getClass().getSimpleName() + "," + m.getClass().getSimpleName() + ","
                                        + k.getClass().getSimpleName() + "," + p + "," + si + "," + cost);
                                System.out.println(c.getClass().getSimpleName() + "," + m.getClass().getSimpleName()
                                        + "," + k.getClass().getSimpleName() + "," + p + "," + si + "," + cost);
                            }
                        }
                    }
                }
            }
        });
        t6.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();
        pwatt.close();
        pwberlin.close();
        pwbier.close();
        pwch.close();
        pwa.close();
        pwr.close();

    }
}
