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
            if (!Double.isFinite(value))
                throw new IllegalArgumentException("Invalid value");

            if (unit == null)
                throw new IllegalArgumentException("Unit cannot be null");

            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return value * unit.getFactor();
        }

        public QuantityLength convertTo(LengthUnit targetUnit) {
            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double converted = toFeet() / targetUnit.getFactor();
            return new QuantityLength(converted, targetUnit);
        }

        // UC6 Method
        public QuantityLength add(QuantityLength other) {
            return add(other, this.unit);
        }

        // UC7 Method
        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {

            if (other == null)
                throw new IllegalArgumentException("Second operand cannot be null");

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double totalFeet = this.toFeet() + other.toFeet();
            double result = totalFeet / targetUnit.getFactor();

            return new QuantityLength(result, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            QuantityLength other = (QuantityLength) obj;

            return Math.abs(this.toFeet() - other.toFeet()) < 0.0001;
        }

        @Override
        public String toString() {
            return "Quantity(" + Math.round(value * 1000.0) / 1000.0 + ", " + unit + ")";
        }
    }

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println("Input: add(" + q1 + ", " + q2 + ", FEET)");
        System.out.println("Output: " + q1.add(q2, LengthUnit.FEET));

        System.out.println("\nInput: add(" + q1 + ", " + q2 + ", INCHES)");
        System.out.println("Output: " + q1.add(q2, LengthUnit.INCHES));

        System.out.println("\nInput: add(" + q1 + ", " + q2 + ", YARDS)");
        System.out.println("Output: " + q1.add(q2, LengthUnit.YARDS));

        QuantityLength q3 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q4 = new QuantityLength(3.0, LengthUnit.FEET);

        System.out.println("\nInput: add(" + q3 + ", " + q4 + ", YARDS)");
        System.out.println("Output: " + q3.add(q4, LengthUnit.YARDS));

        QuantityLength q5 = new QuantityLength(36.0, LengthUnit.INCHES);
        QuantityLength q6 = new QuantityLength(1.0, LengthUnit.YARDS);

        System.out.println("\nInput: add(" + q5 + ", " + q6 + ", FEET)");
        System.out.println("Output: " + q5.add(q6, LengthUnit.FEET));

        QuantityLength q7 = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength q8 = new QuantityLength(1.0, LengthUnit.INCHES);

        System.out.println("\nInput: add(" + q7 + ", " + q8 + ", CENTIMETERS)");
        System.out.println("Output: " + q7.add(q8, LengthUnit.CENTIMETERS));

        QuantityLength q9 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q10 = new QuantityLength(0.0, LengthUnit.INCHES);

        System.out.println("\nInput: add(" + q9 + ", " + q10 + ", YARDS)");
        System.out.println("Output: " + q9.add(q10, LengthUnit.YARDS));

        QuantityLength q11 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q12 = new QuantityLength(-2.0, LengthUnit.FEET);

        System.out.println("\nInput: add(" + q11 + ", " + q12 + ", INCHES)");
        System.out.println("Output: " + q11.add(q12, LengthUnit.INCHES));
    }
}