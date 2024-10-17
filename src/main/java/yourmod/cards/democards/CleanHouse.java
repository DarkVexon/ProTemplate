package yourmod.cards.democards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yourmod.cards.AbstractEasyCard;
import yourmod.powers.CleanHousePower;

import static yourmod.ModFile.makeID;
import static yourmod.util.Wiz.*;

public class CleanHouse extends AbstractEasyCard {
    public final static String ID = makeID(CleanHouse.class.getSimpleName());
    // intellij stuff attack, all_enemy, common, 7, 3, , , 7, 3

    public CleanHouse() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 6;
        baseMagicNumber = magicNumber = 9;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        applyToSelf(new CleanHousePower(magicNumber));
    }

    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(3);
    }
}