package theTodo.cards;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theTodo.TheTodo;

import java.util.ArrayList;

public abstract class AbstractMultiPreviewCard extends AbstractTodoCard {

    private float rotationTimer = getRotationTimeNeeded();
    private int previewIndex;
    protected ArrayList<AbstractCard> cardToPreview = new ArrayList<>();

    public AbstractMultiPreviewCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(cardID, cost, type, rarity, target, color);
    }

    public AbstractMultiPreviewCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(cardID, cost, type, rarity, target, TheTodo.Enums.TODO_COLOR);
    }

    protected float getRotationTimeNeeded() {
        return 2f;
    }

    @Override
    public void update() {
        super.update();
        if (hb.hovered) {
            if (rotationTimer <= 0F) {
                rotationTimer = getRotationTimeNeeded();
                if (cardToPreview.size() == 0) {
                    cardsToPreview = null;
                } else {
                    cardsToPreview = cardToPreview.get(previewIndex);
                }
                if (previewIndex == cardToPreview.size() - 1) {
                    previewIndex = 0;
                } else {
                    previewIndex++;
                }
            } else {
                rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    @Override
    public void unhover() {
        super.unhover();
        cardsToPreview = null;
    }

    protected void upgradeCardToPreview() {
        for (AbstractCard q : cardToPreview) {
            q.upgrade();
        }
    }
}
