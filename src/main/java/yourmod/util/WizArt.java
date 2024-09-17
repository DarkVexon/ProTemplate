package yourmod.util;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.megacrit.cardcrawl.core.Settings;


public class WizArt {

    //draw helpers
    public static void draw(SpriteBatch sb, TextureRegion tex, float x, float y, float scale) {
        sb.draw(tex, x, y, 0, 0, tex.getRegionWidth(),tex.getRegionHeight(),scale, scale, 0f);
    }

    public static void drawCentered(SpriteBatch sb, TextureRegion tex, float cX, float cY, float scale) {
        sb.draw(tex,
                cX - tex.getRegionWidth() / 2f,
                cY - tex.getRegionHeight() / 2f,
                tex.getRegionWidth() / 2f,
                tex.getRegionHeight() / 2f,
                tex.getRegionWidth(),
                tex.getRegionHeight(),
                scale,
                scale,
                0f);
    }

    //SpriteBatch settings helpers
    public static void clearAllSettings(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.setShader(null);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void setToNormalBlend(SpriteBatch sb) {
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void setToAdditiveBlend(SpriteBatch sb) {
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_DST_ALPHA);
    }

    //textures drawn with this functions follow the alpha channel of what is already drawn
    public static void setToMaskBlend(SpriteBatch sb) {
        sb.setBlendFunction(GL20.GL_DST_ALPHA, GL20.GL_ONE_MINUS_DST_ALPHA);
    }



    //buffer helpers

    //basic fbo over the whole screen. I think one global fbo like this actually covers most fbo uses.
    //other uses can just use their own
    public static FrameBuffer fbo = null;

    public static void beginFbo() {
        if (fbo == null) {
            fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Settings.WIDTH, Settings.HEIGHT, false);
        }
        fbo.begin();
    }

    public static void endFbo() {
        fbo.end();
    }

    public static Texture getFboTexture() {
        return fbo.getColorBufferTexture();
    }

    public static TextureRegion getFboTextureRegion() {
        return new TextureRegion(fbo.getColorBufferTexture());
    }

    public static void clearCurrentBuffer() {
        Gdx.gl20.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glColorMask(true, true, true, true);
    }

    //uses reflection to change the buffer's colorTexture.
    //that way, the original texture isn't disposed at the same time as the buffer, and can be kept for later use
    //the original texture will also survive the buffer being cleared
    //this isn't ideal since it requires creating a new texture that might never be used, but use cases exist
    //and there is no alternative in this version of LibGdx as far as I can tell
    public static void swapColorTexture(FrameBuffer fb, Texture tex, boolean isBufferBound) {
        ReflectionHacks.setPrivate(fb, GLFrameBuffer.class, "colorTexture", tex);
        if (!isBufferBound) {
            fb.begin();
        }
        Gdx.gl20.glBindTexture(tex.glTarget, tex.getTextureObjectHandle());
        Gdx.gl20.glFramebufferTexture2D(36160, 36064, 3553, tex.getTextureObjectHandle(), 0);
        Gdx.gl20.glBindTexture(tex.glTarget, 0);
        if (!isBufferBound) {
            fb.end();
        }
    }

    public static void swapColorTexture(FrameBuffer fb, boolean isBufferBound) {
        Pixmap.Format format = ReflectionHacks.getPrivate(fb, GLFrameBuffer.class, "format");
        Texture tex = new Texture(fb.getWidth(), fb.getHeight(), format);
        swapColorTexture(fb, tex, isBufferBound);
    }

    public static void swapTextureAndClear(FrameBuffer fb, Texture tex) {
        swapColorTexture(fb, tex, true);
        clearCurrentBuffer();
    }

    public static void swapTextureAndClear(FrameBuffer fb) {
        swapColorTexture(fb, true);
        clearCurrentBuffer();
    }


    //SpriteBatch state save (only one at a time)
    //saves the current SpriteBatch settings to load them back after you're done, to make sure you're not disturbing anything that happens around what you're doing
    private static final StateData stateSave = new StateData();

    public static void saveState(SpriteBatch sb) {
        stateSave.save(sb);
    }

    public static void loadState(SpriteBatch sb) {
        stateSave.load(sb);
    }

    private static class StateData {
        private Color color = null;
        private ShaderProgram shader = null;
        private int srcBlendFunc = -1;
        private int dstBlendFunc = -1;

        public void save(SpriteBatch sb) {
            color = sb.getColor();
            shader = sb.getShader();
            srcBlendFunc = sb.getBlendSrcFunc();
            dstBlendFunc = sb.getBlendDstFunc();
        }

        public void load(SpriteBatch sb) {
            sb.setColor(color);
            sb.setShader(shader);
            sb.setBlendFunction(srcBlendFunc, dstBlendFunc);
        }
    }
}
