package com.test.learnkotlin.utils

import com.test.learnkotlin.utils.ReflexUtil
import java.lang.ClassCastException
import java.lang.reflect.ParameterizedType

object ReflexUtil {
    fun <T> getTypeInstance(origin: Any, clazz: Class<*>): T? {
        return getTypeInstance(origin.javaClass, clazz)
    }

    fun <T> getTypeInstance(origin: Class<*>, clazz: Class<*>): T? {
        val targetClass = getTargetClass(origin, clazz)
        if (targetClass != null) {
            try {
                return targetClass.newInstance() as T
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: ClassCastException) {
                e.printStackTrace()
            }
        }
        return null
    }

    fun <T> getTargetClass(origin: Any, clazz: Class<T>): Class<T>? {
        return getTargetClass(origin.javaClass, clazz)
    }

    fun <T> getTargetClass(origin: Class<*>, clazz: Class<T>): Class<T>? {
        try {
            var target: Class<T>? = null
            val genericSuperclass = origin.genericSuperclass
            if (genericSuperclass is ParameterizedType) {
                val actualTypeArguments = genericSuperclass.actualTypeArguments
                for (type in actualTypeArguments) {
                    if (type is Class<*> && clazz.isAssignableFrom(type as Class<T>)) {
                        target = type
                        break
                    }
                }
            }
            return target
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
        return null
    }
}