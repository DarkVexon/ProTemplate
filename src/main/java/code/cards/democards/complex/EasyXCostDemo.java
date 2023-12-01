package code.cards.democards.complex;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import code.actions.EasyXCostAction;
import code.cards.AbstractEasyCard;

import static code.util.Wiz.*;
import static code.ModFile.makeID;

public class EasyXCostDemo extends AbstractEasyCard {
    public final static String ID = makeID(EasyXCostDemo.class.getSimpleName());
    // intellij stuff attack, enemy, rare, , , , , 0, 1

    public EasyXCostDemo() {
        super(ID, -1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        setDamage(5);
        setMagic(0, +1);
        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            applyToSelfTop(new StrengthPower(p, effect + params[0]));
            for (int i = 0; i < effect + params[0]; i++)
                dmgTop(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
            return true;
        }, magicNumber));
    }
}