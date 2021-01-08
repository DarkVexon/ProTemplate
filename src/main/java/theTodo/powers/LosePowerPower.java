package theTodo.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class LosePowerPower extends AbstractLambdaPower {
    private String idToLose;

    public LosePowerPower(AbstractCreature owner, String idToLose, int amount) {
        super("Lose" + idToLose, PowerType.DEBUFF, owner, amount);
        this.idToLose = idToLose;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot(new ReducePowerAction(owner, owner, idToLose, amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this.ID));
    }
}
