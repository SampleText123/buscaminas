/* Falta: 
 * Como no me funcionaba una cosa he hecho pruebas a ver que iba mal, no sé si todo está en el sitio.
 * Habrá alguna que otra cosa en el código puesta como comentario que era lo que estaba probando.
 */
public class Buscaminas {
    //Atributos
    private final int MINA = -1;
    private final int NUM_MINAS = 10;
    private final int MARCA = -2;
   
    private int[][] tablero;
    //private int[][] tablero2;

    private boolean[][] tabVisitadas;
    private int fila;
    private int columna;
        
    //Constructor
    public Buscaminas(int numFil, int numCol) {
        tablero = new int[numFil][numCol];
        tablero = minasAleatorias(tablero, NUM_MINAS);        
        tabVisitadas = new boolean[numFil][numCol];
        //tablero2 = new int[numFil][numCol];   
    }
    
    /*public void colocarMinas()
    {
        tablero = minasAleatorias(tablero, NUM_MINAS);
    }*/
    
    //toString que debe salir por pantalla
    public String toString(int[][] m, boolean[][] mB) {
        String cad = "\tc0\tc1\tc2\tc3\tc4\tc5\tc6\tc7\nf0\t";
        int k = 0;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (m[i][j] == 0 && mB[i][j]) { //casilla sin mina y visitada
                    cad += " " + "\t";
                }
                else if(!mB[i][j] && m[i][j] == MARCA) { 
                    cad += "M" + "\t";
                }
                else if ((m[i][j] == 0 || m[i][j] == MINA) && !mB[i][j]) { //casilla sin visitar
                    cad += "X" + "\t";
                }
                else { cad += m[i][j] + "\t"; } //casilla visitada
            }
            k++;
            if (k < 8) {
                cad += "\nf" + k + "\t";
            }
        }
        
        return cad;
    }
    
    //toString auxiliar (solo de prueba) que señala las minas con (*)
    public String toStringPrueba(int[][] m, boolean[][] mB) {
        String cad = "\tc0\tc1\tc2\tc3\tc4\tc5\tc6\tc7\nf0\t";
        int k = 0;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (m[i][j] == MINA) {
                    cad += "*\t";
                }
                else {
                    cad += m[i][j] + "\t";
                }
            }
            k++;
            if (k < 8) {
                cad += "\nf" + k + "\t";
            }
        }
        return cad;
    }
    
    //contador de minas adyacentes
    public int cuentaMinas(int[][] m, int fil, int col) {
        int suma = 0;
        if (fil-1 >= 0 && col-1 >= 0 && m[fil-1][col-1] == MINA) {
            suma += 1;
        }
        if (fil-1 >= 0 && m[fil-1][col] == MINA) {
            suma += 1;
        }
        if (fil-1 >= 0 && col+1 <= 7 && m[fil-1][col+1] == MINA) {
            suma += 1;
        }
        if (col-1 >= 0 && m[fil][col-1] == MINA) {
            suma += 1;
        }
        if (col+1 <= 7 && m[fil][col+1] == MINA) {
            suma += 1;
        }
        if (fil+1 <= 7 && col-1 >= 0 && m[fil+1][col-1] == MINA) {
            suma += 1;
        }
        if (fil+1 <= 7 && m[fil+1][col] == MINA) {
            suma += 1;
        }
        if (fil+1 <= 7 && col+1 <= 7 && m[fil+1][col+1] == MINA) {
            suma += 1;
        }
        return suma;
    }
    
    public String buscaMinas(int[][] m, boolean[][] mB, int fil, int col) {
        if (fil < 0 || fil > m.length-1 || col < 0 || col > m[0].length-1) {
            return "";
        }
        
        else if (mB[fil][col] == true) {
            return "";
        }
        
        else {
            mB[fil][col] = (mB[fil][col] == false)?true:false;
            m[fil][col] = cuentaMinas(m, fil, col);
            if (m[fil][col] == 0) {
                buscaMinas(m, mB, fil-1, col-1);
                buscaMinas(m, mB, fil-1, col);
                buscaMinas(m, mB, fil-1, col+1);
                buscaMinas(m, mB, fil, col-1);
                buscaMinas(m, mB, fil, col+1);
                buscaMinas(m, mB, fil+1, col-1);
                buscaMinas(m, mB, fil+1, col);
                buscaMinas(m, mB, fil+1, col+1);
            }
            return toString(m, mB);
        }
    }
    
    //tablero principal, el del juego
    public int[][] getTablero() {
        return tablero;
    }
    
    //tablero auxiliar booleano para casillas visitadas
    public boolean[][] getTabVisitadas() {
        return tabVisitadas;
    }
    
    public int getFila() {
        return this.fila;
    }
    
    public int getColumna() {
        return this.columna;
    }
    
    public boolean esMina(int fil, int col) {
        return this.tablero[fil][col] == MINA;
    }
    
    //generador de minas aleatorias
    public int[][] minasAleatorias(int[][] m, int minas) {
        int al1, al2;
        String cad = "";
        do {
            do {
                al1 = (int)(Math.random()*7);
                al2 = (int)(Math.random()*7);
            } while (m[al1][al2] == MINA);
            
            m[al1][al2] = -1;
            minas--;
        } while (minas > 0);
        
        return m;
    }   
    
    //Ver si casilla está visitada.
    public void setVisitado(int fil, int col){

        if(!this.tabVisitadas[fil][col])
        {
            if(esMina(fil, col) == true)
                {
                    tablero[fil][col] = MINA;
                    mostrarBombas();
                }
            tabVisitadas[fil][col] = true;
        }
    }
    
    //Marcar casillas no visitadas.
    public void casMarcada(int fil, int col){
        if (tabVisitadas[fil][col] == false){
            tablero[fil][col] = MARCA;
            toString(tablero, tabVisitadas);
        }        
    }
    
    //Muestra todo el tablero cuando acaba el juego.
    public void mostrarBombas(){
        for(int i = 0; i <=tablero.length; i++){
            for (int j=0; j<=tablero[i].length; j++){
                if (tablero[i][j] != MINA)
                    System.out.println("| |");
                else
                    System.out.println("|X|");
            }
            System.out.print("\n");
        }
        
    }
    
    //Fin de programa.
    public void juegoAcaba(){
        int contador = 0;
        for(int i = 0; i<=tabVisitadas.length; i++){
            for (int j = 0; i<=tabVisitadas[i].length; j++){
                if(tabVisitadas[i][j] == false) contador++;
            }
        }
        
        if (contador == 10) System.out.println("Has ganado.");
        else System.out.println("Has perdido.");
        
        mostrarBombas();

    }
        
}



