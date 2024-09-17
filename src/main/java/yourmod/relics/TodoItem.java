package yourmod.relics;

import yourmod.CharacterFile;

import static yourmod.ModFile.makeID;

public class TodoItem extends AbstractEasyRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, CharacterFile.Enums.TODO_COLOR);
    }
}
