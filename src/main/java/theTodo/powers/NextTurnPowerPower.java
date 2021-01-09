package theTodo.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class NextTurnPowerPower extends AbstractEasyPower {
    private AbstractPower powerToGain;

    public NextTurnPowerPower(AbstractCreature owner, AbstractPower powerToGrant) {
        super("Next Turn " + powerToGrant.name, PowerType.DEBUFF, false, owner, powerToGrant.amount);
        this.powerToGain = powerToGrant;
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, powerToGain, powerToGain.amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this.ID));
    }

    @Override
    public void updateDescription() {
        if (powerToGain == null) {
            description = "???";
        } else {
            description = "Next turn, gain #b" + powerToGain.amount + " " + powerToGain.name + ".";
        }
    }
}
