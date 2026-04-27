package quantitymeasurement;

public class QuantityMeasurementApp {

    private double value;

    public QuantityMeasurementApp(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        QuantityMeasurementApp other = (QuantityMeasurementApp) obj;

        return Double.compare(this.value, other.value) == 0;
    }

    // UC1 - Feet Equality Check
    public static boolean checkFeetEquality(double value1, double value2) {
        QuantityMeasurementApp feet1 = new QuantityMeasurementApp(value1);
        QuantityMeasurementApp feet2 = new QuantityMeasurementApp(value2);
        return feet1.equals(feet2);
    }

    // UC2 - Inches Equality Check
    public static boolean checkInchesEquality(double value1, double value2) {
        QuantityMeasurementApp inch1 = new QuantityMeasurementApp(value1);
        QuantityMeasurementApp inch2 = new QuantityMeasurementApp(value2);
        return inch1.equals(inch2);
    }

    public static void main(String[] args) {

        // UC1 Output
        System.out.println("Feet Equality Check:");
        System.out.println("Input: 1.0 ft and 1.0 ft");
        System.out.println("Output: Equal (" + checkFeetEquality(1.0, 1.0) + ")");

        // UC2 Output
        System.out.println("\nInches Equality Check:");
        System.out.println("Input: 1.0 inch and 1.0 inch");
        System.out.println("Output: Equal (" + checkInchesEquality(1.0, 1.0) + ")");
    }
}