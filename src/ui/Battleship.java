import java.util.Scanner;
import java.util.Random;

public class Battleship {

    // Positions the player
    private int playerBoat;
    private int playerMedicShip1;
    private int playerMedicShip2;
    private int playerAmmoShip1;
    private int playerAmmoShip2;
    private int playerAmmoShip3;

    // Positions of the machine
    private int machineBoat;
    private int machineMedicShip1;
    private int machineMedicShip2;
    private int machineAmmonShip1;
    private int machineAmmonShip2;
    private int machineAmmonShip3;

    // Ensure the shot location is not repeated
    private boolean [] machineShotsFired = new boolean[10];
    private boolean [] playerShotsFired = new boolean[10];

    public static void main (String[] args){

        Scanner in = new Scanner(System.in);
        Random random = new Random();
        Battleship game = new Battleship();

        while (true){

            int option = game.menu(in);

            if (option == 1){
                System.out.println("");
                System.out.println("Do you want to a short tutorial?");
                System.out.println("1. Yes, I do.");
                System.out.println("2. No, I don't.");

                game.tutorial(in);

                System.out.println("Remember the following things:");
                System.out.println("You must indicate the coordinates for each ship or shot.");
                System.out.println("And keep in mind that ships must not overlap.");
                System.out.println("Now, start placing the ships!");
                System.out.println("");

                // Create our array that will be used as the player board
                int [] playerBoard = new int[10];
                playerBoard = game.playerLocateShips(in, playerBoard);

                // Create our array that will be used to save the machine's location info
                int [] machineBoard = new int[10];

                int [] playerVisionBoard = new int[10];
                
                System.out.println("Very good! now, start playing the game");

                machineBoard = game.machineLocateShips(random, machineBoard);

                System.out.println("");

                game.shot(in, random, playerBoard, machineBoard, playerVisionBoard);
        

            }else {
                System.out.println("");
                System.out.println("Thanks for playing");
                System.out.println("");
                break;
            }
        }

    in.close(); 
    }

    /**
     * Displays the main menu and prompts the user to choose an option.
     * 
     * <b> pre: </b> object Scanner <br>
     * <b> post: </b> it's return option>=1 and option<=2 <br>
     * 
     * @param in - object to read user input.
     * @return int - The option selected by the user
     */
    public int menu (Scanner in){

        System.out.println("");
        int option = 0;
        System.out.println("¡Welcome to a Battleship simulator game!");
        System.out.println("");
        System.out.println("Select the option that you want to do");
        System.out.println("1. Play");
        System.out.println("2. Exit the game");

        while(true){
            System.out.print("Select: ");
            option = in.nextInt();
            if (option>=1 && option<=2){
                break;
            }else{
                System.out.println("Number out of range.");                    
            }
        }

        return option;
    }

    /**
     * Provides a short tutorial about the game rules if the user selects it.
     * 
     * <b> pre: </b> object Scanner <br>
     * <b> post: </b> nothing <br>
     * 
     * @param in - object to read user input.
     */
    public void tutorial (Scanner in){

        int verification;
        while(true){
            System.out.print("Select: ");
            verification = in.nextInt();
            if (verification>=1 && verification<=2){
                break;
            }else{
                System.out.println("Number out of range.");                    
            }
        }
        System.out.println("");

        if (verification == 1){
            System.out.println("Basic rules:");
            System.out.println("1. The board is a line of 10 tiles (1x10).");
            System.out.println("2. SHIPS:");
            System.out.println("   - Speedboat: occupies 1 tile.");
            System.out.println("   - Medic Ship: occupies 2 consecutive tiles.");
            System.out.println("   - Ammunition Ship: occupies 3 consecutive tiles.");
            System.out.println("3. SHIP PLACEMENT:");
            System.out.println("   - You must place your ships on empty tiles.");
            System.out.println("4. SHOOTING:");
            System.out.println("   - Each turn, both the player and the machine fire once.");
            System.out.println("   - If you hit an enemy ship, a mark will appear on the board.");
            System.out.println("   - If you destroy all parts of a ship, it will sink.");
            System.out.println("BOARD SYMBOLS:");
            System.out.println("- 0: Water (no ship or unexplored area).");
            System.out.println("- 1: Ship (visible only to the player).");
            System.out.println("- 2: Hit (you have struck an enemy ship).");
            System.out.println("- 3: Sunken Ship (all its parts have been destroyed).");
            System.out.println("GAME OBJECTIVE:");
            System.out.println("Sink all enemy ships before they sink yours!");
            System.out.println("");
        }
    }

