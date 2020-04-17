import java.util.*;
import java.util.stream.IntStream;

public class Helper {

    static int geneCount = 5000;
    private final static Random R = new Random(9999999);
    static Gene[] POLYGONS = produceData(geneCount);

    private Helper() {
        throw new RuntimeException("No!");
    }

    static final Gene[] produceData(final int count) {

        final Gene[] data = new Gene[count];

        for(int i = 0; i < count; i++) {
            int x_ind = randomIndex(Main.width);
            int y_ind = randomIndex(Main.height);
            int color = Main.image.getRGB(randomIndex(Main.width), randomIndex(Main.height));
            List<List<Integer>> vals = getRandomPoints(x_ind, y_ind, color);
            int[] x_poly;
            int[] y_poly;

            if(vals.size() == 0){

                x_poly = new int[]{x_ind, x_ind - 15, x_ind + 15};
                y_poly = new int[]{y_ind, y_ind + 20, y_ind - 20};

            } else {
                int size = vals.get(0).size();
                x_poly = new int[size];
                y_poly = new int[size];

                for(int x = 0; x < size; x++){
                    x_poly[x] = vals.get(0).get(x);
                }
                for(int x = 0; x < size; x++){
                    y_poly[x] = vals.get(1).get(x);
                }
            }

            data[i] = new Gene(x_ind, y_ind, color, x_poly, y_poly);
        }
        return data;
    }

    static int randomIndex(final int limit) {
        return R.nextInt(limit);
    }

    static List<List<Integer>> getPoints(int x_ind, int y_ind, int color){

        int circle_size = Main.width < Main.height ? Main.width : Main.height;
        List<List<Integer>> listOfLists = new ArrayList<List<Integer>>();
        listOfLists.add(0, new ArrayList<>());
        listOfLists.add(1, new ArrayList<>());

        for (int x = 0; x < circle_size; x++) {
            for (int y = 0; y < circle_size; y++) {
                double dx = x - x_ind;
                double dy = y - y_ind;
                double distanceSquared = dx * dx + dy * dy;

                if (distanceSquared <= Math.pow(circle_size / 2, 2)) {
                    if ((Math.abs(Main.image.getRGB(x, y) - color) <= 3 || isTransparent(x, y)) && (Math.abs(dy) < 100 && Math.abs(dx) < 100)) {
                        listOfLists.get(0).add(x);
                        listOfLists.get(1).add(y);
                    }
                }
            }
        }

        if(listOfLists.get(0).size() >=3){

            List<Integer> x_poly = new ArrayList<>(Arrays.asList(listOfLists.get(0).get(0), listOfLists.get(0).get((listOfLists.get(0).size() - 1) / 2), listOfLists.get(0).get(listOfLists.get(0).size() - 1)));
            List<Integer> y_poly = new ArrayList<>(Arrays.asList(listOfLists.get(1).get(0), listOfLists.get(1).get((listOfLists.get(1).size() - 1) / 2), listOfLists.get(1).get(listOfLists.get(1).size() - 1)));
            listOfLists.add(0, x_poly);
            listOfLists.add(1, y_poly);
        }

        return listOfLists;
    }

    static boolean isTransparent( int x, int y ) {
        int pixel = Main.image.getRGB(x,y);
        if( (pixel>>24) == 0x00 ) {
            return true;
        } else {
            return false;
        }
    }

    static List<List<Integer>> getRandomPoints(int x_ind, int y_ind, int color){

        int circle_size = Main.width < Main.height ? Main.width : Main.height;

        List<List<Integer>> listOfLists = new ArrayList<List<Integer>>();
        listOfLists.add(0, new ArrayList<>());
        listOfLists.add(1, new ArrayList<>());

        for (int x = 0; x < circle_size; x++) {
            for (int y = 0; y < circle_size; y++) {
                double dx = x - x_ind;
                double dy = y - y_ind;
                double distanceSquared = dx * dx + dy * dy;

                if (distanceSquared <= Math.pow(circle_size / 2, 2)) {
                    if (Math.abs(Main.image.getRGB(x, y) - color) <= 20) {
                        listOfLists.get(0).add(x);
                        listOfLists.get(1).add(y);
                    }
                }
            }
        }

        if(listOfLists.get(0).size() >= 2){
            List<Integer> x_poly = new ArrayList<>(Arrays.asList(listOfLists.get(0).get(0), listOfLists.get(0).get((listOfLists.get(0).size() - 1) / 2), listOfLists.get(0).get(listOfLists.get(0).size() - 1)));
            List<Integer> y_poly = new ArrayList<>(Arrays.asList(listOfLists.get(1).get(0), listOfLists.get(1).get((listOfLists.get(1).size() - 1) / 2), listOfLists.get(1).get(listOfLists.get(1).size() - 1)));
            listOfLists.add(0, x_poly);
            listOfLists.add(1, y_poly);
        }

        return listOfLists;
    }

    static<T> List<T>[] split(final List<T> focus) {

        final List<T> a = new ArrayList<>();
        final List<T> b = new ArrayList<>();
        final int size = focus.size();
        final int splitPoint = 1 + Helper.randomIndex(focus.size());

        IntStream.range(0, size).forEach(i -> {
            if(i < (size+1)/splitPoint) {
                a.add(focus.get(i));
            } else {
                b.add(focus.get(i));
            }
        });

        return (List<T>[]) new List[] {a, b};
    }

}