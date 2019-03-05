import java.util.ArrayList;

/**
 * @Author: jiaxing liu
 * @Date: 2019/3/3 17:39
 */
public class Player {
    private Chess chess;

    Positon getLastMove() {
        return lastMove;
    }

    public void setLastMove(Positon lastMove) {
        this.lastMove = lastMove;
    }

    private Positon lastMove;
    int getColor() {
        return color;
    }

    Player(){
        score = 2;
    }

    void setColor(int color) {
        this.color = color;
    }

    private int color;

    int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int score;//分数

    void start(Chess chess){
        this.chess = chess;
    }

    private boolean move(int x, int y,Player player){
        int zscore = chess.move(x,y,color);
        score += zscore + (zscore > 0?1:0);
        player.decreaseScore(zscore);
        return zscore > 0;

    }
    private void decreaseScore(int count){
        score -= count;
    }
    boolean move(Positon positon,Player player){
        return move(positon.getPosition_x(),positon.getPosition_y(),player);
    }

    boolean autoMove(Player player){
        ArrayList<Positon> positons = chess.getOptions(color);
        if(positons.size() == 0){
            return false;//无路可走
        }
        int index = 0;int score = 0;int zscore;
        for(int i = 0; i < positons.size(); i++){
            if((zscore = chess.checkAvailab(positons.get(i),color,false)) > score){
                index = i;
                score = zscore;
            }
        }
        player.decreaseScore(score);
        this.score += score + 1;
        lastMove = positons.get(index);
        chess.move(positons.get(index), color);
        return true;
    }
    boolean canMove(){
        return !chess.checkDead(color);
    }
}
