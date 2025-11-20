import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Prosta gra wyścigowa dla trzech graczy. Każdy gracz steruje własnym autem:
 * <ul>
 *   <li>BMW X6M (czarny) – sterowanie strzałkami.</li>
 *   <li>Mercedes W200 (srebrny) – sterowanie klawiszami WASD.</li>
 *   <li>Mercedes C klasa (biały) – sterowanie klawiszami IJKL.</li>
 * </ul>
 */
public class Game extends JPanel {

    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 500;
    private static final int ROAD_TOP = 120;
    private static final int ROAD_BOTTOM = 380;
    private static final int FINISH_X = 420;
    private static final int CAR_WIDTH = 70;
    private static final int CAR_HEIGHT = 40;
    private static final int STEP = 3;

    private static final long serialVersionUID = 1L;

    private final Car[] players;
    private String winner;

    private static class Car {
        final String name;
        final Color color;
        int x;
        int y;

        boolean up;
        boolean down;
        boolean left;
        boolean right;

        Car(String name, Color color, int x, int y) {
            this.name = name;
            this.color = color;
            this.x = x;
            this.y = y;
        }

        void updatePosition() {
            if (up) {
                y -= STEP;
            }
            if (down) {
                y += STEP;
            }
            if (left) {
                x -= STEP;
            }
            if (right) {
                x += STEP;
            }

            if (x < 10) {
                x = 10;
            }
            if (x > FRAME_WIDTH - CAR_WIDTH - 10) {
                x = FRAME_WIDTH - CAR_WIDTH - 10;
            }
            if (y < ROAD_TOP) {
                y = ROAD_TOP;
            }
            if (y > ROAD_BOTTOM - CAR_HEIGHT) {
                y = ROAD_BOTTOM - CAR_HEIGHT;
            }
        }
    }

    public Game() {
        this.players = new Car[] {
                new Car("BMW X6M (czarny)", Color.BLACK, 40, 160),
                new Car("Mercedes W200 (srebrny)", Color.LIGHT_GRAY, 40, 240),
                new Car("Mercedes C klasa (biały)", Color.WHITE, 40, 320)
        };

        winner = null;

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                handleKey(e, false);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                handleKey(e, true);
            }
        });
        setFocusable(true);
    }

    private void handleKey(KeyEvent e, boolean pressed) {
        switch (e.getKeyCode()) {
            // Player 1 – strzałki
            case KeyEvent.VK_UP:
                players[0].up = pressed;
                break;
            case KeyEvent.VK_DOWN:
                players[0].down = pressed;
                break;
            case KeyEvent.VK_LEFT:
                players[0].left = pressed;
                break;
            case KeyEvent.VK_RIGHT:
                players[0].right = pressed;
                break;

            // Player 2 – WASD
            case KeyEvent.VK_W:
                players[1].up = pressed;
                break;
            case KeyEvent.VK_S:
                players[1].down = pressed;
                break;
            case KeyEvent.VK_A:
                players[1].left = pressed;
                break;
            case KeyEvent.VK_D:
                players[1].right = pressed;
                break;

            // Player 3 – IJKL
            case KeyEvent.VK_I:
                players[2].up = pressed;
                break;
            case KeyEvent.VK_K:
                players[2].down = pressed;
                break;
            case KeyEvent.VK_J:
                players[2].left = pressed;
                break;
            case KeyEvent.VK_L:
                players[2].right = pressed;
                break;
            default:
                break;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Tło drogi
        g2d.setColor(new Color(40, 40, 40));
        g2d.fillRect(0, ROAD_TOP, FRAME_WIDTH, ROAD_BOTTOM - ROAD_TOP);

        // Linia mety
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(FINISH_X, ROAD_TOP, 6, ROAD_BOTTOM - ROAD_TOP);

        // Linie pasów
        g2d.setColor(Color.WHITE);
        for (int y = ROAD_TOP; y < ROAD_BOTTOM; y += 40) {
            g2d.fillRect(FINISH_X + 10, y + 10, 15, 4);
        }

        // Opisy sterowania
        g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2d.drawString("Strzałki: BMW X6M", 15, 25);
        g2d.drawString("WASD: Mercedes W200", 15, 45);
        g2d.drawString("IJKL: Mercedes C klasa", 15, 65);

        // Samochody
        for (Car car : players) {
            g2d.setColor(car.color);
            g2d.fillRoundRect(car.x, car.y, CAR_WIDTH, CAR_HEIGHT, 10, 10);
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect(car.x + 5, car.y + 8, 12, 8);
            g2d.fillRect(car.x + CAR_WIDTH - 17, car.y + 8, 12, 8);
            g2d.fillRect(car.x + 5, car.y + CAR_HEIGHT - 16, 12, 8);
            g2d.fillRect(car.x + CAR_WIDTH - 17, car.y + CAR_HEIGHT - 16, 12, 8);

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2d.drawString(car.name, car.x, car.y - 5);
        }

        if (winner != null) {
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(50, 200, 400, 100);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("SansSerif", Font.BOLD, 24));
            g2d.drawString("Wygrywa: " + winner, 80, 255);
        }
    }

    private void updateGame() {
        if (winner != null) {
            return;
        }

        for (Car car : players) {
            car.updatePosition();
            if (car.x + CAR_WIDTH >= FINISH_X) {
                winner = car.name;
            }
        }

        if (winner != null) {
            repaint();
            JOptionPane.showMessageDialog(this, "Zwycięzca: " + winner, "Koniec wyścigu",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Trzyosobowy wyścig samochodowy");
        Game game = new Game();
        frame.add(game);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int count = 0;
        while (true) {
            game.updateGame();
            if (count % 2 == 0) {
                game.repaint();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            count++;
        }
    }
}
