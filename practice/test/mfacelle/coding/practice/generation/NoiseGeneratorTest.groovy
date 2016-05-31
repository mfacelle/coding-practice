package mfacelle.coding.practice.generation

import spock.lang.Specification


class NoiseGeneratorTest extends Specification {

    def "test BasicNoiseGenerator"() {
        when: "noise is generated"
        double[][] terrain = BasicNoiseGenerator.generateIntegerNoise(200,200, 1, 0.1, 0, 1, 1)
        //NoiseGenerator.printMap2D(terrain)
        ImageWriter.greyWriteImage(terrain)

        then: "inspect results by sight"
        true
    }

    // ---

    def "test NoiseGenerator"() {
        when: "noise is generated"
        long startTime = System.currentTimeMillis()
        double[][] terrain = NoiseGenerator.generateNoise(300,300, 300,300, 3, 0.5, 1, 2)
        long timeTaken = System.currentTimeMillis() - startTime
        ImageWriter.greyWriteImage(terrain)

        println("time taken: " + timeTaken)

        then: "inspect results by sight"
        true
    }

    def "test NoiseGenerator rounded noise"() {
        when: "noise is generated"
        long startTime = System.currentTimeMillis()
        double[][] terrain = NoiseGenerator.generateRoundedNoise(100,100, 100,100, 3, 0.5, 1, 2)
        long timeTaken = System.currentTimeMillis() - startTime
        ImageWriter.greyWriteImage(terrain)


        println("time taken: " + timeTaken + " ms")

        then: "inspect results by sight"
        true
    }

    // ---

    def "test ocean creation"() {
        when: "noise is generated and oceans made"
        double[][] terrain = NoiseGenerator.generateRoundedNoise(200,200, 200,200, 10, 0.8, 1, 3)
        ImageWriter.greyWriteImage(terrain, "beforeOcean.png")
        terrain = NoiseGenerator.makeOceans(terrain, 0.5, 0.3)
        ImageWriter.greyWriteImage(terrain, "afterOcean.png")

        then: "inspect"
        true
    }
}
