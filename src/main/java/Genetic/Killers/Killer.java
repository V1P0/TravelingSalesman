package Genetic.Killers;

import Genetic.Population;

public interface Killer {
    void kill(Population population, int expectedSize);
}
