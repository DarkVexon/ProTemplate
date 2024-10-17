package yourmod.cards.democards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.defect.SeekAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.cards.AbstractEasyCard;

import static yourmod.ModFile.makeID;
import static yourmod.util.Wiz.*;

public class Rope extends AbstractEasyCard implements StartupCard {
    public final static String ID = makeID(Rope.class.getSimpleName());
    // intellij stuff skill, none, starter, , , , , , 

    public Rope() {
        super(ID, 2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SeekAction(1));
    }

    public void upp() {
        upgradeBaseCost(1);
    }

    @Override
    public boolean atBattleStartPreDraw() {
        freeToPlayOnce = true;
        return false;
    }
}