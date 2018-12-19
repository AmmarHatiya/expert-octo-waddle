import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

//https://stackoverflow.com/questions/44138641/2d-bullet-game-in-java
public class Tank extends Entity {
    public enum Direction {
        N, S, E, W;
    }
    private Direction direction = Direction.N;

    private int speed = 1; //how fast moves
    private boolean shoot = false;

    public static final Color MOD_YELLOW = new Color(254, 255, 24, 251);

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics2D g2d) {
        //base
        g2d.setColor(Color.lightGray);
        g2d.fillRect((int)x-15, (int)y-15 , 30, 30);

        g2d.setColor(Color.green);
        g2d.fillOval((int)x, (int)y , 5, 5);
    }

    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);

        if (e.getKeyCode() == KeyEvent.VK_A)
            this.vx = -speed;
        if (e.getKeyCode() == KeyEvent.VK_D)
            this.vx = +speed;
        if (e.getKeyCode() == KeyEvent.VK_W)
            this.vy = -speed;
        if (e.getKeyCode() == KeyEvent.VK_S)
            this.vy = +speed;
    }

    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);

        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D)
            this.vx = 0;
        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S)
            this.vy = 0;
    }

    public void check(Entity p) {

    }

    public void check(Particle p) {

    }

    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        this.addParticleToLevel.accept(new Bullet(this.x, this.y, e.getX(), e.getY()));
    }
}
