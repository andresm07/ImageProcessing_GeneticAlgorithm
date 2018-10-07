/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareaprogramada1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Andrés Miranda Arias
 */
public class DNA 
{
    public BufferedImage individuo;
    public int fitness = 1;
    public double normalizedFitness = 0;
    public static int totalScore = 0;
    public ArrayList<Color> usefulColors = new ArrayList<>();
    
    public DNA(BufferedImage target)
    {
        this.individuo = new BufferedImage(target.getWidth(), target.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < target.getHeight(); i++)
        {
            for(int j = 0; j < target.getWidth(); j++)
            {
                int p = crearPixel();
                    this.individuo.setRGB(i,j,p);
                    /*Color color = new Color(i,j,p);
                    if(!this.usefulColors.contains(color))
                    {
                        this.usefulColors.add(color);
                        for(int k = 0; k < 3; k++)
                        {
                            usefulColors.add(newColor());
                        }
                    }*/
            }
        }
    }
    
    private Color newColor()
    {
        return new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
    }
    
    public void calculateEuclideanFitness(BufferedImage target)
    {
        int sumatoria = 0;
        for(int i = 0; i < target.getHeight(); i++)
        {
            for(int j = 0; j < target.getWidth(); j++)
            {
                int colorIndividuo = this.individuo.getRGB(i,j);
                int rojoIndividuo = (colorIndividuo & 0xff0000) >> 16;
                int verdeIndividuo = (colorIndividuo & 0xff00) >> 8;
                int azulIndividuo = (colorIndividuo & 0xff);
                
                int colorTarget = target.getRGB(i,j);
                int rojoTarget = (colorTarget & 0xff0000) >> 16;
                int verdeTarget = (colorTarget & 0xff00) >> 8;
                int azulTarget = (colorTarget & 0xff);
                
                int restaRojo = Math.abs(rojoTarget-rojoIndividuo);
                int restaVerde = Math.abs(verdeTarget-verdeIndividuo);
                int restaAzul = Math.abs(azulTarget-azulIndividuo);
                
                int cuadradoRojo = (int) Math.pow(restaRojo,2);
                int cuadradoVerde = (int) Math.pow(restaVerde,2);
                int cuadradoAzul = (int) Math.pow(restaAzul,2);
                
                int sumaFinal = cuadradoRojo + cuadradoVerde + cuadradoAzul;
                sumatoria += sumaFinal;
            }
        }
        this.fitness = (int)Math.sqrt(sumatoria);
    }
    
    public void calculateManhattanFitness(BufferedImage target)
    {
        int sumatoria = 0;
        for(int i = 0; i < target.getHeight(); i++)
        {
            for(int j = 0; j < target.getWidth(); j++)
            {
                int colorIndividuo = this.individuo.getRGB(i,j);
                int rojoIndividuo = (colorIndividuo & 0xff0000) >> 16;
                int verdeIndividuo = (colorIndividuo & 0xff00) >> 8;
                int azulIndividuo = (colorIndividuo & 0xff);
                
                int colorTarget = target.getRGB(i,j);
                int rojoTarget = (colorTarget & 0xff0000) >> 16;
                int verdeTarget = (colorTarget & 0xff00) >> 8;
                int azulTarget = (colorTarget & 0xff);
                
                int restaRojo = Math.abs(rojoTarget-rojoIndividuo);
                int restaVerde = Math.abs(verdeTarget-verdeIndividuo);
                int restaAzul = Math.abs(azulTarget-azulIndividuo);
                
                int sumaFinal = restaRojo + restaVerde + restaAzul;
                sumatoria += sumaFinal;
            }
        }
        this.fitness = sumatoria;
    }
    
    public void calculateFitness(BufferedImage target)
    {
        for(int i = 0; i < target.getHeight(); i++)
        {
            for(int j = 0; j < target.getWidth(); j++)
            {
                int colorIndividuo = this.individuo.getRGB(j,i);
                
                /*int rojoIndividuo = (colorIndividuo >> 16) & 0xff;
                int verdeIndividuo = (colorIndividuo >> 8) & 0xff;
                int azulIndividuo = (colorIndividuo) & 0xff;*/
                
                int colorTarget = target.getRGB(j,i);
                /*int rojoTarget = (colorTarget >> 16) & 0xff;
                int verdeTarget = (colorTarget >> 8) & 0xff;
                int azulTarget = (colorTarget) & 0xff;*/
                
                if(colorIndividuo == colorTarget)
                {
                    this.fitness++;
                }
            }
        }
        this.fitness = (int) Math.pow(this.fitness,2);
    }
    
    public int crearPixel()
    {
        int a = 255;
        int r = (int)(Math.random()*256);
        int g = (int)(Math.random()*256);
        int b = (int)(Math.random()*256);
        int p = (a<<24) | (r<<16) | (g<<8) | b;
        return p;
    }
    
    public void mutar(int porcentajeMutacion)
    {
        int ran;
        for(int i = 0; i < this.individuo.getHeight(); i++)
        {
            for(int j = 0; j < this.individuo.getWidth(); j++)
            {
                ran = (int) (Math.random() * 101);
                if(ran < porcentajeMutacion)
                {
                    int p = this.crearPixel();
                    try
                    {
                        this.individuo.setRGB(i, j, p);
                    }
                    catch(Exception e)
                    {
                        System.out.println("error");
                    }
                }
            }
        }
    }
    
    public DNA(DNA padre, DNA madre)
    {
        this.individuo = new BufferedImage(padre.individuo.getWidth(), padre.individuo.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < padre.individuo.getWidth(); i++)
        {
            for(int j = 0; j < padre.individuo.getHeight(); j++)
            {
                int rgb = (i+j)%2 == 0 ? padre.individuo.getRGB(i,j) : madre.individuo.getRGB(i,j);
                this.individuo.setRGB(i,j,rgb);
            }
        }
    }
    
    public DNA crossover(DNA padre)
    {
        return new DNA(this,padre);
    }
}
