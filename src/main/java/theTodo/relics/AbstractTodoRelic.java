package theTodo.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theTodo.util.TextureLoader;

import static theTodo.TodoMod.getModID;
import static theTodo.TodoMod.makeRelicPath;

public abstract class AbstractTodoRelic extends CustomRelic {
    public AbstractCard.CardColor color;

    public AbstractTodoRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
        this(setId, tier, sfx, null);
    }

    public AbstractTodoRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx, AbstractCard.CardColor color) {
        super(setId, TextureLoader.getTexture(makeRelicPath(setId + ".png")), tier, sfx);
        outlineImg = TextureLoader.getTexture(makeRelicPath(setId + "Outline.png"));
        this.color = color;
    }

    protected void att(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    protected void atb(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void applyToEnemy(AbstractMonster m, AbstractPower po) {
        atb(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    protected void applyToSelf(AbstractPower po) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    public static String makeID(String blah) {
        return getModID() + ":" + blah;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}