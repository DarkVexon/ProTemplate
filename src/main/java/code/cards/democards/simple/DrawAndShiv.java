package code.cards.democards.simple;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import code.cards.AbstractEasyCard;

import static code.ModFile.makeID;
import static code.util.Wiz.*;

public class DrawAndShiv extends AbstractEasyCard {
    public final static String ID = makeID(DrawAndShiv.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , , 

    public DrawAndShiv() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF); // This card is a 1 cost Uncommon Skill that targets the Self.
        cardsToPreview = new Shiv(); // Preview a Shiv when hovering over this card.
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(1)); // Add to the bottom of the action queue an action which draws 1 card.
        makeInHand(new Shiv()); // Add to the bottom of the action queue an action which adds a Shiv into your hand. (This is shorthanded by makeInHand).
    }

    public void upp() {
        upgradeBaseCost(0); // Upgrade the base cost to 0. Other upgrade logic isn't necessary.
    }
}