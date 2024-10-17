package yourmod.cards.democards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.cards.AbstractEasyCard;

import static yourmod.ModFile.makeID;
import static yourmod.util.Wiz.atb;

public class Spit extends AbstractEasyCard {
    public final static String ID = makeID(Spit.class.getSimpleName());
    // intellij stuff attack, enemy, common, 5, 3, , , , 

    public Spit() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        atb(new DiscardAction(p, p, 1, false));
        atb(new DrawCardAction(1));
    }

    public void upp() {
        upgradeDamage(3);
    }
}