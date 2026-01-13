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
        char[][] taulell = new char[files][cols];

        generarTaulell(taulell);
        imprimirTaulell(taulell);

        /*
        metodo para nombres 
            validacion no esta vacio
        como decidir orden? + primer turno blancas
            ejemplo -> if turno == 1 -> blancas == true
            metodo de decision de turnos
        pedir casilla origen + casilla destino -> recomendacion profe usar (abs) para no repetir codigo (destino-origen para saber cuanto se mueve -> en caso de caballo??)
        parte dificil -> validacion de movimiento
            lado correcto?
            no atraviesa ninguna ficha propia?

        */
        
    
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
}