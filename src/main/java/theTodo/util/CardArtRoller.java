package theTodo.util;

import basemod.BaseMod;
import basemod.patches.whatmod.WhatMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.github.tommyettinger.colorful.Shaders;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.random.Random;
import theTodo.cards.AbstractEasyCard;

import java.util.ArrayList;
import java.util.HashMap;

public class CardArtRoller {
    private static HashMap<String, TextureAtlas.AtlasRegion> doneCards = new HashMap<>();
    public static HashMap<String, ReskinInfo> infos = new HashMap<String, ReskinInfo>();
    private static ShaderProgram shade = new ShaderProgram(Shaders.vertexShaderHSLC, Shaders.fragmentShaderHSLC);

    public static void computeCard(AbstractEasyCard c) {
        c.portrait = doneCards.computeIfAbsent(c.cardID, key -> {
            ReskinInfo r = infos.computeIfAbsent(key, key2 -> {
                Random rng = new Random((long) c.cardID.hashCode());
                ArrayList<AbstractCard> cardsList = Wiz.getCardsMatchingPredicate(s -> s.type == c.type && WhatMod.findModName(s.getClass()) == null, true);
                String q = Wiz.getRandomItem(cardsList, rng).cardID;
                return new ReskinInfo(q, rng.random(0.35f, 0.65f), rng.random(0.35f, 0.65f), rng.random(0.35f, 0.65f), rng.random(0.35f, 0.65f), rng.randomBoolean());
            });
            Color HSLC = new Color(r.H, r.S, r.L, r.C);
            TextureAtlas.AtlasRegion t = CardLibrary.getCard(r.origCardID).portrait;
            t.flip(false, true);
            FrameBuffer fb = ImageHelper.createBuffer(250, 190);
            OrthographicCamera og = new OrthographicCamera(250, 190);
            SpriteBatch sb = new SpriteBatch();
            sb.setProjectionMatrix(og.combined);
            ImageHelper.beginBuffer(fb);
            sb.setShader(shade);
            sb.setColor(HSLC);
            sb.begin();
            sb.draw(t, -125, -95);
            sb.end();
            fb.end();
            TextureRegion a = ImageHelper.getBufferTexture(fb);
            return new TextureAtlas.AtlasRegion(a.getTexture(), 0, 0, 250, 190);
        });
    }

    public static Texture getPortraitTexture(AbstractCard c) {
        ReskinInfo r = infos.get(c.cardID);
        Color HSLC = new Color(r.H, r.S, r.L, r.C);
        TextureAtlas.AtlasRegion t = new TextureAtlas.AtlasRegion(TexLoader.getTexture("images/1024Portraits/" + CardLibrary.getCard(r.origCardID).assetUrl + ".png"), 0, 0, 500, 380);
        t.flip(false, true);
        FrameBuffer fb = ImageHelper.createBuffer(500, 380);
        OrthographicCamera og = new OrthographicCamera(500, 380);
        SpriteBatch sb = new SpriteBatch();
        sb.setProjectionMatrix(og.combined);
        ImageHelper.beginBuffer(fb);
        sb.setShader(shade);
        sb.setColor(HSLC);
        sb.begin();
        sb.draw(t, -250, -190);
        sb.end();
        fb.end();
        t.flip(false, true);
        TextureRegion a = ImageHelper.getBufferTexture(fb);
        return a.getTexture();

        //Actually, I think this can work. Because SingleCardViewPopup disposes of the texture, we can just make a new one every time.
    }

    public static class ReskinInfo {
        public String origCardID;
        public float H;
        public float S;
        public float L;
        public float C;
        public boolean flipX;

        public ReskinInfo(String ID, float H, float S, float L, float C, boolean flipX) {
            this.origCardID = ID;
            this.H = H;
            this.S = S;
            this.L = L;
            this.C = C;
            this.flipX = flipX;
        }
    }
}
