/**
 * @Author: jiaxing liu
 * @Date: 2019/3/2 12:43
 */
class Piece {
    private int color;//颜色
    private Positon positon;//位置
    Piece(int color){
        this.color = color;
    }
    Piece(int color,int position_x,int position_y){
        this.color = color;
        positon = new Positon(position_x,position_y);
    }
    Positon getPositon(){
        return positon;
    }
    int getColor(){
        return color;
    }
    void setColor(int color){
        this.color = color;
    }
    void changeColor(){
        if(this.color == Constant.BLACK){
            this.color = Constant.WHITE;
        }else {
            this.color = Constant.BLACK;
        }
    }
}
