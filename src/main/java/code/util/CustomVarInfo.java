package code.util;

import basemod.abstracts.DynamicVariable;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.function.BiFunction;

import static code.ModFile.makeID;

public class CustomVarInfo {
    public int base;
    public int value;
    private boolean upgraded;
    private boolean forceModified;
    public AbstractEasyCard.CalculationType type;

    public BiFunction<AbstractMonster, Integer, Integer> calculation = CustomVarInfo::noCalc;

    public CustomVarInfo(int base, AbstractEasyCard.CalculationType type) {
        this.value = this.base = base;
        this.upgraded = false;
        this.type = type;
    }

    private static int noCalc(AbstractMonster m, int base) {
        return base;
    }

    public boolean isModified() {
        return forceModified || base != value;
    }

    public boolean isUpgraded() {
        return upgraded;
    }

    public void upgrade(int upgradeAmt) {
        this.value = this.base += upgradeAmt;
        this.upgraded = true;
    }

    public static class QuickDynamicVariable extends DynamicVariable {
        final String localKey, key;

        public QuickDynamicVariable(String key) {
            this.localKey = key;
            this.key = makeID(key);
        }

        @Override
        public String key() {
            return key;
        }

        @Override
        public void setIsModified(AbstractCard c, boolean v) {
            if (c instanceof AbstractEasyCard) {
                CustomVarInfo var = ((AbstractEasyCard) c).getCustomVar(localKey);
                if (var != null)
                    var.forceModified = v;
            }
        }

        @Override
        public boolean isModified(AbstractCard c) {
            return c instanceof AbstractEasyCard && ((AbstractEasyCard) c).isCustomVarModified(localKey);
        }

        @Override
        public int value(AbstractCard c) {
            return c instanceof AbstractEasyCard ? ((AbstractEasyCard) c).customVar(localKey) : 0;
        }

        @Override
        public int baseValue(AbstractCard c) {
            return c instanceof AbstractEasyCard ? ((AbstractEasyCard) c).customVarBase(localKey) : 0;
        }

        @Override
        public boolean upgraded(AbstractCard c) {
            return c instanceof AbstractEasyCard && ((AbstractEasyCard) c).customVarUpgraded(localKey);
        }
    }
}