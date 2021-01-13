package theTodo.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import theTodo.cards.AbstractEasyCard;

public class CardArtRollerPatches {
    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "close"
    )
    public static class NoMoreUnloadUntilISaySo {
        @SpireInsertPatch(
                rloc = 5
        )
        public static SpireReturn Insert(SingleCardViewPopup __instance) {
            if (((AbstractCard)ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card")) instanceof AbstractEasyCard) {
                ReflectionHacks.setPrivate(__instance, SingleCardViewPopup.class, "portraitImg", null);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
