package yourmod.cards.democards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.cards.AbstractEasyCard;
import yourmod.specialmechanics.treasures.AbstractTreasureCard;

import static yourmod.ModFile.makeID;
import static yourmod.util.Wiz.makeInHand;

public class Dig extends AbstractEasyCard {
    public final static String ID = makeID(Dig.class.getSimpleName());
    // intellij stuff skill, self, common, , , 12, 4, , 

    public Dig() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 12;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        for (int i = 0; i < 2; i++) {
            makeInHand(AbstractTreasureCard.returnRandomTreasure());
        }
    }

    public void upp() {
        upgradeBlock(4);
    }
}