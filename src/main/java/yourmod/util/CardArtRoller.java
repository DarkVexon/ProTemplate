package yourmod.util;

import basemod.patches.whatmod.WhatMod;
import yourmod.ModFile;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Defend_Blue;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.cards.green.Defend_Green;
import com.megacrit.cardcrawl.cards.green.Strike_Green;
import com.megacrit.cardcrawl.cards.purple.Defend_Watcher;
import com.megacrit.cardcrawl.cards.purple.Strike_Purple;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.random.Random;
import yourmod.cards.AbstractEasyCard;

import java.util.ArrayList;
import java.util.HashMap;

import static com.badlogic.gdx.graphics.GL20.GL_DST_COLOR;
import static com.badlogic.gdx.graphics.GL20.GL_ZERO;

public class CardArtRoller {
    private static final Texture attackMask = TexLoader.getTexture(ModFile.makeImagePath("masks/AttackMask.png"));
    private static final Texture skillMask = TexLoader.getTexture(ModFile.makeImagePath("masks/SkillMask.png"));
    private static final Texture powerMask = TexLoader.getTexture(ModFile.makeImagePath("masks/PowerMask.png"));

    public static final String partialHueRodrigues =
            "vec3 applyHue(vec3 rgb, float hue)\n" +
                    "{\n" +
                    "    vec3 k = vec3(0.57735);\n" +
                    "    float c = cos(hue);\n" +
                    "    //Rodrigues' rotation formula\n" +
                    "    return rgb * c + cross(k, rgb) * sin(hue) + k * dot(k, rgb) * (1.0 - c);\n" +
                    "}\n";
    public static final String vertexShaderHSLC = "attribute vec4 a_position;\n"
            + "attribute vec4 a_color;\n"
            + "attribute vec2 a_texCoord0;\n"
            + "uniform mat4 u_projTrans;\n"
            + "varying vec4 v_color;\n"
            + "varying vec2 v_texCoords;\n"
            + "varying float v_lightFix;\n"
            + "\n"
            + "void main()\n"
            + "{\n"
            + "   v_color = a_color;\n"
            + "   v_texCoords = a_texCoord0;\n"
            + "   v_color.a = pow(v_color.a * (255.0/254.0) + 0.5, 1.709);\n"
            + "   v_lightFix = 1.0 + pow(v_color.a, 1.41421356);\n"
            + "   gl_Position =  u_projTrans * a_position;\n"
            + "}\n";

    public static final String fragmentShaderHSLC =
            "#ifdef GL_ES\n" +
                    "#define LOWP lowp\n" +
                    "precision mediump float;\n" +
                    "#else\n" +
                    "#define LOWP \n" +
                    "#endif\n" +
                    "varying vec2 v_texCoords;\n" +
                    "varying float v_lightFix;\n" +
                    "varying LOWP vec4 v_color;\n" +
                    "uniform sampler2D u_texture;\n" +
                    partialHueRodrigues +
                    "void main()\n" +
                    "{\n" +
                    "    float hue = 6.2831853 * (v_color.x - 0.5);\n" +
                    "    float saturation = v_color.y * 2.0;\n" +
                    "    float brightness = v_color.z - 0.5;\n" +
                    "    vec4 tgt = texture2D( u_texture, v_texCoords );\n" +
                    "    tgt.rgb = applyHue(tgt.rgb, hue);\n" +
                    "    tgt.rgb = vec3(\n" +
                    "     (0.5 * pow(dot(tgt.rgb, vec3(0.375, 0.5, 0.125)), v_color.w) * v_lightFix + brightness),\n" + // lightness
                    "     ((tgt.r - tgt.b) * saturation),\n" + // warmth
                    "     ((tgt.g - tgt.b) * saturation));\n" + // mildness
                    "    gl_FragColor = clamp(vec4(\n" +
                    "     dot(tgt.rgb, vec3(1.0, 0.625, -0.5)),\n" + // back to red
                    "     dot(tgt.rgb, vec3(1.0, -0.375, 0.5)),\n" + // back to green
                    "     dot(tgt.rgb, vec3(1.0, -0.375, -0.5)),\n" + // back to blue
                    "     tgt.a), 0.0, 1.0);\n" + // keep alpha, then clamp
                    "}";

