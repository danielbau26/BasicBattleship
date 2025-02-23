import java.util.Scanner;

public class Battleship {
    public static void main (String[] args){

        Scanner in = new Scanner(System.in);
        Battleship test = new Battleship();

        while (true){

            int opcion = test.menu(in);

            if (opcion == 1){
                System.out.println("Desea un breve tutorial?");
                System.out.println("1. Sí.");
                System.out.println("2. No.");
                int tutorial = in.nextInt();

                System.out.println("");

                if (tutorial == 1){
                    System.out.println("A continuación se realizara una breve explicación del juego.");
                    System.out.println("El tablero va a ser del tamaño de 10x1 el cual se compone de distintos números:");
                    System.out.println("0: Si es tu tablero, el 0 significa que en ese lugar hay agua.");
                    System.out.println("0: Si el tablero enemigo, el 0 significa que o hay agua o aun no has atacado.");
                    System.out.println("1: Solo lo ves tú y significa que en esa posición hay un barco.");
                    System.out.println("2: Significa que le has o te han impactado a un barco.");
                    System.out.println("3: Sginifica que le has o te han derribado un barco.");
                    System.out.println("");
                }

                System.out.println("Recuerda lo siguiente: ");
                System.out.println("Debes indicar las coordenadas X para cada barco o disparo. Recuerda que los barcos no se deben asolapar.");
                System.out.println("Ahora comenzemos a jugar");

                System.out.println("");

                //Se inicia nuestro arreglo que va a funcionar como el tablero
                int [] tablero = new int[10];

                // Se pide la coordenada de la lancha
                int lancha = 0;
                System.out.println("Ubica tu lancha (ocupación 1 casilla): ");
                System.out.println("Ingrese la coordenada de la Lancha (1-10)");

                // Se verifica si la coordenada se encuentra dentro del rango y se agrega el barco al tablero
                while(true){
                    System.out.print("Coordenada: ");
                    lancha = in.nextInt();
                    if (lancha>=1 && lancha<=10){
                        tablero[lancha-1] = 1;
                        break;
                    }else{
                        System.out.println("Número fuera del rango.");                    
                    }
                }
                System.out.println("Se ha ubicado la lancha en la casillas " + lancha);
                System.out.println("");

                // Se pide la primera coordenada del barco médico
                int posicionMedico1 = 0;
                System.out.println("Ubica tu Barco Médico (ocupación 2 casillas): ");
                System.out.println("Ingrese la coordenada inicial del Barco Médico (1-10)");

                // Se verifica si la coordenada se encuentra dentro del rango, no este ocupada y se ubica una parte del barco en el tablero
                while(true){
                    System.out.print("Coordenada 1: ");
                    posicionMedico1 = in.nextInt();
                    if (posicionMedico1>=1 && posicionMedico1<=10){
                        if (tablero[posicionMedico1-1] == 0){
                            tablero[posicionMedico1-1] = 1;
                            break; 
                        }else{
                            System.out.println("Esa posición se encuentra ocupada, ingrese otra coordenada");
                        }
                    }else{
                        System.out.println("Número fuera del rango.");                  
                    }
                }

                // Se pide la segunda coordenada del barco médico
                int posicionMedico2 = 0;
                System.out.println("Ingrese la coordenada final del Barco Médico (1-10)");

                // Se verifica si la coordenada se encuentra dentro del rango, no este ocupada, este seguida a la coordenada anterior y se agrega la otra parte del barco al tablero
                while(true){
                    System.out.print("Coordenada 2: ");
                    posicionMedico2 = in.nextInt();
                    if (posicionMedico2>=1 && posicionMedico2<=10){
                        if (tablero[posicionMedico2-1] == 0){
                            if (posicionMedico2>posicionMedico1 && posicionMedico2<posicionMedico1+2){
                                tablero[posicionMedico2-1] = 1;
                                break;
                            }else if (posicionMedico2<posicionMedico1 && posicionMedico2>posicionMedico1-2){
                                tablero[posicionMedico2-1] = 1;
                                break;
                            }else{
                                System.out.println("Tienes que ubicar la coordenada seguida de la coordenada " + posicionMedico1);
                            }
                        }else {
                            System.out.println("Esa posición se encuentra ocupada, ingrese otra coordenada");
                        }
                    }else {
                        System.out.println("Número fuera del rango.");                
                    }
                }
                System.out.println("Se ha ubicado el barco médico en las casillas (" + posicionMedico1 + ", " + posicionMedico2 + ")");
                System.out.println("");

                int posicionMunicion1 = 0;
                System.out.println("Ubica tu Barco Munición (ocupación 3 casillas): ");
                System.out.println("Ingrese la coordenada inicial del Barco Munición (1-10)");
        
                // Se verifica si la coordenada se encuentra dentro del rango, no este ocupada y se ubica una parte del barco en el tablero
                while(true){
                    System.out.print("Coordenada 1: ");
                    posicionMunicion1 = in.nextInt();
                    if (posicionMunicion1>=1 && posicionMunicion1<=10){

                        if (posicionMunicion1<=8){
                            if (tablero[posicionMunicion1-1]==0 && tablero[posicionMunicion1] == 0 && tablero[posicionMunicion1+1] == 0){
                                tablero[posicionMunicion1-1] = 1;
                                break;
                            }else{
                                System.out.println("Esa posición se encuentra ocupada o no hay suficientes espacio para el barco, ingrese otra coordenada.");
                            }
                        }else if (posicionMunicion1>=3){
                            if (tablero[posicionMunicion1-1]==0 && tablero[posicionMunicion1-2]==0 && tablero[posicionMunicion1-3] == 0){
                                tablero[posicionMunicion1-1] = 1;
                                break;
                            }else{
                                System.out.println("Esa posición se encuentra ocupada o no hay suficientes espacio para el barco, ingrese otra coordenada.");
                            }
                        }else{
                            System.out.println("Tienes que ubicar la coordenada con dos espacios vacios ya sea hacia adelante o atras, ingrese otra coordenada.");
                        }

                    }else{
                        System.out.println("Número fuera del rango.");                  
                    }
                }
        
                int posicionMunicion2 = 0;
                System.out.println("Ingrese la coordenada final del Barco Munición (1-10)");
        
                // Se verifica si la coordenada se encuentra dentro del rango, no este ocupada, este dos espacios antes o despues a la coordenada anterior y se agrega la otra parte del barco al tablero
                while(true){
                    System.out.print("Coordenada 2: ");
                    posicionMunicion2 = in.nextInt();
                    if (posicionMunicion2>=1 && posicionMunicion2<=10){
                        if (posicionMunicion2>posicionMunicion1 && posicionMunicion2==posicionMunicion1+2){
                            tablero[posicionMunicion2-1] = 1;
                            tablero[posicionMunicion2-2] = 1;
                            break;
                        }else if (posicionMunicion2<posicionMunicion1 && posicionMunicion2==posicionMunicion1-2){
                            tablero[posicionMunicion2-1] = 1;
                            tablero[posicionMunicion2] = 1;
                                break;
                        }else{
                            System.out.println("Tienes que ubicar la coordenada dos espacios antes o despues de la coordenada " + posicionMunicion1);
                            }
                    }else {
                        System.out.println("Número fuera del rango.");                
                    }
                }
                System.out.println("Se ha ubicado el barco municion en las casillas (" + posicionMunicion1 + ", " + posicionMunicion2 + ")");
                System.out.println("");


                System.out.println("Finalmente tu tablero queda de esa forma: ");
                for(int i = 0; i<tablero.length; i++){
                    System.out.print(tablero[i] + " ");
                }

                System.out.println("");

                System.out.println("Muy bien, empezemos ahora a jugar");
        

            }else {
                System.out.println("Gracias por jugar.");
                break;
            }
        }

    in.close();


    }

    public int menu (Scanner in){

        int opcion = 0;
        System.out.println("Bienvenido a una simulación del juego de Battership");
        System.out.println("Seleccione la opción que desea hacer: ");
        System.out.println("1. Jugar");
        System.out.println("2. Salir del programa");

        while(true){
            opcion = in.nextInt();
            if (opcion>=1 && opcion<=2){
                break;
            }else{
                System.out.println("Número fuera del rango.");                    
            }
        }

        return opcion;
    }

}