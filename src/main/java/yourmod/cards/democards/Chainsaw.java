package yourmod.cards.democards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import yourmod.cards.AbstractEasyCard;
import yourmod.util.Wiz;

import static yourmod.ModFile.makeID;

public class Chainsaw extends AbstractEasyCard {
    public final static String ID = makeID(Chainsaw.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 18, 6, , , , 

    public Chainsaw() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 18;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m))
            return false;
        if (m != null) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return m.hasPower(VulnerablePower.POWER_ID);
        }
        for (AbstractMonster mm : Wiz.getEnemies()) {
            if (canUse(p, mm))
                return true;
        }
        return false;
    }

    public void upp() {
        upgradeDamage(6);
    }
}