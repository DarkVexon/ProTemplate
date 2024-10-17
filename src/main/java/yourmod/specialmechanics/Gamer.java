package yourmod.specialmechanics;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.HashSet;
import java.util.Set;

public class Gamer {
    public static boolean isGamer;
    private static Set<AbstractCard.CardType> typesThisTurn = new HashSet<>();

    private static void becomeGamer() {
        isGamer = true;
    }

    public static void startOfCombat() {
        isGamer = false;
    }

    public static void startOfTurn() {
        isGamer = false;
        typesThisTurn.clear();
    }

    public static void onCardPlayed(AbstractCard card) {
        typesThisTurn.add(card.type);
        if (typesThisTurn.size() >= 3 && !isGamer) {
            becomeGamer();
        }
    }
}
