package theTodo.cards.cardvars;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theTodo.cards.AbstractTodoCard;

public class SillyVariable extends DynamicVariable {

    @Override
    public String key() {
        return "todo";
    } //TODO: Change this so your mod doesn't conflict!

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractTodoCard) card).isSillyModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractTodoCard) card).silly;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractTodoCard) {
            ((AbstractTodoCard) card).isSillyModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractTodoCard) card).baseSilly;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractTodoCard) card).upgradedSilly;
    }
}