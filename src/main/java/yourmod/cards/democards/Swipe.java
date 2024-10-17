package yourmod.cards.democards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import yourmod.cards.AbstractEasyCard;

import static yourmod.ModFile.makeID;
import static yourmod.util.Wiz.*;

public class Swipe extends AbstractEasyCard {
    public final static String ID = makeID(Swipe.class.getSimpleName());
    // intellij stuff skill, enemy, starter, , , , , 1, 1

    public Swipe() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (getLogicalPowerAmount(m, StrengthPower.POWER_ID) > 0) {
            applyToEnemy(m, new StrengthPower(m, -magicNumber));
            applyToSelf(new StrengthPower(p, magicNumber));
            exhaust = true;
        } else {
            atb(new DrawCardAction(2));
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}