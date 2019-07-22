package main.java.game;

public class RunProgram {

/////!!!!!!!!!!!!!!!!!!!!to run the program from command line :1.cd to engineering-task
///                                          2. mvn clean install
///                                          3. mvn exec:java -Dexec.mainClass=main.java.game.RunProgram
//      or just export as a jar

    /**
     * here the program starts
     *
     */
    public static void main(String[] args) {
        //size of GameBoard
        final int height = 10;
        final int width = 10;
        GameBoard gameBoard = new GameBoard(height, width);
        gameBoard.placeCarrier();
        gameBoard.placeCarrier();
        gameBoard.placeSubmarine();
        gameBoard.placeSubmarine();
        gameBoard.placeCruiser();
        gameBoard.placeCruiser();
        gameBoard.placeDestroyer();
        gameBoard.placeDestroyer();
        gameBoard.printGameBoard();
    }
}
