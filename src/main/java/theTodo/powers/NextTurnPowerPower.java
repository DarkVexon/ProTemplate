package theTodo.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theTodo.util.FBHelper;
import theTodo.util.TexLoader;

import java.util.HashMap;

public class NextTurnPowerPower extends AbstractEasyPower {
    private AbstractPower powerToGain;
    private static Texture arrow = TexLoader.getTexture("todomodResources/images/ui/arrow.png");
    public static HashMap<String, Texture> bufferHashMap = new HashMap<>();

    public NextTurnPowerPower(AbstractCreature owner, AbstractPower powerToGrant) {
        super("Next Turn " + powerToGrant.name, powerToGrant.type, false, owner, powerToGrant.amount);
        this.img = bufferHashMap.computeIfAbsent(powerToGrant.ID, (x) -> {
            SpriteBatch sb = new SpriteBatch();
            FrameBuffer fb = FBHelper.createBuffer();
            FBHelper.beginBuffer(fb);
            sb.begin();
            sb.draw(powerToGrant.region48, 0, 0);
            sb.draw(arrow, 0, 0);
            sb.end();
            fb.end();
            return FBHelper.getBufferTexture(fb).getTexture();
        });
        this.region48 = new TextureAtlas.AtlasRegion(img, 0, 0, img.getWidth(), img.getHeight());
        this.region128 = new TextureAtlas.AtlasRegion(img, 0, 0, img.getWidth(), img.getHeight());
        this.powerToGain = powerToGrant;
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, powerToGain, powerToGain.amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this.ID));
    }

    @Override
    public void updateDescription() {
        if (powerToGain == null) {
            description = "???";
        } else {
            description = "Next turn, gain #b" + powerToGain.amount + " " + powerToGain.name + ".";
        }
    }
}
