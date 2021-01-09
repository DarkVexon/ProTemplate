package theTodo.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static theTodo.TodoMod.makeID;

public class RetainMod extends AbstractCardModifier {
    public static String ID = makeID("RetainMod");

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return "Retain. NL " + rawDescription;
    }

    public boolean shouldApply(AbstractCard card) {
        return !card.selfRetain;
    }

    public void onInitialApplication(AbstractCard card) {
        card.selfRetain = true;
    }

    public AbstractCardModifier makeCopy() {
        return new RetainMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
