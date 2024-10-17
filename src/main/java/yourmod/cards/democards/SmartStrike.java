package yourmod.cards.democards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.cards.AbstractEasyCard;

import static yourmod.ModFile.makeID;
import static yourmod.util.Wiz.atb;

public class SmartStrike extends AbstractEasyCard {
    public final static String ID = makeID(SmartStrike.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , , 

    public SmartStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (p.hand.size() >= 5) {
                    dmgTop(m, AttackEffect.SLASH_DIAGONAL);
                }
            }
        });
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = AbstractDungeon.player.hand.size() >= 6 ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(3);
    }
}