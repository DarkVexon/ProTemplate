package code.cards;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import code.util.CustomVarInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import code.CharacterFile;
import code.util.CardArtRoller;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static code.ModFile.makeImagePath;
import static code.ModFile.modID;
import static code.util.Wiz.atb;
import static code.util.Wiz.att;

public abstract class AbstractEasyCard extends CustomCard {

    protected final CardStrings cardStrings;

    public int secondMagic;
    public int baseSecondMagic;
    public boolean upgradedSecondMagic;
    public boolean isSecondMagicModified;

    public int secondDamage;
    public int baseSecondDamage;
    public boolean upgradedSecondDamage;
    public boolean isSecondDamageModified;

    private boolean needsArtRefresh = false;

    final protected Map<String, CustomVarInfo.QuickDynamicVariable> customDynVars = new HashMap<>();
    final protected Map<String, CustomVarInfo> cardVariables = new HashMap<>();

    public AbstractEasyCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        this(cardID, cost, type, rarity, target, CharacterFile.Enums.TODO_COLOR);
    }

    public AbstractEasyCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(cardID, "", getCardTextureString(cardID.replace(modID + ":", ""), type),
                cost, "", type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(this.cardID);
        rawDescription = cardStrings.DESCRIPTION;
        name = originalName = cardStrings.NAME;
        initializeTitle();
        initializeDescription();

        if (textureImg.contains("ui/missing.png")) {
            if (CardLibrary.cards != null && !CardLibrary.cards.isEmpty()) {
                CardArtRoller.computeCard(this);
            } else
                needsArtRefresh = true;
        }
    }

    @Override
    protected Texture getPortraitImage() {
        if (textureImg.contains("ui/missing.png")) {
            return CardArtRoller.getPortraitTexture(this);
        } else {
            return super.getPortraitImage();
        }
    }

    public static String getCardTextureString(final String cardName, final AbstractCard.CardType cardType) {
        String textureString;

        switch (cardType) {
            case ATTACK:
            case POWER:
            case SKILL:
                textureString = makeImagePath("cards/" + cardName + ".png");
                break;
            default:
                textureString = makeImagePath("ui/missing.png");
                break;
        }

        FileHandle h = Gdx.files.internal(textureString);
        if (!h.exists()) {
            textureString = makeImagePath("ui/missing.png");
        }
        return textureString;
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

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        }
        for (CustomVarInfo var : cardVariables.values()) {
            if (var.type == CalculationType.DAMAGE) {
                var.value = var.base;

                int tmp = baseDamage;
                baseDamage = var.calculation.apply(null, var.base);

                super.applyPowers();

                var.value = damage;
                baseDamage = tmp;
            } else {
                var.value = var.calculation.apply(null, var.base);
            }
        }
        super.applyPowers();
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

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        }
        for (CustomVarInfo var : cardVariables.values()) {
            if (var.type == CalculationType.DAMAGE) {
                var.value = var.base;

                int tmp = baseDamage;
                baseDamage = var.calculation.apply(mo, var.base);

                super.calculateCardDamage(mo);

                var.value = damage;
                baseDamage = tmp;
            } else {
                var.value = var.calculation.apply(mo, var.base);
            }
        }
        super.calculateCardDamage(mo);
    }

    @Override
    protected void applyPowersToBlock() {
        for (CustomVarInfo var : cardVariables.values()) {
            if (var.type == CalculationType.BLOCK) {
                var.value = var.base;

                int tmp = baseBlock;
                baseBlock = var.calculation.apply(null, var.base);

                super.applyPowersToBlock();

                var.value = block;
                baseBlock = tmp;
            }
        }
        super.applyPowersToBlock();
    }

    public void resetAttributes() {
        super.resetAttributes();
        secondMagic = baseSecondMagic;
        isSecondMagicModified = false;
        secondDamage = baseSecondDamage;
        isSecondDamageModified = false;
        for (CustomVarInfo var : cardVariables.values()) {
            var.value = var.base;
        }
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedSecondMagic) {
            secondMagic = baseSecondMagic;
            isSecondMagicModified = true;
        }
        if (upgradedSecondDamage) {
            secondDamage = baseSecondDamage;
            isSecondDamageModified = true;
        }
    }

    protected void upgradeSecondMagic(int amount) {
        baseSecondMagic += amount;
        secondMagic = baseSecondMagic;
        upgradedSecondMagic = true;
    }

    protected void upgradeSecondDamage(int amount) {
        baseSecondDamage += amount;
        secondDamage = baseSecondDamage;
        upgradedSecondDamage = true;
    }

    protected void uDesc() {
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upp();
        }
    }

    public abstract void upp();

    public void update() {
        super.update();
        if (needsArtRefresh) {
            CardArtRoller.computeCard(this);
        }
    }

    // These shortcuts are specifically for cards. All other shortcuts that aren't specifically for cards can go in Wiz.
    protected void dmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    protected void dmgTop(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    protected void allDmg(AbstractGameAction.AttackEffect fx) {
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    protected void allDmgTop(AbstractGameAction.AttackEffect fx) {
        att(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    protected void altDmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, secondDamage, damageTypeForTurn), fx));
    }

    protected void blck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    public String cardArtCopy() {
        return null;
    }

    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return null;
    }

    protected void upMagic(int x) {
        upgradeMagicNumber(x);
    }

    protected void upSecondMagic(int x) {
        upgradeSecondMagic(x);
    }

    protected void upSecondDamage(int x) {
        upgradeSecondDamage(x);
    }

    // Custom Var gubbins
    protected final void setCustomVar(String key, int base, CalculationType type) {
        if (!customDynVars.containsKey(key)) {
            CustomVarInfo.QuickDynamicVariable var = new CustomVarInfo.QuickDynamicVariable(key);
            customDynVars.put(key, var);
            BaseMod.addDynamicVariable(var);
        }
        cardVariables.put(key, new CustomVarInfo(base, type));
    }

    protected final void setCustomVar(String key, int base) {
        setCustomVar(key, base, CalculationType.NONE);
    }

    protected void setCustomVarCalculation(String key, BiFunction<AbstractMonster, Integer, Integer> calculation) {
        CustomVarInfo var = cardVariables.get(key);
        if (var != null) var.calculation = calculation;
    }

    public CustomVarInfo getCustomVar(String key) {
        return cardVariables.get(key);
    }

    public int customVarBase(String key) {
        CustomVarInfo var = cardVariables.get(key);
        if (var == null) return -1;
        return var.base;
    }
    public int customVar(String key) {
        CustomVarInfo var = cardVariables.get(key);
        if (var == null) return -1;
        return var.value;
    }
    public boolean isCustomVarModified(String key) {
        CustomVarInfo var = cardVariables.get(key);
        if (var == null) return false;
        return var.isModified();
    }
    public boolean customVarUpgraded(String key) {
        CustomVarInfo var = cardVariables.get(key);
        if (var == null) return false;
        return var.isUpgraded();
    }

    public void upgradeCustomVar(String key, int upgradeAmt) {
        CustomVarInfo var = cardVariables.get(key);
        if (var != null) var.upgrade(upgradeAmt);
    }

    public enum CalculationType {
        DAMAGE,
        BLOCK,
        NONE
    }
}