    private static final String vertexBicolorShader = "#version 330\n" +
            "\n" +
            "uniform mat4 u_projTrans;\n" +
            "\n" +
            "in vec4 a_position;\n" +
            "in vec2 a_texCoord0;\n" +
            "in vec4 a_color;\n" +
            "\n" +
            "out vec4 v_color;\n" +
            "out vec2 v_texCoord;\n" +
            "\n" +
            "void main() {\n" +
            "    gl_Position = u_projTrans * a_position;\n" +
            "    v_color = a_color;\n" +
            "    v_texCoord = a_texCoord0;\n" +
            "}";

    private static final String fragmentBicolorShader = "const float SQRT = 1.73205;\n" +
            "\n" +
            "varying vec2 v_texCoord;\n" +
            "\n" +
            "uniform float lRed;\n" +
            "uniform float lGreen;\n" +
            "uniform float lBlue;\n" +
            "uniform float rRed;\n" +
            "uniform float rGreen;\n" +
            "uniform float rBlue;\n" +
            "uniform float anchorAR;\n" +
            "uniform float anchorAG;\n" +
            "uniform float anchorAB;\n" +
            "uniform float anchorBR;\n" +
            "uniform float anchorBG;\n" +
            "uniform float anchorBB;\n" +
            "\n" +
            "uniform sampler2D u_texture;\n" +
            "uniform vec2 u_screenSize;\n" +
            "\n" +
            "void main() {\n" +
            "\tvec4 color = texture2D(u_texture, v_texCoord);\n" +
            "\n" +
            "    vec3 T = vec3(color.r,color.g,color.b);\n" +
            "    vec3 aA = vec3(anchorAR,anchorAG,anchorAB);\n" +
            "    vec3 aB = vec3(anchorBR,anchorBG,anchorBB);\n" +
            "\n" +
            "    float lT = length(T);\n" +
            "\n" +
            "    float distA = 0.2126*abs(aA.r - T.r) + 0.7152*abs(aA.g - T.g) + 0.0722*abs(aA.b - T.b);\n" +
            "    float distB = 0.2126*abs(aB.r - T.r) + 0.7152*abs(aB.g - T.g) + 0.0722*abs(aB.b - T.b);\n" +
            "\n" +
            "    float vT = distA/(distB+distA);\n" +
            "\n" +
            "    float newR = lRed + (rRed - lRed)*vT;\n" +
            "    float newG = lGreen + (rGreen - lGreen)*vT;\n" +
            "    float newB = lBlue + (rBlue - lBlue)*vT;\n" +
            "\n" +
            "    vec3 newColor = vec3(newR,newG,newB)*lT;\n" +
            "\n" +
            "    gl_FragColor = vec4(newColor,color.a);\n" +
            "}";

    private static HashMap<String, TextureAtlas.AtlasRegion> doneCards = new HashMap<>();
    public static HashMap<String, ReskinInfo> infos = new HashMap<>();
    private static ShaderProgram shade = new ShaderProgram(vertexShaderHSLC, fragmentShaderHSLC);
    private static ShaderProgram bicolorShader = new ShaderProgram(vertexBicolorShader,fragmentBicolorShader);
    private static String[] strikes = {
            Strike_Red.ID,
            Strike_Blue.ID,
            Strike_Green.ID,
            Strike_Purple.ID
    };
    private static String[] defends = {
            Defend_Red.ID,
            Defend_Blue.ID,
            Defend_Green.ID,
            Defend_Watcher.ID
    };
    private static ArrayList<String> possAttacks = new ArrayList<>();
    private static ArrayList<String> openAttacks = new ArrayList<>();
    private static ArrayList<String> doneAttacks = new ArrayList<>();
    private static ArrayList<String> possSkills = new ArrayList<>();
    private static ArrayList<String> openSkills = new ArrayList<>();
    private static ArrayList<String> doneSkills = new ArrayList<>();
    private static ArrayList<String> possPowers = new ArrayList<>();
    private static ArrayList<String> openPowers = new ArrayList<>();
    private static ArrayList<String> donePowers = new ArrayList<>();
    private static CardLibrary.LibraryType[] basicColors = {
            CardLibrary.LibraryType.RED,
            CardLibrary.LibraryType.GREEN,
            CardLibrary.LibraryType.BLUE,
            CardLibrary.LibraryType.PURPLE,
            CardLibrary.LibraryType.COLORLESS,
            CardLibrary.LibraryType.CURSE
    };


