/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareaprogramada1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Andrés Miranda Arias
 */
public class Poblacion 
{
    public int poblacionInicial;
    public int probabilidadCruce;
    public int porcentajeMutacion;
    public int individuosAptos;
    public static BufferedImage imagenMeta;
    ArrayList<DNA> population = new ArrayList<>();
    
    public Poblacion(BufferedImage target, int poblacionInicial)
    {
        Poblacion.imagenMeta = target;
        this.poblacionInicial = poblacionInicial;
        for(int i = 0; i < this.poblacionInicial; i++)
        {
            this.population.add(new DNA(Poblacion.imagenMeta));
        }
    }
    
    public void setImagen(BufferedImage imagenMeta)
    {
        Poblacion.imagenMeta = imagenMeta;
    }
    
    public void setPoblacionInicial(int poblacionInicial)
    {
        this.poblacionInicial = poblacionInicial;
    }
    
    public void setProbabilidadCruce(int probabilidadCruce)
    {
        this.probabilidadCruce = probabilidadCruce;
    }
    
    public void setPorcentajeMutacion(int porcentajeMutacion)
    {
        this.porcentajeMutacion = porcentajeMutacion;
    }
    
    public void setIndividuosAptos(int individuosAptos)
    {
        this.individuosAptos = individuosAptos;
    }
    
    public void mutarPoblacion()
    {
        for(int i = 0; i < this.population.size(); i ++)
        {
            this.population.get(i).mutar(this.porcentajeMutacion);
        }
    }
    
    public void normalizedFitness()
    {
        DNA.totalScore = 0;
        for(DNA dna : this.population)
        {
            DNA.totalScore += dna.fitness;
        }
        
        for(DNA dna : this.population)
        {
            float f = dna.fitness;
            float d = DNA.totalScore;
            System.out.println("f "+Float.toString(f));
            System.out.println("d "+Float.toString(d));
            dna.normalizedFitness = dna.fitness/DNA.totalScore;
        }
    }
    
    public DNA pickParent()
    {
        double r = Math.random();
        int index = 0;
        while(r > 0)
        {
            System.out.println("r"+r);
            System.out.println(this.population.get(index).normalizedFitness);
            r = r - this.population.get(index).normalizedFitness;
            index++;
        }
        index--;
        return this.population.get(index);
    }
    
    public void calcularFitness()
    {
        for(DNA dna : this.population)
        {
            dna.calculateFitness(Poblacion.imagenMeta);
        }
    }
    
    public void crossOver()
    {
        ArrayList<DNA> newGeneration = new ArrayList<>();
        this.normalizedFitness();
        int porcentaje = (this.population.size()*this.probabilidadCruce)/100;
        for(int i = 0; i < porcentaje; i++)
        {
            //System.out.println("crossover entro for para pickparent");
            DNA padre = this.pickParent();
            DNA madre = this.pickParent();
            if(padre != madre)
            {
                DNA hijo = padre.crossover(madre);
                newGeneration.add(hijo);
                continue;
            }
            i--;
        }
        while(newGeneration.size() < this.population.size())
        {
            newGeneration.add(this.population.get((int)(Math.random()*this.population.size())));
        }
        
        this.population = newGeneration;
        for(int i = 0; i < this.population.size(); i++)
        {
            try
            {
                String name = "output" + Integer.toString(i) + ".png";
                File f = new File ("D:\\Andrés Miranda Arias\\Documents\\Analisis de Algoritmos\\Tarea Programada 1\\tareaprogramada1\\outputimages\\" + name);
                ImageIO.write(this.population.get(i).individuo,"png",f);
            }
            catch(IOException e)
            {
                System.out.println(e);
            }
        }
        this.mutarPoblacion();
        this.calcularFitness();
        DNA.totalScore = 0;
    }
    
    
}
