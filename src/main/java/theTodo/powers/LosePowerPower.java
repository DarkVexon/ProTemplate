package theTodo.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static theTodo.TodoMod.makeID;

public class LosePowerPower extends AbstractTodoPower {
    private String idToLose;
    private String nameToLose;

    public LosePowerPower(AbstractCreature owner, String nameToLose, int amount) {
        super("Lose " + nameToLose, PowerType.DEBUFF, false, owner, amount);
        this.nameToLose = nameToLose;
        this.idToLose = makeID(nameToLose);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot(new ReducePowerAction(owner, owner, idToLose, amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this.ID));
    }

    @Override
    public void updateDescription() {
        description = "At the end of your turn, lose #b" + amount + " " + nameToLose + ".";
    }
}
