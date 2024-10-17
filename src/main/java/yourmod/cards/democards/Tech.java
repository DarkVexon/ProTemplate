package yourmod.cards.democards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.cards.AbstractEasyCard;

import static yourmod.ModFile.makeID;
import static yourmod.util.Wiz.atb;

public class Tech extends AbstractEasyCard {
    public final static String ID = makeID(Tech.class.getSimpleName());
    // intellij stuff attack, enemy, common, 4, 2, , , , 

    public Tech() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.costForTurn < c.cost || c.freeToPlay()) {
            atb(new DiscardToHandAction(this));
        }
    }

    public void upp() {
        upgradeDamage(2);
    }
}