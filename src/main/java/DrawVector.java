import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Line2D;
import lombok.*;
import lombok.extern.java.Log;
import org.apache.log4j.Logger;


/*

TODO

 */
public class DrawVector extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(DrawVector.class);

    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;
    private SwingGUI frameGUI;
    private Vector newVector;

    public DrawVector(SwingGUI frameGUI){
        this.frameGUI = frameGUI;

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        setSize(WIDTH,HEIGHT);

        JButton buttonClose = new JButton("Close");
        buttonClose.setBounds(299,299,50,50);
        buttonClose.addActionListener(this::actionPerformed);
        //this.add(buttonClose);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.GREEN);
        //Line2D lin = new Line2D.Float(WIDTH/2, HEIGHT/2, Integer.parseInt(frameGUI.getVector1x().getText()), Integer.parseInt(frameGUI.getVector1y().getText()));
        g2.setColor(Color.BLUE);
        //Line2D lin2 = new Line2D.Float(WIDTH/2, HEIGHT/2, Integer.parseInt(frameGUI.getVector2x().getText()), Integer.parseInt(frameGUI.getVector2y().getText()));
        g2.setColor(Color.RED);
        Line2D lin3= null;
        if(this.newVector != null){
            lin3 = new Line2D.Float(WIDTH/2, HEIGHT/2, this.newVector.getX(), this.newVector.getY());
        }
        //g2.draw(lin);
        //g2.draw(lin2);
        try{
            g2.draw(lin3);
        } catch (Exception e){
            LOGGER.error(e);
        }

    }

    public void setNewVector(Vector vector){
        this.newVector = vector;
    }

    public void init(){
        this.setVisible(true);
    }

    private void actionPerformed(ActionEvent event){
        this.frameGUI.closeDraw();
    }
    //public void closeFrame(){
    //    this.setVisible(false);
    //    this.dispose();
    //}
}

