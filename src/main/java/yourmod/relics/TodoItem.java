package yourmod.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import yourmod.CharacterFile;

import static yourmod.ModFile.makeID;
import static yourmod.util.Wiz.applyToSelf;

public class TodoItem extends AbstractEasyRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, CharacterFile.Enums.TODO_COLOR);
    }

    @Override
    public void atBattleStart() {
        flash();
        applyToSelf(new EquilibriumPower(AbstractDungeon.player, 1));
    }
}