    private static FrameBuffer smallBuffer = null;
    private static OrthographicCamera smallCamera = null;

    public static void computeCard(AbstractEasyCard c) {
        c.portrait = doneCards.computeIfAbsent(c.cardID, key -> {
            ReskinInfo r = infos.computeIfAbsent(key, key2 -> {
                Random rng = new Random((long) c.cardID.hashCode());
                String q;
                if (c.cardArtCopy() != null) {
                    q = c.cardArtCopy();
                } else if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
                    q = strikes[MathUtils.random(0, 3)];
                } else if (c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) {
                    q = defends[MathUtils.random(0, 3)];
                } else if (c.type == AbstractCard.CardType.ATTACK) {
                    if (possAttacks.isEmpty()) {
                        for (CardLibrary.LibraryType l : basicColors) {
                            for (AbstractCard card : CardLibrary.getCardList(l)) {
                                if (card.type == AbstractCard.CardType.ATTACK && WhatMod.findModID(card.getClass()) == null && !card.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
                                    possAttacks.add(card.cardID);
                                    openAttacks.add(card.cardID);
                                }
                            }
                        }
                    }
                    q = possAttacks.get(rng.random(possAttacks.size() - 1));
                    if (openAttacks.contains(q)) {
                        openAttacks.remove(q);
                        doneAttacks.add(q);
                    } else {
                        if (!openAttacks.isEmpty()) {
                            q = openAttacks.get(rng.random(openAttacks.size() - 1));
                            openAttacks.remove(q);
                            doneAttacks.add(q);
                        }
                    }
                } else if (c.type == AbstractCard.CardType.POWER) {
                    if (possPowers.isEmpty()) {
                        for (CardLibrary.LibraryType l : basicColors) {
                            for (AbstractCard card : CardLibrary.getCardList(l)) {
                                if (card.type == AbstractCard.CardType.POWER && WhatMod.findModID(card.getClass()) == null) {
                                    possPowers.add(card.cardID);
                                    openPowers.add(card.cardID);
                                }
                            }
                        }
                    }
                    q = possPowers.get(rng.random(possPowers.size() - 1));
                    if (openPowers.contains(q)) {
                        openPowers.remove(q);
                        donePowers.add(q);
                    } else {
                        if (!openPowers.isEmpty()) {
                            q = openPowers.get(rng.random(openPowers.size() - 1));
                            openPowers.remove(q);
                            donePowers.add(q);
                        }
                    }
                } else {
                    if (possSkills.isEmpty()) {
                        for (CardLibrary.LibraryType l : basicColors) {
                            for (AbstractCard card : CardLibrary.getCardList(l)) {
                                if (card.type == AbstractCard.CardType.SKILL && WhatMod.findModID(card.getClass()) == null && !card.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) {
                                    possSkills.add(card.cardID);
                                    openSkills.add(card.cardID);
                                }
                            }
                        }
                    }
                    q = possSkills.get(rng.random(possSkills.size() - 1));
                    if (openSkills.contains(q)) {
                        openSkills.remove(q);
                        doneSkills.add(q);
                    } else {
                        if (!openSkills.isEmpty()) {
                            q = openSkills.get(rng.random(openSkills.size() - 1));
                            openSkills.remove(q);
                            doneSkills.add(q);
                        }
                    }
                }
                if (c.reskinInfo(q) != null) {
                    return c.reskinInfo(q);
                } else {
                    return new ReskinInfo(q, rng.random(0.35f, 0.65f), rng.random(0.35f, 0.65f), rng.random(0.35f, 0.65f), rng.random(0.35f, 0.65f), rng.randomBoolean());
                }
            });
            Color HSLC = new Color(r.H, r.S, r.L, r.C);
            AbstractCard artCard = CardLibrary.getCard(r.origCardID);
            TextureAtlas.AtlasRegion t = artCard.portrait;
            t.flip(r.flipX, true);
            boolean shouldSwapColorTexture = true;
            if (smallBuffer == null) {
                smallBuffer = ImageHelper.createBuffer(250, 190);
                shouldSwapColorTexture = false;
            }
            if (smallCamera == null) {
                smallCamera = new OrthographicCamera(250, 190);
            }
            smallCamera.position.setZero();
            if (needsMask(c, artCard)) {
                if (artCard.type == AbstractCard.CardType.ATTACK) {
                    if (c.type == AbstractCard.CardType.POWER) {
                        //Attack to Power
                        smallCamera.zoom = 0.976f;
                        smallCamera.translate(-3, 0);
                    } else {
                        //Attack to Skill, Status, Curse
                        smallCamera.zoom = 0.9f;
                        smallCamera.translate(0, -10);
                    }
                } else if (artCard.type == AbstractCard.CardType.POWER) {
                    if (c.type == AbstractCard.CardType.ATTACK) {
                        //Power to Attack
                        smallCamera.zoom = 0.9f;
                        smallCamera.translate(0, -10);
                    } else {
                        //Power to Skill, Status, Curse
                        smallCamera.zoom = 0.825f;
                        smallCamera.translate(-1, -18);
                    }
                } else {
                    if (c.type == AbstractCard.CardType.POWER) {
                        //Skill, Status, Curse to Power
                        smallCamera.zoom = 0.976f;
                        smallCamera.translate(-3, 0);
                    }
                    //Skill, Status, Curse to Attack is free
                }
                smallCamera.update();
            }
            SpriteBatch sb = new SpriteBatch();
            sb.setProjectionMatrix(smallCamera.combined);
            smallBuffer.begin();
            if (shouldSwapColorTexture) {
                WizArt.swapTextureAndClear(smallBuffer);
            } else {
                WizArt.clearCurrentBuffer();
            }
            sb.begin();
            if (!r.isBicolor) {
                sb.setShader(shade);
                sb.setColor(HSLC);
            } else {
                sb.setShader(bicolorShader);
                sb.setColor(Color.WHITE);
                setBicolorShaderValues(r);
            }
            sb.draw(t, -125, -95);
            if (needsMask(c, artCard)) {
                sb.setBlendFunction(GL_DST_COLOR, GL_ZERO);
                Texture mask = getMask(c);
                sb.setProjectionMatrix(new OrthographicCamera(250, 190).combined);
                sb.draw(mask, -125, -95, -125, -95, 250, 190, 1, 1, 0, 0, 0, mask.getWidth(), mask.getHeight(), false, true);
            }
            sb.end();
            smallBuffer.end();
            t.flip(r.flipX, true);
            TextureRegion a = ImageHelper.getBufferTexture(smallBuffer);
            return new TextureAtlas.AtlasRegion(a.getTexture(), 0, 0, 250, 190);
        });
    }

    private static FrameBuffer portraitBuffer = null;
    private static OrthographicCamera portraitCamera = null;

    public static Texture getPortraitTexture(AbstractCard c) {
        ReskinInfo r = infos.get(c.cardID);
        Color HSLC = new Color(r.H, r.S, r.L, r.C);
        AbstractCard artCard = CardLibrary.getCard(r.origCardID);
        TextureAtlas.AtlasRegion t = new TextureAtlas.AtlasRegion(TexLoader.getTexture("images/1024Portraits/" + artCard.assetUrl + ".png"), 0, 0, 500, 380);
        t.flip(r.flipX, true);
        boolean shouldSwapColorTexture = true;
        if (portraitBuffer == null) {
            portraitBuffer = ImageHelper.createBuffer(500, 380);
            shouldSwapColorTexture = false;
        }
        if (portraitCamera == null) {
            portraitCamera = new OrthographicCamera(500, 380);
        }
        portraitCamera.position.setZero();
        if (needsMask(c, artCard)) {
            if (artCard.type == AbstractCard.CardType.ATTACK) {
                if (c.type == AbstractCard.CardType.POWER) {
                    //Attack to Power
                    portraitCamera.zoom = 0.976f;
                    portraitCamera.translate(-6, 0);
                } else {
                    //Attack to Skill, Status, Curse
                    portraitCamera.zoom = 0.9f;
                    portraitCamera.translate(0, -20);
                }
            } else if (artCard.type == AbstractCard.CardType.POWER) {
                if (c.type == AbstractCard.CardType.ATTACK) {
                    //Power to Attack
                    portraitCamera.zoom = 0.9f;
                    portraitCamera.translate(0, -20);
                } else {
                    //Power to Skill, Status, Curse
                    portraitCamera.zoom = 0.825f;
                    portraitCamera.translate(-2, -36);
                }
            } else {
                if (c.type == AbstractCard.CardType.POWER) {
                    //Skill, Status, Curse to Power
                    portraitCamera.zoom = 0.976f;
                    portraitCamera.translate(-6, 0);
                }
                //Skill, Status, Curse to Attack is free
            }
            portraitCamera.update();
        }
        SpriteBatch sb = new SpriteBatch();
        sb.setProjectionMatrix(portraitCamera.combined);
        portraitBuffer.begin();
        if (shouldSwapColorTexture) {
            WizArt.swapTextureAndClear(portraitBuffer);
        }
        sb.begin();
        if (!r.isBicolor) {
            sb.setShader(shade);
            sb.setColor(HSLC);
        } else {
            sb.setShader(bicolorShader);
            sb.setColor(Color.WHITE);
            setBicolorShaderValues(r);
        }
        sb.draw(t, -250, -190);
        if (needsMask(c, artCard)) {
            sb.setBlendFunction(GL_DST_COLOR, GL_ZERO);
            Texture mask = getMask(c);
            sb.setProjectionMatrix(new OrthographicCamera(500, 380).combined);
            sb.draw(mask, -250, -190, -250, -190, 500, 380, 1, 1, 0, 0, 0, mask.getWidth(), mask.getHeight(), false, true);
        }
        sb.end();
        portraitBuffer.end();
        t.flip(r.flipX, true);
        TextureRegion a = ImageHelper.getBufferTexture(portraitBuffer);
        return a.getTexture();

        //Actually, I think this can work. Because SingleCardViewPopup disposes of the texture, we can just make a new one every time.
    }

    private static void setBicolorShaderValues(ReskinInfo info) {
        bicolorShader.setUniformf("lRed", info.target1.r);
        bicolorShader.setUniformf("lGreen", info.target1.g);
        bicolorShader.setUniformf("lBlue", info.target1.b);
        bicolorShader.setUniformf("rRed", info.target2.r);
        bicolorShader.setUniformf("rGreen", info.target2.g);
        bicolorShader.setUniformf("rBlue", info.target2.b);
        bicolorShader.setUniformf("anchorAR", info.anchor1.r);
        bicolorShader.setUniformf("anchorAG", info.anchor1.g);
        bicolorShader.setUniformf("anchorAB", info.anchor1.b);
        bicolorShader.setUniformf("anchorBR", info.anchor2.r);
        bicolorShader.setUniformf("anchorBG", info.anchor2.g);
        bicolorShader.setUniformf("anchorBB", info.anchor2.b);
    }

    public static Texture getMask(AbstractCard card) {
        switch (card.type) {
            case SKILL:
            case STATUS:
            case CURSE:
                return skillMask;
            case ATTACK:
                return attackMask;
            case POWER:
                return powerMask;
        }
        return skillMask;
    }
    public static int getMaskIndex(AbstractCard card) {
        switch (card.type) {
            case SKILL:
            case STATUS:
            case CURSE:
                return 2;
            case ATTACK:
                return 1;
            case POWER:
                return 0;
        }
        return 0;
    }

    public static boolean needsMask(AbstractCard card, AbstractCard desiredArt) {
        return getMaskIndex(card) != getMaskIndex(desiredArt);
    }

    public static class ReskinInfo {
        public String origCardID;
        public boolean isBicolor = false;
        public float H;
        public float S;
        public float L;
        public float C;
        public boolean flipX;

        public Color anchor1;
        public Color anchor2;
        public Color target1;
        public Color target2;

        public ReskinInfo(String ID, float H, float S, float L, float C, boolean flipX) {
            this.origCardID = ID;
            this.H = H;
            this.S = S;
            this.L = L;
            this.C = C;
            this.flipX = flipX;
        }


        //If you use this constructor, the original image will be changed so that :
        //the anchor1 color will be made into the target1 color
        //the anchor2 color will be made into the target2 color
        //every other color will be made into a color between the two targets, based on its distance to the anchors
        public ReskinInfo(String ID, Color anchor1, Color anchor2, Color target1, Color target2, boolean flipX) {
            this.origCardID = ID;
            this.anchor1 = anchor1;
            this.anchor2 = anchor2;
            this.target1 = target1;
            this.target2 = target2;
            this.flipX = flipX;
            isBicolor = true;
        }
    }
}
