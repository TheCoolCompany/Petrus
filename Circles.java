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
        protected static final int MAX_CIRCLES = 1 + ThreadLocalRandom.current().nextInt(30, 1000);
        protected static final int MAX_Hail = 1 + ThreadLocalRandom.current().nextInt(200, 400);
        int xSpeed = 3;
        int Petri = 2; 
        int lol = 5;
        protected int maxsun = 1;
        private int oldPetri = 0;
        private float t = 0;
        public TestPane() {
            
            circles = new ArrayList<>(MAX_CIRCLES);
            
            Timer timer = new Timer(20, new ActionListener() {
                @Override
                
                public void actionPerformed(ActionEvent e) {
                    t += 0.02;
                    if(oldPetri != Petri){
                        System.out.println(t);
                        circles.removeAll(circles);
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
                        List<Circle> stopmove = new ArrayList<>(MAX_CIRCLES);
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
            int radius = 3 + rnd.nextInt(10);           
            int speedy = 1 + ThreadLocalRandom.current().nextInt(1, 3);
            int speedx = speedy + ThreadLocalRandom.current().nextInt(1, 10);
            int y = 5;
            if (x + radius > getWidth()) {
                x = getWidth() - radius;
            }
            Circle circle = new Circle(radius, new Color(100, 0, 255));
            circle.setLocation(x, y);
            circle.setYDelta(speedx); 
            circle.setXDelta(speedy);  
            return circle;

        }
        protected Circle hail() 
        {
            int x = rnd.nextInt(getWidth());
            int radius = 10;//rnd.nextInt(10);           
            int speedy = 5 + ThreadLocalRandom.current().nextInt(1, 10);
            int y = 5;
            if (x + radius > getWidth()) {
                x = getWidth() - radius;
            }
            Circle circle = new Circle(radius, new Color(128, 128, 128));
            circle.setLocation(x, y);
            circle.setYDelta(speedy);  
            return circle;

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
  
   
}  
   


