package com.jackmeng.halcyon.core.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import com.jackmeng.halcyon.use_HalcyonCore;

/**
 * Common and primitive manipulation and functionality
 * functions.
 *
 * @author Jack Meng
 */
public class use_Commons
{

  public static final use_Commons INTERNALS = new use_Commons();

  private use_Commons()
  {
  }

  public static String weak_delimiter(String str, String delimiter, int validLength)
  {
    return str != null ? str.length() > validLength ? str.substring(0, validLength) + delimiter
        : str.length() < validLength ? str + copies_Of(validLength, " ") : str : "";
  }

  public static Object pick(Object... e)
  {
    return e[const_Commons.RNG.nextInt(e.length)];
  }

  public static Object pick(const_Bias bias, Object... e)
  {
    return bias == const_Bias.UP_BIAS ? e[const_Commons.RNG.nextInt(e.length / 2) + e.length / 2]
        : e[const_Commons.RNG.nextInt(e.length / 2)];
  }

  public static String strong_delimiter(String str, String delimiter, int validLength)
  {
    return str != null ? str.length() > validLength ? str.substring(0, validLength) + delimiter : str : "";
  }

  public static String compress_str(String str)
  {
    Map< Character, String > table = use_Algos.huffman_table(str);
    StringBuilder sb = new StringBuilder();
    for (char x : str.toCharArray())
      sb.append(table.get(x));
    return sb.toString();
  }

  public static String copies_Of(int n, String s)
  {
    return String.valueOf(s).repeat(Math.max(0, n + 1));
  }

  /**
   * @param key
   * @param comparators
   * @return boolean
   */
  public static boolean ends_with(String key, String... comparators)
  {
    for (String r : comparators)
      if (r.endsWith(key))
        return true;
    return false;
  }

  public static String rndstr(int length, int left, int right) // length, ascii_min, ascii_max
  {
    StringBuilder sb = new StringBuilder();
    while (length-- > 0)
      sb.append((char) (left + (int) (use_HalcyonCore.rng.nextDouble() * (right - left + 1))));
    return sb.toString();
  }

  public static String expand_exception(Exception e)
  {
    StringBuilder sb = new StringBuilder("Exception Occurred: " + e.getMessage()).append("\nLocalized:")
        .append(e.getLocalizedMessage());
    for (StackTraceElement s : e.getStackTrace())
      sb.append("\tat ").append(s.getClassName()).append(".").append(s.getMethodName()).append("(")
          .append(s.getFileName()).append(":").append(s.getLineNumber()).append(")").append("\n");
    return sb.toString();
  }

  public static String normalize_string(String e)
  {
    return e.substring(0, 1).toUpperCase() + e.substring(1, e.length() - 1).toLowerCase();
  }

  public static boolean is_generic(Class< ? > c)
  {
    return c.getTypeParameters().length > 0;
  }

  public static boolean str_empty(String s)
  {
    return s == null || s.isBlank() || s.length() == 0;
  }

  public static Number round_off_bd(Number e, int amount)
  {
    return BigDecimal.valueOf(e.doubleValue()).setScale(amount, RoundingMode.HALF_UP).doubleValue();
  }

  public static < T > T new_Instance(Class< T > classObj, Class< ? >[] constructorSig, Object... args)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException
  {
    return (T) classObj.getConstructor(constructorSig).newInstance(args);
  }

  public static < T > T new_instance(Class< T > classObj, Object... args)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException
  {
    return new_Instance(classObj, null, args);
  }

  public final boolean is_generic(String str) throws ClassNotFoundException
  {
    return Class.forName(str).getTypeParameters().length > 0;
  }

  public final Class< ? > find_class(String fullName) // for example com.jackmeng.core.util.use_Commons
  {
    try
    {
      return Class.forName(fullName);
    } catch (ClassNotFoundException e)
    {
      return null;
    }
  }

  public final Class< ? > find_class(String className, String pkgName)
  {
    try
    {
      return Class.forName(pkgName + "." + className.substring(0, className.lastIndexOf('.')));
    } catch (ClassNotFoundException e)
    {
      return null;
    }
  }

  public final Set< Class< ? > > list_class(String pkgName)
  {
    return new BufferedReader(
        new InputStreamReader(Objects
            .requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream(pkgName.replaceAll("[.]", "/")))))
                .lines()
                .filter(x -> x.endsWith(".class")).map(x -> find_class(x, pkgName)).collect(Collectors.toSet());
  }
}
