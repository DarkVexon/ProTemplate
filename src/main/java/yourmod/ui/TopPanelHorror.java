package yourmod.ui;

import basemod.TopPanelItem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import yourmod.ModFile;
import yourmod.specialmechanics.Horror;
import yourmod.util.TexLoader;

public class TopPanelHorror extends TopPanelItem {

    private static final float tipYpos = Settings.HEIGHT - (120.0f * Settings.scale);

    public static final String ID = ModFile.makeID("HorrorInfo");

    public static final Texture ICON = TexLoader.getTexture("todomodResources/images/ui/horror.png");
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    public TopPanelHorror() {
        super(ICON, ID);
    }

    @Override
    public boolean isClickable() {
        return false;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (Horror.horrorAmt > 0) {
            render(sb, Color.WHITE);
            if (getHitbox().hovered) {
                TipHelper.renderGenericTip(getHitbox().x, tipYpos, uiStrings.TEXT[0], uiStrings.TEXT[1]);
            }
        }
    }

    @Override
    protected void onClick() {

    }
}
