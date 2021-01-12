package theTodo.util;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.github.tommyettinger.colorful.Shaders;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.random.Random;
import javafx.scene.Camera;
import theTodo.cards.AbstractTodoCard;

import java.util.ArrayList;
import java.util.HashMap;

public class CardArtRoller {
    private static HashMap<String, TextureAtlas.AtlasRegion> doneCards = new HashMap<>();
    private static ShaderProgram shade = new ShaderProgram(Shaders.vertexShaderHSLC, Shaders.fragmentShaderHSLC);

    public static void computeCard(AbstractTodoCard c) {
        c.portrait = doneCards.computeIfAbsent(c.cardID, key -> {
            Random rng = new Random();
            ArrayList<AbstractCard> cardsList = Wiz.getCardsMatchingPredicate(r -> r.type == c.type && BaseMod.isBaseGameCardColor(r.color), true);
            AbstractCard q = Wiz.getRandomItem(cardsList, rng);
            Color HSLC = new Color(rng.random(0.35f, 0.65f), rng.random(0.35f, 0.65f), rng.random(0.35f, 0.65f), rng.random(0.35f, 0.65f));
            TextureAtlas.AtlasRegion t = q.portrait;
            t.flip(false, true);
            FrameBuffer fb = FBHelper.createBuffer();
            SpriteBatch sb = new SpriteBatch();
            FBHelper.beginBuffer(fb);
            sb.setShader(shade);
            sb.setColor(HSLC);
            sb.begin();
            sb.draw(t, 0, 0);
            sb.end();
            fb.end();
            TextureRegion a = FBHelper.getBufferTexture(fb);
            return new TextureAtlas.AtlasRegion(a.getTexture(), 0, 0, 250, 190);
        });
    }
}
