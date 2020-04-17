import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {

    private List<Chromosome> population;
    private final int initialSize;

    Population(final Gene[] points, final int initialSize) {

        this.population = inititalize(points, initialSize);
        this.initialSize = initialSize;
    }

    List<Chromosome> getPopulation() {
        return this.population;
    }

    Chromosome getAlpha() {
        return this.population.get(0);
    }

    private List<Chromosome> inititalize(final Gene[] points, final int initialSize) {

        final List<Chromosome> newOne = new ArrayList<>();
        for(int x = 0; x < initialSize; x++) {
            final Chromosome chromosome = Chromosome.create(points);
            newOne.add(chromosome);
        }
        return newOne;
    }

    void update() {

        doCrossOver();
        doMutation();
        doSpawn();
        doSelection();
    }

    private void doSelection() {

        this.population.sort(Comparator.comparingDouble(Chromosome::getFitness));
        this.population = this.population.stream().limit(this.initialSize).collect(Collectors.toList());
    }

    private void doSpawn() {

        IntStream.range(0, 1000).forEach(e -> {
            this.population.add(Chromosome.create(Helper.POLYGONS));
        });
    }

    private void doMutation() {

        final List<Chromosome> newPopulation = new ArrayList<>();
        for(int x = 0; x < this.population.size()/10; x++) {
            final Chromosome mChromosome = this.population.get(Helper.randomIndex(this.population.size())).mutate();
            newPopulation.add(mChromosome);
        }

        this.population.addAll(newPopulation);
    }

    private void doCrossOver() {

        final List<Chromosome> newPopulation = new ArrayList<>();
        for(final Chromosome x : this.population) {
            final Chromosome mate = getMate(x);
            newPopulation.addAll(Arrays.asList(x.crossOver(mate)));
        }

        this.population.addAll(newPopulation);
    }

    private Chromosome getMate(final Chromosome chromosome) {

        Chromosome mate;
        if(this.population.size() <= 2){
            mate = this.population.get(Helper.randomIndex(this.population.size()));
            while(chromosome == mate) {
                mate = this.population.get(Helper.randomIndex(this.population.size()));
            }
        } else {
            int index = 1;
            mate = this.population.get(index);
            while(chromosome == mate) {
                mate = this.population.get(index++);
            }
        }
        return mate;
    }

}