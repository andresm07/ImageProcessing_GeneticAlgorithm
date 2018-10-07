/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareaprogramada1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Andrés Miranda Arias
 */
public class VanGoghEvolucional {
    
    public static void main(String [] args) throws IOException
    {
        BufferedImage img = ImageIO.read(new File("D:\\Andrés Miranda Arias\\Documents\\Analisis de Algoritmos\\Tarea Programada 1\\tareaprogramada1\\imagen1.png"));
        Poblacion poblacion = new Poblacion(img,10);
        System.out.println("creo poblacion");
        
        for(int i = 0; i < 10; i++)
        {
            try
            {
                String name = "output" + Integer.toString(i) + ".png";
                File f = new File ("D:\\Andrés Miranda Arias\\Documents\\Analisis de Algoritmos\\Tarea Programada 1\\tareaprogramada1\\" + name);
                ImageIO.write(poblacion.population.get(i).individuo,"png",f);
            }
            catch(IOException e)
            {
                System.out.println(e);
            }
        }
        
        poblacion.setPorcentajeMutacion(10);
        System.out.println("seteo mutacion");
        poblacion.mutarPoblacion();
        System.out.println("muto poblacion");
        poblacion.calcularFitness();
        System.out.println("calculo fitness");
        
        for(int i = 0; i < 10; i++)
        {
            System.out.println(poblacion.population.get(i).fitness);
        }
        poblacion.setProbabilidadCruce(10);
        poblacion.crossOver();
        System.out.println("hago crossover");
        
    }
    
}
