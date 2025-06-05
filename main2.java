import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

public class Main {

    // Global constants
    public static final double G = 6.67430e-11;
    public static final double AU_TO_METERS = 1.495978707e11;
    public static final double SECONDS_PER_DAY = 86400;

    // Parent Class: Represents a generic celestial body.
    static class CelestialBody {
        private String name;
        private String category;
        private double mass;
        private double radius;
        private double orbitalRadius;
        private double currentTheta;
        private double orbitalPeriod;

        // Constructor for CelestialBody.
        public CelestialBody(String name, double mass, double radius) {
            this.name = name;
            this.category = "unspecified";
            this.mass = mass;
            this.radius = radius;
            this.orbitalRadius = 0.0;
            this.currentTheta = 0.0;
            this.orbitalPeriod = 0.0;
        }

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public double getMass() {
            return mass;
        }

        public void setMass(double mass) {
            this.mass = mass;
        }

        public double getRadius() {
            return radius;
        }

        public void setRadius(double radius) {
            this.radius = radius;
        }

        public double getOrbitalRadius() {
            return orbitalRadius;
        }

        public void setOrbitalRadius(double orbitalRadius) {
            this.orbitalRadius = orbitalRadius;
        }

        public double getCurrentTheta() {
            return currentTheta;
        }

        // Ensures theta stays within 0 to 2*PI radians.
        public void setCurrentTheta(double currentTheta) {
            this.currentTheta = currentTheta % (2 * Math.PI);
        }

        public double getOrbitalPeriod() {
            return orbitalPeriod;
        }

        public void setOrbitalPeriod(double orbitalPeriod) {
            this.orbitalPeriod = orbitalPeriod;
        }

        // Calculates the density.
        public double getDensity() {
            if (radius <= 0) {
                return 0.0;
            }
            double volume = (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
            return mass / volume;
        }

        // Calculates surface gravity.
        public double getSurfaceGravity() {
            if (radius <= 0) {
                return 0.0;
            }
            return (G * mass) / Math.pow(radius, 2);
        }

        // Simulates orbital revolution.
        public void revolve(double deltaTime) {
            if (orbitalPeriod > 0) {
                double angularSpeed = (2 * Math.PI) / orbitalPeriod;
                double deltaTheta = angularSpeed * deltaTime;
                setCurrentTheta(currentTheta + deltaTheta);
            }
        }

        // Provides formatted coordinates.
        public String getCoordinates() {
            DecimalFormat rFormatter = new DecimalFormat("0.##E0");
            DecimalFormat thetaFormatter = new DecimalFormat("0.00");
            return "(" + rFormatter.format(orbitalRadius) + " m, " + thetaFormatter.format(currentTheta) + " rad)";
        }

        @Override
        public String toString() {
            return name + " (" + category + ")";
        }
    }

    // Child Class: Represents a Planet.
    static class Planet extends CelestialBody {
        private int numberOfMoons;
        private boolean hasRings;
        private boolean supportsLife;

        // Constructor for Planet.
        public Planet(String name, double mass, double radius, double orbitalRadius,
                      double orbitalPeriod, int moons, boolean rings, boolean supportsLife) {
            super(name, mass, radius);
            setCategory("planet");
            setOrbitalRadius(orbitalRadius);
            setOrbitalPeriod(orbitalPeriod);
            this.numberOfMoons = moons;
            this.hasRings = rings;
            this.supportsLife = supportsLife;
        }

        // Getters and Setters for Planet-specific properties
        public int getNumberOfMoons() {
            return numberOfMoons;
        }

        public void setNumberOfMoons(int numberOfMoons) {
            this.numberOfMoons = numberOfMoons;
        }

        public boolean hasRings() {
            return hasRings;
        }

        public void setHasRings(boolean hasRings) {
            this.hasRings = hasRings;
        }

        public boolean supportsLife() {
            return supportsLife;
        }

        public void setSupportsLife(boolean supportsLife) {
            this.supportsLife = supportsLife;
        }

        // Describes the planet's moons.
        public void describeMoons() {
            System.out.println(getName() + " has " + numberOfMoons + " known moon(s).");
        }

        // Checks for presence of rings.
        public void checkRings() {
            System.out.println(getName() + (hasRings ? " has a visible ring system." : " does not have rings."));
        }

        // Assesses if the planet supports life.
        public void assessLifeSupport() {
            System.out.println(getName() + (supportsLife ? " is capable of supporting life." : " is not known to support life."));
        }

        @Override
        public String toString() {
            return super.toString() + " with " + numberOfMoons + " moon(s) and " + (hasRings ? "rings" : "no rings") + " at " + getCoordinates();
        }
    }

