package quantitymeasurement;

import java.util.Objects;

// UC8: Refactoring Unit Enum to Standalone with Conversion Responsibility
public class QuantityMeasurementApp {

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength q3 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q4 = new QuantityLength(2.54, LengthUnit.CENTIMETERS);

        // Convert
        System.out.println("1 FEET to INCHES = " + q1.convertTo(LengthUnit.INCHES));

        // Equality
        System.out.println("12 INCHES == 1 FEET ? " + q2.equals(q1));
        System.out.println("36 INCHES == 1 YARD ? " +
                new QuantityLength(36, LengthUnit.INCHES).equals(q3));

        // Add with same unit
        System.out.println("1 FEET + 12 INCHES = " +
                q1.add(q2, LengthUnit.FEET));

        // Add with target unit
        System.out.println("1 FEET + 12 INCHES in YARDS = " +
                q1.add(q2, LengthUnit.YARDS));

        // CM conversion
        System.out.println("2.54 CM to INCHES = " +
                q4.convertTo(LengthUnit.INCHES));
    }
}

// Standalone Enum
enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double convertToBaseUnit(double value) {
        return value * factor;   // base = FEET
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / factor;
    }

    public double getFactor() {
        return factor;
    }
}

// Quantity Class
class QuantityLength {

    private final double value;
    private final LengthUnit unit;
    private static final double EPSILON = 0.0001;

    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null || Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid input");
        }
        this.value = value;
        this.unit = unit;
    }

    public QuantityLength convertTo(LengthUnit targetUnit) {
        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);
        return new QuantityLength(round(converted), targetUnit);
    }

    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        double base1 = unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sumBase = base1 + base2;
        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityLength(round(result), targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityLength)) return false;

        QuantityLength other = (QuantityLength) obj;

        double base1 = unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(base1 - base2) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(round(unit.convertToBaseUnit(value)));
    }

    @Override
    public String toString() {
        return "Quantity(" + round(value) + ", " + unit + ")";
    }

    private double round(double num) {
        return Math.round(num * 100.0) / 100.0;
    }
}