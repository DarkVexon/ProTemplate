package theTodo.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theTodo.TodoMod;
import theTodo.util.TexLoader;

import java.util.HashMap;
import java.util.Map;

import static theTodo.TodoMod.makeID;

public abstract class AbstractEasyPower extends AbstractPower {
    public AbstractEasyPower(String NAME, PowerType powerType, boolean isTurnBased, AbstractCreature owner, int amount) {
        this.ID = makeID(NAME.replaceAll("([ ])", ""));
        this.isTurnBased = isTurnBased;

        this.name = NAME;

        this.owner = owner;
        this.amount = amount;
        this.type = powerType;

        Texture normalTexture = TexLoader.getTexture(TodoMod.modID + "Resources/images/powers/" + NAME.replaceAll("([ ])", "") + "32.png");
        Texture hiDefImage = TexLoader.getTexture(TodoMod.modID + "Resources/images/powers/" + NAME.replaceAll("([ ])", "") + "84.png");
        if (hiDefImage != null) {
            region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
            if (normalTexture != null)
                region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        } else if (normalTexture != null) {
            this.img = normalTexture;
            region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        }

        this.updateDescription();
    }
}