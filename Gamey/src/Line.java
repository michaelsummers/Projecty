import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

/**
 * Line Smasher is a game where the player tries to clear away
 * the blue pixels that the game creates.
 * In tracer, the player travels at a constant speed and erases
 * blue pixels that occupy the space beneath the player. There is
 * an ever-growing line of blue and the occasional large blue dot
 * that creates blue space. As the player clears blue, their power
 * increases. Once it reaches the maximum amount, the player can
 * release this power with a click to destroy a large area. The
 * player can also hold the space bar for a speed boost, which
 * uses power.
 */
public class Line extends JFrame{
    private static BufferedImage[] rules;
    private static Point[] posis;
    private static BufferedImage surprise;

    private static BufferedImage icon;

    private volatile int stage;
    private boolean paint;

    private int level;
    private int power;
    private int score;

    private double hor;
    private double ver;

    private double newX;
    private double newY;
    private double rotate;
    private double speed;

    private boolean boost;
    private int xBoom;
    private int yBoom;
    private int opac;

    private ArrayList<Point> surprises;

    private BufferStrategy strategy;
    private BufferedImage img;
    private Graphics imG;
    private byte[] pixels;

    private T thread;

    /**
     * Create the images for the instructions at the start of the game
     */
    static{
        rules = new BufferedImage[10];
        posis = new Point[10];
        Graphics g;

        rules[0] = new BufferedImage(250, 55, 5);
        posis[0] = new Point(100, 150);
        g = rules[0].getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(1, 1, 248, 53);
        g.setColor(Color.BLACK);
        g.drawString("Welcome to Line Smasher!", 10, 15);
        g.drawString("In this game, you must destroy blue areas", 10, 30);
        g.drawString("by moving your character over them.", 10, 45);
        g.dispose();

        rules[1] = new BufferedImage(140, 55, 5);
        posis[1] = new Point(208, 231);
        g = rules[1].getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(1, 1, 138, 53);
        g.setColor(Color.BLACK);
        g.drawString("This is your character.", 10, 15);
        g.drawString("It will clear any blue", 10, 30);
        g.drawString("areas beneath it.", 10, 45);
        g.dispose();

        rules[2] = new BufferedImage(160, 55, 5);
        posis[2] = new Point(209, 421);
        g = rules[2].getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(1, 1, 158, 53);
        g.setColor(Color.BLACK);
        g.drawString("This is the controller.", 10, 15);
        g.drawString("Move your mouse around", 10, 30);
        g.drawString("it to change direction.", 10, 45);
        g.dispose();

        rules[3] = new BufferedImage(230, 40, 5);
        posis[3] = new Point(100, 150);
        g = rules[3].getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(1, 1, 228, 38);
        g.setColor(Color.BLACK);
        g.drawString("If you move off the edge of the screen,", 10, 15);
        g.drawString("You will reappear on the other side.", 10, 30);
        g.dispose();

        rules[4] = new BufferedImage(330, 70, 5);
        posis[4] = new Point(90, 40);
        g = rules[4].getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(1, 1, 328, 68);
        g.setColor(Color.BLACK);
        g.drawString("This bar is the power bar. When it fills up, click to", 10, 15);
        g.drawString("demolish a large area around you. You can also hold the", 10, 30);
        g.drawString("space bar for a speed boost, which will use some power.", 10, 45);
        g.drawString("As you clear blue matter, this will fill up.", 10, 60);
        g.dispose();

        rules[5] = new BufferedImage(190, 70, 5);
        posis[5] = new Point(270, 140);
        g = rules[5].getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(1, 1, 188, 68);
        g.setColor(Color.BLACK);
        g.drawString("This bar is the danger bar.", 10, 15);
        g.drawString("It fills up as more blue appears.", 10, 30);
        g.drawString("When it fills up, the game ends.", 10, 45);
        g.drawString("Keep an eye on it!", 10, 60);
        g.dispose();

        rules[6] = new BufferedImage(130, 40, 5);
        posis[6] = new Point(300, 500);
        g = rules[6].getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(1, 1, 128, 38);
        g.setColor(Color.BLACK);
        g.drawString("This is your score.", 10, 15);
        g.drawString("It increases with time.", 10, 30);
        g.dispose();

        rules[7] = new BufferedImage(300, 55, 5);
        posis[7] = new Point(70, 150);
        g = rules[7].getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(1, 1, 298, 53);
        g.setColor(Color.BLACK);
        g.drawString("Every once in a while, a large blue area will appear.", 10, 15);
        g.drawString("These blobs will raise the danger level significantly.", 10, 30);
        g.drawString("Clear them to gain a huge amount of power.", 10, 45);
        g.dispose();

        rules[8] = new BufferedImage(240, 40, 5);
        posis[8] = new Point(100, 150);
        g = rules[8].getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(1, 1, 238, 38);
        g.setColor(Color.BLACK);
        g.drawString("That's it! Make sure your mouse is over", 10, 15);
        g.drawString("the controller, then click to start.", 10, 30);
        g.dispose();

        surprise = new BufferedImage(30, 30, 5);
        g = surprise.getGraphics();
        g.setColor(Color.YELLOW);
        g.dispose();

        icon = new BufferedImage(25, 25, 5);
        g = icon.getGraphics();
        g.setColor(Color.BLUE);
        g.drawLine(5, 21, 14, 11);
        g.drawLine(6, 21, 15, 11);
        g.setColor(Color.WHITE);
        g.drawRect(1, 1, 22, 22);
        g.setColor(Color.RED);
        g.fillOval(12, 8, 7, 7);
        g.dispose();

    }

