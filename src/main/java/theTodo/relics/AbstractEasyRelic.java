package theTodo.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theTodo.util.TextureLoader;

import static theTodo.TodoMod.makeRelicPath;

public abstract class AbstractEasyRelic extends CustomRelic {
    public AbstractCard.CardColor color;

    public AbstractEasyRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
        this(setId, tier, sfx, null);
    }

    public AbstractEasyRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx, AbstractCard.CardColor color) {
        super(setId, TextureLoader.getTexture(makeRelicPath(setId + ".png")), tier, sfx);
        outlineImg = TextureLoader.getTexture(makeRelicPath(setId + "Outline.png"));
        this.color = color;
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}