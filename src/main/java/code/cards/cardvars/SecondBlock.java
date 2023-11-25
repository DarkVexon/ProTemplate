package code.cards.cardvars;

import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static code.ModFile.makeID;

public class SecondBlock extends AbstractEasyDynamicVariable {
    @Override
    public String key() {
        return makeID("sb");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractEasyCard) {
            return ((AbstractEasyCard) card).isSecondBlockModified;
        }
        return false;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractEasyCard) {
            ((AbstractEasyCard) card).isSecondBlockModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractEasyCard) {
            return ((AbstractEasyCard) card).secondBlock;
        }
        return -1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractEasyCard) {
            return ((AbstractEasyCard) card).baseSecondBlock;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractEasyCard) {
            return ((AbstractEasyCard) card).upgradedSecondBlock;
        }
        return false;
    }
}