package mfacelle.coding.practice.generation;

/**
 *  Very simple terrain noise generator.
 *
 *  Uses a single "octave" of noise - only one frequency
 *
 *  Only used to test the simplex noise classes found online
 *
 *  Based off example class presented at:
 *  http://stackoverflow.com/questions/18279456/any-simplex-noise-tutorials-or-resources
 */
public class BasicNoiseGenerator {
    private SimplexNoiseOctave octave;

    private double frequency;
    private double amplitude;
    private int scale;
    private double persistence;

    // ---

    /**
     *
     * @param scale         size of the largest feature
     * @param persistence   on (0,1) - how much variation in terrain
     * @param seed          for random generation
     * @param frequency     (because this is without octaves) - frequency used
     * @param amplitude     (because this is without octave) - amplitude used
     */
    public BasicNoiseGenerator(int scale, double persistence, int seed, double frequency, double amplitude) {

        octave = new SimplexNoiseOctave(seed);

        this.scale = scale;
        this.persistence = persistence;
        this.frequency = frequency;
        this.amplitude = amplitude;
    }

    // ---

    public double getNoise(int x, int y) {
        return octave.noise(x/frequency, y/frequency) * amplitude;
    }


    // ---

    /** generate a 2D matrix with integer noise on [0,1] */
    public static double[][] generateIntegerNoise(int sizeX, int sizeY, int scale, double persistence, int seed, double frequency, double amplitude) {
        double[][] terrain = new double[sizeX][sizeY];

        BasicNoiseGenerator noiseGenerator = new BasicNoiseGenerator(scale, persistence, seed, frequency, amplitude);

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                terrain[i][j] = (noiseGenerator.getNoise(i,j) + 1) / 2;
            }
        }

        return terrain;
    }
}
