public class Action {
    String action;
    Node parentState;

    Action(String action, Node parentState) {
        this.action = action;
        this.parentState = parentState;
    }

    Node applyAction() {
        Node newState = new Node(parentState.getX(), parentState.getY(), 0);
        switch (action) {
            case "UP":
                newState.setY(newState.getY() - 1);
                break;
            case "DOWN":
                newState.setY(newState.getY() + 1);
                break;
            case "RIGHT":
                newState.setX(newState.getX() + 1);
                break;
            case "LEFT":
                newState.setX(newState.getX() - 1);
                break;
        }
        return newState;
    }
}