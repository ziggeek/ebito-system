package com.ebito.exceptionhandler.util;

public class Utils {

    /**
     * Метод проверяет является ли объект экземпляром класса.
     * Метод отличается от instanceof тем, что
     * безопасен при отсутствии класса в Classpath,
     * т.к. првоерка проводится по имени класса.
     */
    public static boolean isInstance(Object instance, String className) {
        if (instance != null){
            Class clazz = instance.getClass();

            while (clazz != null) {
                if (clazz.getName().equals(className)) {
                    return true;
                }
                clazz = clazz.getSuperclass();
            }
        }
        return false;
    }
}
