package yourmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static yourmod.ModFile.makeID;

public class CleanHousePower extends AbstractEasyPower {
    public static String ID = makeID(CleanHousePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public CleanHousePower(int amount) {
        super(ID, powerStrings.NAME, PowerType.BUFF, false, AbstractDungeon.player, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (EnergyPanel.totalCount > 0) {
            flash();
            addToBot(new DamageAllEnemiesAction(owner, DamageInfo.createDamageMatrix(amount), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            removeThis();
        }
    }

    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }
}
