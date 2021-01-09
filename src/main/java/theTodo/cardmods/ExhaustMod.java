package theTodo.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static theTodo.TodoMod.makeID;

public class ExhaustMod extends AbstractCardModifier {
    public static String ID = makeID("ExhaustMod");

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL Exhaust.";
    }

    public boolean shouldApply(AbstractCard card) {
        return !card.exhaust;
    }

    public void onInitialApplication(AbstractCard card) {
        card.exhaust = true;
    }

    public AbstractCardModifier makeCopy() {
        return new ExhaustMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
