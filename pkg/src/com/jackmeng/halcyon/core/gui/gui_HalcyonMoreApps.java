package com.jackmeng.halcyon.core.gui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import static com.jackmeng.halcyon.const_Lang.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Optional;

import com.jackmeng.halcyon.Halcyon;
import com.jackmeng.halcyon.const_Core;
import com.jackmeng.halcyon.core.abst.evnt_WindowFocusAdapter;
import com.jackmeng.halcyon.core.abst.impl_App;
import com.jackmeng.halcyon.core.abst.impl_HalcyonRefreshable;
import com.jackmeng.halcyon.core.gui.gui_HalcyonFrame.TitledFrame.ComponentResizer;
import com.jackmeng.halcyon.core.ploogin.impl_Ploogin;
import com.jackmeng.halcyon.core.util.const_GeneralStatus;
import com.jackmeng.halcyon.core.util.use_ResourceFetcher;
import com.jackmeng.halcyon.core.util.use_Struct.struct_Pair;

public class gui_HalcyonMoreApps
    implements
    Runnable,
    impl_HalcyonRefreshable< struct_Pair< Optional< String >, Optional< impl_App > > >
{

  private final JFrame frame;
  private final JPanel panel;
  private int pX, pY;

  public gui_HalcyonMoreApps()
  {
    frame = new JFrame(_lang(LANG_MOREAPPS_TITLE));
    frame.setIconImage(
        use_ResourceFetcher.fetcher.getFromAsImageIcon(const_ResourceManager.GUI_PROGRAM_LOGO).getImage());
    frame.setPreferredSize(new Dimension(const_Manager.GUI_MOREAPPS_WIDTH, const_Manager.GUI_MOREAPPS_HEIGHT));
    frame.setUndecorated(true);
    frame.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, const_ColorManager.DEFAULT_DARK_BG));
    frame.addWindowFocusListener(new evnt_WindowFocusAdapter() {
      @Override public void windowLostFocus(WindowEvent e)
      {
        frame.dispose();
      }
    });
    ComponentResizer cr = new ComponentResizer();
    cr.registerComponent(frame);
    frame.addMouseMotionListener(cr);
    /*---------------------------------------------------------------------------------------------- /
    / dont add two motion listeners to a single frame!! this causes the frame to glitch when resized /
    / (when registered to a component resizer)                                                       /
    /-----------------------------------------------------------------------------------------------*/
    const_Core.APPS_POOL.addRefreshable(this);

    panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.CENTER));

    JScrollPane jsp = new JScrollPane(panel);
    jsp.setBorder(BorderFactory.createEmptyBorder());
    jsp.addMouseListener(new MouseAdapter() {
      @Override public void mousePressed(MouseEvent me)
      {
        pX = me.getX();
        pY = me.getY();
      }

      @Override public void mouseDragged(MouseEvent me)
      {
        frame.setLocation(frame.getLocation().x + me.getX() - pX, frame.getLocation().y + me.getY() - pY);
      }
    });
    jsp.addMouseMotionListener(new MouseMotionAdapter() {
      @Override public void mouseDragged(MouseEvent me)
      {
        frame.setLocation(frame.getLocation().x + me.getX() - pX, frame.getLocation().y + me.getY() - pY);
      }
    });
    frame.getContentPane().add(jsp);
  }

  private JPanel get_PlooginEntry(impl_App x)
  {
    JPanel main = new JPanel();
    main.setBorder(BorderFactory.createLineBorder(const_ColorManager.DEFAULT_DARK_BG));
    main.setLayout(new FlowLayout(FlowLayout.CENTER));
    JButton action = new JButton();
    action.addActionListener(e -> x.run());
    action.setRolloverEnabled(false);
    action.setContentAreaFilled(false);
    if (x.icon() == null)
      action.setText(x.id());
    else
      action.setIcon(x.icon());

    if (x instanceof impl_Ploogin x2)
      action.setToolTipText("<html><strong>Ploogin: " + x2.id() + "</strong><br>Author: " + x2.author()
          + "<br>Description: " + x2.description() + "</html>");
    else
      action.setToolTipText("<html><strong>App: " + x.id() + "</strong><br>hash: " + x.hashCode() + "</html>");
    main.add(action);
    return main;
  }

  @Override public void run()
  {
    frame.pack();
    frame.setLocationRelativeTo(Halcyon.main.expose());
    frame.setVisible(true);
  }

  @Override public void refresh(const_GeneralStatus type,
      struct_Pair< Optional< String >, Optional< impl_App > > refreshed)
  {
    refreshed.second.ifPresent(type == const_GeneralStatus.ADDITION ? x -> panel.add(get_PlooginEntry(x))
        : x -> panel.remove(get_PlooginEntry(x)));

  }

  @Override public void dry_refresh()
  {
    // DO NOTHING
  }
}
