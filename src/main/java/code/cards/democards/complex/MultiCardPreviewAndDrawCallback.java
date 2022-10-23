package code.cards.democards.complex;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Safety;
import com.megacrit.cardcrawl.cards.tempCards.Smite;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import code.cards.AbstractEasyCard;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;
import static code.util.Wiz.shuffleIn;

public class MultiCardPreviewAndDrawCallback extends AbstractEasyCard {
    public final static String ID = makeID(MultiCardPreviewAndDrawCallback.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , ,

    public MultiCardPreviewAndDrawCallback() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY); // This card is a 1 cost Uncommon Attack that targets ALL enemies.
        baseDamage = 10;
        MultiCardPreview.add(this, new Smite(), new Safety()); // Display both Smite and Safety when you hover this card.
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard q = new Smite(); // Create a Smite.
        if (upgraded) q.upgrade(); // If this card is upgraded, Upgrade it.
        shuffleIn(q); // Shuffle it into the draw pile.
        AbstractCard q2 = new Safety(); // Same for Safety.
        if (upgraded) q2.upgrade();
        shuffleIn(q2);
        atb(new DrawCardAction(1, new AbstractGameAction() { // Add an action that draws 1 card. As soon as that's done, add another action that...
            @Override
            public void update() {
                isDone = true; // is done immediately AND...
                if (DrawCardAction.drawnCards.stream().anyMatch(q -> q.color.equals(CardColor.COLORLESS))) { // checks if any cards drawn were colorless...
                    allDmgTop(AttackEffect.SLASH_VERTICAL); // and if so deals the damage to ALL enemies.
                }
            }
        }));
    }

    public void upp() {
        upgradeDamage(1); // here, we show upgraded smite and safeties.
        AbstractCard q = new Smite();
        q.upgrade();
        AbstractCard q2 = new Safety();
        q2.upgrade();
        MultiCardPreview.clear(this);
        MultiCardPreview.add(this, q, q2);
        uDesc();
    }
}