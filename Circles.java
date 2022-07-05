import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;





public class Circles {
    public static void main(String[] args) throws InterruptedException{ 
        {   
            int o =  1;
            Random rnd = new Random();
            Circles c = new Circles();
            while(o == 1){
                TimeUnit.SECONDS.sleep(5);    
                c.pane.Petri = rnd.nextInt(3);
                System.out.println(c.pane.Petri);
            }
        }
    }

    TestPane pane;

    public Circles() throws InterruptedException {
        try {EventQueue.invokeAndWait(new Runnable() {
            @Override
            public void run() { 
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                        System.out.println("FEEEHLER!");
                    }   
                        JFrame frame = new JFrame("Weather");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setLayout(new BorderLayout());
                        pane = new TestPane();
                        frame.add(pane);
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                }
        });
    }catch (InvocationTargetException ex){
        System.out.println("FEEEHLER!");
    }
    }

    public static class TestPane extends JPanel {

        private Random rnd = new Random();
        private List<Circle> circles;
        private List<Rect> rects;
        protected static final int MAX_CIRCLES = ThreadLocalRandom.current().nextInt(30, 1000);
        protected static final int MAX_Hail = ThreadLocalRandom.current().nextInt(200, 400);
        int xSpeed = 3;
        int Petri = 3; 
        int lol = 5;
        protected int maxsun = 1;
        private int oldPetri = 0;
        private float t = 0;
        public TestPane() {
            
            circles = new ArrayList<>(MAX_CIRCLES);
            rects= new ArrayList<>(MAX_CIRCLES);
            
            Timer timer = new Timer(20, new ActionListener() {
                @Override
                
                public void actionPerformed(ActionEvent e) {
                    t += 0.02;
                    if(oldPetri != Petri){
                        System.out.println(t);
                        circles.removeAll(circles);
                        rects.removeAll(rects);
                        if(Petri == 1){
                            circles.add(createSun());
                        }
                        oldPetri = Petri; 
                    }    
                    if(Petri == 0){               
                        while (circles.size() < MAX_CIRCLES) {
                            circles.add(rain());
                        }
                        List<Circle> purge = new ArrayList<>(MAX_CIRCLES);
                        for (Circle circle : circles) {
                            Point p = circle.getLocation();
                            p.y += circle.getYDelta();
                            p.x += circle.getxDelta();
                            if (p.y > getHeight()) {
                                purge.add(circle);
                            }
                            else if(p.x > getWidth()){
                                purge.add(circle);
                            }
                            else {
                                circle.setLocation(p);
                            }
                        }
                        circles.removeAll(purge);

                        repaint();
                    }
                    else if(Petri == 1){  
                        for(Circle sun : circles){
                            Point p = sun.getLocation();
                            p.y +=  Math.pow(lol, 4)*-0.0000005;
                            p.x += 1;

                                sun.setLocation(p);
                            repaint();
                        }  
                    }
                    else if(Petri == 2){               
                        while (circles.size() < MAX_Hail) {
                            circles.add(hail());
                        }
                        List<Circle> stopmove = new ArrayList<>(MAX_Hail);
                        for (Circle circle : circles) {
                            Point p = circle.getLocation();
                            p.y += circle.getYDelta();
                            p.x += circle.getxDelta();
                            if (p.y > getHeight()) {
                                stopmove.add(circle);
                            }
                            else if(p.x > getWidth()){
                                stopmove.add(circle);
                            }
                            else {
                                circle.setLocation(p);
                            }
                        }
                        circles.removeAll(stopmove);
                        repaint();
                    } 
                    else if(Petri == 3){
                        while(rects.size()< MAX_Hail){
                            rects.add(bomb());
                        }
                        List<Rect> destroy = new ArrayList<>(MAX_CIRCLES);
                        for (Rect rect : rects){
                            Point p = rect.getLocation();
                            p.y += rect.getyDelta();
                            if(p.y > getHeight()){
                                destroy.add(rect);
                            }
                            else if(p.x > getWidth()){
                                destroy.add(rect);
                            }
                            else{
                                rect.setLocation(p);
                            }
                        }
                        rects.removeAll(destroy);
                        repaint();
                    }
                
                }   
            });
            timer.start();
            
        }
        protected Circle createSun()
        {
            int radius = 100;  
            int speedx = 2;    
            Circle sun = new Circle(radius, new Color(255, 255, 0));
            sun.setLocation(getWidth()/4, 0);
            int y = 200;
            int x = 200;
            sun.setLocation(x, y);
            sun.setXDelta(speedx);
            return sun;
        }
        protected Circle rain() 
        {
            int x = rnd.nextInt(getWidth());
            int c1 = rnd.nextInt(256);
            int c2 = rnd.nextInt(256);
            int c3 = rnd.nextInt(256);
            int radius = 3 + rnd.nextInt(10);           
            int speedy = 1 + ThreadLocalRandom.current().nextInt(1, 3);
            int speedx = speedy + ThreadLocalRandom.current().nextInt(1, 10);
            int y = ThreadLocalRandom.current().nextInt(radius, 100);
            if (x + radius > getWidth()) {
                x = getWidth() - radius;
            }
            Circle circle = new Circle(radius, new Color(c1, c2, c3));
            circle.setLocation(x, y);
            circle.setYDelta(speedx); 
            circle.setXDelta(speedy);  
            return circle;

        }
        protected Circle hail() 
        {
            int x = rnd.nextInt(getWidth());
            int c1 = rnd.nextInt(256);
            int c2 = rnd.nextInt(256);
            int c3 = rnd.nextInt(256);
            int radius = 10;//rnd.nextInt(10);           
            int speedy = 5 + ThreadLocalRandom.current().nextInt(1, 10);
            int y = ThreadLocalRandom.current().nextInt(radius, 100);
            if (x + radius > getWidth()) {
                x = getWidth() - radius;
            }
            Circle circle = new Circle(radius, new Color(c1, c2, c3));
            circle.setLocation(x, y);
            circle.setYDelta(speedy);  
            return circle;

        }
        protected Rect bomb(){
            int x = rnd.nextInt(getWidth());
            
            int width = 5;
            int speedy = 8;
            int y = ThreadLocalRandom.current().nextInt(width, 200);
            Rect rect = new Rect(width, new Color(0, 0, 255));
            if (x + width > getWidth()){
                x = getWidth() - width;
            }
            rect.setLocation(x, y);
            rect.setYDelta(speedy);
            return rect;
        }
       
        

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(500, 500);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            for (Circle circle : circles) {
                circle.paint(g);
            }
            for(Circle sun : circles){
                sun.paint(g);
            }
            for(Circle hail : circles){
                hail.paint(g);
            }
            for(Circle rainbow : circles){
                rainbow.paint(g);
            }
            for(Rect bomb : rects){
                bomb.paint(g);
            }
            g2d.dispose();
        }
    }
    public static class Circle {
        private final int radius;
        private final Color color;
        private int x;
        private int y;
        private int yDelta;
        private int xDelta;
        public Circle(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }
        public void setLocation(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public void setLocation(Point p) {
            setLocation(p.x, p.y);
        }
        public Point getLocation() {
            return new Point(x, y);            
        }

        public void setYDelta(int yDelta) {
            this.yDelta = yDelta;
        }
       
        public void setXDelta(int xDelta) {
            this.xDelta = xDelta;
        }
        public int getxDelta(){
            return xDelta;
        }
        public int getYDelta() {
            return yDelta;
        }

        public void paint(Graphics g) {
            g.setColor(color);
            g.fillOval(x, y, radius, radius);
            
        }
    } 
    public static class Rect{
        private int x, y, width;
        private final Color c;
        private int xDelta, yDelta;
        public Rect(int width, Color c){
            this.width = width;
            this.c = c;
        }
        public void setLocation(int x, int y){
            this.x = x;
            this.y = y;
        }
        public void setLocation(Point p) {
            setLocation(p.x, p.y);
        }
        public Point getLocation(){
            return new Point(x, y);
        }
        public void setYDelta(int yDelta){
            this.yDelta = yDelta;
        }
        public void setXDelta(int xDelta){
            this.xDelta = xDelta;
        }
        public int getyDelta(){
            return yDelta;
        }
        public int getxDelta(){
            return xDelta;
        }
        public void paint(Graphics g){
            g.setColor(c);
            g.fillRect(x, y, width, width);
        }
    }   
}  
   


