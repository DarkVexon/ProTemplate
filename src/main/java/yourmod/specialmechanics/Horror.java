package yourmod.specialmechanics;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static yourmod.util.Wiz.*;

public class Horror {
    public static int horrorAmt;

    public static void addHorror(int amount) {
        horrorAmt += amount;
    }

    public static void startOfCombat() {
        if (horrorAmt >= 2) {
            atb(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 2));
        }
        if (horrorAmt >= 3) {
            forAllMonstersLiving(q -> {
                applyToEnemy(q, new IntangiblePower(q, 1));
                applyToEnemy(q, new StrengthPower(q, 1));
            });
        }
    }

    public static void startOfTurn() {
        if (horrorAmt >= 4) {
            atb(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, 1, true));
        }
    }

    public static void onCardPlayed(AbstractCard card) {
        if (horrorAmt >= 5) {
            atb(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, BaseMod.MAX_HAND_SIZE, true));
        }
    }

    public static void removeHorror(int amount) {
        horrorAmt -= amount;
    }
}
