package yourmod.specialmechanics.delay;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class DelayManager {
    public static ArrayList<Delay> delays;

    public static void startOfCombat() {
        delays.clear();
    }

    public static void onCardPlayed(AbstractCard card) {
        if (card.type == AbstractCard.CardType.SKILL) {
            for (Delay d : delays) {
                d.timer -= 1;
                if (d.timer == 0) {
                    d.execute.run();
                }
            }
            delays.removeIf(q -> q.timer == 0);
        }
    }

    public static void update() {

    }

    public static void render(SpriteBatch sb) {

    }
}
