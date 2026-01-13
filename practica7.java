import java.util.Scanner;
//import java.util.InputMismatchException;
public class practica7 {
    Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        practica7 p = new practica7();
        p.principal();    
    }

    public void principal() {
        int files = 9;
        int cols = 9;
        int maxJugadors = 2;
        char[][] taulell = new char[files][cols];
        String[] jugadors = new String[maxJugadors];
        boolean partida = true;
        boolean abandonar = false;

        generarTaulell(taulell);
        imprimirTaulell(taulell);
        
        mostrarMissatgeInici(jugadors);

        //boolean de blancas -> if true mueven blancas
        //contador de turnos (hace falta?)
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

        while(partida && !abandonar) {

        }
    
        sc.close();
    }

    public void generarTaulell(char[][] taulell) {
        inicialitzarTaulell(taulell);
        generarPeces(taulell);
    }

    public void inicialitzarTaulell(char[][] taulell) {
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
    }

    public void generarPeces(char[][] taulell) {
        //generar peones
        for(int i = 1; i < taulell.length; i++) {
            taulell[7][i] = 'P';
            taulell[2][i] = 'p';
        }

        int filaPiezasBlancas = 8;
        int filaPiezasNegras = 1;

        // Torres
        int[] torres = {1, 8};
        for (int i = 0; i < torres.length; i++) {
            taulell[filaPiezasBlancas][torres[i]] = 'T';   
            taulell[filaPiezasNegras][torres[i]] = 't';    
        }

        //caballs
        int[] caballos = {2, 7};
        for (int i = 0; i < caballos.length; i++) {
            taulell[filaPiezasBlancas][caballos[i]] = 'C';
            taulell[filaPiezasNegras][caballos[i]] = 'c';
        }

        // Alfiles
        int[] alfiles = {3, 6};
        for (int i = 0; i < alfiles.length; i++) {
            taulell[filaPiezasBlancas][alfiles[i]] = 'A';
            taulell[filaPiezasNegras][alfiles[i]] = 'a';
        }

        // Rey y reina
        taulell[filaPiezasBlancas][4] = 'K';
        taulell[filaPiezasBlancas][5] = 'Q';
        taulell[filaPiezasNegras][4] = 'q';
        taulell[filaPiezasNegras][5] = 'k';
    }

    public void imprimirTaulell(char[][] taulell) {
        for(int f = 0; f < taulell.length; f++) {
            for (int c = 0; c < taulell[f].length; c++) {
                System.out.print(taulell[f][c] + " ");
            }
            System.out.println("");
        }
    }

    public String preguntarNom(String color) {
        String nom = "";

        do {
            System.out.print("Jugador " + color + ": ");
            nom = sc.nextLine();

            if (nom.isEmpty()) {
                System.out.println("El nom no pot estar buit. Si us plau, escriu un nom vàlid.");
            }
            
        } while (nom.isEmpty());
        return nom;
    }

    public void mostrarMissatgeInici(String[] jugadors) {
        System.out.println("Benvinguts al joc d'escacs!");
        System.out.println("Si us plau, introduïu els noms dels jugadors:\n");

        jugadors[0] = preguntarNom("1 (blanques)");
        jugadors[1] = preguntarNom("2 (negres)");
    }

    public int conversionLetras(char letra) {
        int casilla = 0;

        letra = Character.toLowerCase(letra);
        casilla = letra - 'a' + 1;

        return casilla;
    }

    public boolean movimentLegal(String color, String casillaOrigen) {
        boolean legal = false;

        if (color.equals("blanques")) {
            
        }

        return legal;
    }

}