package theTodo.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theTodo.TodoMod;

import static theTodo.util.Wiz.*;

public class ExampleTwoAmountPower extends AbstractEasyPower implements OnReceivePowerPower {
    // intellij stuff Example, buff, false
    private static final String SIMPLE_NAME = "ExampleTwoAmount";
    public static final String POWER_ID = TodoMod.makeID(SIMPLE_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String LOC_NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ExampleTwoAmountPower(AbstractCreature owner, int amount, int amount2) {
        super(SIMPLE_NAME, PowerType.BUFF, false, owner, amount);
        name = LOC_NAME;
        isTwoAmount = true;
        this.amount2 = amount2;
        canGoNegative2 = true;
        updateDescription();
    }

    // THIS IS IMPORTANT!
    // You need to have this for it to stack the second amount properly
    @Override
    public boolean onReceivePower(AbstractPower pow, AbstractCreature target, AbstractCreature source) {
        if (pow instanceof ExampleTwoAmountPower && target == owner)
            amount2 += ((ExampleTwoAmountPower)pow).amount2;
        return true;
    }

    @Override
    public void atStartOfTurn() {
        applyToSelf(new StrengthPower(adp(), amount));
        applyToSelf(new DexterityPower(adp(), amount2));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[2];
    }
}