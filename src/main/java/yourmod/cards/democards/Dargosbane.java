package yourmod.cards.democards;

import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.cards.AbstractEasyCard;
import yourmod.util.Wiz;

import java.util.ArrayList;

import static yourmod.ModFile.makeID;
import static yourmod.util.Wiz.atb;

public class Dargosbane extends AbstractEasyCard {
    public final static String ID = makeID(Dargosbane.class.getSimpleName());
    // intellij stuff attack, enemy, common, 7, 2, , , , 

    public Dargosbane() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 7;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        if (upgraded) {
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    ArrayList<AbstractCard> valids = new ArrayList<>();
                    for (AbstractCard c : AbstractDungeon.player.hand.group) {
                        if (!c.selfRetain) {
                            valids.add(c);
                        }
                    }
                    if (!valids.isEmpty()) {
                        AbstractCard toHit = Wiz.getRandomItem(valids);
                        CardModifierManager.addModifier(toHit, new RetainMod());
                        toHit.superFlash();
                    }
                }
            });
        } else {
            atb(new SelectCardsInHandAction(cardStrings.EXTENDED_DESCRIPTION[0], (c -> !c.selfRetain), (cards) -> {
                for (AbstractCard c : cards) {
                    CardModifierManager.addModifier(c, new RetainMod());
                    c.superFlash();
                }
            }));
        }
    }

    public void upp() {
        upgradeDamage(2);
    }
}