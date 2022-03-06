package theTodo.cards.democards.simple;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theTodo.cards.AbstractEasyCard;

import static theTodo.TodoMod.makeID;
import static theTodo.util.Wiz.*;

public class TwoTypesOfDamage extends AbstractEasyCard {
    public final static String ID = makeID("TwoTypesOfDamage");
    // intellij stuff skill, self, uncommon, , , , , ,

    public TwoTypesOfDamage() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY); // This card is a 1 cost Common Attack that targets an Enemy.
        baseDamage = 8;
        baseSecondDamage = secondDamage = 15;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(VulnerablePower.POWER_ID)) {
            altDmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        }
        else {
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        }
    }

    public void upp() {
        upgradeBaseCost(0); // Upgrade the base cost to 0. Other upgrade logic isn't necessary.
    }
}