package net.wohlfart.neutron.scene.util;

import com.badlogic.gdx.utils.ObjectIntMap;


public class EnumWeights {
    
    final ObjectIntMap<Class<?>> weights;

    public EnumWeights(Class<?>... clazzes) {  
        weights = new ObjectIntMap<Class<?>>();   // not found value 0
        
        int lastDigits = 1;
        for (int i = clazzes.length - 1; i >= 0 ; i--) {
            @SuppressWarnings("unchecked")
            Class<Enum<?>> clazz = (Class<Enum<?>>) clazzes[i];
            int elems = clazz.getEnumConstants().length;
            weights.put(clazz, lastDigits);
            lastDigits = lastDigits * elems;    
        }
    }

    public int getWeightFor(Enum<?>... inums) {
        int result = 0;
        for (Enum<?> inum : inums) {
        	assert (weights.containsKey(inum.getDeclaringClass()));
            result += weights.get(inum.getDeclaringClass(), 0) * inum.ordinal();
        }
        return result;
    }
    
}
