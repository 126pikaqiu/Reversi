import java.util.ArrayList;

/**
 * @Author: jiaxing liu
 * @Date: 2019/3/2 13:14
 */
class Chess {
    int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    private int dimension;

    Piece[][] getPieces() {
        return pieces;
    }

    public void setPieces(Piece[][] pieces) {
        this.pieces = pieces;
    }

    private Piece[][] pieces;
    Chess(int size){
        this.dimension = size;
        pieces = new Piece[size][size];
        addPiece(new Piece(Constant.WHITE,size / 2, size / 2));
        addPiece(new Piece(Constant.WHITE,size / 2 - 1, size / 2 - 1));
        addPiece(new Piece(Constant.BLACK,size / 2 - 1, size / 2));
        addPiece(new Piece(Constant.BLACK,size / 2, size / 2 - 1));
    }
    private Piece getPiece(int x,int y){ //根据位置获得棋子
        return pieces[x][y];
    }
    private void addPiece(Piece piece){
        int x = piece.getPositon().getPosition_x();
        int y = piece.getPositon().getPosition_y();
        pieces[x][y] = piece;
    }
    ArrayList<Positon> getOptions(int color){ //获得某个颜色的可选合法位置
        ArrayList<Positon> positons = new ArrayList<>();
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if(checkAvailab(i,j,color,false) > 0){
                    positons.add(new Positon(i,j));
                }
            }
        }
        return positons;
    }
    private int checkAvailab(int x, int y, int color, boolean isMove){ //返回某个位置放置棋子后需要翻转的棋子数目
        if(pieces[x][y] != null){
            return 0;
        }
        int score = 0;
        int[][] direction = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}};
        int index;
        for(int i = 0; i < 8; i++){
            index = 1;
            while (x + (index + 1) * direction[i][0] >= 0 && x + (index + 1) * direction[i][0] < dimension
                    && y + (index + 1) * direction[i][1] >= 0 && y + (index + 1) * direction[i][1] < dimension
                    && pieces[x + index * direction[i][0]][y + index * direction[i][1]] != null
                    && pieces[x + (index + 1) * direction[i][0]][y + (index + 1) * direction[i][1]] != null
                    && pieces[x + index * direction[i][0]][y + index * direction[i][1]].getColor() == Constant.getAnotherColor(color)){
                if(pieces[x + (index + 1) * direction[i][0]][y + (index + 1) * direction[i][1]].getColor() == color && !isMove){
                    score += index;
                    break;
                }else if(pieces[x + (index + 1) * direction[i][0]][y + (index + 1) * direction[i][1]].getColor() == color){
                    score += index;
                    for(int j = 1; j <= index; j++){
                        pieces[x + j * direction[i][0]][y + j * direction[i][1]].changeColor();
                    }
                    break;
                }
                index++;
            }
        }
        return score;
    }
    int checkAvailab(Positon positon,int color,boolean isMove){
        return checkAvailab(positon.getPosition_x(),positon.getPosition_y(),color,isMove);
    }
    boolean checkFull(){ //检测棋盘是否被填满
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if(pieces[i][j] == null){
                    return false;
                }
            }
        }
        return true;
    }
    int move(int x,int y,int color){
        int count = checkAvailab(x,y,color,true);
        addPiece(new Piece(color,x,y));
        return count;
    }
    void move(Positon positon, int color){
        move(positon.getPosition_x(), positon.getPosition_y(), color);
    }

    boolean checkDead(int color){ //无子可下
        return getOptions(color).size() == 0;
    }
}
