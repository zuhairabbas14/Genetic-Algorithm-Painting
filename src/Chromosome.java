import java.util.*;
import java.util.List;

public class Chromosome {

    private final List<Gene> chromosome;
    private final double fitness;

    public double getFitness() {
        return this.fitness;
    }

    private Chromosome(final List<Gene> chromosome) {
        this.chromosome = Collections.unmodifiableList(chromosome);
        this.fitness = calculateFitness();
    }

    static Chromosome create(final Gene[] items) {
        final List<Gene> genes = Arrays.asList(Arrays.copyOf(items, items.length));
        Collections.shuffle(genes);
        return new Chromosome(genes);
    }

    List<Gene> getChromosome() {
        return this.chromosome;
    }

    double calculateFitness() {

        double total = 0.0f;

        for (int i = 0; i < this.chromosome.size(); i++) {

            Gene focus = this.chromosome.get(i);

            for(int x = 0; x < focus.getX_array().length; x++){

                int real_color = Main.image.getRGB(focus.getX_array()[x], focus.getY_array()[x]);
                int foc_color = focus.getColor();

                if((Math.abs(real_color) != foc_color)){
                    total += Math.abs(real_color - foc_color);
                }
            }
        }

        return total;
    }


    Chromosome[] crossOver(final Chromosome x) {

        final List<Gene>[] mine = Helper.split(this.chromosome);
        final List<Gene>[] partner = Helper.split(x.getChromosome());
        final List<Gene> first_cross = new ArrayList<>(mine[0]);
        final List<Gene> second_cross = new ArrayList<>(partner[1]);

        for(Gene gene : partner[0]) {
            if(!first_cross.contains(gene)) {
                first_cross.add(gene);
            }
        }

        for(Gene gene : partner[1]) {
            if(!first_cross.contains(gene)) {
                first_cross.add(gene);
            }
        }

        for(Gene gene : mine[0]) {
            if(!second_cross.contains(gene)) {
                second_cross.add(gene);
            }
        }

        for(Gene gene : mine[1]) {
            if(!second_cross.contains(gene)) {
                second_cross.add(gene);
            }
        }

        return new Chromosome[] { new Chromosome(first_cross), new Chromosome(second_cross) };
    }

    Chromosome mutate() {

        final List<Gene> newCopy = new ArrayList<>(this.chromosome);

        for(int i = 0; i < 2; i++){

//            int c = 0;
            int index = Helper.randomIndex(newCopy.size());
            while(newCopy.get(index).getColor() == Main.image.getRGB(newCopy.get(index).getX(), newCopy.get(index).getY())){
                index = Helper.randomIndex(newCopy.size());
//                c++;
//                if(c > Helper.geneCount){
//                    break;
//                }
            }

            int a = Helper.randomIndex(Main.width);
            int b = Helper.randomIndex(Main.height);
            Gene foc = newCopy.get(index);
            foc.setX(a);
            foc.setY(b);
            foc.setColor(Main.image.getRGB(a,b));
            int[] x_poly;
            int[] y_poly;
            List<List<Integer>> values = Helper.getPoints(a,b ,foc.getColor());
            int size = values.get(0).size();
            x_poly = new int[size];
            y_poly = new int[size];

            for(int x = 0; x < size; x++){
                x_poly[x] = values.get(0).get(x);
            }
            for(int x = 0; x < size; x++){
                y_poly[x] = values.get(1).get(x);
            }

            foc.setX_array(x_poly);
            foc.setY_array(y_poly);
        }

        return new Chromosome(newCopy);
    }

}