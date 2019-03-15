package entity;

/**
 * @Author: jiaxing liu
 * @Date: 2019/3/2 12:49
 */
public class Position {
    private int position_x;
    private int position_y;
    Position(int x, int y){
        position_x = x;
        position_y = y;
    }
    public int getPosition_x(){
        return position_x;
    }
    public int getPosition_y(){
        return position_y;
    }
}
