package quantitymeasurement;

public class QuantityMeasurementApp {

    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double getFactor() {
            return factor;
        }
    }

    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        private double toBaseFeet() {
            return value * unit.getFactor();
        }

        public QuantityLength convertTo(LengthUnit targetUnit) {
            double baseFeet = toBaseFeet();
            double convertedValue = baseFeet / targetUnit.getFactor();
            return new QuantityLength(convertedValue, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            QuantityLength other = (QuantityLength) obj;

            return Math.abs(this.toBaseFeet() - other.toBaseFeet()) < 0.0001;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid numeric value");

        if (source == null || target == null)
            throw new IllegalArgumentException("Unit cannot be null");

        double baseFeet = value * source.getFactor();
        return baseFeet / target.getFactor();
    }

    // Overloaded Method 1
    public static void demonstrateLengthConversion(double value,
                                                   LengthUnit from,
                                                   LengthUnit to) {

        double result = convert(value, from, to);

        System.out.println("Input: convert(" + value + ", " + from + ", " + to + ")");
        System.out.println("Output: " + result);
    }

    // Overloaded Method 2
    public static void demonstrateLengthConversion(QuantityLength quantity,
                                                   LengthUnit to) {

        QuantityLength result = quantity.convertTo(to);

        System.out.println("Input: " + quantity);
        System.out.println("Converted To: " + result);
    }

    public static void main(String[] args) {

        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES);
        demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCHES, LengthUnit.YARDS);
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES);
        demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCHES);

        QuantityLength q = new QuantityLength(2.0, LengthUnit.YARDS);
        demonstrateLengthConversion(q, LengthUnit.INCHES);
    }
}