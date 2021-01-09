package theTodo.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static theTodo.TodoMod.makeID;

public class EtherealMod extends AbstractCardModifier {
    public static String ID = makeID("EtherealMod");

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return "Ethereal. NL " + rawDescription;
    }

    public boolean shouldApply(AbstractCard card) {
        return !card.isEthereal;
    }

    public void onInitialApplication(AbstractCard card) {
        card.isEthereal = true;
    }

    public AbstractCardModifier makeCopy() {
        return new EtherealMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
