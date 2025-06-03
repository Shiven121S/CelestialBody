public class CelestialSimulation {

    static class CelestialBody {
        private String name;
        private String category;
        private double radius;
        private double theta;

        public CelestialBody(String n, double r) {
            name = n;
            category = "unspecified";
            radius = r;
            theta = 0.0;
        }

        public String getName() { return name; }
        public String getCategory() { return category; }
        public double getRadius() { return radius; }
        public double getTheta() { return theta; }

        public void setName(String x) { name = x; }
        public void setCategory(String x) { category = x; }
        public void setRadius(double x) { radius = x; }
        public void setTheta(double x) { theta = x; }

        public String getCoordinates() {
            double piMultiplier = theta / Math.PI;
            String rFormatted = String.format("%.2E", radius);
            String thetaFormatted = String.format("%.3f", piMultiplier);
            return "(" + rFormatted + " m, " + thetaFormatted + "π)";
        }

        public String toString() {
            return name + " (" + category + ")";
        }
    }

    static class Planet extends CelestialBody {
        private int numberOfMoons;
        private double orbitCenterMass;
        private static final double G = 6.67430e-11;

        public Planet(String name, double radius, int moons, double orbitCenterMass) {
            super(name, radius);
            setCategory("planet");
            numberOfMoons = moons;
            this.orbitCenterMass = orbitCenterMass;
        }

        public void revolve(double timeSeconds) {
            double r = getRadius();
            double v = Math.sqrt(G * orbitCenterMass / r);
            double omega = v / r;
            double newTheta = (getTheta() + omega * timeSeconds) % (2 * Math.PI);
            setTheta(newTheta);
            System.out.printf("%s revolved for %.0f seconds. New θ = %.3fπ%n", getName(), timeSeconds, newTheta / Math.PI);
        }

        @Override
        public String toString() {
            return super.toString() + " with " + numberOfMoons + " moon(s) at " + getCoordinates();
        }
    }

    static class Moon extends CelestialBody {
        private String planetOrbiting;
        private double orbitCenterMass;
        private static final double G = 6.67430e-11;

        public Moon(String name, double radius, String planet, double orbitCenterMass) {
            super(name, radius);
            setCategory("moon");
            planetOrbiting = planet;
            this.orbitCenterMass = orbitCenterMass;
        }

        public void revolve(double timeSeconds) {
            double r = getRadius();
            double v = Math.sqrt(G * orbitCenterMass / r);
            double omega = v / r;
            double newTheta = (getTheta() + omega * timeSeconds) % (2 * Math.PI);
            setTheta(newTheta);
            System.out.printf("%s revolved for %.0f seconds. New θ = %.3fπ%n", getName(), timeSeconds, newTheta / Math.PI);
        }

        public void tideEffect() {
            System.out.println(getName() + " is affecting tides on " + planetOrbiting + ".");
        }

        @Override
        public String toString() {
            return super.toString() + " orbiting " + planetOrbiting + " at " + getCoordinates();
        }
    }

    public static void main(String[] args) {
        double massOfSun = 1.989e30;    // kg
        double massOfEarth = 5.972e24;  // kg

        Planet earth = new Planet("Earth", 1.496e11, 1, massOfSun); // 1 AU
        Moon moon = new Moon("Moon", 3.844e8, "Earth", massOfEarth); // Moon's orbit

        System.out.println(earth);
        System.out.println(moon);

        double time = 86400; // 1 day in seconds

        earth.revolve(time);
        moon.revolve(time);

        System.out.println(earth);
        System.out.println(moon);

        moon.tideEffect();
    }
}
