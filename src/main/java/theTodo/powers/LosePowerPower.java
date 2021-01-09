package theTodo.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theTodo.util.FBHelper;
import theTodo.util.TexLoader;

import java.util.HashMap;

public class LosePowerPower extends AbstractEasyPower {
    private AbstractPower powerToLose;
    private static Texture chain = TexLoader.getTexture("todomodResources/images/ui/chain.png");
    public static HashMap<String, Texture> bufferHashMap = new HashMap<>();

    public LosePowerPower(AbstractCreature owner, AbstractPower powerToLose, int amount) {
        super("Lose " + powerToLose.name, PowerType.DEBUFF, false, owner, amount);
        this.img = bufferHashMap.computeIfAbsent(powerToLose.ID, (x) -> {
            SpriteBatch sb = new SpriteBatch();
            FrameBuffer fb = FBHelper.createBuffer();
            FBHelper.beginBuffer(fb);
            sb.begin();
            sb.draw(powerToLose.region48, 0, 0);
            sb.draw(chain, 0, 0);
            sb.end();
            fb.end();
            return FBHelper.getBufferTexture(fb).getTexture();
        });
        this.region48 = new TextureAtlas.AtlasRegion(img, 0, 0, img.getWidth(), img.getHeight());
        this.region128 = new TextureAtlas.AtlasRegion(img, 0, 0, img.getWidth(), img.getHeight());
        this.powerToLose = powerToLose;
        updateDescription();
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        super.renderIcons(sb, x, y, Color.RED.cpy());
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot(new ReducePowerAction(owner, owner, powerToLose.ID, amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this.ID));
    }

    @Override
    public void updateDescription() {
        if (powerToLose == null) {
            description = "???";
        } else {
            description = "At the end of your turn, lose #b" + amount + " " + powerToLose.name + ".";
        }
    }
}
