package theTodo.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theTodo.cards.EasyModalChoiceCard;

import java.util.ArrayList;

public class EasyModalChoiceAction extends SelectCardsAction {
    public EasyModalChoiceAction(ArrayList<EasyModalChoiceCard> list, int amount, String textforSelect) {
        super(new ArrayList<>(list), amount, textforSelect, (cards) -> {
            for (AbstractCard q : cards) {
                q.onChoseThisOption();
            }
        });
    }

    public EasyModalChoiceAction(ArrayList<EasyModalChoiceCard> list, int amount) {
        this(list, amount, "Choose.");
    }

    public EasyModalChoiceAction(ArrayList<EasyModalChoiceCard> list) {
        this(list, 1, "Choose.");
    }
}
