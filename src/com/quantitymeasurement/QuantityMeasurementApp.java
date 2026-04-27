package quantitymeasurement;

public class QuantityMeasurementApp {

    // UC4 Extended Units Enum
    enum LengthUnit {
        FEET(1.0),                 // base unit
        INCH(1.0 / 12.0),         // 12 inches = 1 foot
        YARDS(3.0),              // 1 yard = 3 feet
        CENTIMETERS(0.0328084);  // 1 cm = 0.0328084 feet

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double getFactor() {
            return factor;
        }
    }

    // Same class used for all UC
    static class QuantityLength {
        private double value;
        private LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return value * unit.getFactor();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(3.0, LengthUnit.FEET);

        System.out.println("Input: " + q1 + " and " + q2);
        System.out.println("Output: Equal (" + q1.equals(q2) + ")");

        QuantityLength q3 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q4 = new QuantityLength(36.0, LengthUnit.INCH);

        System.out.println("Input: " + q3 + " and " + q4);
        System.out.println("Output: Equal (" + q3.equals(q4) + ")");

        QuantityLength q5 = new QuantityLength(2.0, LengthUnit.YARDS);
        QuantityLength q6 = new QuantityLength(2.0, LengthUnit.YARDS);

        System.out.println("Input: " + q5 + " and " + q6);
        System.out.println("Output: Equal (" + q5.equals(q6) + ")");

        QuantityLength q7 = new QuantityLength(2.0, LengthUnit.CENTIMETERS);
        QuantityLength q8 = new QuantityLength(2.0, LengthUnit.CENTIMETERS);

        System.out.println("Input: " + q7 + " and " + q8);
        System.out.println("Output: Equal (" + q7.equals(q8) + ")");

        QuantityLength q9 = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        QuantityLength q10 = new QuantityLength(0.393701, LengthUnit.INCH);

        System.out.println("Input: " + q9 + " and " + q10);
        System.out.println("Output: Equal (" + q9.equals(q10) + ")");
    }
}