    /**
     * Set up the game
     */
    public Line(){
        super("Line Smasher!");
        setIconImage(icon);
        setSize(476, 564);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(new ML());
        addKeyListener(new KL());
        stage = 0;
        hor = 200;
        ver = 200;

        newX = 200;
        newY = 200;
        rotate = Math.random() * Math.PI * 2;
        speed = Math.random() / 16;

        boost = false;
        xBoom = 0;
        yBoom = 0;
        opac = 0;

        img = new BufferedImage(400, 400, 5);
        imG = img.getGraphics();
        pixels = ((DataBufferByte)img.getRaster().getDataBuffer()).getData();
        for(int i = 0; i < 1024; i++) {
            addPoint();
        }
        level = 0;
        for(int i = 0; i < 480000; i += 3) {
            if(pixels[i] == -1){
                level++;
            }
        }
        power = 0;
        score = 0;
        thread = new T();
        setVisible(true);
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        paint = true;
    }

    /**
     * Add a new point to the blue enemy line in a "random" position
     */
    private void addPoint() {
        imG.setColor(Color.BLUE);
        imG.fillOval((int)newX - 1, (int)newY - 1, 3, 3);
        newX = (newX + Math.cos(rotate) + 400) % 400;
        newY = (newY + Math.sin(rotate) + 400) % 400;
        rotate = (rotate + speed) % (Math.PI * 2);
        speed -= speed / 32 + (Math.random() - 0.5) / 64;
    }

    /**
     * The paint methdd, calls draw
     * @param g the Graphics that are not used
     */
    @Override
    public void paint(Graphics g){
        if(paint) {
            draw();
        }
    }

    /**
     * Draw the graphics for the game
     */
    private void draw(){
        Graphics g = strategy.getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 476, 564);

        g.drawImage(img, 8, 31, null);

        if(opac > 0){
            g.setColor(new Color(255, 0, 0, opac));
            g.fillOval(xBoom, yBoom, 101, 101);
        }

        if(power == 1024){
            g.setColor(Color.RED);
            g.fillOval((int)hor + 3, (int)ver + 26, 11, 11);
        }
        else if(boost){
            g.setColor(new Color(255, 100, 150));
            g.fillOval((int)hor + 3, (int)ver + 26, 11, 11);
            g.setColor(Color.RED);
        }
        else{
            g.setColor(Color.WHITE);
            g.fillOval((int)hor + 3, (int)ver + 26, 11, 11);
            g.setColor(Color.RED);
        }
        int val2 = (int)(power * 125 / 256.0);
        g.fillRect(413, 531 - val2, 25, val2);

        g.setColor(Color.BLUE);
        int val = level / 8;
        g.fillRect(443, 531 - val, 25, val);

        g.setColor(Color.BLACK);
        g.drawRect(413, 31, 25, 500);
        g.drawRect(443, 31, 25, 500);
        g.drawOval(169, 441, 80, 80);
        g.drawString("" + score, 413, 551);
        g.drawRect(413, 536, 55, 20);

