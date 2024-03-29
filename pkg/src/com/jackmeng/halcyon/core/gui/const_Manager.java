package com.jackmeng.halcyon.core.gui;

public abstract interface const_Manager
{

  int GUI_MOREAPPS_WIDTH = 370;
  int GUI_MOREAPPS_HEIGHT = 410;

  int FRAME_MIN_WIDTH = 520;
  int FRAME_MIN_HEIGHT = 620;
  int FRAME_TITLEBAR_HEIGHT = 23;

  int DGUI_APPS_WIDTH = 30;
  int DGUI_APPS_APPS_ICON_VGAP = 6;
  int DGUI_APPS_APPS_ICON_HGAP = 4;
  int DGUI_APPS_ICON_BTN_WIDTH = 18;
  int DGUI_APPS_FILELIST_LEAF_ICON_W_H = 16;
  int DGUI_APPS_FILELIST_TABBUTTONS_ICON_W_H = 16;
  int DGUI_APPS_FILELIST_WIDTH = FRAME_MIN_WIDTH - DGUI_APPS_WIDTH;
  int DGUI_APPS_FILELIST_HEIGHT = (FRAME_MIN_HEIGHT / 2) + 50;

  int DGUI_TOP_CTRL_BUTTONS_DEF_WXH = 30;

  int DGUI_TOP = FRAME_MIN_HEIGHT - DGUI_APPS_FILELIST_HEIGHT;

  float PROGRAM_DEFAULT_FONT_SIZE = 14F;

  short GUI_MAX_RROUND_FACTOR = 15;

  boolean DEBUG_GRAPHICS = false;
}
