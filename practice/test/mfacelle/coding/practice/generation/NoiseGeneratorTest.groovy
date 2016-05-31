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
        double[][] terrain = NoiseGenerator.generateNoise(300,300, 300,300, 0, 1000, 0.5)
        long timeTaken = System.currentTimeMillis() - startTime

        println("time taken: " + timeTaken)

        then: "inspect results by sight"
        true
    }
}
