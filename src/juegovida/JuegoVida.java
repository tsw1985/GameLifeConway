package juegovida;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class JuegoVida 
{
        
    JFrame frame;
    JPanel tablero;
    
    int[][] contenedor;
    int[][] tableroActual;
    int[][] tableroFuturo ;
    int[][] auxiliar ;
    
    int filas = 40;
    int columnas = 40;
    int milisegundos = 800;
    
    JButton[][] botones;

    public JuegoVida()
    {}

    private void construyePanelInferior()
    {
        botones = new JButton[filas][columnas];
        tablero= new JPanel();
        tablero.setLayout(new GridLayout(filas,columnas));
        
        
        for(int i = 0 ; i < filas ; i++)
        {
            for(int j = 0 ; j < columnas ; j++)
            {
                JButton boton = new JButton();
                String id = String.valueOf(i) +"-" + String.valueOf(j);
                boton.setName(id);
                
                boton.setSize(5, 5);
                boton.setToolTipText(id);

                botones[i][j] = boton;
            }
        }
        
        for(int i = 0 ; i < filas ; i++)
        {
            for(int j = 0 ; j < columnas ; j++)
            {
                tablero.add(botones[i][j]);
            }
        }
                  
    }
    
          
    private void construyeVentana()
    {
        frame =new JFrame("El juego de la vida. Por Gabriel Gonzalez");
        frame.setSize(600, 600);
        frame.add(tablero);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    
    private void empiezaJuego()
    {
        //Igualo la copia del tablero con la primera version del tablero
        //para cuando acabe las iteraciones aplicar los cambios , ya que
        //es una de las reglas del juego
        
        contenedor           = new int[3][3];
        tableroActual        = new int[filas][columnas];
        tableroFuturo        = new int[filas][columnas];
        auxiliar             = new int[filas][columnas];
        
        inicializaTableros(tableroActual);
        iniciaVida();
        maquillaTablero();
        pintaTablero(tableroActual);

     do
     {
         
        //Recorre Tablero para realizar las comprobaciones de las celulas
        for(int i= 0 ; i < filas -2 ; i++)
        {            
            for(int j = 0 ; j < columnas -2 ; j++)
            {
                int celda1 = tableroActual[(i)][(j)];
                int celda2 = tableroActual[(i)][(j+1)];
                int celda3 = tableroActual[(i)][(j+2)];
                
                int celda4 = tableroActual[(i+1)][(j)];
                int celda5 = tableroActual[(i+1)][(j+1)];
                int celda6 = tableroActual[(i+1)][(j+2)];
                
                int celda7 = tableroActual[(i+2)][(j)];
                int celda8 = tableroActual[(i+2)][(j+1)];
                int celda9 = tableroActual[(i+2)][(j+2)];
                
                
                contenedor[0][0] = celda1;
                contenedor[0][1] = celda2;
                contenedor[0][2] = celda3;
                
                contenedor[1][0] = celda4;
                contenedor[1][1] = celda5;
                contenedor[1][2] = celda6;
                
                contenedor[2][0] = celda7;
                contenedor[2][1] = celda8;
                contenedor[2][2] = celda9;
                
                    int contador = 0;
                    int k = 0;
                    int l = 0;
                    
                    for(k = 0 ; k < 3 ; k++)
                    {
                        for( l= 0 ; l < 3 ; l++)
                        {
                            if( !(k == 1 && l == 1))
                            {
                                if(contenedor[k][l] == 1)
                                {
                                    contador++;
                                }
                            }
                           
                        }
                    }

                    if(contador < 2 && contenedor[1][1] == 1) 
                    {  
                        tableroFuturo[(i+1)][(j+1)] = 0;
                    }
                    else if(contador > 3 && contenedor[1][1] == 1) 
                    { 
                        tableroFuturo[(i+1)][(j+1)] = 0;
                    }
                    else if(contador == 3 && contenedor[1][1] == 0 ) 
                    { 
                        tableroFuturo[(i+1)][(j+1)] = 1;
                    }
                    else if(contador == 3 && contenedor[1][1] == 1 ) 
                    { 
                        tableroFuturo[(i+1)][(j+1)] = 1;
                    }
                    else if(contador == 2 ) 
                    {
                        tableroFuturo[(i+1)][(j+1)] = tableroActual[(i+1)][(j+1)];
                    }
                    
                    
            }
            
        } 
    
        auxiliar = tableroActual;
        tableroActual = tableroFuturo;
        tableroFuturo = auxiliar;
        
        try 
        {
            Thread.currentThread().sleep(milisegundos);
         } 
        catch (InterruptedException ie) 
        {
           System.out.println("Exception " + ie.toString() );
        }
        
        pintaTablero(tableroFuturo);
        inicializaTableros(tableroFuturo);

     }
     while(true);
        
    }
    
    
    
    
       
    private void pintaTablero(int[][]tabla)
    {
        for(int i = 0 ; i < filas ; i++)
        {
            for(int j = 0; j < columnas ; j++)
            {
                if(tabla[i][j] == 1)
                {
                    botones[i][j].setBackground(Color.red);
                }
                
                if(tabla[i][j] == 0)
                {
                    botones[i][j].setBackground(Color.GRAY);
                }
            
            }
        }
    }
    
    
    private void maquillaTablero()
    {
        for(int i = 0 ; i < filas ; i++)
        {
            for(int j = 0; j < columnas ; j++)
            {
                    botones[i][j].setBackground(Color.GRAY);
            }
        }
    
    }
    
    private void inicializaTableros(int[][] tablero)
    {
        for(int i = 0 ; i < filas ; i++)
        {
            for(int j= 0 ; j < columnas ; j++)
            {
                tablero[i][j] = 0;
            }
        }
    }
    
    private void iniciaVida()
    {
        //Celula palo.
        /*tableroActual[3][2] = 1;
        tableroActual[3][3] = 1;
        tableroActual[3][4] = 1;*/
        
            for(int j= 0 ; j < 324 ; j++)
            {
                int y = (int)Math.floor(Math.random()*(0-filas+1)+filas);
                int x = (int)Math.floor(Math.random()*(0-columnas+1)+columnas);
                
                tableroActual[y][x] = 1;
            }
            
    }
    
    
    public void mensaje(String texto,String titulo)
    {
        JOptionPane.showMessageDialog(null, texto, titulo, JOptionPane.WARNING_MESSAGE);
    }


    public static void main(String [] inforux)
    {
        JuegoVida juego = new JuegoVida();
        juego.construyePanelInferior();
        juego.construyeVentana();
        juego.empiezaJuego();
        
    }   

}
