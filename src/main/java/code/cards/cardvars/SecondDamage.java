package code.cards.cardvars;

import code.cards.BaseCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static code.ModFile.makeID;

public class SecondDamage extends BaseDynamicVariable {

    @Override
    public String key() {
        return makeID("sd");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof BaseCard) {
            return ((BaseCard) card).isSecondDamageModified;
        }
        return false;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof BaseCard) {
            ((BaseCard) card).isSecondDamageModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof BaseCard) {
            return ((BaseCard) card).secondDamage;
        }
        return -1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof BaseCard) {
            return ((BaseCard) card).baseSecondDamage;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof BaseCard) {
            return ((BaseCard) card).upgradedSecondDamage;
        }
        return false;
    }
}