package theTodo.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LosePowerPower extends AbstractEasyPower {
    private AbstractPower powerToLose;

    public LosePowerPower(AbstractCreature owner, AbstractPower powerToLose, int amount) {
        super("Lose " + powerToLose.name, PowerType.DEBUFF, false, owner, amount);
        this.powerToLose = powerToLose;
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot(new ReducePowerAction(owner, owner, powerToLose.ID, amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this.ID));
    }

    @Override
    public void updateDescription() {
        if (powerToLose == null) {
            description = "???";
        } else {
            description = "At the end of your turn, lose #b" + amount + " " + powerToLose.name + ".";
        }
    }
}
