import java.util.ArrayList;
import java.util.Scanner;
public class practica7 {
    Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        practica7 p = new practica7();
        p.principal();    
    }

    public void principal() {
        boolean partida = true;
        boolean abandonar = false;
        boolean blanques = true;    //blanques -> mouen blanques, !blanques -> mouen negres
        char[] peçesBlanques = {'Q', 'K', 'T', 'A', 'C', 'P'};
        char[] peçesNegres = {'q', 'k', 't', 'a', 'c', 'p'};
        ArrayList<String> movimentsBlanques = new ArrayList<>();
        ArrayList<String> movimentsNegres = new ArrayList<>();

        char[][] taulell = generarTaulell();
        String[] jugadors = mostrarMissatgeInici();
        imprimirTaulell(taulell);

        while(partida && !abandonar) {
            if (blanques) {
                gestionarTorn("blanques", taulell, blanques, peçesBlanques, peçesNegres, movimentsBlanques, movimentsNegres, jugadors);

            } else {
                gestionarTorn("negres", taulell, blanques, peçesBlanques, peçesNegres, movimentsBlanques, movimentsNegres, jugadors);
            }

            blanques = !blanques; //cambia de torn
        }
    }

    public char[][] inicialitzarTaulell() {
        int files = 9;
        int cols = 9;
        char[][] taulell = new char[files][cols];
        taulell[0][0] = ' ';

        //generar fila de lletres
        char lletra = 'a';
        for(int c = 1; c < taulell.length; c++) {
            taulell[0][c] = lletra++;      
        }

        //generar columna de numeros
        for(int f = 1; f < taulell.length; f++) {  
            taulell[f][0] = (char) ('0' + f);  
        }

        //generar punts en la resta del taulell
        for(int f = 1; f < taulell.length; f++) {
            for (int c = 1; c < taulell[f].length; c++) {
                taulell[f][c] = '.';
            }
        }
        return taulell;
    }

    public void generarPeces(char[][] taulell) {
        int filaPeçesBlanques = 8;
        int filaPeçesNegres = 1;

        //generar peons
        for(int i = 1; i < taulell.length; i++) {
            taulell[7][i] = 'P';
            taulell[2][i] = 'p';
        }

        // Torres
        int[] torres = {1, 8};
        for (int i = 0; i < torres.length; i++) {
            taulell[filaPeçesBlanques][torres[i]] = 'T';   
            taulell[filaPeçesNegres][torres[i]] = 't';    
        }

        //cavalls
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
        taulell[filaPeçesBlanques][5] = 'Q';
        taulell[filaPeçesBlanques][4] = 'K';
        taulell[filaPeçesNegres][5] = 'k';
        taulell[filaPeçesNegres][4] = 'q';
    }

    public char[][] generarTaulell() {
        char[][] taulell = inicialitzarTaulell();
        generarPeces(taulell);
        return taulell;
    }

    public void imprimirTaulell(char[][] taulell) {
        System.out.println();

        for(int f = 0; f < taulell.length; f++) {
            for (int c = 0; c < taulell[f].length; c++) {
                System.out.print(taulell[f][c] + " ");
            }
            System.out.println("");
        }

        System.out.println();
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
    public void gestionarTorn(String torn, char[][] taulell, boolean blanques, char[] peçesBlanques, char[] peçesNegres, ArrayList<String> movimentsBlanques, ArrayList<String> movimentsNegres, String[] jugadorsArray) {
        System.out.println("Torn de les " + torn + ": ");

        int[] casellaOrigen = validarCasellaOrigen(torn, taulell, blanques, peçesBlanques, peçesNegres);
        int[] casellaDesti = validarMoviment(taulell, blanques, peçesBlanques, peçesNegres, casellaOrigen);

        executarMoviment(taulell, casellaOrigen, casellaDesti, blanques, movimentsBlanques, movimentsNegres, jugadorsArray);
    }

    public int[] validarCasellaOrigen(String torn, char[][] taulell, boolean blanques, char[] peçesBlanques, char[] peçesNegres) {
        String casellaOrigen = "";
        boolean valid = false;
        int[] casellaOrigenArray = new int[2];

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

        casellaOrigenArray = conversioLletres(casellaOrigen);

        return casellaOrigenArray;
    }

    public int[] validarCasellaDesti() {
        String casellaFinal = demanarCasella("destí");
        int[] casellaDestiArray = conversioLletres(casellaFinal);

        return casellaDestiArray;
    }

    public int[] validarMoviment(char[][] taulell, boolean blanquesBool, char[] peçesBlanques, char[] peçesNegres, int[] casellaOrigen) {
        boolean validacioPeça = false;
        boolean peçaCami = false;
        String peçaDesti = "";
        int[] casellaDesti = new int[2];
        char tipusPeça = taulell[casellaOrigen[0]][casellaOrigen[1]];
        
        do {
            casellaDesti = validarCasellaDesti();

            if (Character.toLowerCase(tipusPeça) != 'c' && Character.toLowerCase(tipusPeça) != 'k') {
                peçaCami = peçaCami(casellaDesti, casellaOrigen, taulell);
            }

            peçaDesti = peçaDesti(peçesBlanques, peçesNegres, blanquesBool, casellaDesti, taulell);
            validacioPeça = revisarMoviment(tipusPeça, blanquesBool, casellaOrigen, casellaDesti, peçaDesti, peçaCami);
            
        } while (!validacioPeça);

        return casellaDesti;
    }

    //revisa el tipus de moviment que fa cada peça
    public boolean revisarMoviment(char tipusPeça, boolean blanquesBool, int[] casellaOrigen, int[] casellaDesti, String peçaDesti, boolean peçaCami) {
        boolean validacioPeça = false;

        switch (Character.toLowerCase(tipusPeça)) {
            case 'p':
                validacioPeça = validarPeo(casellaOrigen, casellaDesti, blanquesBool, peçaDesti, peçaCami);

                if (!validacioPeça) {
                    missatgeError(peçaCami, peçaDesti, "peó");
                }
                break;

            case 't':
                validacioPeça = validarTorre(casellaOrigen, casellaDesti, peçaDesti, peçaCami);

                if (!validacioPeça) {
                    missatgeError(peçaCami, peçaDesti, "torre");
                }
                break;

            case 'a':
                validacioPeça = validarAlfil(casellaOrigen, casellaDesti, peçaDesti, peçaCami);

                if (!validacioPeça) {
                    missatgeError(peçaCami, peçaDesti, "alfil");
                }
                break;

            case 'c':
                validacioPeça = validarCavall(casellaOrigen, casellaDesti, peçaDesti);

                if (!validacioPeça) {
                    missatgeError(peçaCami, peçaDesti, "cavall");
                }
                break;

            case 'q':
                validacioPeça = validarReina(casellaOrigen, casellaDesti, peçaDesti, peçaCami);

                if (!validacioPeça) {
                    missatgeError(peçaCami, peçaDesti, "reina");
                }
                break;

            case 'k':
                validacioPeça = validarRei(casellaOrigen, casellaDesti, peçaDesti, peçaCami);

                if (!validacioPeça) {
                    missatgeError(peçaCami, peçaDesti, "rei");
                }
                break;
        }
        return validacioPeça;
    }

    //mostra els missatges dels possibles errors de moviment
    public void missatgeError(boolean peçaCami, String peçaDesti, String tipusPeça) {
        if (peçaCami && !tipusPeça.equals("cavall")) {
            System.out.println("[Error] No pots saltar per sobre d'altres peces. Hi ha una peça bloquejant el camí");

        } else if (peçaDesti.equals("aliada")) {
            System.out.println("[Error] Aquest moviment no és vàlid segons les regles de moviment de la " + tipusPeça + ".");

        } else {
            System.out.println("[Error] Moviment invàlid per la " + tipusPeça + ".");
        }
    }

    public void executarMoviment(char[][] taulell, int[] casellaOrigen, int[] casellaDesti, boolean blanques, ArrayList<String> movimentsBlanques, ArrayList<String> movimentsNegres, String[] jugadorsArray) {
        String casellaOrigenString = "";
        String casellaDestiString = "";
        String jugadors = "";

        actualitzarTaulell(taulell, casellaOrigen, casellaDesti);

        casellaOrigenString = convertirCasellaAText(casellaOrigen);
        casellaDestiString = convertirCasellaAText(casellaDesti);

        if (blanques) {
            movimentsBlanques.add(casellaOrigenString + ", " + casellaDestiString);
            jugadors = jugadorsArray[0];

        } else {
            movimentsNegres.add(casellaOrigenString + ", " + casellaDestiString);
            jugadors = jugadorsArray[1];
        }

        System.out.println("Moviment " + jugadors + ": " + casellaOrigenString + ", " + casellaDestiString);
        imprimirTaulell(taulell);
    }

    public void actualitzarTaulell(char[][] taulell, int[] casellaOrigen, int[] casellaDesti) {
        taulell[casellaDesti[0]][casellaDesti[1]] = taulell[casellaOrigen[0]][casellaOrigen[1]];
        taulell[casellaOrigen[0]][casellaOrigen[1]] = '.';
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

    //converteix el format donat per cada casella ex:"c7", en un format nunmeric ex:"3,7"
    public int[] conversioLletres(String casella) {
        int[] casellaValidada = new int[2];
        
        char lletra = casella.charAt(0);

        lletra = Character.toLowerCase(lletra);
        casellaValidada[1] = Character.toLowerCase(casella.charAt(0)) - 'a' + 1;

        casellaValidada[0] = casella.charAt(1) - '0';

        return casellaValidada;
    }

    //converteix la casella de format int[] en el mateix format de l'entrada per poder portar un registre dels moviments posteriorment
    public String convertirCasellaAText(int[] casella) {
        String casellaText = "";

        char lletra = (char) ('a' + casella[1] - 1);
        int numero = casella[0];

        casellaText = "" + lletra + numero;

        return casellaText;
    }

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
        boolean hiHaPeca = false;

        int desplaçamentFila = casellaDesti[0] - casellaOrigen[0];
        int desplaçamentCol = casellaDesti[1] - casellaOrigen[1];

        int direccioFila = 0;
        int direccioCol = 0;
        int desplaçamentPeça = 0;

        //seqüènica de condicionals que comrova si el moviment es positiu o negatiu
        if (desplaçamentFila > 0) {
            direccioFila = 1;
        }
        if (desplaçamentFila < 0) {
            direccioFila = -1;
        }

        if (desplaçamentCol > 0) {
            direccioCol = 1;
        }
        if (desplaçamentCol < 0) {
            direccioCol = -1;
        }

        //càlcul del desplaçament
        if (Math.abs(desplaçamentFila) > Math.abs(desplaçamentCol)) {
            desplaçamentPeça = Math.abs(desplaçamentFila);
        } else {
            desplaçamentPeça = Math.abs(desplaçamentCol);
        }

        for (int i = 1; i < desplaçamentPeça && hiHaPeca == false; i++) {
            int filaActual = casellaOrigen[0] + i * direccioFila;
            int colActual = casellaOrigen[1] + i * direccioCol;

            if (taulell[filaActual][colActual] != '.') {
                hiHaPeca = true;
            }
        }

        return hiHaPeca;
    }

    public boolean validarPeo(int[] casellaOrigen, int[] casellaDesti, boolean blanques, String peçaDesti, boolean peçaCami) {
        boolean valid = false;
        int desplaçamentVertical = casellaDesti[0] - casellaOrigen[0];
        int desplaçamentHoritzontal = casellaDesti[1] - casellaOrigen[1];
        int direccio = 0;

        if (blanques) {
            direccio = -1; // blanques pugen
        } else {
            direccio = 1;  // negres baixen
        }

        //durant el primer moviment dels peons es poden moure 2 caselles en lloc d'1
        if ((blanques && casellaOrigen[0] == 7 && desplaçamentVertical == -2 && desplaçamentHoritzontal == 0) || 
            (!blanques && casellaOrigen[0] == 2 && desplaçamentVertical == 2 && desplaçamentHoritzontal == 0)) {
            if (peçaDesti.equals("neutre") && !peçaCami) {
                valid = true;
            }
        }

        if (desplaçamentVertical == direccio && desplaçamentHoritzontal == 0) {
            if (peçaDesti.equals("neutre") && !peçaCami) {
                valid = true;
            }
        }

        //moviment diagonal per matar a alguna peça contraria
        if (desplaçamentVertical == direccio && Math.abs(desplaçamentHoritzontal) == 1 && peçaDesti.equals("enemiga")) {
            valid = true;
        }

        return valid;
    }

    public boolean validarTorre(int[] casellaOrigen, int[] casellaDesti, String peçaDesti, boolean peçaCami) {
        boolean valid = false;
        int desplaçamentVertical = casellaDesti[0] - casellaOrigen[0];
        int desplaçamentHoritzontal = casellaDesti[1] - casellaOrigen[1];

        if ((desplaçamentVertical == 0 && desplaçamentHoritzontal != 0) || 
            (desplaçamentVertical != 0 && desplaçamentHoritzontal == 0)) {
            if (!peçaCami && !peçaDesti.equals("aliada")) {
                valid = true;
            }
        }

        return valid;
    }

    public boolean validarAlfil(int[] casellaOrigen, int[] casellaDesti, String peçaDesti, boolean peçaCami) {
        boolean valid = false;
        int desplaçamentVertical = Math.abs(casellaDesti[0] - casellaOrigen[0]);
        int desplaçamentHoritzontal = Math.abs(casellaDesti[1] - casellaOrigen[1]);
        
        if ((desplaçamentHoritzontal == desplaçamentVertical) && desplaçamentHoritzontal > 0) {
            if (!peçaCami && !peçaDesti.equals("aliada")) {
                valid = true;
            }
        }

        return valid;
    }

    public boolean validarReina(int[] casellaOrigen, int[] casellaDesti, String peçaDesti, boolean peçaCami) {
        boolean valid = false;
        int desplaçamentVertical = Math.abs(casellaDesti[0] - casellaOrigen[0]);
        int desplaçamentHoritzontal = Math.abs(casellaDesti[1] - casellaOrigen[1]);

        //moviment diagonal
        if ((desplaçamentHoritzontal == desplaçamentVertical && desplaçamentHoritzontal > 0)) {
            if (!peçaCami && !peçaDesti.equals("aliada")) {
                valid = true;
            }
        }

        //moviment vertical i horitzontal
        if ((desplaçamentVertical == 0 && desplaçamentHoritzontal != 0) || 
            (desplaçamentVertical != 0 && desplaçamentHoritzontal == 0)) {
            if (!peçaCami && !peçaDesti.equals("aliada")) {
                valid = true;
            }
        }
        return valid;
    }

    public boolean validarRei(int[] casellaOrigen, int[] casellaDesti, String peçaDesti, boolean peçaCami) {
        boolean valid = false;
        int desplaçamentVertical = Math.abs(casellaDesti[0] - casellaOrigen[0]);
        int desplaçamentHoritzontal = Math.abs(casellaDesti[1] - casellaOrigen[1]);

        if ((desplaçamentVertical <= 1 && desplaçamentHoritzontal <= 1) && 
            (desplaçamentVertical != 0 || desplaçamentHoritzontal != 0)) {
            if (!peçaCami && !peçaDesti.equals("aliada")) {
                valid = true;
            }
        }

        return valid;
    }

    public boolean validarCavall(int[] casellaOrigen, int[] casellaDesti, String peçaDesti) {
        boolean valid = false;
        int desplaçamentVertical = Math.abs(casellaDesti[0] - casellaOrigen[0]);
        int desplaçamentHoritzontal = Math.abs(casellaDesti[1] - casellaOrigen[1]);

        if ((desplaçamentVertical == 2 && desplaçamentHoritzontal == 1) || 
            (desplaçamentVertical == 1 && desplaçamentHoritzontal == 2)) {
            if(!peçaDesti.equals("aliada")) {
                valid = true;
            }
        }

        return valid;
    }

    public int[] buscarRei(char[][] taulell, boolean blanques) {
        char bandoRei = ' ';
        boolean casellaReiBool = false;
        int[] casellaRei = new int[2];

        if (blanques) {
            bandoRei = 'K';

        } else {
            bandoRei = 'k';
        }

        for (int files = 1; files < taulell.length && !casellaReiBool; files++) {
            for (int cols = 1; cols < taulell[files].length && !casellaReiBool; cols ++) {
                if (taulell[files][cols] == bandoRei) {
                    casellaReiBool = true;
                    casellaRei[0] = files;
                    casellaRei[1] = cols;
                }
            }
        }

        return casellaRei;
    }

    public void reiEnEscac() {
        
    }
}