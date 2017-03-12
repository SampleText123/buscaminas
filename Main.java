import java.util.*;

public class Main {
   public static void main(String[] args) {
        Scanner t = new Scanner(System.in).useLocale(Locale.US);
        
        int filaUsr, columnaUsr;
        String seleccionar;
        
        Buscaminas b = new Buscaminas(8,8);
        System.out.println(b.toStringPrueba(b.getTablero(), b.getTabVisitadas()));
        System.out.println("El tablero tiene una dimensiones de " + b.getFila() + "x" + b.getColumna());
        //en vez de esto creo que queda mejor hacer otro metodo menu()
        do {
           System.out.println("Introduce fila: ");
            filaUsr = t.nextInt();
            System.out.println("Introduce columna: ");
            columnaUsr = t.nextInt();
            //a continuacion debe preguntar si marca mina
            do{
                System.out.println("¿Quiere visitar o marcar una casilla?");
                seleccionar = t.next();
            }while(seleccionar != "M" && seleccionar != "V");
            
            if (seleccionar == "M"){
                b.casMarcada(filaUsr, columnaUsr);
            }
            else b.setVisitado(filaUsr, columnaUsr);
            
            
            System.out.println(b.buscaMinas(b.getTablero(), b.getTabVisitadas(), filaUsr, columnaUsr));
        
            b.juegoAcaba(); //Esto no sé si dará algún error.
        } while (!b.esMina(filaUsr, columnaUsr));
        
        
   }
}