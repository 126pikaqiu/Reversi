import java.util.Scanner;

/**
 * @Author: jiaxing liu
 * @Date: 2019/3/2 13:05
 */
class Reversi {

    private Player computerPlayer = new Player();

    private Player player = new Player();

    private boolean isGaveUp;
    long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    private long time;
    private Chess chess;
    void start(){
        int size = getSize();
        chess = new Chess(size);
        int color = getColor();
        computerPlayer.start(chess);
        player.start(chess);
        computerPlayer.setColor(color);
        player.setColor(Constant.getAnotherColor(color));
        if(color == Constant.BLACK){
            computerPlayer.autoMove(player);
            printComputerMove();
            printChess();
        }
    }
    private boolean oWin(){
        return player.getScore() > computerPlayer.getScore() && player.getColor() == Constant.WHITE;
    }
    private boolean tie(){
        return player.getScore() == computerPlayer.getScore();
    }
    private String gameOver(){
        if(oWin()){
            return Constant.OWIN;
        }else if(tie()){
            return Constant.TIE;
        }else {
            return Constant.XWIN;
        }
    }

    private boolean checkGameOver(){
        return (chess.checkDead(Constant.WHITE) && chess.checkDead(Constant.WHITE)) || chess.checkFull();
    }

    private void printComputerMove(){
        int color = computerPlayer.getColor();
        Positon positon = computerPlayer.getLastMove();
        if(color == Constant.BLACK){
            System.out.println("Computer places " + "● " + (char)(positon.getPosition_x() + 'a') + (char)(positon.getPosition_y() + 'a'));
        }else{
            System.out.println("Computer places " + "○ " + (char)(positon.getPosition_x() + 'a') + (char)(positon.getPosition_y() + 'a'));
        }
    }
    private void printChess(){
        String icons = Constant.ICONS;
        Piece[][] pieces = chess.getPieces();
        int size = pieces.length;
        System.out.print("   ");
        for(int i = 0; i < size; i++){
            System.out.print(" " + icons.charAt(i));
        }
        System.out.println();
        for(int i = 0; i < size; i++){
            System.out.print("　" + icons.charAt(i));
            for(int j = 0; j < size; j++){
                if(pieces[i][j] == null){
                    System.out.print(" ☐");
                }else if(pieces[i][j].getColor() == Constant.BLACK){
                    System.out.print(" ●");
                }else{
                    System.out.print(" ○");
                }
            }
            System.out.println();
        }
    }
    private void printNoValid(int color){
        System.out.print(color == Constant.WHITE?"○":"●" + " player has no valid move.");
    }
    private void printScore(){
        System.out.println("● : ○ = " + getScore());
    }
    private int getSize(){
        Scanner scanner = new Scanner(System.in);
        int size = 3;
        while (!(size % 2 == 0 && size >= 4 && size <= 26)){
            System.out.print(Constant.DIMENSION);
            size = scanner.nextInt();
        }
        return size;
    }
    private int getColor(){
        Scanner scanner = new Scanner(System.in);
        String color = "a";
        while (color.toUpperCase().charAt(0) != 'X' && color.toUpperCase().charAt(0) != 'O'){
            System.out.print(Constant.ENTER_COLOR);
            color = scanner.next();
        }
        return color.toUpperCase().charAt(0) == 'X'?Constant.BLACK:Constant.WHITE;
    }
    int getDimen(){
        return chess.getDimension();
    }

    void play(){
        time = System.currentTimeMillis();
        while (!checkGameOver()){
            if(!playerMove()){
                time = System.currentTimeMillis() - time;
                isGaveUp = true;
                return;
            }
            if(checkGameOver()) break;
            computerMove();
        }
        if((chess.checkDead(Constant.WHITE) && chess.checkDead(Constant.WHITE)) && !chess.checkFull()) {
            System.out.println(Constant.BOTH_NO_VALID);
        }
        System.out.println(Constant.GAME_OVER);
        printScore();
        System.out.println(gameOver());
        time = System.currentTimeMillis() - time;
    }

    private Positon getPosition(){
        int color = player.getColor();
        int size = getDimen();
        Scanner scanner = new Scanner(System.in);
        String pos = "a1";
        while(!(pos.length() >= 2 && pos.charAt(0) >= 'a' && pos.charAt(0) <= 'a' + size - 1 && pos.charAt(1) >= 'a' && pos.charAt(1) <= 'a' + size - 1)){
            System.out.print("Enter move for " + (color == Constant.BLACK?"●":"○") + " (RowCol): ");
            pos = scanner.nextLine().trim().toLowerCase();
        }
        return new Positon(pos.charAt(0) - 'a',pos.charAt(1) - 'a');
    }
    private boolean playerMove(){
        if(player.canMove()) {
            if(player.move(getPosition(),computerPlayer)){
                printChess();
            }else {
                System.out.println(Constant.INVALID_MOVE);
                System.out.println(Constant.GAME_OVER);
                if(player.getColor() == Constant.BLACK){
                    System.out.println(Constant.OWIN);
                }else{
                    System.out.println(Constant.XWIN);
                }
                return false;
            }
        }
        else{
            printNoValid(player.getColor());
        }
        return true;
    }
    private void computerMove(){
        if(computerPlayer.canMove()){
            computerPlayer.autoMove(player);
            printComputerMove();
            printChess();
        }else{
            printNoValid(computerPlayer.getColor());
        }
    }
    int getComputerColor(){
        return computerPlayer.getColor();
    }

    String getScore(){
        String score = "";
        int comScore = computerPlayer.getScore();
        int plyScore = player.getScore();
        if(computerPlayer.getColor() == Constant.BLACK){
            score = comScore + " : " + plyScore;
        }else{
            score = plyScore + " : " + comScore;
        }
        if(isGaveUp){
            score = Constant.HUMAN_GAVE_UP;
        }
        return score;
    }
}
