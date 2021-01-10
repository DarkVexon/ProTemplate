package theTodo.cards.cardvars;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theTodo.cards.AbstractTodoCard;

import static theTodo.TodoMod.makeID;

public class SecondDamage extends DynamicVariable {

    @Override
    public String key() {
        return makeID("sd");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractTodoCard) {
            return ((AbstractTodoCard) card).isSecondDamageModified;
        }
        return false;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractTodoCard) {
            ((AbstractTodoCard) card).isSecondDamageModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractTodoCard) {
            return ((AbstractTodoCard) card).secondDamage;
        }
        return -1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractTodoCard) {
            return ((AbstractTodoCard) card).baseSecondDamage;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractTodoCard) {
            return ((AbstractTodoCard) card).upgradedSecondDamage;
        }
        return false;
    }
}