package yourmod.cards.democards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.cards.AbstractEasyCard;
import yourmod.util.Wiz;

import java.util.ArrayList;

import static yourmod.ModFile.makeID;
import static yourmod.util.Wiz.atb;

public class Cheapen extends AbstractEasyCard {
    public final static String ID = makeID(Cheapen.class.getSimpleName());
    // intellij stuff skill, self, common, , , 4, 3, , 

    public Cheapen() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                ArrayList<AbstractCard> valids = new ArrayList<>();
                for (AbstractCard c : p.hand.group) {
                    if (c.cost > 0 && c.costForTurn > 0 && !c.freeToPlayOnce) {
                        valids.add(c);
                    }
                }
                if (!valids.isEmpty()) {
                    AbstractCard toHit = Wiz.getRandomItem(valids);
                    toHit.freeToPlayOnce = true;
                    toHit.superFlash();
                }
            }
        });
    }

    public void upp() {
        upgradeBlock(3);
    }
}