        Point mouse = getMousePosition();
        if(stage < 8){
            g.drawImage(rules[stage], posis[stage].x, posis[stage].y, null);
            g.drawString("SKIP", 196, 488);
        }
        else if(stage == 8){
            g.drawImage(rules[8], posis[8].x, posis[8].y, null);
            g.drawString("START", 191, 488);
        }
        else if(stage == 10){
            g.setColor(Color.WHITE);
            g.fillRect(100, 200, 200, 90);
            g.setColor(Color.BLACK);
            g.drawRect(101, 201, 197, 87);
            g.drawString("RETRY", 190, 488);
            g.setFont(new Font("Sans_Serif", Font.BOLD, 25));
            g.drawString("Your Score:", 110, 225);
            g.setFont(new Font("Sans_Serif", Font.BOLD, 50));
            g.drawString("" + score, 110, 275);
        }
        else if(mouse != null && (mouse.x - 209) * (mouse.x - 209) + (mouse.y - 481) * (mouse.y - 481) < 1600){
            g.fillOval(mouse.x - 8, mouse.y - 8, 17, 17);
        }
        g.dispose();
        strategy.show();
    }

    /**
     * makes a new game
     * @param args is unnecessary
     */
    public static void main(String[] args){
        new Line();
    }

    /**
     * MouseListener for JFrame
     */
    private class ML implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e){
        }

        /**
         * Updates the stage of the game or resets the game when it ends
         * @param e is unnecessary
         */
        @Override
        public void mousePressed(MouseEvent e){
            Point mouse = getMousePosition();
            if(stage == 9 && power == 1024){
                power = 0;
                imG.setColor(Color.BLACK);
                imG.fillOval((int) hor - 50, (int) ver - 50, 101, 101);
                xBoom = (int)hor - 42;
                yBoom = (int)ver - 19;
                opac = 255;

            }
            else if(mouse != null && (mouse.x - 209) * (mouse.x - 209) + (mouse.y - 481) * (mouse.y - 481) < 1600){
                if(stage == 10) {
                    thread = new T();
                    stage = 0;
                    hor = 200;
                    ver = 200;
                    newX = 200;
                    newY = 200;
                    rotate = Math.random() * Math.PI * 2;
                    speed = Math.random() / 16;

                    for (int i = 0; i < 480000; i += 3) {
                        pixels[i] = 0;
                    }
                    for(int i = 0; i < 1024; i++) {
                        addPoint();
                    }
                    level = 0;
                    for(int i = 0; i < 480000; i += 3) {
                        if(pixels[i] == -1){
                            level++;
                        }
                    }
                    score = 0;
                    power = 0;
                    stage = 8;
                    draw();
                }
                else if(stage < 9) {
                    thread.start();
                    stage = 9;
                }
            }
            else if(stage < 8) {
                stage++;
                draw();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e){
        }

        @Override
        public void mouseEntered(MouseEvent e){
        }

        @Override
        public void mouseExited(MouseEvent e){
        }
    }

    private class KL implements KeyListener {
        boolean pressedBefore;

        private KL(){
            pressedBefore = false;
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(!pressedBefore && e.getKeyCode() == KeyEvent.VK_SPACE){
                boost = true;
                pressedBefore = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                boost = false;
                pressedBefore = false;
            }
        }
    }

    /**
     * The thread where the game takes place
     */
    private class T extends Thread{
        /**
         * The loop that executes during the game, controlling all functions
         * The player and enemy move here, along with powers
         */
        @Override
        public void run(){
            int pause = 500;
            long start = System.currentTimeMillis();
            while(true) {
                Point mouse = getMousePosition();
                if(mouse != null) {
                    double direction = Math.atan2(mouse.y - 481, mouse.x - 209);
                    if(boost && power > 15){
                        hor = (hor + 2 * Math.cos(direction) + 400) % 400;
                        ver = (ver + 2 * Math.sin(direction) + 400) % 400;
                        power -= 16;
                    }
                    else{
                        boost = false;
                        hor = (hor + Math.cos(direction) + 400) % 400;
                        ver = (ver + Math.sin(direction) + 400) % 400;
                    }

                    imG.setColor(Color.BLACK);
                    imG.fillOval((int) hor - 5, (int) ver - 5, 11, 11);

                    if(opac > 0){
                        opac -= 15;
                    }

                    int count = 0;
                    for (int i = 0; i < 480000; i += 3) {
                        if (pixels[i] == -1) {
                            count++;
                        }
                    }

                    power += level - count;
                    if (power > 1024) {
                        power = 1024;
                    }

                    addPoint();
                    if(pause == 0){
                        imG.fillOval((int)(Math.random() * 380), (int)(Math.random() * 380),20, 20);
                        pause = 500;
                    }
                    else{
                        pause--;
                    }

                    level = 0;
                    for(int i = 0; i < 480000; i += 3) {
                        if(pixels[i] == -1){
                            level++;
                        }
                    }
                    if(level > 4000){
                        level = 4000;
                        stage = 10;
                        draw();
                        break;
                    }

                    draw();
                    try{
                        Thread.sleep(20 + start - System.currentTimeMillis());
                    }
                    catch(Exception ex){
                    }
                    start = System.currentTimeMillis();
                    score++;
                }
            }
        }
    }
}