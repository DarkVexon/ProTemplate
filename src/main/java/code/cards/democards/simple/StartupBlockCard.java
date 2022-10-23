package code.cards.democards.simple;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import code.cards.AbstractEasyCard;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class StartupBlockCard extends AbstractEasyCard implements StartupCard { // This card implements StartupCard, an extra 'type' of card that adds the ability to trigger at the start of combat.
    public final static String ID = makeID(StartupBlockCard.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , ,

    public StartupBlockCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY); // This card is a 1 cost Common Skill that targets an enemy.
        baseDamage = 7;
        isMultiDamage = true; // This card attacks ALL enemies, so isMultiDamage is true...
        baseMagicNumber = magicNumber = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.FIRE); // and allDmg is used instead of dmg.
    }

    @Override
    public boolean atBattleStartPreDraw() {
        atb(new GainBlockAction(AbstractDungeon.player, magicNumber)); // Since this isn't a card being played, we use a flat value (magicnumber) for the Block.
        return true; // The return value is if you want this card to show up pre-fight, to indicate the effect triggered. Since it always triggers, we always return true.
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}