    /**
     * Allows the player to place their ships on the board while ensuring valid placement
     * 
     * <b> pre: </b> object Scanner <br>
     * <b> pre: </b> board [10] <br>
     * <b> post: </b> array with the player ships <br>
     * 
     * @param in - object to read user input.
     * @param board - array that functions as the player board, are empty
     * @return int [] - array where the player ships' locations are
     */
    public int [] playerLocateShips (Scanner in, int [] board){

        // Requesting the boat's coordinate
        playerBoat = 0;
        System.out.println("Place your boat (ocupation 1 box): ");
        System.out.println("Enter the coordinate of the boat (1-10)");

        // Checking if the coordinate is inside the range and the boat is added to the board
        while(true){
            System.out.print("Coordinate: ");
            playerBoat = in.nextInt();
            if (playerBoat>=1 && playerBoat<=10){
                board[playerBoat-1] = 1;
                break;
            }else{
                System.out.println("Number out of range.");                    
            }
        }
        System.out.println("The boat has been placed on the tile " + playerBoat);
        System.out.println("This is how your board looks: ");
        showBoard(board);

        // Requesting the Medic ship's coordinate
        playerMedicShip1 = 0;
        System.out.println("Place your Medic ship (ocupation 2 boxs): ");
        System.out.println("Enter, the initial coordinate of the Medic Ship (1-10)");

        // Checking if the coordinate is inside the range and isn't occupied, then location a part of the ship on the board
        while(true){
            System.out.print("Coordinate 1: ");
            playerMedicShip1 = in.nextInt();
            if (playerMedicShip1>=1 && playerMedicShip1<=10){
                if (board[playerMedicShip1-1] == 0){
                    board[playerMedicShip1-1] = 1;
                    break; 
                }else{
                    System.out.println("That position is occupied. Enter another coordinate");
                }
            }else{
                System.out.println("Number out of range.");                  
            }
        }

        // Requesting the final Medic ship's coordinate
        playerMedicShip2 = 0;
        System.out.println("Enter the final coordinate of the Medic Ship (1-10)");

        // Checking if the coordinate is insinde the range, isn't occupied and is follow of the previous coordinate, then location the next part of the ship on the board
        while(true){
            System.out.print("Coordinate 2: ");
            playerMedicShip2 = in.nextInt();
            if (playerMedicShip2>=1 && playerMedicShip2<=10){
                if (board[playerMedicShip2-1] == 0){
                    if (playerMedicShip2>playerMedicShip1 && playerMedicShip2<playerMedicShip1+2){
                        board[playerMedicShip2-1] = 1;
                        break;
                    }else if (playerMedicShip2<playerMedicShip1 && playerMedicShip2>playerMedicShip1-2){
                        board[playerMedicShip2-1] = 1;
                        break;
                    }else{
                        System.out.println("You must place the coordinate following of the coordinate " + playerMedicShip1);
                    }
                }else {
                    System.out.println("That position is occupied, enter another coordinate");
                }
            }else {
                System.out.println("Number out of range.");                
            }
        }
        System.out.println("The medic ship has been placed on the tile (" + playerMedicShip1 + " - " + playerMedicShip2 + ")");
        System.out.println("This is how your board looks: ");
        showBoard(board);

        // Requesting the initial Ammon ship's coordinate
        playerAmmoShip1 = 0;
        System.out.println("Place your Ammon ship (ocupation 3 boxs): ");
        System.out.println("Enter the initial coordination of the Ammon Ship (1-10)");

        // Checking if the coordinate is inside of the range, is not occupied, has enough space on the sides for the other part and placing one part of the ship on the board
        while(true){
            System.out.print("Coordinate 1: ");
            playerAmmoShip1 = in.nextInt();
            if (playerAmmoShip1>=1 && playerAmmoShip1<=10){

                if (playerAmmoShip1<=8 && board[playerAmmoShip1-1]==0 && board[playerAmmoShip1] == 0 && board[playerAmmoShip1+1] == 0){
                    board[playerAmmoShip1-1] = 1;
                    break;
                }else if (playerAmmoShip1>=3){
                    if (board[playerAmmoShip1-1]==0 && board[playerAmmoShip1-2]==0 && board[playerAmmoShip1-3] == 0){
                        board[playerAmmoShip1-1] = 1;
                        break;
                    }else{
                        System.out.println("That coordinate is occupied or there isn't sufficient space for the ship. Enter another coordination.");
                    }
                }else if (playerAmmoShip1<=8){
                    System.out.println("That coordinate is occupied or there isn't sufficient space for the ship. Enter another coordination.");
                }else{
                    System.out.println("You must place the coordinate with two empty spaces, either forward or backward. Enter another coordinate");
                }

            }else{
                System.out.println("Number out of range.");                  
            }
        }

        // Requesting the final Ammon ship's coordinate
        playerAmmoShip3 = 0;
        System.out.println("Enter the final coordation of the Ammon Ship (1-10)");

        // Checking if the coordinate is inside of the range, is two spaces before or after the previous coordinate, and adding the other part of the ship to the board.
        while(true){
            System.out.print("Coordinate 2: ");
            playerAmmoShip3 = in.nextInt();
            if (playerAmmoShip3>=1 && playerAmmoShip3<=10){
                if (playerAmmoShip3>playerAmmoShip1 && playerAmmoShip3==playerAmmoShip1+2 && board[playerAmmoShip3-1]==0 && board[playerAmmoShip3-2]==0){
                    board[playerAmmoShip3-1] = 1;
                    playerAmmoShip2 = playerAmmoShip3-1;
                    board[playerAmmoShip2-1] = 1;
                    break;
                }else if (playerAmmoShip3<playerAmmoShip1 && playerAmmoShip3==playerAmmoShip1-2 && board[playerAmmoShip3]==0 && board[playerAmmoShip3-1]==0){
                    board[playerAmmoShip3-1] = 1;
                    playerAmmoShip2 = playerAmmoShip3+1;
                    board[playerAmmoShip2-1] = 1;
                        break;
                }else if (playerAmmoShip3>playerAmmoShip1 && playerAmmoShip3==playerAmmoShip1+2 || playerAmmoShip3<playerAmmoShip1 && playerAmmoShip3==playerAmmoShip1-2){
                    System.out.println("That coordinate is occupied or there isn't sufficient space for the ship. Enter another coordination.");
                }else{
                    System.out.println("You must place the coordinate with two spaces before or after the coordinate " + playerAmmoShip1);
                    }
            }else {
                System.out.println("Number out of range.");                
            }
        }
        System.out.println("The ammon ship has been placed on the tile (" + playerAmmoShip1 + " - " + playerAmmoShip2 + " - " + playerAmmoShip3 + ")");
        System.out.println("");


        System.out.println("Finally, your board looks like this: ");
        showBoard(board);

        return board;
    }

