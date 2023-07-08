package gamelogic;

import java.util.ArrayList;

public class Snake {
    private ArrayList<BodyPart> body;
    private BodyPart head;
    private double speed;


    public Snake(BodyPart head) {
        body = new ArrayList<>();
        this.head = head;
        body.add(head);
        speed = (Math.exp((-1 / 100) * body.size() + 5) + 1);
    }

    public void doAction(Item item) {
        item.onAction();
    }

    public void update() {
        for (int i = body.size() - 1; i > 0; i--) {
            body.set(i, body.get(i - 1));
        }
        body.set(0, combinePoints(body.get(0)));
    }

    public BodyPart combinePoints(BodyPart previousPoint) {
        head = new BodyPart(
                new Vector2I(previousPoint.getPos().getX() + body.get(0).getDir().pos.getX(),
                        previousPoint.getPos().getY() + body.get(0).getDir().pos.getY()),
                previousPoint.getDir());

        return head;
    }

    public boolean collided() {
        for (int i = 1; i < body.size(); i++) {
            Vector2I part = body.get(i).getPos();
            if (part.getX() == head.getPos().getX() && part.getY() == head.getPos().getY())
                return true;
        }
        return false;
    }

    public void reset() {
        body.clear();
        Vector2I head = new Vector2I(0, 0);
        body.add(new BodyPart(head, Directions.DOWN));
        speed = (Math.exp((-1.0 / 100.0) * body.size() + 5.0) + 1.0);
    }


    public void setDirection(Directions direction) {
        if (direction != body.get(0).getDir().getOpposite())
            body.get(0).setDir(direction);
    }


    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setSpeed() {
        speed = (Math.exp((-1.0 / 100.0) * body.size() + 5.0) + 1.0);
    }

    public ArrayList<BodyPart> getBody() {
        return body;
    }

    public double getSpeed() {
        return speed;
    }

    public int getX() {
        return body.get(0).getPos().getX();
    }

    public int getY() {
        return body.get(0).getPos().getY();
    }

}
