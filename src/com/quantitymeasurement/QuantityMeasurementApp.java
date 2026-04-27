package quantitymeasurement;

import java.util.Objects;

// UC9 : Weight Measurement Equality, Conversion and Addition
public class QuantityMeasurementApp {

    public static void main(String[] args) {

        // WEIGHT DEMO
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        QuantityWeight w3 = new QuantityWeight(2.20462, WeightUnit.POUND);

        System.out.println("1 KG == 1000 G ? " + w1.equals(w2));
        System.out.println("1 KG == 2.20462 LB ? " + w1.equals(w3));

        System.out.println("1 KG to GRAM = " + w1.convertTo(WeightUnit.GRAM));
        System.out.println("500 G to POUND = " +
                new QuantityWeight(500, WeightUnit.GRAM).convertTo(WeightUnit.POUND));

        System.out.println("1 KG + 1000 G = " + w1.add(w2));
        System.out.println("1 KG + 1000 G in GRAM = " +
                w1.add(w2, WeightUnit.GRAM));

        System.out.println("2 KG + 4 LB in KG = " +
                new QuantityWeight(2, WeightUnit.KILOGRAM)
                        .add(new QuantityWeight(4, WeightUnit.POUND),
                                WeightUnit.KILOGRAM));
    }
}

// ================= WEIGHT UNIT =================
enum WeightUnit {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double factor;

    WeightUnit(double factor) {
        this.factor = factor;
    }

    public double convertToBaseUnit(double value) {
        return value * factor; // base = KG
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / factor;
    }

    public double getFactor() {
        return factor;
    }
}

// ================= QUANTITY WEIGHT =================
class QuantityWeight {

    private final double value;
    private final WeightUnit unit;
    private static final double EPSILON = 0.0001;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null || Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid input");
        }
        this.value = value;
        this.unit = unit;
    }

    public QuantityWeight convertTo(WeightUnit targetUnit) {
        double base = unit.convertToBaseUnit(value);
        double result = targetUnit.convertFromBaseUnit(base);
        return new QuantityWeight(round(result), targetUnit);
    }

    // default result in first operand unit
    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    // explicit target unit
    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        double base1 = unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sum = base1 + base2;
        double result = targetUnit.convertFromBaseUnit(sum);

        return new QuantityWeight(round(result), targetUnit);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityWeight other = (QuantityWeight) obj;

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