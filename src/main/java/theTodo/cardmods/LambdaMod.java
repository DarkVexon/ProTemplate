package theTodo.cardmods;

import basemod.abstracts.AbstractCardModifier;
import theTodo.cards.democards.InlineCardModDemo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class LambdaMod extends AbstractCardModifier {

    public LambdaMod() {

    }

    @Override
    public AbstractCardModifier makeCopy() {
        try {
            Field outer = this.getClass().getDeclaredField("this$0");
            outer.setAccessible(true);
            Class q = outer.get(this).getClass();
            Constructor c = this.getClass().getDeclaredConstructor(q);
            c.setAccessible(true);
            return ((LambdaMod) c.newInstance(q.cast(null)));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
