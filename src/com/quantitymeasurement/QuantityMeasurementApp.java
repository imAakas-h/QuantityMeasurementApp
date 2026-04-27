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

            double convertedValue = toFeet() / targetUnit.getFactor();
            return new QuantityLength(convertedValue, targetUnit);
        }

        public QuantityLength add(QuantityLength other) {
            if (other == null)
                throw new IllegalArgumentException("Second operand cannot be null");

            double totalFeet = this.toFeet() + other.toFeet();
            double result = totalFeet / this.unit.getFactor();

            return new QuantityLength(result, this.unit);
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
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(2.0, LengthUnit.FEET);
        System.out.println("Input: add(" + q1 + ", " + q2 + ")");
        System.out.println("Output: " + q1.add(q2));

        QuantityLength q3 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q4 = new QuantityLength(12.0, LengthUnit.INCHES);
        System.out.println("\nInput: add(" + q3 + ", " + q4 + ")");
        System.out.println("Output: " + q3.add(q4));

        QuantityLength q5 = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength q6 = new QuantityLength(1.0, LengthUnit.FEET);
        System.out.println("\nInput: add(" + q5 + ", " + q6 + ")");
        System.out.println("Output: " + q5.add(q6));

        QuantityLength q7 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q8 = new QuantityLength(3.0, LengthUnit.FEET);
        System.out.println("\nInput: add(" + q7 + ", " + q8 + ")");
        System.out.println("Output: " + q7.add(q8));

        QuantityLength q9 = new QuantityLength(36.0, LengthUnit.INCHES);
        QuantityLength q10 = new QuantityLength(1.0, LengthUnit.YARDS);
        System.out.println("\nInput: add(" + q9 + ", " + q10 + ")");
        System.out.println("Output: " + q9.add(q10));

        QuantityLength q11 = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength q12 = new QuantityLength(1.0, LengthUnit.INCHES);
        System.out.println("\nInput: add(" + q11 + ", " + q12 + ")");
        System.out.println("Output: " + q11.add(q12));

        QuantityLength q13 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q14 = new QuantityLength(0.0, LengthUnit.INCHES);
        System.out.println("\nInput: add(" + q13 + ", " + q14 + ")");
        System.out.println("Output: " + q13.add(q14));

        QuantityLength q15 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q16 = new QuantityLength(-2.0, LengthUnit.FEET);
        System.out.println("\nInput: add(" + q15 + ", " + q16 + ")");
        System.out.println("Output: " + q15.add(q16));
    }
}