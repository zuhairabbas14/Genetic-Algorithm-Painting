import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends JPanel {

    private final Population population;
    private final AtomicInteger generation;
    static BufferedImage image;

    static {
        try {
            image = ImageIO.read(new File("5" + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int width = image.getWidth();
    static int height = image.getHeight();
    static final int WIDTH = 1920;
    static final int HEIGHT = 1080;

    private Main() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        this.population = new Population(Helper.POLYGONS, 50);
        this.generation = new AtomicInteger(0);
        final Timer timer = new Timer(0, (ActionEvent e) -> {
            this.population.update();
            repaint();
        });

        timer.start();
    }

    @Override
    public void paintComponent(final Graphics graphics) {

        super.paintComponent(graphics);
        final Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.BLACK);
        double total = 512 * 512;
        double percentage = (((total - this.population.getAlpha().getFitness())) / total) * 100;
        g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawString("gen : " + this.generation.incrementAndGet(), 900, 300);
        g.drawString("pop size : " +this.population.getPopulation().size(), 900, 400);
        g.drawString("fitness : "
                +String.format("%.2f", this.population.getAlpha().getFitness()), 900, 700);
        g.drawString("Fitness percrnt : " + percentage, 900, 500);


        drawBestChromosome(g);
    }

    private void drawBestChromosome(final Graphics2D g) {

        final java.util.List<Gene> chromosome = this.population.getAlpha().getChromosome();

            for(final Gene gene : chromosome) {

                int argb = gene.getColor();
                int b = (argb)&0xFF;
                int gC = (argb>>8)&0xFF;
                int r = (argb>>16)&0xFF;

                Color myColor = new Color(r, gC, b, 144);

                g.setColor(myColor);
                g.fillOval(gene.getX() ,gene.getY(),18, 18);
                for(int x = 0; x < gene.getX_array().length; x++){
                    g.fillRect(gene.getX_array()[x], gene.getY_array()[x],9,9);
                }

            }

        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 32));

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setTitle("Genetic Algorithms");
            frame.setResizable(true);
            frame.add(new Main(), BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}