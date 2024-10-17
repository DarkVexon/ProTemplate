package yourmod.cards.democards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import yourmod.cards.AbstractEasyCard;
import yourmod.specialmechanics.Horror;

import static yourmod.ModFile.makeID;
import static yourmod.util.Wiz.applyToSelf;

public class MurderMysteryMayhem extends AbstractEasyCard implements OnObtainCard {
    public final static String ID = makeID(MurderMysteryMayhem.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 13, 4, , , , 

    public MurderMysteryMayhem() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 13;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
        applyToSelf(new StrengthPower(p, Horror.horrorAmt));
    }

    public void upp() {
        upgradeDamage(4);
    }

    @Override
    public void onObtainCard() {
        Horror.addHorror(1);
    }

    @Override
    public void onRemoveFromMasterDeck() {
        Horror.removeHorror(1);
    }
}