package yourmod.cards.democards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.cards.AbstractEasyCard;
import yourmod.specialmechanics.Gamer;

import static yourmod.ModFile.makeID;

public class BitBlast extends AbstractEasyCard {
    public final static String ID = makeID(BitBlast.class.getSimpleName());
    // intellij stuff attack, enemy, common, 9, 3, 9, 3, , 

    public BitBlast() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 9;
        baseBlock = 9;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        if (Gamer.isGamer) {
            blck();
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = Gamer.isGamer ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(3);
        upgradeBlock(3);
    }
}