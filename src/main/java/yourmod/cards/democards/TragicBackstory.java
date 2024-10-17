package yourmod.cards.democards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.cards.AbstractEasyCard;

import static yourmod.ModFile.makeID;

public class TragicBackstory extends AbstractEasyCard {
    public final static String ID = makeID(TragicBackstory.class.getSimpleName());
    // intellij stuff attack, enemy, common, 14, 4, , , , 

    public TragicBackstory() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 14;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.exhaustPile.size() < 2) {
            cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }
        return super.canUse(p, m);
    }

    public void upp() {
        upgradeDamage(4);
    }
}