    // Child Class: Represents a Moon.
    static class Moon extends CelestialBody {
        private String planetOrbitingName;
        private boolean tidallyLocked;

        // Constructor for Moon.
        public Moon(String name, double mass, double radius, double orbitalRadius,
                    double orbitalPeriod, String planetOrbitingName, boolean tidallyLocked) {
            super(name, mass, radius);
            setCategory("moon");
            setOrbitalRadius(orbitalRadius);
            setOrbitalPeriod(orbitalPeriod);
            this.planetOrbitingName = planetOrbitingName;
            this.tidallyLocked = tidallyLocked;
        }

        // Getters and Setters for Moon-specific properties
        public String getPlanetOrbitingName() {
            return planetOrbitingName;
        }

        public void setPlanetOrbitingName(String planetOrbitingName) {
            this.planetOrbitingName = planetOrbitingName;
        }

        public boolean isTidallyLocked() {
            return tidallyLocked;
        }

        public void setTidallyLocked(boolean tidallyLocked) {
            this.tidallyLocked = tidallyLocked;
        }

        // Simulates a tidal effect.
        public void exertTidalInfluence() {
            System.out.println(getName() + " is exerting tidal forces on " + planetOrbitingName + ".");
        }

        // Describes tidal lock status.
        public void describeTidalLock() {
            System.out.println(getName() + (tidallyLocked ? " is tidally locked to " : " is not tidally locked to ") + planetOrbitingName + ".");
        }

        @Override
        public String toString() {
            return super.toString() + " orbiting " + planetOrbitingName + " at " + getCoordinates();
        }
    }

    // Child Class: Represents a Comet.
    static class Comet extends CelestialBody {
        private double eccentricity;
        private double perihelionDistance;

        // Constructor for Comet.
        public Comet(String name, double mass, double radius, double eccentricity,
                     double orbitalPeriod, double perihelionDistance) {
            super(name, mass, radius);
            setCategory("comet");
            this.eccentricity = eccentricity;
            this.perihelionDistance = perihelionDistance;
            setOrbitalPeriod(orbitalPeriod);
        }

        // Getters and Setters for Comet-specific properties
        public double getEccentricity() {
            return eccentricity;
        }

        public void setEccentricity(double eccentricity) {
            this.eccentricity = eccentricity;
        }

        public double getPerihelionDistance() {
            return perihelionDistance;
        }

        public void setPerihelionDistance(double perihelionDistance) {
            this.perihelionDistance = perihelionDistance;
        }

        // Describes the orbit's shape.
        public void describeOrbitShape() {
            System.out.println(getName() + " has an eccentric orbit with value: " + eccentricity + " and perihelion distance " + String.format("%.2E", perihelionDistance) + " m.");
        }

        // Simulates brightening near a star.
        public void brightenNearStar() {
            System.out.println(getName() + " is brightening as it approaches the central star!");
        }

        @Override
        public String toString() {
            return super.toString() + " with eccentricity " + eccentricity + " at " + getCoordinates();
        }
    }

    // Child Class: Represents a Star.
    static class Star extends CelestialBody {
        private double surfaceTemperature;
        private String spectralType;

        // Constructor for Star.
        public Star(String name, double mass, double radius, double surfaceTemperature, String spectralType) {
            super(name, mass, radius);
            setCategory("star");
            this.surfaceTemperature = surfaceTemperature;
            this.spectralType = spectralType;
            setOrbitalRadius(0.0);
            setOrbitalPeriod(0.0);
        }

        // Getters and Setters for Star-specific properties
        public double getSurfaceTemperature() {
            return surfaceTemperature;
        }

        public void setSurfaceTemperature(double surfaceTemperature) {
            this.surfaceTemperature = surfaceTemperature;
        }

        public String getSpectralType() {
            return spectralType;
        }

        public void setSpectralType(String spectralType) {
            this.spectralType = spectralType;
        }

        // Simulates energy emission.
        public void emitLightAndHeat() {
            System.out.println(getName() + " is emitting light and heat from its surface at " + String.format("%,.0f", surfaceTemperature) + " Kelvin.");
        }

        // Describes the star's spectral type.
        public void describeSpectralType() {
            System.out.println(getName() + " is a " + spectralType + " star.");
        }

        @Override
        public String toString() {
            return super.toString() + " (" + spectralType + ") at " + String.format("%,.0f", surfaceTemperature) + " K";
        }
    }

    // Client Program / Utility Class: Manages a collection of CelestialBodies.
    static class SolarSystem {
        private List<CelestialBody> bodies;
        private CelestialBody centralStar;

        // Constructor for SolarSystem.
        public SolarSystem(CelestialBody centralStar) {
            this.bodies = new ArrayList<>();
            this.centralStar = centralStar;
            if (centralStar != null) {
                this.bodies.add(centralStar);
            }
        }

