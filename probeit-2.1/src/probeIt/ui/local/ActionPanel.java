package probeIt.ui.local;


import java.awt.*; 
import java.awt.event.*; 
import java.awt.font.*; 
import java.awt.image.BufferedImage; 
import javax.swing.*;  

class ActionPanel extends JPanel 
{ 
    String text; 
    Font font;
    boolean selected; 
    BufferedImage open, closed; 
    Rectangle target; 
    
    final int OFFSET = 30, PAD    =  5; 

    public ActionPanel(String text, MouseListener ml) 
    { 
        this.text = text; 
        //addMouseListener(ml); 
        font = new Font("sans-serif", Font.PLAIN, 35); 
        selected = false; 
        setBackground(new Color(200,210,235)); 
        setPreferredSize(new Dimension(200,40)); 
        setBorder(BorderFactory.createRaisedBevelBorder());
        setPreferredSize(new Dimension(200,40)); 
        createImages(); 
        addMouseListener(ml);
        setRequestFocusEnabled(true);
    } 

    public void toggleSelection() 
    { 
        selected = !selected;
        repaint(); 
    }

    protected void paintComponent(Graphics g) 
    { 
        super.paintComponent(g); 
        Graphics2D g2 = (Graphics2D)g; 
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON); 
        int w = getWidth(); 
        int h = getHeight(); 
        if(selected) 
            g2.drawImage(open, PAD, 0, this); 
        else 
            g2.drawImage(closed, PAD, 0, this); 
        g2.setFont(font); 
        FontRenderContext frc = g2.getFontRenderContext(); 
        LineMetrics lm = font.getLineMetrics(text, frc); 
        float height = lm.getAscent() + lm.getDescent(); 
        float x = OFFSET; 
        float y = (h + height)/2 - lm.getDescent(); 
        g2.drawString(text, x, y); 
    } 

    private void createImages() 
    { 
        int w = 20; 
        int h = getPreferredSize().height; 
        target = new Rectangle(2, 0, 20, 18); 
        open = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB); 
        Graphics2D g2 = open.createGraphics(); 
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON); 
        g2.setPaint(getBackground()); 
        g2.fillRect(0,0,w,h); 
        int[] x = { 2, w/2, 18 }; 
        int[] y = { 4, 15,   4 }; 
        Polygon p = new Polygon(x, y, 3); 
        g2.setPaint(Color.green.brighter()); 
        g2.fill(p); 
        g2.setPaint(Color.blue.brighter()); 
        g2.draw(p); 
        g2.dispose(); 
        closed = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB); 
        g2 = closed.createGraphics(); 
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON); 
        g2.setPaint(getBackground()); 
        g2.fillRect(0,0,w,h); 
        x = new int[] { 3, 13,   3 }; 
        y = new int[] { 4, h/2, 16 }; 
        p = new Polygon(x, y, 3); 
        g2.setPaint(Color.red); 
        g2.fill(p); 
        g2.setPaint(Color.blue.brighter()); 
        g2.draw(p); 
        g2.dispose(); 
    } 
}  