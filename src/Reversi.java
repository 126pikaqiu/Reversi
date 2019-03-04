import java.util.Scanner;

/**
 * @Author: jiaxing liu
 * @Date: 2019/3/2 13:05
 */
class Reversi {

    private Player computerPlayer = new Player();

    private Player player = new Player();

    private Chess chess;
    void start(){
        computerPlayer.start(chess);
        player.start(chess);
        int size = getSize();
        chess = new Chess(size);
        int color = getColor();
        computerPlayer.setColor(color);
        player.setColor(Constant.getAnotherColor(color));
        if(color == Constant.BLACK){
            computerPlayer.autoMove(player);
            printComputerMove();
            printChess();
        }
    }
    private boolean win(){
        return player.getScore() > computerPlayer.getScore() && player.getColor() == Constant.WHITE;
    }
    private boolean tie(){
        return player.getScore() == computerPlayer.getScore();
    }
    private String gameOver(){
        if(win()){
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
        int comScore = computerPlayer.getScore();
        int plyScore = player.getScore();
        if(computerPlayer.getColor() == Constant.BLACK){
            System.out.println("● : ○ = " + comScore + " : " + plyScore);
        }else{
            System.out.println("● : ○ = " + plyScore + " : " + comScore);
        }
    }
    private int getSize(){
        Scanner scanner = new Scanner(System.in);
        int size = 3;
        while (!(size % 2 == 0 && size >= 4 && size <= 26)){
            System.out.print(Constant.DIMENSION);
            size = scanner.nextInt();
            System.out.println();
        }
        return size;
    }
    private int getColor(){
        Scanner scanner = new Scanner(System.in);
        String color = "a";
        while (color.toUpperCase().charAt(0) != 'X' || color.toUpperCase().charAt(0) != 'O'){
            System.out.print(Constant.ENTER_COLOR);
            color = scanner.next();
            System.out.println();
        }
        return color.toUpperCase().charAt(0) == 'X'?Constant.BLACK:Constant.WHITE;
    }
    private int getDimen(){
        return chess.getDimension();
    }

    void play(){
        while (!checkGameOver()){
            playerMove();
            computerMove();
        }
        if((chess.checkDead(Constant.WHITE) && chess.checkDead(Constant.WHITE))) {
            System.out.println(Constant.BOTH_NO_VALID);
        }
        System.out.println(Constant.GAME_OVER);
        printScore();
        System.out.println(gameOver());
    }

    private Positon getPosition(){
        int color = player.getColor();
        int size = getDimen();
        Scanner scanner = new Scanner(System.in);
        String pos = "a1";
        while(!(pos.length() >= 2 && pos.charAt(0) >= 'a' && pos.charAt(0) <= 'a' + size - 1 && pos.charAt(1) >= 'a' && pos.charAt(1) <= 'a' + size - 1)){
            System.out.print("Enter move for " + (color == Constant.BLACK?"●":"○") + " (RowCol): ");
            pos = scanner.nextLine().trim();
        }
        return new Positon(pos.charAt(0) - 'a',pos.charAt(1) - 'a');
    }
    private void playerMove(){
        if(player.canMove()) {
            if(player.move(getPosition(),computerPlayer)){
                printChess();
            }else {
                System.out.println(Constant.INVALID_MOVE);
            }
        }
        else{
            printNoValid(player.getColor());
        }
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
}
