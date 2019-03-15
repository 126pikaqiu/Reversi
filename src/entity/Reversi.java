package entity;

import constant.FileConstant;
import constant.InfoConstant;
import util.FileUtil;
import util.Util;

import java.text.MessageFormat;
import java.util.Scanner;

/**
 * @Author: jiaxing liu
 * @Date: 2019/3/2 13:05
 */
public class Reversi {

    private Player computerPlayer = new Player();

    private Player player = new Player();

    private boolean isGaveUp;
    private long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    private long time;
    private Chess chess;

    public void start(){
        int size = getSize();
        chess = new Chess(size);
        int color = getColor();
        computerPlayer.start(chess);
        player.start(chess);
        computerPlayer.setColor(color);
        player.setColor(Util.getAnotherColor(color));
        if(color == InfoConstant.BLACK){
            computerPlayer.autoMove(player);
            printComputerMove();
            printChess();
        }
    }
    private boolean oWin(){
        return player.getScore() > computerPlayer.getScore() && player.getColor() == InfoConstant.WHITE;
    }
    private boolean tie(){
        return player.getScore() == computerPlayer.getScore();
    }
    private String gameOver(){
        if(oWin()){
            return InfoConstant.OWIN;
        }else if(tie()){
            return InfoConstant.TIE;
        }else {
            return InfoConstant.XWIN;
        }
    }

    private boolean checkGameOver(){
        return (chess.checkDead(InfoConstant.WHITE) && chess.checkDead(InfoConstant.WHITE)) || chess.checkFull();
    }

    private void printComputerMove(){
        int color = computerPlayer.getColor();
        Position positon = computerPlayer.getLastMove();
        System.out.println(MessageFormat.format(InfoConstant.COMPUTER_MOVE,
                Util.color2char(color),Util.position2string(positon)));
    }

    private void printChess(){
        String icons = InfoConstant.ICONS;
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
                }else if(pieces[i][j].getColor() == InfoConstant.BLACK){
                    System.out.print(" ●");
                }else{
                    System.out.print(" ○");
                }
            }
            System.out.println();
        }
    }

    private void printNoValid(int color){
        System.out.print(MessageFormat.format(InfoConstant.HAS_NO_VALID_MOVE,Util.color2char(color)));
    }
    private void printScore(){
        System.out.println("● : ○ = " + getScore());
    }
    private int getSize(){
        Scanner scanner = new Scanner(System.in);
        int size = 3;
        while (!(size % 2 == 0 && size >= 4 && size <= 26)){
            System.out.print(InfoConstant.DIMENSION);
            size = scanner.nextInt();
        }
        return size;
    }
    private int getColor(){
        Scanner scanner = new Scanner(System.in);
        String color = "a";
        while (color.toUpperCase().charAt(0) != 'X' && color.toUpperCase().charAt(0) != 'O'){
            System.out.print(InfoConstant.ENTER_COLOR);
            color = scanner.next();
        }
        return color.toUpperCase().charAt(0) == 'X'? InfoConstant.BLACK: InfoConstant.WHITE;
    }

    public void play(){
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
        if((chess.checkDead(InfoConstant.WHITE) && chess.checkDead(InfoConstant.WHITE)) && !chess.checkFull()) {
            System.out.println(InfoConstant.BOTH_NO_VALID);
        }
        System.out.println(InfoConstant.GAME_OVER);
        printScore();
        System.out.println(gameOver());
        time = System.currentTimeMillis() - time;
    }

    private Position getPosition(){
        int color = player.getColor();
        int size = chess.getDimension();
        Scanner scanner = new Scanner(System.in);
        String pos = "a1";
        while(!(pos.length() >= 2 && pos.charAt(0) >= 'a' && pos.charAt(0) <= 'a' + size - 1 && pos.charAt(1) >= 'a' && pos.charAt(1) <= 'a' + size - 1)){
            System.out.print(MessageFormat.format(InfoConstant.PLAYER_ENTER,Util.color2char(color)));
            pos = scanner.nextLine().trim().toLowerCase();
        }
        return new Position(pos.charAt(0) - 'a',pos.charAt(1) - 'a');
    }
    private boolean playerMove(){
        if(player.canMove()) {
            if(player.move(getPosition(),computerPlayer)){
                printChess();
            }else {
                System.out.println(InfoConstant.INVALID_MOVE);
                System.out.println(InfoConstant.GAME_OVER);
                if(player.getColor() == InfoConstant.BLACK){
                    System.out.println(InfoConstant.OWIN);
                }else{
                    System.out.println(InfoConstant.XWIN);
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

    private String[] getData(){
        String[] data = new String[6];
        data[0] = Util.getTime();
        data[1] = "" + (getTime() / 1000);
        data[2] = "" + chess.getDimension() + "*" + chess.getDimension();
        data[3] = computerPlayer.getColor() == InfoConstant.WHITE?"human":"computer";
        data[4] = computerPlayer.getColor() == InfoConstant.BLACK?"human":"computer";
        data[5] = getScore();
        return data;
    }

    private String getScore(){
        String score;
        int comScore = computerPlayer.getScore();
        int plyScore = player.getScore();
        if(computerPlayer.getColor() == InfoConstant.BLACK){
            score = comScore + " : " + plyScore;
        }else{
            score = plyScore + " : " + comScore;
        }
        if(isGaveUp){
            score = InfoConstant.HUMAN_GAVE_UP;
        }
        return score;
    }
    public void archive(){
        FileUtil.write(getData(), FileConstant.FILE_PATH);
    }
}
