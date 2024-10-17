package yourmod.cards.democards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.cards.AbstractEasyCard;

import static yourmod.ModFile.makeID;

public class PityParty extends AbstractEasyCard {
    public final static String ID = makeID(PityParty.class.getSimpleName());
    // intellij stuff skill, self, common, , , 7, 3, , 

    public PityParty() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 7;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void tookDamage() {
        if (AbstractDungeon.player.hand.contains(this) && !freeToPlayOnce) {
            freeToPlayOnce = true;
            superFlash();
        }
    }

    public void upp() {
        upgradeBlock(3);
    }
}