package theTodo.util;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Random;

public class Wiz {
    //The wonderful Wizard of Oz allows access to most easy compilations of data, or functions.

    public static ArrayList<AbstractMonster> monsterList() {
        ArrayList<AbstractMonster> monsters = new ArrayList<>(AbstractDungeon.getMonsters().monsters);
        monsters.removeIf(m -> m.isDead || m.isDying);
        return monsters;
    }

    public static <T> T getRandomItem(ArrayList<T> list) {
        return list.isEmpty() ? null : list.get(AbstractDungeon.cardRandomRng.random(list.size()-1));
    }

    public static void atb(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static void att(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }
}
