package entity;

import constant.InfoConstant;

/**
 * @Author: jiaxing liu
 * @Date: 2019/3/2 12:43
 */
class Piece {
    private int color;//颜色
    private Position positon;//位置
    Piece(int color){
        this.color = color;
    }
    Piece(int color,int position_x,int position_y){
        this.color = color;
        positon = new Position(position_x,position_y);
    }
    Position getPositon(){
        return positon;
    }
    int getColor(){
        return color;
    }
    void setColor(int color){
        this.color = color;
    }
    void changeColor(){
        if(this.color == InfoConstant.BLACK){
            this.color = InfoConstant.WHITE;
        }else {
            this.color = InfoConstant.BLACK;
        }
    }
}
