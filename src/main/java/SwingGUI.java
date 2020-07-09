import org.apache.log4j.Logger;
import lombok.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

@Getter
@Setter
public class SwingGUI { // здесь было наследование от Listener
    private static final Logger LOGGER = Logger.getLogger(SwingGUI.class);
    private static final int AmountVECTORS = 2; // количество векторов (для суммы)

    private JFrame frame, resultFrame;
    private JLabel label;
    private JButton buttonSum, buttonSub, buttonLength, buttonAngle, buttonReset;
    private ArrayList<JTextField> jTextFieldsX;
    private ArrayList<JTextField> jTextFieldsY;
    private Vector newVector;
    private DrawVector drawVector;
    private ArrayList<Vector> vectorsFromTextFields;

    SwingGUI(){
        this.frame = new JFrame("Vectors");
        frame.setSize(300,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.resultFrame = new JFrame("Result");
        resultFrame.setSize(250,100);
        resultFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        resultFrame.setLocationRelativeTo(frame);

        this.label = new JLabel("Empty");

        buttonSum = new JButton("Сумма");
        buttonSum.addActionListener(this::actionPerformed);
        buttonSub = new JButton("Разница");
        buttonSub.addActionListener(this::actionPerformed);
        buttonLength = new JButton("Длина");
        buttonLength.addActionListener(this::actionPerformed);
        buttonAngle = new JButton("Угол");
        buttonAngle.addActionListener(this::actionPerformed);
        buttonReset = new JButton("Reset");
        buttonReset.addActionListener(this::actionPerformed);

        jTextFieldsX = new ArrayList<>();
        jTextFieldsY = new ArrayList<>();
        setJTextFieldVectors();
        addJTextFieldVectors();

        frame.add(buttonSum);
        frame.add(buttonSub);
        frame.add(buttonLength);
        frame.add(buttonAngle);
        frame.add(buttonReset);

        resultFrame.add(label);
        resultFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 25 ,25));
        frame.setVisible(true);
    }

    private void actionPerformed(ActionEvent event){
        String command = event.getActionCommand();
        switch (command) {
            case "Сумма" -> {
                if(!checkForErrorEmptyBox()){
                    errorBox(false);
                    break;
                }
                ArrayList<Vector> vectors = getVectors(AmountVECTORS); //количество векторов для суммы
                this.newVector = Vector.getSumOfVector(vectors);
                label.setText("New " + this.newVector.toString());
                initiateDraw();
                resultFrame();
            }
            case "Разница" -> {
                int firstV = 0; //индексы векторов для вычитания
                int secondV = 1; //
                if(!checkForErrorEmptyBox(firstV) && !checkForErrorEmptyBox(secondV)){
                    errorBox(false);
                    break;
                }
                ArrayList<Vector> vectors = getVectors(firstV,secondV);
                this.newVector = Vector.getSubtractionOfVector(vectors.get(firstV), vectors.get(secondV));
                label.setText("New " + this.newVector.toString());
                initiateDraw();
                resultFrame();
            }
            case "Длина" -> {
                int indexOfVector = 0; // индекс вектора для вычислений
                if(!checkForErrorEmptyBox(indexOfVector)){
                    errorBox(false);
                    break;
                }
                this.newVector = new Vector(Integer.parseInt(jTextFieldsX.get(indexOfVector).getText()), Integer.parseInt(jTextFieldsY.get(indexOfVector).getText()));
                label.setText("Длина = "+ Vector.getVectorLength(newVector));
                initiateDraw();
                resultFrame();
            }
            case "Угол" -> {
                int firstV = 0; //индексы векторов для вычислений
                int secondV = 1;
                if((jTextFieldsX.get(firstV).getText().equals("0") && jTextFieldsY.get(firstV).getText().equals("0") ||
                        (jTextFieldsX.get(secondV).getText().equals("0") && jTextFieldsY.get(secondV).getText().equals("0")))) {
                    errorBox(true);
                    break;
                }
                if(!checkForErrorEmptyBox(firstV) && !checkForErrorEmptyBox(secondV)){
                    errorBox(false);
                    break;
                }
                try{
                    label.setText("Угол = "+ Vector.getAngleBetweenVectors(new Vector(Integer.parseInt(jTextFieldsX.get(firstV).getText()),
                            Integer.parseInt(jTextFieldsY.get(firstV).getText())),
                            new Vector(Integer.parseInt(jTextFieldsX.get(secondV).getText()),
                                    Integer.parseInt(jTextFieldsY.get(secondV).getText()))
                            ));
                } catch (Exception e) {
                    LOGGER.error("error : ",e);
                }
                resultFrame();
            }
            case "Reset" -> {
                for (int i = 0; i <AmountVECTORS; i++) {
                    jTextFieldsX.get(i).setText(" vector "+i+" x ");
                    jTextFieldsY.get(i).setText(" vector "+i+" y ");
                }
                resultFrame.setVisible(false);
                resultFrame.dispose();
            }
        }
    }

    private ArrayList<Vector> getVectors (int AMOUNT){
        ArrayList<Vector> vectors = new ArrayList<>();
        for (int i = 0; i < AMOUNT ; i++) {
            Vector temp1 = new Vector(Integer.parseInt(jTextFieldsX.get(i).getText()),
                    Integer.parseInt(jTextFieldsY.get(i).getText()));
            vectors.add(temp1);
        }
        return vectors;
    }

    private ArrayList<Vector> getVectors(int firstVector, int secondVector) // индексы векторов
    {
        ArrayList<Vector> vectors = new ArrayList<>();
        vectors.add(new Vector((Integer.parseInt(jTextFieldsX.get(firstVector).getText())),
                Integer.parseInt(jTextFieldsY.get(firstVector).getText())));
        vectors.add(new Vector((Integer.parseInt(jTextFieldsX.get(secondVector).getText())),
                Integer.parseInt(jTextFieldsY.get(secondVector).getText())));
        return vectors;
    }

    private void resultFrame(){
        this.resultFrame.setVisible(true);
    }

    private void errorBox(boolean angleFlag)
    {
        if(angleFlag){
            JOptionPane.showMessageDialog(null, "Нельзя посчитать угол с нулевым вектором");
        }
        else {
            JOptionPane.showMessageDialog(null, "Заполните пожалуйста все нужные поля только числами");
        }
    }
    private boolean checkForErrorEmptyBox(){
        boolean noMistake = false;
        for (int i = 0; i < AmountVECTORS; i++) {
                jTextFieldsX.get(i).setText(jTextFieldsX.get(i).getText().replaceAll("\\s+",""));
                jTextFieldsY.get(i).setText(jTextFieldsY.get(i).getText().replaceAll("\\s+",""));
                if(jTextFieldsX.get(i).getText().matches("-?\\d+(\\.\\d+)?") &&
                        jTextFieldsY.get(i).getText().matches("-?\\d+(\\.\\d+)?")){
                    noMistake = true;
                }
            }
        return  noMistake;
    }
    private boolean checkForErrorEmptyBox(int indexVector){
        boolean noMistake = false;
        jTextFieldsX.get(indexVector).setText(jTextFieldsX.get(indexVector).getText().replaceAll("\\s+",""));
        jTextFieldsY.get(indexVector).setText(jTextFieldsY.get(indexVector).getText().replaceAll("\\s+",""));
        if(jTextFieldsX.get(indexVector).getText().matches("-?\\d+(\\.\\d+)?") &&
                jTextFieldsY.get(indexVector).getText().matches("-?\\d+(\\.\\d+)?")
        ){
            noMistake = true;
        } 
        return noMistake;
    }
    public void initiateDraw(){
        this.drawVector = new DrawVector(this);
        drawVector.setNewVector(this.newVector);
        drawVector.init();
    }
    public void closeDraw(){
        this.drawVector = null;
    }

    private void setJTextFieldVectors(){
        for (int i = 0; i < AmountVECTORS; i++) {
            jTextFieldsX.add(new JTextField(" vector "+i+" x "));
            jTextFieldsY.add(new JTextField(" vector "+i+" y "));
        }
    }
    private void addJTextFieldVectors(){
        for (int i = 0; i < AmountVECTORS; i++) {
            this.frame.add(this.jTextFieldsX.get(i));
            this.frame.add(this.jTextFieldsY.get(i));
        }
    }
}