package yourmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import yourmod.specialmechanics.Gamer;
import yourmod.specialmechanics.Horror;

public class ExtraHooks {
    @SpirePatch(clz = AbstractPlayer.class, method = "preBattlePrep")
    public static class PreBattlePrep {
        public static void Prefix(AbstractPlayer __instance) {

        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "applyStartOfCombatLogic")
    public static class StartOfCombat {
        public static void Prefix(AbstractPlayer __instance) {
            Horror.startOfCombat();
            Gamer.startOfCombat();
        }
    }

    @SpirePatch(clz = MainMenuScreen.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {})
    public static class OnRunStart {
        public static void Postfix() {
            Horror.horrorAmt = 0;
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "applyStartOfTurnPostDrawRelics")
    public static class StartOfTurn {
        public static void Prefix(AbstractPlayer __instance) {
            Horror.startOfTurn();
            Gamer.startOfTurn();
        }
    }

    @SpirePatch(clz = CardGroup.class, method = "triggerOnOtherCardPlayed")
    public static class OnCardPlayed {
        public static void Prefix(CardGroup __instance, AbstractCard abstractCard) {
            Horror.onCardPlayed(abstractCard);
            Gamer.onCardPlayed(abstractCard);
        }
    }
}
