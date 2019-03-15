package util;

import constant.InfoConstant;
import entity.Position;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @Author: jiaxing liu
 * @Date: 2019/3/8 12:57
 */
public class Util {

    public static boolean isNumeric(String string){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(string).matches();
    }

    public static int getAnotherColor(int color){
        return color == InfoConstant.BLACK? InfoConstant.WHITE: InfoConstant.BLACK;
    }

    public static String getTime(){
        long l = System.currentTimeMillis();
        Date date = new Date(l);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        return dateFormat.format(date);
    }
    public static String color2char(int color){
        return color == InfoConstant.BLACK?InfoConstant.STRING_BLACK:InfoConstant.STRING_WHITE;
    }
    public static String position2string(Position position){
        return "" + (char)(position.getPosition_x() + 'a') + (char)(position.getPosition_y() + 'a');
    }
}
