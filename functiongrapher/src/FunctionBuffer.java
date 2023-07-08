import javafx.scene.paint.Color;
import test.Expression;
import test.ExpressionBuilder;

public class FunctionBuffer {

    private String functionText;
    private double entailXValue;
    private Color color;

    FunctionBuffer() {
        this("", Color.BROWN);
    }

    FunctionBuffer(String functionText, Color color) {
        this(functionText, 0, color);
    }

    FunctionBuffer(String functionText, double entailXValue, Color color) {
        setFunctionText(functionText);
        setEntailXValue(entailXValue);
        setColor(color);
    }

    public Expression getFormattedFunction(double xValue) {
        return getFormattedFunction(functionText, xValue);
    }


    public static Expression getFormattedFunction(String functionAsString, double xValue) {
        Expression e = new ExpressionBuilder(functionAsString)
                .variables("x")
                .build()
                .setVariable("x", xValue);
        return e;
    }

    public void setFunctionText(String functionText) {
        this.functionText = functionText;
    }

    public void setEntailXValue(double entailXValue) {
        this.entailXValue = entailXValue;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void getColor(Color color) {
        this.color = color;
    }

    public String getFunctionText() {
        return functionText;
    }

    public double getEntailXValue() {
        return entailXValue;
    }


    public Color getColor() {
        return color;
    }
}
