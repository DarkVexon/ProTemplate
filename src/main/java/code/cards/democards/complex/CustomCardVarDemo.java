package code.cards.democards.complex;

import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Byrd;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static code.ModFile.makeID;
import static code.cards.AbstractEasyCard.CalculationType.*;
import static code.util.Wiz.applyToEnemy;
import static code.util.Wiz.atb;

public class CustomCardVarDemo extends AbstractEasyCard {
    public final static String ID = makeID(CustomCardVarDemo.class.getSimpleName());
    private final static String damageVarKey = "thirdDamage";
    private final static String blockVarKey = "secondBlock";
    private final static String poisonVarKey = "poisonHatesByrds";

    public CustomCardVarDemo() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setCustomVar(damageVarKey, 5, DAMAGE);
        setCustomVar(blockVarKey, 5, BLOCK);
        setCustomVar(poisonVarKey, 2);
        setCustomVarCalculation(poisonVarKey, (m, value) -> {
            if (m instanceof Byrd) return value * 2;
            return value;
        });
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DamageAction(m, new DamageInfo(p, customVar(damageVarKey))));
        atb(new GainBlockAction(p, customVar(blockVarKey)));
        applyToEnemy(m, new PoisonPower(m, p, customVar(poisonVarKey)));
    }

    @Override
    public void upp() {
        upgradeCustomVar(damageVarKey, 1);
        upgradeCustomVar(blockVarKey, 1);
        upgradeCustomVar(poisonVarKey, 1);
    }
}
