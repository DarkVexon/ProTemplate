package theTodo.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theTodo.TheTodo;

import java.util.ArrayList;

import static theTodo.TodoMod.makeID;
import static theTodo.util.TextureLoader.getCardTextureString;
import static theTodo.util.Wiz.*;

public abstract class AbstractTodoCard extends CustomCard {

    protected final CardStrings cardStrings;

    public int silly;
    public int baseSilly;
    public boolean upgradedSilly;
    public boolean isSillyModified;

    public int secondDamage;
    public int baseSecondDamage;
    public boolean upgradedSecondDamage;
    public boolean isSecondDamageModified;

    public AbstractTodoCard(final String cardname, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        this(cardname, cost, type, rarity, target, TheTodo.Enums.TODO_COLOR);
    }

    public AbstractTodoCard(final String cardname, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(makeID(cardname), "ERROR", getCardTextureString(cardname, type),
                cost, "ERROR", type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(cardID);
        rawDescription = cardStrings.DESCRIPTION;
        originalName = cardStrings.NAME;
        name = originalName;
        initializeTitle();
        initializeDescription();
    }

    @Override
    public void applyPowers() {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.applyPowers();

            secondDamage = damage;
            baseDamage = tmp;

            super.applyPowers();

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.calculateCardDamage(mo);

            secondDamage = damage;
            baseDamage = tmp;

            super.calculateCardDamage(mo);

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.calculateCardDamage(mo);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upp();
        }
    }

    public abstract void upp();

    protected void dmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    protected void allDmg(AbstractGameAction.AttackEffect fx) {
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    protected void blck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    public void makeInHand(AbstractCard c, int i) {
        atb(new MakeTempCardInHandAction(c, i));
    }

    protected void makeInHand(AbstractCard c) {
        makeInHand(c, 1);
    }

    protected void shuffleIn(AbstractCard c, int i) {
        atb(new MakeTempCardInDrawPileAction(c, i, true, true));
    }

    protected void shuffleIn(AbstractCard c) {
        shuffleIn(c, 1);
    }

    protected void topDeck(AbstractCard c, int i) {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, i, false, true));
    }

    protected void topDeck(AbstractCard c) {
        topDeck(c, 1);
    }

    protected void applyToEnemy(AbstractMonster m, AbstractPower po) {
        atb(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    protected void applyToEnemyTop(AbstractMonster m, AbstractPower po) {
        att(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    protected void applyToSelf(AbstractPower po) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    protected void applyToSelfTop(AbstractPower po) {
        att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    protected WeakPower autoWeak(AbstractMonster m, int i) {
        return new WeakPower(m, i, false);
    }

    protected VulnerablePower autoVuln(AbstractMonster m, int i) {
        return new VulnerablePower(m, i, false);
    }

    public void resetAttributes() {
        super.resetAttributes();
        silly = baseSilly;
        isSillyModified = false;
        secondDamage = baseSecondDamage;
        isSecondDamageModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedSilly) {
            silly = baseSilly;
            isSillyModified = true;
        }
        if (upgradedSecondDamage) {
            secondDamage = baseSecondDamage;
            isSecondDamageModified = true;
        }
    }

    void upgradeSilly(int amount) {
        baseSilly += amount;
        silly = baseSilly;
        upgradedSilly = true;
    }

    void upgradeSecondDamage(int amount) {
        baseSecondDamage += amount;
        secondDamage = baseSecondDamage;
        upgradedSecondDamage = true;
    }
}
