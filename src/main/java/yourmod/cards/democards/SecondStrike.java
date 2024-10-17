package yourmod.cards.democards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.cards.AbstractEasyCard;

import static yourmod.ModFile.makeID;

public class SecondStrike extends AbstractEasyCard {
    public final static String ID = makeID(SecondStrike.class.getSimpleName());
    // intellij stuff attack, enemy, common, 6, 3, , , , 

    public SecondStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        if (cardPlayed.type == CardType.ATTACK && !freeToPlayOnce) {
            setCostForTurn(costForTurn - 1);
        }
    }

    public void upp() {
        upgradeDamage(3);
    }
}