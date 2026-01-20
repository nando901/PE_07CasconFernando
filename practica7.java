import java.util.ArrayList;
import java.util.Scanner;
//import java.util.InputMismatchException;
public class practica7 {
    Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        practica7 p = new practica7();
        p.principal();    
    }

    //comprobar que las casillas inicio y fin son plausibles (1 o 2?) -> o abandonar?
        //1 blancas y 1 negras o los 2 juntos?
        //para eso mirar casilla de origen, en que tipo de pieza cae?
        //varios else if dependiendo de en que tipo de pieza cae?
        //luego mirar el movimiento que quiere hacer, concuerda con el tipo de ficha? -> intentar usar 1 para los 2 bandos
        //interrumpe a alguna ficha de tu tablero?
        //si haces ese movimiento, tu rey queda en jaque? -> como puedo hacer esto?
        //si es legal, matas a alguna ficha del contrario?
        //banquillo de muertes
        //si el peon llega al final, cambia a cualquier ficha a eleccion
        //actualizar y mostrar tablero
        //si blancas = true -> false
        //registrar movimiento en un arraylist
    //repetir

    public void principal() {
        char[] peçesBlanques = {'Q', 'K', 'T', 'A', 'C', 'P'};
        char[] peçesNegres = {'q', 'k', 't', 'a', 'c', 'p'};
        ArrayList<String> movimentsBlanques = new ArrayList<>();
        ArrayList<String> movimentsNegres = new ArrayList<>();

        char[][] taulell = generarTaulell();
        String[] jugadors = mostrarMissatgeInici();

        boolean partida = true;
        boolean abandonar = false;
        boolean blanques = true; //blanques -> nouen blanques, !blanques -> mouen negres

        while(partida && !abandonar) {
            if (blanques) {
                torn("blanques", taulell, blanques, peçesBlanques, peçesNegres);
                //guardar en arraylist el movimiento

            } else {
                torn("negres", taulell, blanques, peçesBlanques, peçesNegres);
                //guardar en arraylist el movimiento
            }

            imprimirTaulell(taulell);
            blanques = !blanques; //cambia de torn
        }
    }


    //inicialitza totes les caselles del taulell, posant "." 
    public char[][] inicialitzarTaulell() {
        int files = 9;
        int cols = 9;
        char[][] taulell = new char[files][cols];

        taulell[0][0] = ' ';

        //generar fila de letras
        char lletra = 'a';
        for(int c = 1; c < taulell.length; c++) {
            taulell[0][c] = lletra++;      
        }

        //generar columna de letras
        for(int f = 1; f < taulell.length; f++) {  
            taulell[f][0] = (char) ('0' + f);  
        }

        //generar puntos por el tablero
        for(int f = 1; f < taulell.length; f++) {
            for (int c = 1; c < taulell[f].length; c++) {
                taulell[f][c] = '.';
            }
        }
        return taulell;
    }

        //genera las peces blanques i negres
    public void generarPeces(char[][] taulell) {
        //generar peones
        for(int i = 1; i < taulell.length; i++) {
            taulell[7][i] = 'P';
            taulell[2][i] = 'p';
        }

        int filaPeçesBlanques = 8;
        int filaPeçesNegres = 1;

        // Torres
        int[] torres = {1, 8};
        for (int i = 0; i < torres.length; i++) {
            taulell[filaPeçesBlanques][torres[i]] = 'T';   
            taulell[filaPeçesNegres][torres[i]] = 't';    
        }

        //caballs
        int[] cavalls = {2, 7};
        for (int i = 0; i < cavalls.length; i++) {
            taulell[filaPeçesBlanques][cavalls[i]] = 'C';
            taulell[filaPeçesNegres][cavalls[i]] = 'c';
        }

        // alfils
        int[] alfils = {3, 6};
        for (int i = 0; i < alfils.length; i++) {
            taulell[filaPeçesBlanques][alfils[i]] = 'A';
            taulell[filaPeçesNegres][alfils[i]] = 'a';
        }

        // Rei i reina
        taulell[filaPeçesBlanques][4] = 'K';
        taulell[filaPeçesBlanques][5] = 'Q';
        taulell[filaPeçesNegres][4] = 'q';
        taulell[filaPeçesNegres][5] = 'k';
    }

    public char[][] generarTaulell() {
        char[][] taulell = inicialitzarTaulell();
        generarPeces(taulell);
        return taulell;
    }

    public void imprimirTaulell(char[][] taulell) {
        for(int f = 0; f < taulell.length; f++) {
            for (int c = 0; c < taulell[f].length; c++) {
                System.out.print(taulell[f][c] + " ");
            }
            System.out.println("");
        }
    }

    public String[] mostrarMissatgeInici() {
        int maxJugadors = 2;
        String[] jugadors = new String[maxJugadors];

        System.out.println("Benvinguts al joc d'escacs!");
        System.out.println("Introduïu els noms dels jugadors per començar la partida:\n");

        jugadors[0] = preguntarNom("1 (blanques)");
        jugadors[1] = preguntarNom("2 (negres)");

        return jugadors;
    }

    public String preguntarNom(String color) {
        String nom = "";
        boolean valid = true;

        do {
            System.out.print("Jugador " + color + ": ");
            nom = sc.nextLine();
            valid = true;

            if (nom.isEmpty()) {
                System.out.println("[Error] El nom no pot estar buit. Torna-ho a intentar.");
                valid = false;

            } else if (nom.matches("\\d+")) {
                System.out.println("[Error] El nom no pot constar de números. Torna-ho a intentar.");
                valid = false;
            }

        } while (!valid);
        return nom;
    }

    //posiblemente en el futuro devuelva el movimiento ya verificado
    public void torn(String torn, char[][] taulell, boolean blanques, char[] peçesBlanques, char[] peçesNegres) {
        boolean valid =  false;
        boolean validacioPeça = false;

        System.out.println("\nTorn de les " + torn + ": ");

        String casellaOrigen = "";
        do {
            casellaOrigen = demanarCasella("origen");

            if (blanques) {
                valid = comprobarCasellaOrigen(casellaOrigen, taulell, peçesBlanques);
            } else {
                valid = comprobarCasellaOrigen(casellaOrigen, taulell, peçesNegres);
            }

            if (!valid) {
                System.out.println("[Error] La casella d'origen no conté ninguna peça " + torn + ". Torna-ho a intentar.");
            }
        } while (!valid);


        //para que torn no sea tan grande, crear un metodo a parte que se llame validarMoviment con todo esto
        do {
            validacioPeça = false;
            String casellaFinal = demanarCasella("final");
            
            int[] casellaOrigenArray = conversioLletres(casellaOrigen);
            int[] casellaDestiArray = conversioLletres(casellaFinal);

            String peçaDesti = peçaDesti(peçesBlanques, peçesNegres, blanques, casellaDestiArray, taulell);
            Boolean peçaCami = peçaCami(casellaDestiArray, casellaOrigenArray, taulell);

            switch (taulell[casellaOrigenArray[0]][casellaOrigenArray[1]]) {
                case 'P':
                case 'p':
                    validacioPeça = validarPeo(casellaOrigenArray, casellaDestiArray, blanques, peçaDesti, peçaCami);

                    if (!validacioPeça) {
                        System.out.println("[Error] Moviment invàlid");
                    }

                    break;
            
                default:
                    break;
            }
        } while (!validacioPeça);
    }

    public String demanarCasella(String tipusCasella) {
        String casella = "";
        boolean valid = true;

        do {
            System.out.print("Casella " + tipusCasella + ": ");
            casella = sc.nextLine();
            valid = true;

            if (casella.isEmpty()) {
                System.out.println("[Error] La casella no pot quedar buida. Torna-ho a intentar.");
                valid = false;

            } else if (!casella.equalsIgnoreCase("abandonar") && casella.length() != 2) {
                System.out.println("[Error] Format incorrecte. La casella ha de ser una lletra seguida d'un número (ex: e2).");
                valid = false;

            } else if (!casella.equalsIgnoreCase("abandonar") && (casella.charAt(0) < 'a' || casella.charAt(0) > 'h' || casella.charAt(1) < '1' || casella.charAt(1) > '8')) {
                System.out.println("[Error] La casella ha d'estar entre 'a1' i 'h8'. Torna-ho a intentar.");
                valid = false;
            }

        } while (!valid);

        return casella;
    }

    //converteix el format donat per cada casella "c7", en un format nunmeric "3,7"
    public int[] conversioLletres(String casella) {
        int[] casellaValidada = new int[2];
        
        char lletra = casella.charAt(0);

        lletra = Character.toLowerCase(lletra);
        casellaValidada[1] = Character.toLowerCase(casella.charAt(0)) - 'a' + 1;

        casellaValidada[0] = casella.charAt(1) - '0';

        return casellaValidada;
    }

    //acabar de decidir si boolean u otra cosa
    public boolean comprobarCasellaOrigen (String casellaOrigen, char[][] taulell, char[] peçes) {
        boolean valid = false;
        int[] casella = conversioLletres(casellaOrigen);

        for (int i = 0; (i < peçes.length && !valid); i++) {
            if (taulell[casella[0]][casella[1]] == peçes[i]) {
                valid = true;
            }
        }

        return valid;
    }

    //comprova si hi ha alguna peça en el desti del moviment que l'usuari intenta fer
    public String peçaDesti (char[] peçesBlanques, char[] peçesNegres, boolean blanques, int[] casellaDesti, char[][] tauell) {
        String peça = "neutre";

        if (tauell[casellaDesti[0]][casellaDesti[1]] != '.') {
            for (int i = 0; (i < peçesBlanques.length && peça.equals("neutre")); i++) { 
                if (blanques) {
                    if (tauell[casellaDesti[0]][casellaDesti[1]] == peçesNegres[i]) {
                        peça = "enemiga";

                    } else if (tauell[casellaDesti[0]][casellaDesti[1]] == peçesBlanques[i]) {
                        peça = "aliada";
                    }

                } else {
                    if (tauell[casellaDesti[0]][casellaDesti[1]] == peçesBlanques[i]) {
                        peça = "enemiga";

                    } else if (tauell[casellaDesti[0]][casellaDesti[1]] == peçesNegres[i]) {
                        peça = "aliada";
                    }
                } 
            }
        }
        return peça;
    }

    //comprova si durant el recorregut de la peça hi ha algun "obstacle"
    public boolean peçaCami(int[] casellaDesti, int[] casellaOrigen, char[][] taulell) {
        boolean peçaCami = false;
        int desplaçamentVertical = casellaDesti[1] - casellaOrigen[1];
        int desplaçamentHoritzontal = casellaDesti[0] - casellaOrigen[0];
        int direccioFila;
        int direccioCol;
        int desplaçament = 0;

        if (desplaçamentHoritzontal == 0) {
            desplaçament = Math.abs(desplaçamentVertical);
        } else {
            desplaçament = Math.abs(desplaçamentHoritzontal);
        }

        if (casellaDesti[0] > casellaOrigen[0]) {
            direccioFila = 1;
        } else {
            direccioFila = -1;
        }
        
        if (casellaDesti[1] > casellaOrigen[1]) {
            direccioCol = 1;
        } else {
            direccioCol = -1;
        }

        for (int i = 1; i < desplaçament && peçaCami == false; i++) {
            if (taulell[casellaOrigen[0] + i * direccioFila][casellaOrigen[1] + i * direccioCol] != '.') {
                peçaCami = true;
            }
        }

        return peçaCami;
    }

    //valida moviment peo 
    public boolean validarPeo(int[] casellaOrigen, int[] casellaDesti, boolean blanques, String peçaDesti, boolean peçaCami) {
        boolean valid = false;
        
        int desplaçamentVertical = casellaDesti[1] - casellaOrigen[1];
        int desplaçamentHoritzontal = casellaDesti[0] - casellaOrigen[0];

        int fila = casellaOrigen[0];
        int columna = casellaOrigen[1];

        if ((blanques && fila == 7) || (!blanques && fila == 2)) {
            if ((desplaçamentVertical == 2 && desplaçamentHoritzontal == 0 && peçaDesti.equals("neutre") && !peçaCami)) {
                valid = true;
            }
        }

        if (desplaçamentVertical == 1 && desplaçamentHoritzontal == 0 && peçaDesti.equals("neutre") && !peçaCami) {
            valid = true;
        }

        if ((peçaDesti.equals("enemiga") && !peçaCami) && (desplaçamentVertical == 1 && desplaçamentHoritzontal == 1)) {
            valid = true;
        }

        return valid;
    }
}