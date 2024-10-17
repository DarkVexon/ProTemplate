package yourmod.cards.democards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.cards.AbstractEasyCard;
import yourmod.cards.Strike;

import static yourmod.ModFile.makeID;
import static yourmod.util.Wiz.makeInHand;

public class DieOff extends AbstractEasyCard {
    public final static String ID = makeID(DieOff.class.getSimpleName());
    // intellij stuff attack, enemy, common, 12, 4, , , , 

    public DieOff() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 12;
        cardsToPreview = new Strike();
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        makeInHand(cardsToPreview.makeStatEquivalentCopy());
    }

    public void upp() {
        upgradeDamage(3);
        cardsToPreview.upgrade();
    }
}