public class Gene {

    private int x;
    private int y;
    private int color;
    private int[] x_array;
    private int[] y_array;

    Gene(int x, int y, int color, int[] x_array, int[] y_array) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.x_array = x_array.clone();
        this.y_array = y_array.clone();
    }

    int getX() {
        return this.x;
    }

    int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int[] getX_array() {
        return x_array;
    }

    public void setX_array(int[] x_array) {
        this.x_array = x_array;
    }

    public int[] getY_array() {
        return y_array;
    }

    public void setY_array(int[] y_array) {
        this.y_array = y_array;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Gene gene = (Gene) o;
        return this.x == gene.x &&
                this.y == gene.y && this.color == color;
    }

}