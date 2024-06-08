package code.cards.cardvars;

import code.cards.BaseCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static code.ModFile.makeID;

public class SecondMagicNumber extends BaseDynamicVariable {

    @Override
    public String key() {
        return makeID("m2");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof BaseCard) {
            return ((BaseCard) card).isSecondMagicModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof BaseCard) {
            return ((BaseCard) card).secondMagic;
        }
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof BaseCard) {
            ((BaseCard) card).isSecondMagicModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof BaseCard) {
            return ((BaseCard) card).baseSecondMagic;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof BaseCard) {
            return ((BaseCard) card).upgradedSecondMagic;
        }
        return false;
    }
}