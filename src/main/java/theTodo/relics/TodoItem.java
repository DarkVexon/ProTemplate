package theTodo.relics;

import theTodo.TheTodo;

public class TodoItem extends AbstractTodoRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheTodo.Enums.TODO_COLOR);
    }
}
