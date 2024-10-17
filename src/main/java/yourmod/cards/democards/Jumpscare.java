package yourmod.cards.democards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.cards.AbstractEasyCard;
import yourmod.specialmechanics.delay.Delay;
import yourmod.specialmechanics.delay.DelayManager;

import static yourmod.ModFile.makeID;
import static yourmod.util.Wiz.*;

public class Jumpscare extends AbstractEasyCard {
    public final static String ID = makeID(Jumpscare.class.getSimpleName());
    // intellij stuff attack, all_enemy, common, 6, 2, , , , 

    public Jumpscare() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 7;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                DelayManager.delays.add(new Delay("Deal " + )
            }
        });
    }

    public void upp() {
        upgradeDamage(2);
    }
}