package yourmod.cards.democards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.actions.ScryBlockStatusAction;
import yourmod.cards.AbstractEasyCard;

import static yourmod.ModFile.makeID;
import static yourmod.util.Wiz.*;

public class Flashlight extends AbstractEasyCard {
    public final static String ID = makeID(Flashlight.class.getSimpleName());
    // intellij stuff skill, self, common, , , 3, 2, 3, 1

    public Flashlight() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 3;
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new ScryBlockStatusAction(magicNumber));
    }

    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}