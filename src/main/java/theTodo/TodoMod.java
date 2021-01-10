package theTodo;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import theTodo.cards.AbstractTodoCard;
import theTodo.cards.cardvars.SecondDamage;
import theTodo.cards.cardvars.SillyVariable;
import theTodo.relics.AbstractEasyRelic;

import java.nio.charset.StandardCharsets;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class TodoMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber {

    private static String modID = "todomod";
    public static String getModID() {
        return modID;
    }
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    public static Color todoColor = new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
    public static final String SHOULDER1 = getModID() + "Resources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = getModID() + "Resources/images/char/mainChar/shoulder2.png";
    public static final String CORPSE = getModID() + "Resources/images/char/mainChar/corpse.png";
    private static final String ATTACK_S_ART = getModID() + "Resources/images/512/attack.png";
    private static final String SKILL_S_ART = getModID() + "Resources/images/512/skill.png";
    private static final String POWER_S_ART = getModID() + "Resources/images/512/power.png";
    private static final String CARD_ENERGY_S = getModID() + "Resources/images/512/energy.png";
    private static final String TEXT_ENERGY = getModID() + "Resources/images/512/text_energy.png";
    private static final String ATTACK_L_ART = getModID() + "Resources/images/1024/attack.png";
    private static final String SKILL_L_ART = getModID() + "Resources/images/1024/skill.png";
    private static final String POWER_L_ART = getModID() + "Resources/images/1024/power.png";
    private static final String CARD_ENERGY_L = getModID() + "Resources/images/1024/energy.png";
    private static final String CHARSELECT_BUTTON = getModID() + "Resources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = getModID() + "Resources/images/charSelect/charBG.png";

    public TodoMod() {
        BaseMod.subscribe(this);

        BaseMod.addColor(TheTodo.Enums.TODO_COLOR, todoColor, todoColor, todoColor,
                todoColor, todoColor, todoColor, todoColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }

    public static String makeCharacterPath(String resourcePath) {
        return modID + "Resources/images/character/" + resourcePath;
    }

    public static String makeEffectPath(String resourcePath) {
        return modID + "Resources/images/effects/" + resourcePath;
    }

    public static void initialize() {
        TodoMod todoMod = new TodoMod();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheTodo(TheTodo.characterStrings.NAMES[1], TheTodo.Enums.THE_TODO),
                CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, TheTodo.Enums.THE_TODO);
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
                .packageFilter(AbstractEasyRelic.class)
                .any(AbstractEasyRelic.class, (info, relic) -> {
                    if (relic.color == null) {
                        BaseMod.addRelic(relic, RelicType.SHARED);
                    } else {
                        BaseMod.addRelicToCustomPool(relic, relic.color);
                    }
                    if (!info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new SillyVariable());
        BaseMod.addDynamicVariable(new SecondDamage());
        new AutoAdd(modID)
                .packageFilter(AbstractTodoCard.class)
                .setDefaultSeen(true)
                .cards();
    }


    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Resources/localization/eng/Cardstrings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class, getModID() + "Resources/localization/eng/Relicstrings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class, getModID() + "Resources/localization/eng/Charstrings.json");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }
}
