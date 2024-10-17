package yourmod.cards.democards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import yourmod.cards.AbstractEasyCard;

import static yourmod.ModFile.makeID;
import static yourmod.util.Wiz.*;

public class Hone extends AbstractEasyCard {
    public final static String ID = makeID(Hone.class.getSimpleName());
    // intellij stuff skill, enemy, common, , , , , 1, 1

    public Hone() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        CardGroup possCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard q : p.drawPile.group) {
            possCards.addToRandomSpot(q);
        }
        atb(new SelectCardsAction(possCards.group, 2, cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> cards.forEach(q -> att(new ExhaustSpecificCardAction(q, p.drawPile, false)))));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}