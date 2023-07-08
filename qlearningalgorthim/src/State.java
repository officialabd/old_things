import java.util.ArrayList;

public abstract class State<S> {
    private S state;
    private ArrayList<Action> actions;

    State(S state, int ACTIONS_NO) {
        this.actions = new ArrayList<>();
        this.state = state;
        initActions();
    }

    protected abstract void initActions();

    public abstract State applyAction(Action action);

    public S getState() {
        return state;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }
}
