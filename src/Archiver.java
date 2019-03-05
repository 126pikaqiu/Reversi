import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: jiaxing liu
 * @Date: 2019/3/5 11:07
 */
public class Archiver {
    void archive(Reversi reversi) throws IOException, BiffException, WriteException {
        createXl();
        editXl(reversi);
    }

    private void createXl() throws IOException, WriteException {
        File file = new File("src/Reversi.xls");
        WritableWorkbook writableWorkbook;WritableSheet sheet;
        if(!file.exists()){
            writableWorkbook = Workbook.createWorkbook(file);
            writableWorkbook.createSheet("First Sheet", 0);
            writableWorkbook.write();
            writableWorkbook.close();
        }
    }
    private void editXl(Reversi reversi){
        File file = new File("src/Reversi.xls");
        Workbook workbook = null;
        WritableWorkbook wtbook = null;
        WritableSheet wtSheet = null;
        try {
            workbook = Workbook.getWorkbook(file);
            // jxl.Workbook 对象是只读的，所以如果要修改Excel，需要创建一个可写的副本，副本指向原Excel文件（即下面的new File(excelpath)）
            wtbook = Workbook.createWorkbook(file, workbook);
            wtSheet = wtbook.getSheet(0);
            int rawNum = wtSheet.getRows();
            String[] data = getData(reversi);
            for(int i = 0; i < 5; i++){
                Label lbl = new Label(i, rawNum, data[i]);
                wtSheet.addCell(lbl);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert wtbook != null;
                wtbook.write();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                wtbook.close();
            } catch (WriteException | IOException e) {
                e.printStackTrace();
            }
            workbook.close();
        }
    }
    private String[] getData(Reversi reversi){
        String[] data = new String[5];
        data[0] = getTime();
        data[1] = "" + reversi.getDimen() + "*" + reversi.getDimen();
        data[2] = reversi.getComputerColor() == Constant.WHITE?"human":"computer";
        data[3] = reversi.getComputerColor() == Constant.BLACK?"human":"computer";
        data[4] = reversi.getScore();
        return data;
    }
    private String getTime(){
        long l = System.currentTimeMillis();
        Date date = new Date(l);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");System.out.println(dateFormat.format(date));
        return dateFormat.format(date);
    }
}
