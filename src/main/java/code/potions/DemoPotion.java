package code.potions;

import basemod.BaseMod;
import code.CharacterFile;
import code.ModFile;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static code.ModFile.makeID;
import static code.util.Wiz.*;

public class DemoPotion extends BasePotion {
    public static String ID = makeID("DemoPotion");

    public DemoPotion() {
        super(ID, PotionRarity.COMMON, PotionSize.ANVIL, new Color(0.2f, 0.4f, 0.9f, 1f), new Color(0.6f, 0.8f, 1.0f, 1f), null, CharacterFile.Enums.THE_TODO, ModFile.characterColor);
    }

    public int getPotency(int ascensionlevel) {
        return 10;
    }

    public void use(AbstractCreature creature) {
        applyToSelf(new StrengthPower(adp(), potency));
    }

    public String getDescription() {
        return strings.DESCRIPTIONS[0] + potency + strings.DESCRIPTIONS[1];
    }

    public void addAdditionalTips() {
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("todo")), BaseMod.getKeywordDescription(makeID("todo"))));
    }
}