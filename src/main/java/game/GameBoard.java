package main.java.game;

import java.util.*;

public class GameBoard {

    //instance variable

    private int height;
    private int width;
    private String[][] gameBoard;
    private Set<Integer> availableIndexes;

    /**
     * getter for free indexes set
     *
     * @return
     */
    public Set<Integer> getAvailableIndexes() {
        return availableIndexes;
    }


    /**
     * constructor
     *
     * @param height height of board
     * @param width  width of board
     */
    public GameBoard(int height, int width) {
        this.width = width;
        this.height = height;
        this.availableIndexes = new HashSet<>();
        this.gameBoard = initializeBoard(new String[height][width]);
    }


    /**
     * initializes game board with '*'
     *
     * @param array array to initialize
     * @return
     */
    private String[][] initializeBoard(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = "* ";
                availableIndexes.add(i * array[0].length + j);
            }
        }
        return array;
    }


    /**
     * remove used index from set of available indexes so it could not be chosen again.
     *
     * @param index what index to remove
     */
    public void removeUsedIndex(int index) {
        if (availableIndexes.contains(index)) {
            availableIndexes.remove(index);
        }
    }


    /**
     * given number for etc 0-99 in board of 10x10 translate ut to clolumn height
     *
     * @param index what index to convert
     * @return index converted to height
     */
    public int indexToHeightConverotr(int index) {
        return index / width;
    }


    /**
     * given number for etc 0-99 in board of 10x10 translate ut to clolumn width.
     *
     * @param index what index to convert
     * @return index converted to width
     */
    public int indexToWidthConverotr(int index) {
        return index % height;
    }

    /**
     * prints the board
     */
    public void printGameBoard() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                System.out.print(gameBoard[i][j]);
            }
            System.out.print("\n");
        }
    }

    /**
     * get free (unused) index
     *
     * @return
     */
    public int getRandomIndex() {

        int size = availableIndexes.size();
        int item = new Random().nextInt(size);
        List<Integer> newTemArray = new ArrayList<Integer>(availableIndexes);
        return newTemArray.get(item);
    }

    /**
     * given index palce  mark of ship on board :'s' for submarine ,'d' for destroyer,'c' for cruiser,'a' for aircraft.
     *
     * @param index index needed to be marked on gameboard.
     * @param mark 'a' ,'c','d','s' according to ships type
     */
    public void placeMarkOnBoard(int index, String mark) {
        int height = indexToHeightConverotr(index);
        int width = indexToWidthConverotr(index);
        gameBoard[height][width] = mark;
        removeUsedIndex(index);
    }

    /**
     * given set of indexes and mark it places the mark on all of the sets indexes on gameboard.
     * @param listToMark indexes needed to be marked
     * @param mark 'a' ,'c','d','s' according to ships type
     */
    public void placeMarkSetOnBoard(Set<Integer> listToMark, String mark) {
        for (int index : listToMark) {
            placeMarkOnBoard(index, mark);
        }
    }
    ///////////ships and submarine operation

    /**
     * given current index and numbersToGenerate,generates set of new indexes in ascending order for row
     * @param currentIndex index to start from
     * @param numbersToGenerate number of indexes to geneerate including current index
     * @return set of generated indexes
     */
    public Set<Integer> generateAscRowIndexes(int currentIndex, int numbersToGenerate) {
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < numbersToGenerate; i++) {
            set.add(currentIndex + i);
        }
        return set;
    }

    /**
     * given current index and numbersToGenerate,generates set of new indexes in descending order for row
     * @param currentIndex index to start from
     * @param numbersToGenerate number of indexes to geneerate including current index
     * @return set of generated indexes
     */
    public Set<Integer> generateDescRowIndexes(int currentIndex, int numbersToGenerate) {
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < numbersToGenerate; i++) {
            set.add(currentIndex - i);
        }
        return set;
    }

    /**
     * given current index and numbersToGenerate,generates set of new indexes in ascending order for column,jumps
     * are by width between each new generated index
     * @param currentIndex index to start from
     * @param numbersToGenerate number of indexes to geneerate including current index
     * @return set of generated indexes
     */
    public Set<Integer> generateAscColumnIndexes(int currentIndex, int numbersToGenerate) {
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < numbersToGenerate; i++) {
            set.add(currentIndex + i * width);
        }
        return set;
    }

    /**
     * given current index and numbersToGenerate,generates set of new indexes in descending order for column,jumps
     * are by width between each new generated index
     * @param currentIndex index to start from
     * @param numbersToGenerate number of indexes to geneerate including current index
     * @return set of generated indexes
     */
    public Set<Integer> generateDescColumnIndexes(int currentIndex, int numbersToGenerate) {
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < numbersToGenerate; i++) {
            set.add(currentIndex - i * width);
        }
        return set;
    }

    /**
     * places ship horizontal at first it tries from the index to its left left of the board, if failed it tries to its right
     * @param currentIndex free index
     * @param marksNumber number of marks on board
     * @param mark 'a' ,'c','d','s' according to ships type
     * @return if succeeded true otherwise false
     */
    public boolean placeHorizontal(int currentIndex, int marksNumber, String mark) {
        boolean isPlaced = false;
        int widthIndex = indexToWidthConverotr(currentIndex);
        ///is on same row ,try to mark from cuurrent index to marksnumber time to the left
        if ((widthIndex - marksNumber + 1 >= 0) && (!isPlaced)) {
            Set<Integer> leftRowSet = generateDescRowIndexes(currentIndex, marksNumber);
            if (getAvailableIndexes().containsAll(leftRowSet)) {
                isPlaced = true;
                ///mark to the left vertical
                placeMarkSetOnBoard(leftRowSet, mark);
            }
        }
        ///is on same row ,try to mark from cuurrent index to marksnumber time to the right
        if ((widthIndex + marksNumber - 1 < width) && (!isPlaced)) {
            Set<Integer> rightRowSet = generateAscRowIndexes(currentIndex, marksNumber);
            if (getAvailableIndexes().containsAll(rightRowSet)) {
                isPlaced = true;
                ///mark to the left vertical
                placeMarkSetOnBoard(rightRowSet, mark);
            }
        }
        return isPlaced;
    }

    /**
     * places ship vertical at first it tries from the index to top of the board, if failed it tries to bottom
     * @param currentIndex free index
     * @param marksNumber number of marks on board
     * @param mark 'a' ,'c','d','s' according to ships type
     * @return if succeeded true otherwise false
     */
    public boolean placeVertical(int currentIndex, int marksNumber, String mark) {
        boolean isPlaced = false;
        int heightIndex = indexToHeightConverotr(currentIndex);
        ///is on same row ,try to mark from cuurrent index to marksnumber time to the top
        if ((heightIndex + (-marksNumber + 1) * width >= 0) && (!isPlaced)) {
            Set<Integer> topRowSet = generateDescColumnIndexes(currentIndex, marksNumber);
            if (getAvailableIndexes().containsAll(topRowSet)) {
                isPlaced = true;
                ///mark to the left vertical
                placeMarkSetOnBoard(topRowSet, mark);
            }
        }
        ///is on same row ,try to mark from cuurrent index to marksnumber time to the bottom direction(of the board,ascending on height index)
        if ((heightIndex + (marksNumber - 1) * width < height* width ) && (!isPlaced)) {
            Set<Integer> bottomRowSet = generateAscColumnIndexes(currentIndex, marksNumber);
            if (getAvailableIndexes().containsAll(bottomRowSet)) {
                isPlaced = true;
                ///mark to the left vertical
                placeMarkSetOnBoard(bottomRowSet, mark);
            }
        }
        return isPlaced;
    }

    /**
     * place ship randomly on board ,chooses 0 or 1 ,if succeeded returns true otherwise if failed
     * coin is having xor operation in which it gets its opposite value (0 or 1)
     * @param currentIndex free index
     * @param marksNumber number of marks on board
     * @param mark 'a' ,'c','d','s' according to ships type
     * @return if succeeded true otherwise false
     */
    public boolean placeRandomVerticalOrHorizontal(int currentIndex, int marksNumber, String mark)
    {

        int coin=flipACoin();
        if(placeVerticalOrHorizontal(currentIndex,marksNumber,mark,coin))
        {
            return true;
        }
        else{
            ///0 xor 1 =1 and 1 xor 1 =0 we get the opposite
            coin=coin^1;
            if(placeVerticalOrHorizontal(currentIndex,marksNumber,mark,coin))
            {
                return  true;
            }
            else
            {
                return false;
            }
        }
    }

    /**
     *  place a ship in vertical or horizontal position on board.
     * @param currentIndex free index to start
     * @param marksNumber size of ship:1,2,3,4
     * @param mark 'a' ,'c','d','s' according to ships type
     * @param coin 1 or 0
     * @return if succeeded true otherwise false
     */
    public boolean placeVerticalOrHorizontal(int currentIndex, int marksNumber, String mark,int coin)
    {
        boolean result;
        if(coin==0)
        {
            result=placeVertical(currentIndex,marksNumber,mark);

        }
        else//its 1
        {
            result=placeHorizontal(currentIndex,marksNumber,mark);
        }
        return result;
    }

    /**
     * place submarine on board
     */
    public void placeSubmarine() {
        int freeIndex = getRandomIndex();
        placeMarkOnBoard(freeIndex, "s ");
    }

    /**
     * place carrier on board
     */
    public void placeCarrier() {
        int freeIndex = getRandomIndex();
        if (!placeRandomVerticalOrHorizontal(freeIndex, 4, "a ") )
            placeCarrier();
    }


    /**
     * place carrier on board
     */
    public void placeCruiser() {
        int freeIndex = getRandomIndex();
        if (!placeRandomVerticalOrHorizontal(freeIndex, 3, "c "))
            placeCruiser();
    }

    /**
     * place carrier on board
     */
    public void placeDestroyer() {
        int freeIndex = getRandomIndex();
        if (!placeRandomVerticalOrHorizontal(freeIndex, 2, "d "))
            placeDestroyer();
    }

    /**
     * reurn randomly 1 or 0
     * @return
     */
    public int flipACoin()
    {
       return  (int)Math.round( Math.random());
    }

}
