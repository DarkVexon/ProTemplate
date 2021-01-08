package theTodo.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class AbstractEasyCardmod extends AbstractCardModifier {
    public void atb(AbstractGameAction a) {
        AbstractDungeon.actionManager.addToBottom(a);
    }

    public void att(AbstractGameAction a) {
        AbstractDungeon.actionManager.addToTop(a);
    }
}
