/**
 * @Author: jiaxing liu
 * @Date: 2019/3/2 12:44
 */
class Constant {
    static String ICONS = "ⓐⓑⓒⓓⓔⓕⓖⓗⓘⓙⓚⓛⓜⓝⓞⓟⓠⓡⓢⓣⓤⓥⓦⓧⓨⓩ";
    static final int  BLACK = 0;
    static final int  WHITE = 1;
    static int getAnotherColor(int color){
        return color == BLACK?WHITE:BLACK;
    }
    static final String OWIN = "○ player wins. ";
    static final String XWIN = "● player wins. ";
    static final String TIE = "The game draws. ";
    static final String BOTH_NO_VALID = "Both players have no valid move. ";
    static final String INVALID_MOVE = "Invalid move. ";
    static final String GAME_OVER = "Game over. ";
    static final String DIMENSION = "Enter the board dimension:";
    static final String ENTER_COLOR = "Computer plays (X/O):";
}
