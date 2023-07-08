public abstract class Action<A> {
    private A action;
    private double qValue;

    Action(A action, double qValue) {
        this.action = action;
        this.qValue = qValue;
    }

    public A getAction() {
        return action;
    }

    public double getQValue() {
        return qValue;
    }

    public void setQValue(double qValue) {
        this.qValue = qValue;
    }
}
