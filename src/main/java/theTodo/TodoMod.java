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
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import theTodo.cards.AbstractTodoCard;
import theTodo.relics.*;
import theTodo.util.SecondDamage;
import theTodo.util.SillyVariable;

import java.nio.charset.StandardCharsets;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class TodoMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber {
    public static final String SHOULDER1 = "todomodResources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = "todomodResources/images/char/mainChar/shoulder2.png";
    public static final String CORPSE = "todomodResources/images/char/mainChar/corpse.png";
    private static final String ATTACK_S_ART = "todomodResources/images/512/attack.png";
    private static final String SKILL_S_ART = "todomodResources/images/512/skill.png";
    private static final String POWER_S_ART = "todomodResources/images/512/power.png";
    private static final String CARD_ENERGY_S = "todomodResources/images/512/energy.png";
    private static final String TEXT_ENERGY = "todomodResources/images/512/text_energy.png";
    private static final String ATTACK_L_ART = "todomodResources/images/1024/attack.png";
    private static final String SKILL_L_ART = "todomodResources/images/1024/skill.png";
    private static final String POWER_L_ART = "todomodResources/images/1024/power.png";
    private static final String CARD_ENERGY_L = "todomodResources/images/1024/energy.png";
    private static final String CHARSELECT_BUTTON = "todomodResources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = "todomodResources/images/charSelect/charBG.png";
    private static String modID;
    private static String artifactID;

    public static Color todoColor = new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1); //TODO: Set this to your character's favorite color!

    public TodoMod() {

        BaseMod.subscribe(this);

        modID = "todomod"; //TODO: Change this!
        artifactID = "TheTodo"; //TODO: Change this, but make sure it matches the ArtifactID in your pom.

        BaseMod.addColor(TheTodo.Enums.TODO_COLOR, todoColor, todoColor, todoColor,
                todoColor, todoColor, todoColor, todoColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);

    }

    //makePaths
    public static String makePath(String resourcePath) {
        return modID + "/" + resourcePath;
    }
    public static String makeImagePath(String resourcePath) {
        return modID + "/images/" + resourcePath;
    }
    public static String makeRelicPath(String resourcePath) {
        return modID + "/images/relics/" + resourcePath;
    }
    public static String makePowerPath(String resourcePath) {
        return modID + "/images/powers/" + resourcePath;
    }
    public static String makeCardPath(String resourcePath) {
        return modID + "/images/cards/" + resourcePath;
    }
    public static String makeCharacterPath(String resourcePath) {
        return modID + "/images/character/" + resourcePath;
    }
    public static String makeEffectPath(String resourcePath) {
        return modID + "/images/effects/" + resourcePath;
    }

    public static String getModID() {
        return modID;
    }

    public static void initialize() {
        TodoMod todoMod = new TodoMod();
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheTodo("the Todo", TheTodo.Enums.THE_TODO),
                CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, TheTodo.Enums.THE_TODO);
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(artifactID)
                .packageFilter(AbstractTodoRelic.class)
                .any(AbstractTodoRelic.class, (info, relic) -> {
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
        new AutoAdd(artifactID)
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
