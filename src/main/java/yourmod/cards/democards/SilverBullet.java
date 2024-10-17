package yourmod.cards.democards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.cards.AbstractEasyCard;

import static yourmod.ModFile.makeID;
import static yourmod.util.Wiz.*;

public class SilverBullet extends AbstractEasyCard {
    public final static String ID = makeID(SilverBullet.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 17, 2, , , 1, 1

    public SilverBullet() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 17;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        atb(new DrawCardAction(magicNumber));
    }

    @Override
    public void triggerWhenDrawn() {
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            superFlash();
            freeToPlayOnce = true;
        }
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}