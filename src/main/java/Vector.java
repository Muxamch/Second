import org.apache.log4j.Logger;
import lombok.*;

import java.util.ArrayList;


@Getter
//@AllArgsConstructor
@ToString
public class Vector {
    private static final Logger LOGGER = Logger.getLogger(Vector.class);
    private int x;
    private int y;

    public Vector(){
        this.x = 0;
        this.y = 0;
    }
    public Vector(int x, int y){
        this.x = x;
        this.y = y;
    }

    //сумма векторов. новый
    public static Vector getSumOfVector(ArrayList<Vector> vectors){
        Vector vectorNew = new Vector();
        for(Vector vector : vectors){
            vectorNew.vectorAdd(vector);
        }
        return vectorNew;
    }

    //Прибавить вектор к существующему вектору
    public Vector vectorAdd(Vector newV){
        this.x += newV.x;
        this.y += newV.y;

        return this;
    }

    //Длина вектора
    public static double getVectorLength(Vector vector){

        return Math.sqrt(vector.x * vector.x + vector.y * vector.y);
    }

    //Найти вектор между двумя точками
    public static Vector getVectorFromPoint(Point point1, Point point2){

        return new Vector(point2.getX() - point1.getX(), point2.getY() - point1.getY());
    }

    //вычитание вектора. новый
    public static Vector getSubtractionOfVector(Vector vector1, Vector vector2){
        Vector vectorNew = new Vector();
        vectorNew.x = vector1.x - vector2.x;
        vectorNew.y = vector1.y - vector2.y;

        return vectorNew;
    }

    //отнять вектор от существующего
    public Vector vectorSub(Vector newV){
        this.x -= newV.x;
        this.y -= newV.y;

        return this;
    }

    //Угол между векторами
    public static double getAngleBetweenVectors(Vector vector1, Vector vector2) throws Exception{
        if((vector1.x == 0 && vector1.y == 0) || (vector2.x == 0 && vector2.y == 0)){
            throw new Exception("Нельзя посчитать угол нулевого вектора");
        }
        double angle = (vector1.x * vector2.x + vector1.y * vector2.y)/(Math.sqrt(vector1.x * vector1.x + vector1.y * vector1.y) * Math.sqrt(vector2.x * vector2.x + vector2.y * vector2.y));
        //LOGGER.info(angle);
        angle = Math.acos(angle);
        angle = Math.toDegrees(angle);
        LOGGER.info(angle);
        return angle;
    }
}
