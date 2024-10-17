package yourmod.cards.democards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.cards.AbstractEasyCard;
import yourmod.powers.MeaniePointsPower;

import static yourmod.ModFile.makeID;
import static yourmod.util.Wiz.applyToSelf;

public class BigMeanie extends AbstractEasyCard {
    public final static String ID = makeID(BigMeanie.class.getSimpleName());
    // intellij stuff skill, self, starter, , , 3, 1, 3, 1

    public BigMeanie() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        baseBlock = 3;
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new MeaniePointsPower(magicNumber));
    }

    public void upp() {
        upgradeBlock(1);
        upgradeMagicNumber(1);
    }
}