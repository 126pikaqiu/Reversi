import java.util.regex.Pattern;

/**
 * @Author: jiaxing liu
 * @Date: 2019/3/8 12:57
 */
class Util {

    static boolean isNumeric(String string){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(string).matches();
    }
}
