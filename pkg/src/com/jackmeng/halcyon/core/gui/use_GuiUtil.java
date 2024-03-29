package com.jackmeng.halcyon.core.gui;

import java.util.*;
import java.util.function.Consumer;

import javax.swing.Icon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.jackmeng.halcyon.core.util.use_Commons;
import com.jackmeng.halcyon.core.util.use_Struct;
import com.jackmeng.halcyon.core.util.use_Struct.struct_Pair;

import java.awt.event.*;

import java.awt.*;

public final class use_GuiUtil
{
  private use_GuiUtil()
  {
  }

  public static java.util.List< Component > listComponents_OfContainer(Container c)
  {
    Component[] comps = c.getComponents();
    java.util.List< Component > compList = new ArrayList<>();
    for (Component comp : comps)
    {
      compList.add(comp);
      if (comp instanceof Container)
        compList.addAll(listComponents_OfContainer((Container) comp));
    }
    return compList;
  }

  public static RenderingHints defaultRenderingHints()
  {
    Map< RenderingHints.Key, Object > e = new HashMap<>();
    e.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    e.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
    return new RenderingHints(e);
  }

  public static struct_Pair< Integer, Integer > center_OfScreen()
  {
    return new struct_Pair<>(Toolkit.getDefaultToolkit().getScreenSize().width / 2,
        Toolkit.getDefaultToolkit().getScreenSize().height / 2);
  }

  public static JPopupMenu make_PopupMenu(String label,
      java.util.List< use_Struct.struct_Pair< Object, Consumer< ActionEvent > > > contents)
  {
    JPopupMenu e = new JPopupMenu(label);
    for (use_Struct.struct_Pair< Object, Consumer< ActionEvent > > r : contents)
    {
      JMenuItem item = r.first instanceof String ? new JMenuItem((String) r.first)
          : r.first instanceof Icon ? new JMenuItem((Icon) r.first) : new JMenuItem(use_Commons.rndstr(10, 33, 122));
      item.addActionListener(r.second::accept);
      e.add(item);
    }
    return e;
  }

  public static Runnable package_text_frame(String htmlText, String title, Image optionalIcon, int width, int height)
  {
    JFrame f = new JFrame(title);
    f.setIconImage(optionalIcon);
    f.setPreferredSize(new Dimension(width, height));
    f.getContentPane().setLayout(new BorderLayout());

    JEditorPane j = new JEditorPane("text/html", htmlText);
    j.setPreferredSize(new Dimension(width, height));
    j.setEditable(false);

    f.getContentPane().add(j, BorderLayout.CENTER);

    return () -> {
      f.pack();
      f.setVisible(true);
      f.setLocationRelativeTo(null);
    };
  }

  public static Dimension screen_space()
  {
    return Toolkit.getDefaultToolkit().getScreenSize();
  }
}
