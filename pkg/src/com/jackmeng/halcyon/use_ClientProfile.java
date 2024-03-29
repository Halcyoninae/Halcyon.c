package com.jackmeng.halcyon;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;
import java.awt.image.BufferedImage;

import java.awt.Color;

import com.jackmeng.halcyon.core.abst.impl_ConsoleDebug;
import com.jackmeng.halcyon.core.abst.impl_Identifiable;
import com.jackmeng.halcyon.core.gui.const_ResourceManager;
import com.jackmeng.halcyon.core.util.pstream;
import com.jackmeng.halcyon.core.util.use_Chronos;
import com.jackmeng.halcyon.core.util.use_Color;
import com.jackmeng.halcyon.core.util.use_FSys;
import com.jackmeng.halcyon.core.util.use_Image;
import com.jackmeng.halcyon.core.util.use_ResourceFetcher;
import com.jackmeng.halcyon.core.util.use_Task;
import com.jackmeng.stl.stl_Listener;
import com.jackmeng.stl.stl_ListenerPool;
import com.jackmeng.stl.stl_Struct;

public class use_ClientProfile
    implements
    impl_Identifiable,
    Runnable,
    Serializable,
    impl_ConsoleDebug
{
  public static final int AVATAR_WIDTH_N_HEIGHT = 128;

  private boolean locked = false;
  private final String name;
  private final String saveLocation;
  private Color preferredColor;
  private transient stl_ListenerPool< stl_Struct.struct_Pair< Float, Float > > listeners;
  private transient BufferedImage userAvatar;
  private float totalTimeUsed_Hours, currentTimeUsed_Minutes; // the LONG_MAX value should be enough I hope, its like
                                                              // 100000000 so centuries

  /**
   * @deprecated New format using internalized java serialization instead of
   *             reading
   *             a special format. Instead use {@link #acquire(String)}
   * @param location
   *          Location to save to
   * @return The object read
   */
  @Deprecated(forRemoval = true) public static use_ClientProfile load_instance(String location)
  {
    File t = new File(location);
    if (!t.exists() || !t.isFile())
      use_FSys.writeToFile_O(System.getProperty("user.name") + "\n0", t);
    String[] s = new String[2];
    use_FSys.interpolated_ReadFromFile(new File(location), (x, y) -> s[x.intValue()] = y);
    String[] tr = s;
    if (s.length != 2)
      tr = new String[] { System.getProperty("user.name"), "0" };
    pstream.log.info("LOADED_CLIENT_PROFILE: " + Arrays.toString(tr));
    try
    {
      Float.parseFloat(tr[1]);
    } catch (NumberFormatException | NullPointerException e)
    {
      pstream.log.warn("Failed to load...\nRequirements:\nTime (lfloat): Found: " + tr[1] + " Requires: (lfloat)");
    }
    return null;
    /*------------------------------------------------------------------------------- /
    / return new use_ClientProfile(false, location,                                   /
    /     tr[0] == null || tr[0].isBlank() ? System.getProperty("user.name") : tr[0], /
    /     Float.parseFloat(tr[1] == null ? "0" : tr[1]), Integer.parseInt(tr[2]));    /
    /--------------------------------------------------------------------------------*/
  }

  public static use_ClientProfile acquire(String locale)
  {
    /* Failsafes here */
    File r = new File(locale);
    AtomicReference< use_ClientProfile > obj = new AtomicReference<>();
    if (!r.exists() || !r.isFile())
    {
      obj.set(new use_ClientProfile(false, locale, System.getProperty("user.name"), 0F, use_Color.rndColor(),
          use_ResourceFetcher.fetcher.getFromAsImage(const_ResourceManager.GUI_PROGRAM_LOGO)));
      return obj.get();
    }
    use_FSys.deserialize_OBJ(locale, use_ClientProfile.class, e -> {
      pstream.log.warn("Failed to initialize the user profile. Reinitializing a DEFAULT one.");
      obj.set(default_p(locale));
    }, obj::set);
    return obj.get();
  }

  public static use_ClientProfile default_p(String locale)
  {
    return new use_ClientProfile(false, locale, System.getProperty("user.name"), 0F, use_Color.rndColor(),
        use_ResourceFetcher.fetcher.getFromAsImage(const_ResourceManager.GUI_PROGRAM_LOGO));
  }

  private use_ClientProfile(boolean locked, String saveLocation, String name, float totalTimeUsed, Color preferredColor,
      BufferedImage userPfp)
  {
    listeners = new stl_ListenerPool<>("client_profile" + hashCode());
    this.saveLocation = saveLocation;
    this.totalTimeUsed_Hours = totalTimeUsed;
    this.name = name;
    this.preferredColor = preferredColor;
    this.userAvatar = use_Image.compat_Img(userPfp);
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      pstream.log.info("CLIENT_PROFILE_STATS:\nTime_spent(minutes_total): " + this.totalTimeUsed_Hours * 60);
      use_FSys.serialize_OBJ(saveLocation, this,
          e -> pstream.log.warn("Failed to serialize user profile!"));
    }));
    if (userAvatar.getWidth() != 128 || userAvatar.getHeight() != 128)
    {
      use_Task.run_submit(() -> {
        userAvatar = use_Image
            .image_to_bi(use_Image.subimage_resizing(AVATAR_WIDTH_N_HEIGHT, AVATAR_WIDTH_N_HEIGHT, userPfp));
      });
    }
  }

  public void add_listener(stl_Listener< stl_Struct.struct_Pair< Float, Float > > e)
  {
    listeners.add(e);
  }

  public void rmf_listener(stl_Listener< stl_Struct.struct_Pair< Float, Float > > e)
  {
    listeners.rmf(e);
  }

  public synchronized void sync()
  {
    use_FSys.serialize_OBJ(saveLocation, this,
        e -> pstream.log.warn("Failed to serialize user profile!"));
  }

  public synchronized void addCurrentTime_Minutes_ToHoursVar(float time)
  {
    time = Math.abs(time);
    totalTimeUsed_Hours += time / 60;
  }

  public float exposeCurrentMinutes()
  {
    return currentTimeUsed_Minutes;
  }

  public String getUser_Name()
  {
    return name;
  }

  public float exposeTotalHours()
  {
    return totalTimeUsed_Hours;
  }

  public BufferedImage exposeUserPfp()
  {
    return userAvatar;
  }

  private synchronized void finalizeTime()
  {
    totalTimeUsed_Hours += currentTimeUsed_Minutes / 60;
    currentTimeUsed_Minutes = 0;
  }

  public String stringify()
  {
    finalizeTime();
    return name + "\n" + totalTimeUsed_Hours + "\n";
  }

  @Override public synchronized void run()
  {
    if (!locked)
    {
      const_Core.schedule_secondary_task(new TimerTask() {
        private float s = System.currentTimeMillis();

        @Override public void run()
        {
          float t = System.currentTimeMillis();
          currentTimeUsed_Minutes += use_Chronos.millisToMinutes(t - s);
          s = t;
          if(listeners != null)
            listeners.dispatch(new stl_Struct.struct_Pair<>(totalTimeUsed_Hours, currentTimeUsed_Minutes));
        }
      }, 1000L, 3000L);
    }
  }
}
