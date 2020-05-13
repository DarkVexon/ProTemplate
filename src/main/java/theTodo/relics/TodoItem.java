package theTodo.relics;

public class TodoItem extends AbstractTodoRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT);
    }
}
