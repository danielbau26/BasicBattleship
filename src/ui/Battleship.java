import java.util.Scanner;
import java.util.Random;

public class Battleship {

    // Ubicacaciones del jugador
    private int lanchaJugador;
    private int medicoJugador1;
    private int medicoJugador2;
    private int municionJugador1;
    private int municionJugador2;
    private int municionJugador3;

    // Ubicacaciones de la maquina
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

            int opcion = game.menu(in);

            if (opcion == 1){
                System.out.println("");
                System.out.println("Desea un breve tutorial?");
                System.out.println("1. Sí.");
                System.out.println("2. No.");

                game.tutorial(in);

                System.out.println("Recuerda lo siguiente: ");
                System.out.println("Debes indicar la coordenada para cada barco o disparo.");
                System.out.println("Y ten en cuenta que los barcos no se deben superponer.");
                System.out.println("");
                System.out.println("¡Ahora comencemos a ubicar los barcos!");
                System.out.println("");

                //Se inicia nuestro arreglo que va a funcionar como el tablero del jugador
                int [] tableroJugador = new int[10];
                tableroJugador = game.jugadorUbicaSusBarcos(in, tableroJugador);

                //Se inicia nuestro arreglo que va a guardar la infroamción de la ubicación de la maquina
                int [] tableroMaquina = new int[10];

                int [] tableroVisibleJugador = new int[10];

                System.out.println("Muy bien, ahora empezemos ahora a jugar");

                tableroMaquina = game.maquinaUbicaSusBarcos(random, tableroMaquina); // Necesito guardar el valor de tablero maquina sin que aparezca en pantalla

                System.out.println("");

                tableroJugador = game.disparo(in, random, tableroJugador, tableroMaquina, tableroVisibleJugador);
        

            }else {
                System.out.println("");
                System.out.println("Gracias por jugar.");
                System.out.println("");
                break;
            }
        }

    in.close(); 
    }


    public int menu (Scanner in){

        System.out.println("");
        int opcion = 0;
        System.out.println("Bienvenido a una simulación del juego de Battleship");
        System.out.println("Seleccione la opción que desea realizar: ");
        System.out.println("1. Jugar");
        System.out.println("2. Salir del programa");

        while(true){
            System.out.print("Selección: ");
            opcion = in.nextInt();
            if (opcion>=1 && opcion<=2){
                break;
            }else{
                System.out.println("Número fuera del rango.");                    
            }
        }

        return opcion;
    }

    public void tutorial (Scanner in){

        int verifica;
        while(true){
            System.out.print("Selección: ");
            verifica = in.nextInt();
            if (verifica>=1 && verifica<=2){
                break;
            }else{
                System.out.println("Número fuera del rango.");                    
            }
        }
        System.out.println("");

        if (verifica == 1){
            System.out.println("REGLAS BÁSICAS:");
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

    public int [] jugadorUbicaSusBarcos (Scanner in, int [] tablero){

        // Se pide la coordenada de la lancha
        lanchaJugador = 0;
        System.out.println("Ubica tu lancha (ocupación 1 casilla): ");
        System.out.println("Ingrese la coordenada de la Lancha (1-10)");

        // Se verifica si la coordenada se encuentra dentro del rango y se agrega el barco al tablero
        while(true){
            System.out.print("Coordenada: ");
            lanchaJugador = in.nextInt();
            if (lanchaJugador>=1 && lanchaJugador<=10){
                tablero[lanchaJugador-1] = 1;
                break;
            }else{
                System.out.println("Número fuera del rango.");                    
            }
        }
        System.out.println("Se ha ubicado la lancha en la casilla " + lanchaJugador);
        System.out.println("Así va tu tablero:");
        mostrarTablero(tablero);

        // Se pide la coordenada inicial del barco médico
        medicoJugador1 = 0;
        System.out.println("Ubica tu Barco Médico (ocupación 2 casillas): ");
        System.out.println("Ingrese la coordenada inicial del Barco Médico (1-10)");

        // Se verifica si la coordenada se encuentra dentro del rango, no este ocupada y se ubica una parte del barco en el tablero
        while(true){
            System.out.print("Coordenada 1: ");
            medicoJugador1 = in.nextInt();
            if (medicoJugador1>=1 && medicoJugador1<=10){
                if (tablero[medicoJugador1-1] == 0){
                    tablero[medicoJugador1-1] = 1;
                    break; 
                }else{
                    System.out.println("Esa posición se encuentra ocupada, ingrese otra coordenada");
                }
            }else{
                System.out.println("Número fuera del rango.");                  
            }
        }

        // Se pide la coordenada final del barco médico
        medicoJugador2 = 0;
        System.out.println("Ingrese la coordenada final del Barco Médico (1-10)");

        // Se verifica si la coordenada se encuentra dentro del rango, no este ocupada, este seguida a la coordenada anterior y se agrega la otra parte del barco al tablero
        while(true){
            System.out.print("Coordenada 2: ");
            medicoJugador2 = in.nextInt();
            if (medicoJugador2>=1 && medicoJugador2<=10){
                if (tablero[medicoJugador2-1] == 0){
                    if (medicoJugador2>medicoJugador1 && medicoJugador2<medicoJugador1+2){
                        tablero[medicoJugador2-1] = 1;
                        break;
                    }else if (medicoJugador2<medicoJugador1 && medicoJugador2>medicoJugador1-2){
                        tablero[medicoJugador2-1] = 1;
                        break;
                    }else{
                        System.out.println("Tienes que ubicar la coordenada seguida de la coordenada " + medicoJugador1);
                    }
                }else {
                    System.out.println("Esa posición se encuentra ocupada, ingrese otra coordenada");
                }
            }else {
                System.out.println("Número fuera del rango.");                
            }
        }
        System.out.println("Se ha ubicado el barco médico en las casillas (" + medicoJugador1 + " - " + medicoJugador2 + ")");
        System.out.println("Así va tu tablero:");
        mostrarTablero(tablero);

        // Se pide la coordenada inicial del barco munición
        municionJugador1 = 0;
        System.out.println("Ubica tu Barco Munición (ocupación 3 casillas): ");
        System.out.println("Ingrese la coordenada inicial del Barco Munición (1-10)");

        // Se verifica si la coordenada se encuentra dentro del rango, no este ocupada, halla suficiente espacio a los lados para la otra parte y se ubica una parte del barco en el tablero
        while(true){
            System.out.print("Coordenada 1: ");
            municionJugador1 = in.nextInt();
            if (municionJugador1>=1 && municionJugador1<=10){

                if (municionJugador1<=8 && tablero[municionJugador1-1]==0 && tablero[municionJugador1] == 0 && tablero[municionJugador1+1] == 0){
                    tablero[municionJugador1-1] = 1;
                    break;
                }else if (municionJugador1>=3){
                    if (tablero[municionJugador1-1]==0 && tablero[municionJugador1-2]==0 && tablero[municionJugador1-3] == 0){
                        tablero[municionJugador1-1] = 1;
                        break;
                    }else{
                        System.out.println("Esa posición se encuentra ocupada o no hay suficientes espacio para el barco, ingrese otra coordenada.");
                    }
                }else if (municionJugador1<=8){
                    System.out.println("Esa posición se encuentra ocupada o no hay suficientes espacio para el barco, ingrese otra coordenada.");
                }else{
                    System.out.println("Tienes que ubicar la coordenada con dos espacios vacios ya sea hacia adelante o atras, ingrese otra coordenada.");
                }

            }else{
                System.out.println("Número fuera del rango.");                  
            }
        }

        // Se pide la coordenada final del barco munición
        municionJugador2 = 0;
        System.out.println("Ingrese la coordenada final del Barco Munición (1-10)");

        // Se verifica si la coordenada se encuentra dentro del rango, este dos espacios antes o despues a la coordenada anterior y se agrega la otra parte del barco al tablero
        while(true){
            System.out.print("Coordenada 2: ");
            municionJugador3 = in.nextInt();
            if (municionJugador3>=1 && municionJugador3<=10){
                if (municionJugador3>municionJugador1 && municionJugador3==municionJugador1+2 && tablero[municionJugador3-1]==0 && tablero[municionJugador3-2]==0){
                    tablero[municionJugador3-1] = 1;
                    municionJugador2 = municionJugador3-1;
                    tablero[municionJugador2-1] = 1;
                    break;
                }else if (municionJugador3<municionJugador1 && municionJugador3==municionJugador1-2 && tablero[municionJugador3]==0 && tablero[municionJugador3-1]==0){
                    tablero[municionJugador3-1] = 1;
                    municionJugador2 = municionJugador3+1;
                    tablero[municionJugador2-1] = 1;
                        break;
                }else if (municionJugador3>municionJugador1 && municionJugador3==municionJugador1+2 || municionJugador3<municionJugador1 && municionJugador3==municionJugador1-2){
                    System.out.println("Esa posición se encuentra ocupada o no hay suficientes espacio para el barco, ingrese otra coordenada.");
                }else{
                    System.out.println("Tienes que ubicar la coordenada dos espacios antes o despues de la coordenada " + municionJugador1);
                    }
            }else {
                System.out.println("Número fuera del rango.");                
            }
        }
        System.out.println("Se ha ubicado el barco municion en las casillas (" + municionJugador1 + " - " + municionJugador2 + " - " + municionJugador3 + ")");
        System.out.println("");


        System.out.println("Finalmente tu tablero queda de esta forma: ");
        mostrarTablero(tablero);

        return tablero;
    }

    public int [] maquinaUbicaSusBarcos (Random random, int [] tableroMaquina){


        numeroMaquinaLancha = random.nextInt(10);

        tableroMaquina[numeroMaquinaLancha] = 1;

        //La maquina ubica el barco médico
        while (true){

            numeroMaquinaMedico1 = random.nextInt(10);;
            numeroMaquinaMedico2 = random.nextInt(10);; // Se cambio numeromaquinamedico1 a 2

            if (numeroMaquinaMedico1 != numeroMaquinaLancha && numeroMaquinaMedico2 != numeroMaquinaLancha && numeroMaquinaMedico2 != numeroMaquinaMedico1){
                if (numeroMaquinaMedico1+1 == numeroMaquinaMedico2 || numeroMaquinaMedico1-1 == numeroMaquinaMedico2){
                    tableroMaquina[numeroMaquinaMedico1] = 1;
                    tableroMaquina[numeroMaquinaMedico2] = 1;
                    break;
                }
            }
        }
        numeroMaquinaMunicion2 = -1;
        numeroMaquinaMunicion3 = -1;
        
        while (true){

            numeroMaquinaMunicion1 = random.nextInt(10);

            if (numeroMaquinaMunicion1<=7 && tableroMaquina[numeroMaquinaMunicion1+1] == 0 && tableroMaquina[numeroMaquinaMunicion1+2] == 0){
                numeroMaquinaMunicion2 = numeroMaquinaMunicion1+1;
                numeroMaquinaMunicion3 = numeroMaquinaMunicion1+2;
                break;
            }else if (numeroMaquinaMunicion1>=2 && tableroMaquina[numeroMaquinaMunicion1-1] == 0 && tableroMaquina[numeroMaquinaMunicion1-2] == 0){
                numeroMaquinaMunicion2 = numeroMaquinaMunicion1-1;
                numeroMaquinaMunicion3 = numeroMaquinaMunicion1-2;
                break;
            }
        }
        tableroMaquina[numeroMaquinaMunicion1] = 1;
        tableroMaquina[numeroMaquinaMunicion2] = 1;
        tableroMaquina[numeroMaquinaMunicion3] = 1;
        
        return tableroMaquina;
    }

    public int [] disparo (Scanner in, Random random, int [] tableroJugador, int [] tableroMaquina, int [] tableroVisibleJugador){

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

            if (tableroMaquina[disparoJugador] == 1){
                System.out.println("¡Le has dado a un objetivo!");
                if (disparoJugador==numeroMaquinaLancha){
                    tableroVisibleJugador[disparoJugador] = 3;
                    tableroMaquina[disparoJugador] = 3;
                    System.out.println("¡Enhorabauena! Has hundido la Lancha");

                }else if (disparoJugador==numeroMaquinaMedico1 || disparoJugador==numeroMaquinaMedico2){
                    tableroVisibleJugador[disparoJugador] = 2;
                    tableroMaquina[disparoJugador] = 2;
                    System.out.println("Le has dado a el barco Médico");

                    if (tableroMaquina[numeroMaquinaMedico1]==2 && tableroMaquina[numeroMaquinaMedico2]==2){
                        tableroVisibleJugador[numeroMaquinaMedico1] =3;
                        tableroVisibleJugador[numeroMaquinaMedico2] =3;

                        tableroMaquina[numeroMaquinaMedico1] =3;
                        tableroMaquina[numeroMaquinaMedico2] =3;
                        System.out.println("¡Enhorabauena! Has derribado el barco Médico");
                    }
                }else if(disparoJugador==numeroMaquinaMunicion1 || disparoJugador==numeroMaquinaMunicion2 || disparoJugador==numeroMaquinaMunicion3){
                    tableroVisibleJugador[disparoJugador] = 2;
                    tableroMaquina[disparoJugador] = 2;
                    System.out.println("Le has dado al barco Munición");

                    if (tableroMaquina[numeroMaquinaMunicion1]==2 && tableroMaquina[numeroMaquinaMunicion2]==2 && tableroMaquina[numeroMaquinaMunicion3]==2){
                        tableroVisibleJugador[numeroMaquinaMunicion1] =3;
                        tableroVisibleJugador[numeroMaquinaMunicion2] =3;
                        tableroVisibleJugador[numeroMaquinaMunicion3] =3;

                        tableroMaquina[numeroMaquinaMunicion1] =3;
                        tableroMaquina[numeroMaquinaMunicion2] =3;
                        tableroMaquina[numeroMaquinaMunicion3] =3;
                        System.out.println("¡Enhorabauena! Has derribado el barco Munición");
                    }
                }
            }else{
                System.out.println("Y No le has dado a nada");
            }

            System.out.println("Así se ve la linea enemiga por el momento");
            mostrarTablero(tableroVisibleJugador);

            tableroJugador = disparoMaquina(random, tableroJugador);


            System.out.println("");
            int ganador = determinarGanador(tableroJugador, tableroVisibleJugador);

            if (ganador == 7){
                break;
            }
        }
        return tableroJugador;
    }

    public int [] disparoMaquina (Random random, int [] tableroJugador){
        
        int disparoMaquina = 0;

        do{
            disparoMaquina = random.nextInt(10);
        }while(disparosRealizadosMaquina[disparoMaquina]);

        disparosRealizadosMaquina[disparoMaquina] = true;

        System.out.println("La maquina ataca la casilla: " + (disparoMaquina+1));

        if (tableroJugador[disparoMaquina] == 1){
            System.out.println("¡Te han dado!");

            if (disparoMaquina == lanchaJugador-1){
                System.out.println("Te han derribado la Lancha");
                tableroJugador[disparoMaquina] = 3;

            }else if (disparoMaquina == medicoJugador1-1 || disparoMaquina == medicoJugador2-1){
                System.out.println("Te han dado al barco Médico");
                tableroJugador[disparoMaquina] = 2;
                if (tableroJugador[medicoJugador1-1] == 2 && tableroJugador[medicoJugador2-1] == 2){
                    System.out.println("Te han derribado el barco Médico");
                    tableroJugador[medicoJugador1-1] = 3;
                    tableroJugador[medicoJugador2] = 3;
                }
            }else if (disparoMaquina == municionJugador1-1 || disparoMaquina == municionJugador2-1 || disparoMaquina == municionJugador3-1){
                System.out.println("Te han dado al barco Munición");
                tableroJugador[disparoMaquina] = 2;
                if (tableroJugador[municionJugador1-1] == 2 && tableroJugador[municionJugador2-1] == 2 && tableroJugador[municionJugador3-1] == 2){
                    System.out.println("Te han derribado el barco Munición");
                    tableroJugador[municionJugador1-1] = 3;
                    tableroJugador[municionJugador2-1] = 3;
                    tableroJugador[municionJugador3-1] = 3;
                }
            }
        }else {
            System.out.println("Le ha dado al agua");
            System.out.println("¡Aquí no ha pasado nada!");
        }
        
        System.out.println("Así se ve tu linea por el momento");
        mostrarTablero(tableroJugador);

        return tableroJugador;
    }

    public int determinarGanador (int [] tableroJugador, int [] tableroVisibleJugador){
        
        int contador = 0;
        for (int i = 0; i<tableroJugador.length; i++){
            if (tableroJugador[i] == 3){
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


        for (int i = 0; i<tableroVisibleJugador.length; i++){
            if (tableroVisibleJugador[i] == 3){
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

    public int [] mostrarTablero (int [] tablero){

        for(int i = 0; i<tablero.length; i++){
            System.out.print(tablero[i] + " ");
        }
        System.out.println("\n");
        return tablero;
    }

}