package theTodo.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theTodo.TodoMod;

import static theTodo.TodoMod.getModID;

public abstract class AbstractTodoRelic extends CustomRelic {
    public AbstractCard.CardColor color;

    public AbstractTodoRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
        this(setId, tier, sfx, null);
    }

    public AbstractTodoRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx, AbstractCard.CardColor color) {
        super(setId, "", tier, sfx);

        this.color = color;

        String imgName = getBaseImagePath();
        System.out.println(imgName);

        loadImages((TodoMod.getModID() + "Resources/"), imgName);
        if (img == null || outlineImg == null) {
            loadImages("", "test5.png");
        }
    }

    protected String getBaseImagePath() {
        String id = relicId.replaceFirst(TodoMod.getModID() + ":", "");
        return id + ".png";
    }

    protected void loadImages(String basePath, String imgName) {
        img = ImageMaster.loadImage(basePath + "images/relics/" + imgName);
        outlineImg = ImageMaster.loadImage(basePath + "images/relics/outline/" + imgName);
    }

    protected void att(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    protected void atb(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static String makeID(String blah) {
        return getModID() + ":" + blah;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}