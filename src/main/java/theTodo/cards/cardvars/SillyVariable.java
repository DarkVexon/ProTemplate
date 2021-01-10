package theTodo.cards.cardvars;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theTodo.cards.AbstractTodoCard;

import static theTodo.TodoMod.makeID;

public class SillyVariable extends DynamicVariable {

    @Override
    public String key() {
        return makeID("si");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractTodoCard) {
            return ((AbstractTodoCard) card).isSillyModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractTodoCard) {
            return ((AbstractTodoCard) card).silly;
        }
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractTodoCard) {
            ((AbstractTodoCard) card).isSillyModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractTodoCard) {
            return ((AbstractTodoCard) card).baseSilly;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractTodoCard) {
            return ((AbstractTodoCard) card).upgradedSilly;
        }
        return false;
    }
}