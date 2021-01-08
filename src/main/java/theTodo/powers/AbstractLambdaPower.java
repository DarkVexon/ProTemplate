package theTodo.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

public abstract class AbstractLambdaPower extends AbstractTodoPower {
    public AbstractLambdaPower(String name, PowerType powerType, AbstractCreature owner, int amount) {
        super(name, powerType, true, owner, amount);
    }

    public abstract void updateDescription();
}
