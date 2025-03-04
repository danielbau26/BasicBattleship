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
    private int numeroMaquinaLancha;
    private int numeroMaquinaMedico1;
    private int numeroMaquinaMedico2;
    private int numeroMaquinaMunicion1;
    private int numeroMaquinaMunicion2;
    private int numeroMaquinaMunicion3;

    //Que no se repita la ubicación del disparo
    private boolean [] disparosRealizadosMaquina = new boolean[10];
    private boolean [] disparosRealizadosJugador = new boolean[10];

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

                System.out.println("Remember the next things: ");
                System.out.println("You must indicate the coordinates for each ship or shoot");
                System.out.println("Y ten en cuenta que los barcos no se deben superponer.");
                System.out.println("And remember that the ships don't must __");
                System.out.println("¡Ahora comencemos a ubicar los barcos!");
                System.out.println("");

                //Se inicia nuestro arreglo que va a funcionar como el tablero del jugador
                int [] playerBoard = new int[10];
                playerBoard = game.jugadorUbicaSusBarcos(in, playerBoard);

                //Se inicia nuestro arreglo que va a guardar la infroamción de la ubicación de la maquina
                int [] machineBoard = new int[10];

                int [] playerVisionBoard = new int[10];

                System.out.println("Muy bien, ahora empezemos ahora a jugar");

                machineBoard = game.maquinaUbicaSusBarcos(random, machineBoard);

                System.out.println("");

                playerBoard = game.shot(in, random, playerBoard, machineBoard, playerVisionBoard);
        

            }else {
                System.out.println("");
                System.out.println("Thanks for played.");
                System.out.println("");
                break;
            }
        }

    in.close(); 
    }


    public int menu (Scanner in){

        System.out.println("");
        int option = 0;
        System.out.println("Welcome to a Battleship simulator game");
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

    public void tutorial (Scanner in){

        int verifica;
        while(true){
            System.out.print("Select: ");
            verifica = in.nextInt();
            if (verifica>=1 && verifica<=2){
                break;
            }else{
                System.out.println("Numbre out of range.");                    
            }
        }
        System.out.println("");

        if (verifica == 1){
            System.out.println("Basics rules:");
            System.out.println("1. El tablero Es una línea de 10 casillas (1x10).");
            System.out.println("2. BARCOS:");
            System.out.println("   - Lancha: ocupa 1 casilla.");
            System.out.println("   - Barco Médico: ocupa 2 casillas consecutivas.");
            System.out.println("   - Barco Munición: ocupa 3 casillas consecutivas.");
            System.out.println("3. UBICACIÓN DE BARCOS:");
            System.out.println("   - Debes colocar tus barcos en casillas vacías.");
            System.out.println("4. DISPAROS:");
            System.out.println("   - En cada turno, el jugador y la máquina disparan una vez.");
            System.out.println("   - Si impactas un barco enemigo, aparecerá una marca en el tablero.");
            System.out.println("   - Si destruyes todas las partes de un barco, se hundirá.");
            System.out.println("");
            System.out.println("SÍMBOLOS EN EL TABLERO:");
            System.out.println("- 0: Agua (sin barco o zona no explorada).");
            System.out.println("- 1: Barco (visible solo para el jugador).");
            System.out.println("- 2: Impacto (has golpeado un barco enemigo).");
            System.out.println("- 3: Barco Hundido (todas sus partes han sido destruidas).");
            System.out.println("");
            System.out.println("OBJETIVO DEL JUEGO:");
            System.out.println("¡Hundir todos los barcos enemigos antes de que hundan los tuyos!");
            System.out.println("");
        }
    }

    public int [] jugadorUbicaSusBarcos (Scanner in, int [] board){

        // Se pide la coordenada de la lancha
        playerBoat = 0;
        System.out.println("Locate your boat (ocupation 1 box): ");
        System.out.println("Ingrese la coordenada de la Lancha (1-10)");

        // Se verifica si la coordenada se encuentra dentro del rango y se agrega el barco al tablero
        while(true){
            System.out.print("Coordenada: ");
            playerBoat = in.nextInt();
            if (playerBoat>=1 && playerBoat<=10){
                board[playerBoat-1] = 1;
                break;
            }else{
                System.out.println("Number out of range.");                    
            }
        }
        System.out.println("Se ha ubicado la lancha en la casilla " + playerBoat);
        System.out.println("Así va tu tablero:");
        mostrarTablero(board);

        // Se pide la coordenada inicial del barco médico
        playerMedicShip1 = 0;
        System.out.println("Locate your Medic ship (ocupation 2 boxs): ");
        System.out.println("Ingrese la coordenada inicial del Barco Médico (1-10)");

        // Se verifica si la coordenada se encuentra dentro del rango, no este ocupada y se ubica una parte del barco en el tablero
        while(true){
            System.out.print("Coordenada 1: ");
            playerMedicShip1 = in.nextInt();
            if (playerMedicShip1>=1 && playerMedicShip1<=10){
                if (board[playerMedicShip1-1] == 0){
                    board[playerMedicShip1-1] = 1;
                    break; 
                }else{
                    System.out.println("Esa posición se encuentra ocupada, ingrese otra coordenada");
                }
            }else{
                System.out.println("Number out of range.");                  
            }
        }

        // Se pide la coordenada final del barco médico
        playerMedicShip2 = 0;
        System.out.println("Ingrese la coordenada final del Barco Médico (1-10)");

        // Se verifica si la coordenada se encuentra dentro del rango, no este ocupada, este seguida a la coordenada anterior y se agrega la otra parte del barco al tablero
        while(true){
            System.out.print("Coordenada 2: ");
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
                        System.out.println("Tienes que ubicar la coordenada seguida de la coordenada " + playerMedicShip1);
                    }
                }else {
                    System.out.println("Esa posición se encuentra ocupada, ingrese otra coordenada");
                }
            }else {
                System.out.println("Número fuera del rango.");                
            }
        }
        System.out.println("Se ha ubicado el barco médico en las casillas (" + playerMedicShip1 + " - " + playerMedicShip2 + ")");
        System.out.println("Así va tu tablero:");
        mostrarTablero(board);

        // Se pide la coordenada inicial del barco munición
        playerAmmoShip1 = 0;
        System.out.println("Locate your Ammon ship (ocupation 3 boxs): ");
        System.out.println("Ingrese la coordenada inicial del Barco Munición (1-10)");

        // Se verifica si la coordenada se encuentra dentro del rango, no este ocupada, halla suficiente espacio a los lados para la otra parte y se ubica una parte del barco en el tablero
        while(true){
            System.out.print("Coordenada 1: ");
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
                        System.out.println("Esa posición se encuentra ocupada o no hay suficientes espacio para el barco, ingrese otra coordenada.");
                    }
                }else if (playerAmmoShip1<=8){
                    System.out.println("Esa posición se encuentra ocupada o no hay suficientes espacio para el barco, ingrese otra coordenada.");
                }else{
                    System.out.println("Tienes que ubicar la coordenada con dos espacios vacios ya sea hacia adelante o atras, ingrese otra coordenada.");
                }

            }else{
                System.out.println("Número fuera del rango.");                  
            }
        }

        // Se pide la coordenada final del barco munición
        playerAmmoShip3 = 0;
        System.out.println("Ingrese la coordenada final del Barco Munición (1-10)");

        // Se verifica si la coordenada se encuentra dentro del rango, este dos espacios antes o despues a la coordenada anterior y se agrega la otra parte del barco al tablero
        while(true){
            System.out.print("Coordenada 2: ");
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
                    System.out.println("Esa posición se encuentra ocupada o no hay suficientes espacio para el barco, ingrese otra coordenada.");
                }else{
                    System.out.println("Tienes que ubicar la coordenada dos espacios antes o despues de la coordenada " + playerAmmoShip1);
                    }
            }else {
                System.out.println("Número fuera del rango.");                
            }
        }
        System.out.println("Se ha ubicado el barco municion en las casillas (" + playerAmmoShip1 + " - " + playerAmmoShip2 + " - " + playerAmmoShip3 + ")");
        System.out.println("");


        System.out.println("Finalmente tu tablero queda de esta forma: ");
        mostrarTablero(board);

        return board;
    }

    public int [] maquinaUbicaSusBarcos (Random random, int [] machineBoard){


        numeroMaquinaLancha = random.nextInt(10);

        machineBoard[numeroMaquinaLancha] = 1;

        //La maquina ubica el barco médico
        while (true){

            numeroMaquinaMedico1 = random.nextInt(10);;
            numeroMaquinaMedico2 = random.nextInt(10);; // Se cambio numeromaquinamedico1 a 2

            if (numeroMaquinaMedico1 != numeroMaquinaLancha && numeroMaquinaMedico2 != numeroMaquinaLancha && numeroMaquinaMedico2 != numeroMaquinaMedico1){
                if (numeroMaquinaMedico1+1 == numeroMaquinaMedico2 || numeroMaquinaMedico1-1 == numeroMaquinaMedico2){
                    machineBoard[numeroMaquinaMedico1] = 1;
                    machineBoard[numeroMaquinaMedico2] = 1;
                    break;
                }
            }
        }
        numeroMaquinaMunicion2 = -1;
        numeroMaquinaMunicion3 = -1;
        
        while (true){

            numeroMaquinaMunicion1 = random.nextInt(10);

            if (numeroMaquinaMunicion1<=7 && machineBoard[numeroMaquinaMunicion1+1] == 0 && machineBoard[numeroMaquinaMunicion1+2] == 0){
                numeroMaquinaMunicion2 = numeroMaquinaMunicion1+1;
                numeroMaquinaMunicion3 = numeroMaquinaMunicion1+2;
                break;
            }else if (numeroMaquinaMunicion1>=2 && machineBoard[numeroMaquinaMunicion1-1] == 0 && machineBoard[numeroMaquinaMunicion1-2] == 0){
                numeroMaquinaMunicion2 = numeroMaquinaMunicion1-1;
                numeroMaquinaMunicion3 = numeroMaquinaMunicion1-2;
                break;
            }
        }
        machineBoard[numeroMaquinaMunicion1] = 1;
        machineBoard[numeroMaquinaMunicion2] = 1;
        machineBoard[numeroMaquinaMunicion3] = 1;
        
        return machineBoard;
    }

    public int [] shot (Scanner in, Random random, int [] playerBoard, int [] machineBoard, int [] playerVisionBoard){

        // Se hace el turno a turno
        while (true){
            System.out.println("Dime donde deseas atacar (1-10)");
            int disparoJugador;

            // Se verifica que el disparo este dentro del rango y que no haya disparado en esa posición
            while(true){
                System.out.print("Disparo: ");
                disparoJugador = in.nextInt();
                if (disparoJugador>=1 && disparoJugador<=10){
                    if (disparosRealizadosJugador[disparoJugador-1]){
                        System.out.println("Ya disparaste en esta posición, intenta de nuevo.");
                    }else{
                        break;
                    }
                }else{
                    System.out.println("Número fuera del rango, intenta de nuevo.");                    
                }
            }

            disparosRealizadosJugador[disparoJugador-1] = true;

            disparoJugador -= 1;
            System.out.println("Has atacado la casilla " + (disparoJugador+1));

            if (machineBoard[disparoJugador] == 1){
                System.out.println("¡Le has dado a un objetivo!");
                if (disparoJugador==numeroMaquinaLancha){
                    playerVisionBoard[disparoJugador] = 3;
                    machineBoard[disparoJugador] = 3;
                    System.out.println("¡Enhorabauena! Has hundido la Lancha");

                }else if (disparoJugador==numeroMaquinaMedico1 || disparoJugador==numeroMaquinaMedico2){
                    playerVisionBoard[disparoJugador] = 2;
                    machineBoard[disparoJugador] = 2;
                    System.out.println("Le has dado a el barco Médico");

                    if (machineBoard[numeroMaquinaMedico1]==2 && machineBoard[numeroMaquinaMedico2]==2){
                        playerVisionBoard[numeroMaquinaMedico1] =3;
                        playerVisionBoard[numeroMaquinaMedico2] =3;

                        machineBoard[numeroMaquinaMedico1] =3;
                        machineBoard[numeroMaquinaMedico2] =3;
                        System.out.println("¡Enhorabauena! Has derribado el barco Médico");
                    }
                }else if(disparoJugador==numeroMaquinaMunicion1 || disparoJugador==numeroMaquinaMunicion2 || disparoJugador==numeroMaquinaMunicion3){
                    playerVisionBoard[disparoJugador] = 2;
                    machineBoard[disparoJugador] = 2;
                    System.out.println("Le has dado al barco Munición");

                    if (machineBoard[numeroMaquinaMunicion1]==2 && machineBoard[numeroMaquinaMunicion2]==2 && machineBoard[numeroMaquinaMunicion3]==2){
                        playerVisionBoard[numeroMaquinaMunicion1] =3;
                        playerVisionBoard[numeroMaquinaMunicion2] =3;
                        playerVisionBoard[numeroMaquinaMunicion3] =3;

                        machineBoard[numeroMaquinaMunicion1] =3;
                        machineBoard[numeroMaquinaMunicion2] =3;
                        machineBoard[numeroMaquinaMunicion3] =3;
                        System.out.println("¡Enhorabauena! Has derribado el barco Munición");
                    }
                }
            }else{
                System.out.println("Y No le has dado a nada");
            }

            System.out.println("Así se ve la linea enemiga por el momento");
            mostrarTablero(playerVisionBoard);

            playerBoard = machineShot(random, playerBoard);


            System.out.println("");
            int ganador = determinarGanador(playerBoard, playerVisionBoard);

            if (ganador == 7){
                break;
            }
        }
        return playerBoard;
    }

    public int [] machineShot (Random random, int [] playerBoard){
        
        int disparoMaquina = 0;

        do{
            disparoMaquina = random.nextInt(10);
        }while(disparosRealizadosMaquina[disparoMaquina]);

        disparosRealizadosMaquina[disparoMaquina] = true;

        System.out.println("La maquina ataca la casilla: " + (disparoMaquina+1));

        if (playerBoard[disparoMaquina] == 1){
            System.out.println("¡Te han dado!");

            if (disparoMaquina == playerBoat-1){
                System.out.println("Te han derribado la Lancha");
                playerBoard[disparoMaquina] = 3;

            }else if (disparoMaquina == playerMedicShip1-1 || disparoMaquina == playerMedicShip2-1){
                System.out.println("Te han dado al barco Médico");
                playerBoard[disparoMaquina] = 2;
                if (playerBoard[playerMedicShip1-1] == 2 && playerBoard[playerMedicShip2-1] == 2){
                    System.out.println("Te han derribado el barco Médico");
                    playerBoard[playerMedicShip1-1] = 3;
                    playerBoard[playerMedicShip2] = 3;
                }
            }else if (disparoMaquina == playerAmmoShip1-1 || disparoMaquina == playerAmmoShip2-1 || disparoMaquina == playerAmmoShip3-1){
                System.out.println("Te han dado al barco Munición");
                playerBoard[disparoMaquina] = 2;
                if (playerBoard[playerAmmoShip1-1] == 2 && playerBoard[playerAmmoShip2-1] == 2 && playerBoard[playerAmmoShip3-1] == 2){
                    System.out.println("Te han derribado el barco Munición");
                    playerBoard[playerAmmoShip1-1] = 3;
                    playerBoard[playerAmmoShip2-1] = 3;
                    playerBoard[playerAmmoShip3-1] = 3;
                }
            }
        }else {
            System.out.println("Le ha dado al agua");
            System.out.println("¡Aquí no ha pasado nada!");
        }
        
        System.out.println("Así se ve tu linea por el momento");
        mostrarTablero(playerBoard);

        return playerBoard;
    }

    public int determinarGanador (int [] playerBoard, int [] playerVisionBoard){
        
        int contador = 0;
        for (int i = 0; i<playerBoard.length; i++){
            if (playerBoard[i] == 3){
                contador += 1;
            }
        }

        int ganador = 0;
        if (contador == 7){
            ganador = contador;
            System.out.println("Ha ganado la maquina");
            System.out.println("");
            System.out.println("-------------------------------------------------");
            return ganador;
        }


        for (int i = 0; i<playerVisionBoard.length; i++){
            if (playerVisionBoard[i] == 3){
                contador += 1;
            }
        }

        if (contador == 7){
            ganador = contador;
            System.out.println("¡Felicidades!, has ganado");
            System.out.println("");
            System.out.println("-------------------------------------------------");
            return ganador;
        }

        return ganador;
    }

    public int [] mostrarTablero (int [] board){

        for(int i = 0; i<board.length; i++){
            System.out.print(board[i] + " ");
        }
        System.out.println("\n");
        return board;
    }

}