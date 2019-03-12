import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import java.io.IOException;

/**
 * @Author: jiaxing liu
 * @Date: 2019/3/4 16:38
 */
public class Test {
    public static void main(String[] args) throws WriteException, IOException, BiffException {
        Reversi reversi = new Reversi();
        reversi.start();
        reversi.play();
        reversi.archive();
    }
}
