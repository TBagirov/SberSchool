package org.bagirov.model.task7;

import java.lang.reflect.Method;

public final class BeanUtils {

    private BeanUtils() {}

    /**
     * Scans object "from" for all getters. If object "to"
     * contains correspondent setter, it will invoke it
     * to set property value for "to" which equals to the property
     * of "from".
     * <p/>
     * The type in setter should be compatible to the value returned
     * by getter (if not, no invocation performed).
     * Compatible means that parameter type in setter should
     * be the same or be superclass of the return type of the getter.
     * <p/>
     * The method takes care only about public methods.
     *
     * @param to   Object which properties will be set.
     * @param from Object which properties will be used to get values.
     */
    public static void assign(Object to, Object from) {
        if (to == null || from == null) {
            throw new IllegalArgumentException("Objects 'to' and 'from' must not be null");
        }

        // Get all methods from the "from" object
        Method[] fromMethods = from.getClass().getMethods();
        Method[] toMethods = to.getClass().getMethods();

        for (Method fromMethod : fromMethods) {
            if (isGetter(fromMethod)) {
                String propertyName = getPropertyName(fromMethod.getName());

                for (Method toMethod : toMethods) {
                    if (isSetter(toMethod) && isCompatible(propertyName, fromMethod, toMethod)) {
                        try {
                            Object value = fromMethod.invoke(from);
                            toMethod.invoke(to, value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
    }

    private static boolean isGetter(Method method) {
        return method.getName().startsWith("get")
                && method.getParameterCount() == 0
                && !method.getReturnType().equals(void.class);
    }

    private static boolean isSetter(Method method) {
        return method.getName().startsWith("set")
                && method.getParameterCount() == 1
                && method.getReturnType().equals(void.class);
    }

    private static String getPropertyName(String methodName) {
        return methodName.substring(3);
    }

    private static boolean isCompatible(String propertyName, Method getter, Method setter) {
        String setterPropertyName = getPropertyName(setter.getName());
        return setterPropertyName.equals(propertyName)
                && setter.getParameterTypes()[0].isAssignableFrom(getter.getReturnType());
    }
}
