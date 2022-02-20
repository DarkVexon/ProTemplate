package theTodo.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theTodo.TodoMod;

public class ExampleTwoAmountPower extends AbstractEasyPower {
    // intellij stuff Example, buff, false
    private static final String SIMPLE_NAME = "ExampleTwoAmount";
    public static final String POWER_ID = TodoMod.makeID(SIMPLE_NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String LOC_NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ExampleTwoAmountPower(AbstractCreature owner, int amount) {
        super(SIMPLE_NAME, PowerType.BUFF, false, owner, amount);
        name = LOC_NAME;
        isTwoAmount = true;
        amount2 = 1;
        canGoNegative2 = true;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[2];
    }
}