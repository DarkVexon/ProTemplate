package theTodo.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTodo.powers.ExampleTwoAmountPower;

import static theTodo.TodoMod.makeID;
import static theTodo.util.Wiz.applyToSelf;

public class ExampleTwoAmountPowerCard extends AbstractEasyCard {
    public final static String ID = makeID("ExampleTwoAmountPowerCard");
    private final static int MAGIC = 1;
    private final static int UPGRADE_MAGIC = 1;
    private final static int COST = 1;

    // power, uncommon, self
    public ExampleTwoAmountPowerCard() {
        super(ID, COST, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // Generally you wouldn't use amount2 this way, I am just setting this up for example of handling strings
        ExampleTwoAmountPower pow = new ExampleTwoAmountPower(p, magicNumber);
        if (!upgraded) {
            pow.amount2 = 3;
            if (p.hasPower(ExampleTwoAmountPower.POWER_ID))
                ((ExampleTwoAmountPower)p.getPower(ExampleTwoAmountPower.POWER_ID)).amount2 = 3;
        }
        else {
            pow.amount2 = -4;
            if (p.hasPower(ExampleTwoAmountPower.POWER_ID))
                ((ExampleTwoAmountPower) p.getPower(ExampleTwoAmountPower.POWER_ID)).amount2 = -4;
        }
        applyToSelf(pow);
    }

    public void upp() {
        upgradeMagicNumber(UPGRADE_MAGIC);
    }
}