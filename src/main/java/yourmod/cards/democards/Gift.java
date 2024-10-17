package yourmod.cards.democards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.cards.AbstractEasyCard;
import yourmod.util.Wiz;

import java.util.ArrayList;

import static yourmod.ModFile.makeID;

public class Gift extends AbstractEasyCard {
    public final static String ID = makeID(Gift.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , 6, 3

    public Gift() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (FavoriteAttack.favoriteAttack == null) {
            ArrayList<AbstractCard> options = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                options.add(AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK).makeCopy());
            }
            while (options.size() != 3) {
                int roll = AbstractDungeon.cardRandomRng.random(99);
                AbstractCard.CardRarity cardRarity;
                if (roll < 55) {
                    cardRarity = CardRarity.COMMON;
                } else if (roll < 85) {
                    cardRarity = CardRarity.UNCOMMON;
                } else {
                    cardRarity = CardRarity.RARE;
                }

                AbstractCard tmp = CardLibrary.getAnyColorCard(CardType.ATTACK, cardRarity);
                if (options.stream().noneMatch(q -> q.cardID.equals(tmp.cardID))) {
                    options.add(tmp);
                }
            }
            addToBot(new SelectCardsCenteredAction(options, 1, "Choose your Favorite Attack!", (cards) -> {
                FavoriteAttack.favoriteAttack = cards.get(0);
                Wiz.makeInHandTop(cards.get(0));
            }));
        }
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                addToTop(new ModifyDamageAction(FavoriteAttack.favoriteAttack.uuid, magicNumber));
            }
        });
    }

    public void upp() {
        upgradeMagicNumber(3);
    }
}