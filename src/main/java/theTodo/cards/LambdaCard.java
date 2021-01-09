package theTodo.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static theTodo.TodoMod.makeID;

public abstract class LambdaCard extends AbstractTodoCard {
    private String storeName;
    private String storeDescription;
    private int storeCost;
    private CardType storeType;
    private CardRarity storeRarity;
    private CardTarget storeTarget;
    private int storeDamage;
    private int storeBlock;
    private int storeMagic;

    public LambdaCard(String name, String description, int cost, CardType type, CardRarity rarity, CardTarget target, int damage, int block, int magic) {
        super(makeID(name), cost, type, rarity, target);
        this.storeName = name;
        this.storeDescription = description;
        this.storeCost = cost;
        this.storeType = type;
        this.storeRarity = rarity;
        this.storeTarget = target;
        this.storeDamage = damage;
        this.storeBlock = block;
        this.storeMagic = magic;
        this.name = this.originalName = name;
        this.rawDescription = description;
        initializeTitle();
        initializeDescription();
        baseDamage = damage;
        baseBlock = block;
        baseMagicNumber = magicNumber = magic;
    }

    public LambdaCard(String name, String description, int cost, CardType type, CardRarity rarity, CardTarget target) {
        this(name, description, cost, type, rarity, target, -1, -1, -1);
    }

    @Override
    public AbstractCard makeCopy() {
        try {
            Field outer = this.getClass().getDeclaredField("this$0");
            outer.setAccessible(true);
            Class q = outer.get(this).getClass();
            for (Constructor c : this.getClass().getDeclaredConstructors()) {
                System.out.println(c.toString());
            }
            Constructor c = this.getClass().getDeclaredConstructor(q, String.class, String.class, int.class, CardType.class, CardRarity.class, CardTarget.class, int.class, int.class, int.class);
            c.setAccessible(true);
            return ((LambdaCard) c.newInstance(q.cast(null), this.storeName, this.storeDescription, this.storeCost, this.storeType, this.storeRarity, this.storeTarget, this.storeDamage, this.storeBlock, this.storeMagic));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
