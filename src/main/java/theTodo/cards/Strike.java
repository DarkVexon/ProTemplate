package theTodo.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theTodo.powers.AbstractLambdaPower;
import theTodo.powers.AbstractStartOfTurnPower;
import theTodo.powers.LosePowerPower;

import static theTodo.TodoMod.makeID;

public class Strike extends AbstractTodoCard {

    public final static String ID = makeID("Strike");

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    public Strike() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new AbstractLambdaPower("Daily Flex", AbstractPower.PowerType.BUFF, p, 2) {
            public void atStartOfTurn() {
                applyToSelf(new StrengthPower(owner, amount));
                applyToSelf(new LosePowerPower(owner, StrengthPower.POWER_ID, amount));
            }

            @Override
            public void updateDescription() {
                description = "At the start of your turn, gain #b" + amount + " temporary #yStrength.";
            }
        });
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}
