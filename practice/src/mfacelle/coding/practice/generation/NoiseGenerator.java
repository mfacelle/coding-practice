package mfacelle.coding.practice.generation;

import java.util.Random;

/**
 *  class to generate simplex/perlin noise - for procedural world generation stuff
 *
 *  References:
 *  1. https://en.wikipedia.org/wiki/Perlin_noise
 *  2. https://en.wikipedia.org/wiki/Simplex_noise
 *  3. http://www.java-gaming.org/index.php?topic=31637.0
 *  4. http://staffwww.itn.liu.se/~stegu/simplexnoise/simplexnoise.pdf
 *  5. http://stackoverflow.com/questions/18279456/any-simplex-noise-tutorials-or-resources
 *
 */
public class NoiseGenerator {

    SimplexNoiseOctave[] octaves;
    double[] frequencys;
    double[] amplitudes;

    int largestFeature;
    double persistence;
    int seed;

    public NoiseGenerator(int largestFeature,double persistence, int seed){
        this.largestFeature=largestFeature;
        this.persistence=persistence;
        this.seed=seed;

        //recieves a number (eg 128) and calculates what power of 2 it is (eg 2^7)
        int numberOfOctaves=(int)Math.ceil(Math.log10(largestFeature)/Math.log10(2));

        octaves=new SimplexNoiseOctave[numberOfOctaves];
        frequencys=new double[numberOfOctaves];
        amplitudes=new double[numberOfOctaves];

        Random rnd=new Random(seed);

        for(int i=0;i<numberOfOctaves;i++){
            octaves[i]=new SimplexNoiseOctave(rnd.nextInt());

            frequencys[i] = Math.pow(2,i);
            amplitudes[i] = Math.pow(persistence,octaves.length-i);
        }
    }

    // ---

    public double getNoise(int x, int y){
        double result=0;

        for(int i=0;i<octaves.length;i++) {
            //double frequency = Math.pow(2,i);
            //double amplitude = Math.pow(persistence,octaves.length-i);
            result = result + octaves[i].noise(x / frequencys[i], y / frequencys[i]) * amplitudes[i];
        }
        return result;
    }


    // ---

    // source: http://stackoverflow.com/questions/18279456/any-simplex-noise-tutorials-or-resources
    public static double[][] generateNoise(int sizeX, int sizeY, int resolutionX, int resolutionY, int seed, int scale, double roughness) {
        NoiseGenerator noiseGenerator = new NoiseGenerator(scale, roughness, seed);

        double xStart=0;
        double XEnd=sizeX;
        double yStart=0;
        double yEnd=sizeY;

        double[][] result=new double[resolutionX][resolutionY];

        for(int i=0;i<resolutionX;i++){
            for(int j=0;j<resolutionY;j++){
                int x=(int)(xStart+i*((XEnd-xStart)/resolutionX));
                int y=(int)(yStart+j*((yEnd-yStart)/resolutionY));
                result[i][j]=0.5*(1+noiseGenerator.getNoise(x,y));
            }
        }

        ImageWriter.greyWriteImage(result);
        return result;
    }

    // ---

    public static void printMap2D(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j]);
                // if not the end of the row, add a space between elements
                if (j < map[i].length-1) {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
    }

    public static void printMap2D(double[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.printf("%.2f", map[i][j]);
                // if not the end of the row, add a space between elements
                if (j < map[i].length-1) {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
    }
}