    /**
     * Randomly places the machine's ships on its board while ensuring valid placement
     * 
     * <b> pre: </b> object random <br>
     * <b> pre: </b> machineBoard [10] <br>
     * <b> post: </b> array with the machine ships <br>
     * 
     * @param random - object for generating random positions
     * @param machineBoard - array that functions as the machine board, are empty
     * @return int [] - array where the machine ships' locations are
     */
    public int [] machineLocateShips (Random random, int [] machineBoard){


        // The machine place the boat
        machineBoat = random.nextInt(10);

        machineBoard[machineBoat] = 1;

        // The machine place the Medic Ship
        while (true){

            machineMedicShip1 = random.nextInt(10);;
            machineMedicShip2 = random.nextInt(10);; // Se cambio machineMedicShip1 a 2

            if (machineMedicShip1 != machineBoat && machineMedicShip2 != machineBoat && machineMedicShip2 != machineMedicShip1){
                if (machineMedicShip1+1 == machineMedicShip2 || machineMedicShip1-1 == machineMedicShip2){
                    machineBoard[machineMedicShip1] = 1;
                    machineBoard[machineMedicShip2] = 1;
                    break;
                }
            }
        }
        machineAmmonShip2 = -1;
        machineAmmonShip3 = -1;
        
        // The machine place the Ammon Ship
        while (true){

            machineAmmonShip1 = random.nextInt(10);

            if (machineAmmonShip1<=7 && machineBoard[machineAmmonShip1] == 0 &&  machineBoard[machineAmmonShip1+1] == 0 && machineBoard[machineAmmonShip1+2] == 0){
                machineAmmonShip2 = machineAmmonShip1+1;
                machineAmmonShip3 = machineAmmonShip1+2;
                break;
            }else if (machineAmmonShip1>=2 && machineBoard[machineAmmonShip1] == 0 && machineBoard[machineAmmonShip1-1] == 0 && machineBoard[machineAmmonShip1-2] == 0){
                machineAmmonShip2 = machineAmmonShip1-1;
                machineAmmonShip3 = machineAmmonShip1-2;
                break;
            }
        }
        machineBoard[machineAmmonShip1] = 1;
        machineBoard[machineAmmonShip2] = 1;
        machineBoard[machineAmmonShip3] = 1;
        
        return machineBoard;
    }