        // Adds a celestial body to the system.
        public void addBody(CelestialBody body) {
            if (body != null && !bodies.contains(body)) {
                bodies.add(body);
                // System.out.println("Added " + body.getName() + " to the system."); // Removed for reduced output
            }
        }

        // Displays the current state of all bodies.
        public void displaySystemState() {
            System.out.println("\n--- Current System State ---");
            for (CelestialBody body : bodies) {
                System.out.println(body);
            }
            System.out.println("----------------------------");
        }

        // Runs the simulation for a given duration.
        public void runSimulation(double totalTime, double timeStep) {
            System.out.println("\n--- Starting Simulation for " + (totalTime / SECONDS_PER_DAY) + " days ---");
            for (double t = 0; t < totalTime; t += timeStep) {
                for (CelestialBody body : bodies) {
                    if (body != centralStar) {
                        body.revolve(timeStep);
                    }
                    // Unique method calls removed from loop to reduce output
                }
            }
            System.out.println("\n--- Simulation Ended ---");
        }
    }

    // Main method: Entry point of the program.
    public static void main(String[] args) {
        // Define masses.
        final double SUN_MASS = 1.989e30;
        final double EARTH_MASS = 5.972e24;
        final double MARS_MASS = 6.39e23;
        final double JUPITER_MASS = 1.898e27;
        final double MOON_MASS = 7.342e22;
        final double PHOBOS_MASS = 1.0659e16;
        final double HALLEY_MASS = 2.2e14;

        // Create celestial body instances.
        Star sun = new Star("Sun", SUN_MASS, 6.96340e8, 5778.0, "G2V");

        Planet earth = new Planet("Earth", EARTH_MASS, 6.371e6, 1.0 * AU_TO_METERS,
                                  365.25 * SECONDS_PER_DAY, 1, false, true);
        Planet mars = new Planet("Mars", MARS_MASS, 3.3895e6, 1.524 * AU_TO_METERS,
                                 687.0 * SECONDS_PER_DAY, 2, false, false);
        Planet jupiter = new Planet("Jupiter", JUPITER_MASS, 6.9911e7, 5.2 * AU_TO_METERS,
                                    4332.59 * SECONDS_PER_DAY, 95, true, false);

        Moon earthsMoon = new Moon("Moon", MOON_MASS, 1.7374e6, 3.844e8,
                                   27.32 * SECONDS_PER_DAY, "Earth", true);
        Moon phobos = new Moon("Phobos", PHOBOS_MASS, 1.126e4, 9.377e6,
                               7.65 * 3600.0, "Mars", true);

        Comet halleysComet = new Comet("Halley's Comet", HALLEY_MASS, 5.5e3, 0.967,
                                        76.0 * 365.25 * SECONDS_PER_DAY, 0.587 * AU_TO_METERS);
        Comet enckesComet = new Comet("Encke's Comet", 2.0e12, 2.4e3, 0.847,
                                      3.3 * 365.25 * SECONDS_PER_DAY, 0.336 * AU_TO_METERS);

        // Initialize solar system.
        SolarSystem ourSystem = new SolarSystem(sun);

        // Add bodies to the system.
        ourSystem.addBody(earth);
        ourSystem.addBody(mars);
        ourSystem.addBody(jupiter);
        ourSystem.addBody(earthsMoon);
        ourSystem.addBody(phobos);
        ourSystem.addBody(halleysComet);
        ourSystem.addBody(enckesComet);

        // Display initial state.
        System.out.println("\n--- Initial System Configuration ---");
        ourSystem.displaySystemState();

        // Run simulation.
        double simulationDuration = 2 * SECONDS_PER_DAY;
        double simulationTimeStep = SECONDS_PER_DAY / 24.0;
        ourSystem.runSimulation(simulationDuration, simulationTimeStep);

        // Display final state.
        System.out.println("\n--- System Configuration After Simulation ---");
        ourSystem.displaySystemState();

        // Direct method demonstrations.
        System.out.println("\n--- Key Demonstrations ---");
        sun.emitLightAndHeat(); // Demonstrate a Star method
        earth.describeMoons(); // Demonstrate a Planet method
        earthsMoon.exertTidalInfluence(); // Demonstrate a Moon method
        halleysComet.describeOrbitShape(); // Demonstrate a Comet method

        // Additional calculations.
        System.out.println("\n--- Additional Calculations ---");
        System.out.println(earth.getName() + " Density: " + String.format("%.2f", earth.getDensity()) + " kg/m^3");
        System.out.println(mars.getName() + " Surface Gravity: " + String.format("%.2f", mars.getSurfaceGravity()) + " m/s^2");

        System.out.println("\nProgram finished.");
    }
}
