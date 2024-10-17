package yourmod.cards.democards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.cards.AbstractEasyCard;

import java.util.Arrays;
import java.util.List;

import static yourmod.ModFile.makeID;
import static yourmod.util.Wiz.atb;
import static yourmod.util.Wiz.att;

public class MechanoFist extends AbstractEasyCard {
    public final static String ID = makeID(MechanoFist.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 16, 6, , , , 

    public MechanoFist() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 16;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
        atb(new SelectCardsInHandAction(cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
            for (AbstractCard c : cards) {
                int highestAmt = -1;
                if (c.cost != -1 && c.cost != -2) {
                    highestAmt = c.cost;
                }
                for (DescriptionLine l : c.description) {
                    String result = l.text.replaceAll("[^0-9]+", " ");
                    List<String> numbers = Arrays.asList(result.trim().split(" "));
                    for (String s : numbers) {
                        int found = Integer.valueOf(s);
                        if (found > highestAmt) {
                            highestAmt = found;
                        }
                    }
                }
                att(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                att(new GainBlockAction(p, highestAmt));
            }
        }));
    }

    public void upp() {
        upgradeDamage(6);
    }
}