    /**
     * Handles the shooting phase of the game, allowing the player and the machine 
     * to take turns attacking each other's boards
     * 
     * <b> pre: </b> object Scanner <br>
     * <b> pre: </b> object Random <br>
     * <b> pre: </b> playerBoard with ships <br>
     * <b> pre: </b> machineBoard with ships <br>
     * <b> pre: </b> playerVisionBoard [10] <br>
     * <b> post: </b> nothing <br>
     * 
     * @param in - object to read user input.
     * @param random - object for generating random positions
     * @param playerBoard - array with the player ships
     * @param machineBoard - array with the machine ships
     * @param playerVisionBoard - The player's vision of the machine's board
     */
    public void shot(Scanner in, Random random, int[] playerBoard, int[] machineBoard, int[] playerVisionBoard) {

        // Turn-based gameplay
        while (true) {
            System.out.println("Tell me where you want to attack (1-10)");
            int playerShot;
    
            // Checking if the shot is within range and has not been fired at that position before
            while (true) {
                System.out.print("Shot: ");
                playerShot = in.nextInt();
                if (playerShot >= 1 && playerShot <= 10) {
                    if (playerShotsFired[playerShot - 1]) {
                        System.out.println("You have already shot at this position, try again.");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Number out of range, try again.");
                }
            }
    
            playerShotsFired[playerShot - 1] = true;
    
            playerShot -= 1;
            System.out.println("You have attacked tile: " + (playerShot + 1));
    
            if (machineBoard[playerShot] == 1) {
                System.out.println("You hit a target!");
    
                if (playerShot == machineBoat) {
                    playerVisionBoard[playerShot] = 3;
                    machineBoard[playerShot] = 3;
                    System.out.println("Congratulations! You have sunk the boat.");
    
                } else if (playerShot == machineMedicShip1 || playerShot == machineMedicShip2) {
                    playerVisionBoard[playerShot] = 2;
                    machineBoard[playerShot] = 2;
                    System.out.println("You hit the Medic Ship!");
    
                    if (machineBoard[machineMedicShip1] == 2 && machineBoard[machineMedicShip2] == 2) {
                        playerVisionBoard[machineMedicShip1] = 3;
                        playerVisionBoard[machineMedicShip2] = 3;
    
                        machineBoard[machineMedicShip1] = 3;
                        machineBoard[machineMedicShip2] = 3;
                        System.out.println("Congratulations! You have sunk the Medic Ship.");
                    }
                } else if (playerShot == machineAmmonShip1 || playerShot == machineAmmonShip2 || playerShot == machineAmmonShip3) {
                    playerVisionBoard[playerShot] = 2;
                    machineBoard[playerShot] = 2;
                    System.out.println("You hit the Ammon Ship!");
    
                    if (machineBoard[machineAmmonShip1] == 2 && machineBoard[machineAmmonShip2] == 2 && machineBoard[machineAmmonShip3] == 2) {
                        playerVisionBoard[machineAmmonShip1] = 3;
                        playerVisionBoard[machineAmmonShip2] = 3;
                        playerVisionBoard[machineAmmonShip3] = 3;
    
                        machineBoard[machineAmmonShip1] = 3;
                        machineBoard[machineAmmonShip2] = 3;
                        machineBoard[machineAmmonShip3] = 3;
                        System.out.println("Congratulations! You have sunk the Ammon Ship.");
                    }
                }
            } else {
                System.out.println("You missed!");
            }
    
            System.out.println("This is how the enemy line looks so far:");
            showBoard(playerVisionBoard);
    
            playerBoard = machineShot(random, playerBoard);
    
            System.out.println("");
            int winner = determineWinner(playerBoard, playerVisionBoard);
    
            if (winner == 6) {
                break;
            }
        }
    }
    
    /**
     * Generates and processes the machine's attack on the player's board.
     * 
     * <b> pre: </b> object Random <br>
     * <b> pre: </b> playerBoard with ships <br>
     * <b> post: </b> array <br>
     * 
     * @param random - object for generating random positions
     * @param playerBoard - array with the player ships
     * @return int [] - modified array with machine shots
     */
    public int[] machineShot(Random random, int[] playerBoard) {
    
        int machineShot = 0;
    
        do {
            machineShot = random.nextInt(10);
        } while (machineShotsFired[machineShot]);
    
        machineShotsFired[machineShot] = true;
    
        System.out.println("The machine attacks tile: " + (machineShot + 1));
    
        if (playerBoard[machineShot] == 1) {
            System.out.println("You have been hit!");
    
            if (machineShot == playerBoat - 1) {
                System.out.println("Your boat has been sunk!");
                playerBoard[machineShot] = 3;
    
            } else if (machineShot == playerMedicShip1 - 1 || machineShot == playerMedicShip2 - 1) {
                System.out.println("Your Medic Ship has been hit!");
                playerBoard[machineShot] = 2;
                if (playerBoard[playerMedicShip1 - 1] == 2 && playerBoard[playerMedicShip2 - 1] == 2) {
                    System.out.println("Your Medic Ship has been sunk!");
                    playerBoard[playerMedicShip1 - 1] = 3;
                    playerBoard[playerMedicShip2 - 1] = 3;
                }
            } else if (machineShot == playerAmmoShip1 - 1 || machineShot == playerAmmoShip2 - 1 || machineShot == playerAmmoShip3 - 1) {
                System.out.println("Your Ammon Ship has been hit!");
                playerBoard[machineShot] = 2;
                if (playerBoard[playerAmmoShip1 - 1] == 2 && playerBoard[playerAmmoShip2 - 1] == 2 && playerBoard[playerAmmoShip3 - 1] == 2) {
                    System.out.println("Your Ammon Ship has been sunk!");
                    playerBoard[playerAmmoShip1 - 1] = 3;
                    playerBoard[playerAmmoShip2 - 1] = 3;
                    playerBoard[playerAmmoShip3 - 1] = 3;
                }
            }
        } else {
            System.out.println("It hit the water.");
            System.out.println("Nothing happened!");
        }
    
        System.out.println("This is how your line looks so far:");
        showBoard(playerBoard);
    
        return playerBoard;
    }

    /**
     * Determine a winner
     * 
     * <b> pre: </b> playerBoard with ships <br>
     * <b> pre: </b> plyaerVisionBoard [10] <br>
     * <b> post: </b> winner>=0 <br>
     * 
     * @param playerBoard - array with the player ships
     * @param playerVisionBoard - The player's vision of the machine's board.
     * @return int - a number with the times there are 3 on the board
     */
    public int determineWinner (int [] playerBoard, int [] playerVisionBoard){
        
        int counter1 = 0;
        for (int i = 0; i<playerBoard.length; i++){
            if (playerBoard[i] == 3){
                counter1 += 1;
            }
        }

        if (counter1 == 6){
            int winner = counter1;
            System.out.println("The machine has won the game");
            System.out.println("");
            System.out.println("-------------------------------------------------");
            return winner;
        }

        int counter2 = 0;
        for (int i = 0; i<playerVisionBoard.length; i++){
            if (playerVisionBoard[i] == 3){
                counter2 += 1;
            }
        }

        if (counter2 == 6){
            int winner = counter2;
            System.out.println("¡Congratulations! You have won the game");
            System.out.println("");
            System.out.println("-------------------------------------------------");
            return winner;
        }

        int winner = 0;
        return winner;
    }

    /**
     * Shows a board in screen
     * 
     * <b> pre: </b> board [10] <br>
     * <b> post: </b> nothing <br>
     * 
     * @param board - size 10 rray
     */
    public void showBoard (int [] board){

        for(int i = 0; i<board.length; i++){
            System.out.print(board[i] + " ");
        }
        System.out.println("\n");
    }

}