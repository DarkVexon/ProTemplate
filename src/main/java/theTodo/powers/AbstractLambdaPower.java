package theTodo.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

public abstract class AbstractLambdaPower extends AbstractEasyPower {
    public AbstractLambdaPower(String name, PowerType powerType, boolean isTurnBased, AbstractCreature owner, int amount) {
        super(name, powerType, isTurnBased, owner, amount);
    }

    public abstract void updateDescription();
}
