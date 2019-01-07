import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;


public class Level extends JPanel {
    private List<Entity> entities = Collections.synchronizedList(new LinkedList<>());
    private List<Particle> particles = Collections.synchronizedList(new LinkedList<>());
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;

    public Level() {
        entities.add((new PlayerTank(100, 100)));
        entities.add((new Enemy(1, 500,500)));
        for (Entity e : entities)
            e.addParticleToLevel = particle -> {
                particle.removeSelf = p -> this.particles.remove(p);
                particles.add(particle);
            };
        for (Entity e : entities) {
            e.removeSelf = entity -> this.entities.remove(entity);
            e.removeParticleFromLevel = particle -> this.particles.remove(particle);
        }
        for (Particle p : particles) p.removeSelf = particle -> this.particles.remove(particle);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
            }

            @Override
            public void mousePressed(MouseEvent ev) {
                for (Entity e : entities) e.mousePressed(ev);
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent ev) {
                for (Entity e : entities) e.keyReleased(ev);
            }

            @Override
            public void keyPressed(KeyEvent ev) {
                for (Entity e : entities) e.keyPressed(ev);
            }
        });
        setFocusable(true);
    }

    private void tick() {
        // copy the list so it doesn't crash if the list is modified
        for (Particle p : new ArrayList<>(particles)) p.tick(WIDTH, HEIGHT);
        for (Entity e : new ArrayList<>(entities)) e.tick(WIDTH, HEIGHT);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g); //Clears the panel, for a fresh start
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Entity e : new ArrayList<>(entities)) e.paint(g2d);
        for (Particle p : new ArrayList<>(particles)) p.paint(g2d);
    }


    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Den of Tanks");
        Level jp = new Level();
        frame.add(jp);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            jp.tick(); //Updates the coordinates
            jp.repaint(); //Calls the paint method
            Thread.sleep(1000 / 30); //Pauses for a moment
            //Thread.sleep(1000); //Pauses for a moment
        }
    }

}

