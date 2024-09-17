package yourmod.cards.democards.simple;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import yourmod.cards.AbstractEasyCard;

import static yourmod.ModFile.makeID;

public class TwoTypesOfDamage extends AbstractEasyCard {
    public final static String ID = makeID(TwoTypesOfDamage.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , ,

    public TwoTypesOfDamage() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY); // This card is a 1 cost Common Attack that targets an Enemy.
        baseDamage = 8;
        baseSecondDamage = 15;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(VulnerablePower.POWER_ID)) // If you have VulnerablePower (vulnerable),
            altDmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY); // Deal damage based on secondDamage.
        else
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT); // Otherwise deal normal damage.
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeSecondDamage(5);
    }
}