package com.jackmeng.halcyon.core.gui;

import com.jackmeng.halcyon.use_HalcyonCore;

public abstract interface const_ResourceManager
{
  String RESOURCES = "assets" + use_HalcyonCore.getFileSeparator();

  /*------------------------------------------------------------------- /
  / The following block of constants define resources for the dependent /
  / GUI element AppView                                                 /
  /--------------------------------------------------------------------*/
  String APP_ICONS = RESOURCES + "appicon" + use_HalcyonCore.getFileSeparator();
  String DGUI_APPS_ADD_PLAYLIST = APP_ICONS + "player_add.png";
  String DGUI_APPS_AUDIO_CTRLER = APP_ICONS + "audio_ctrler.png";
  String DGUI_APPS_MINI_PLAYER = APP_ICONS + "player_small.png";
  String DGUI_APPS_PLAYER_SETTINGS = APP_ICONS + "player_settings.png";
  String DGUI_APPS_PLAYER_LISTVIEW = APP_ICONS + "player_listview.png";
  String DGUI_APPS_PLAYER_REFRESH = APP_ICONS + "player_refresh.png";
  String DGUI_APPS_PLAYER_INFO = APP_ICONS + "player_information.png";
  String DGUI_APPS_PLAYER_MOREAPPS = APP_ICONS + "player_moreapps.png";
  String DGUI_APPS_PLAYER_LIKED_MUSIC = APP_ICONS + "player_liked.png";

  String FILELIST_ICONS = RESOURCES + "fileview" + use_HalcyonCore.getFileSeparator();
  String DGUI_FILELIST_LEAF = FILELIST_ICONS + "leaf.png";
  String DGUI_FILELIST_LEAF_CLOSED = FILELIST_ICONS + "leaf_closed.png";
  String DGUI_FILELIST_LEAF_OPEN = FILELIST_ICONS + "leaf_opened.png";
  String DGUI_FILELIST_LEAF_2 = FILELIST_ICONS + "leaf_2.png";

  /*--------------------------------------------------------------------------------------- /
  / The following block of constants define resources for the TOP PANE BUTTON CONTROL ICONS /
  /----------------------------------------------------------------------------------------*/
  String CONTROL_BUTTONS = RESOURCES + "buttons" + use_HalcyonCore.getFileSeparator();
  String CTRL_BUTTON_PREVIOUS_TRACK = CONTROL_BUTTONS + "previoustrack.png";
  String CTRL_BUTTON_PLAY_TRACK = CONTROL_BUTTONS + "playtrack.png";
  String CTRL_BUTTON_SHUFFLE_PLAYSTYLE = CONTROL_BUTTONS + "shuffleplaystyle.png";
  String CTRL_BUTTON_PAUSE_TRACK = CONTROL_BUTTONS + "pausetrack.png";
  String CTRL_BUTTON_UNLIKE_TRACK = CONTROL_BUTTONS + "unliketrack.png";
  String CTRL_BUTTON_LOOP_PLAYSTYLE = CONTROL_BUTTONS + "loopplaystyle.png";
  String CTRL_BUTTON_TRACK_INFORMATION = CONTROL_BUTTONS + "trackinformation.png";
  String CTRL_BUTTON_NEXT_TRACK = CONTROL_BUTTONS + "nexttrack.png";
  String CTRL_BUTTON_LIKE_TRACK = CONTROL_BUTTONS + "liketrack.png";

  /*--------------------------------------------------------------------- /
  / The following block of constants define resources for the APP itself. /
  / For example the logo                                                  /
  /----------------------------------------------------------------------*/
  String APP = RESOURCES + "app" + use_HalcyonCore.getFileSeparator();
  String GUI_PROGRAM_LOGO = APP + "Halcyon_Logo.png";
  String GUI_SPINNER_PULSE = APP + "spinner_pulse.gif";
  String GUI_DISK_ICON = APP + "disk.png";

  /*----------------------------------------------------------------------------- /
  / The following block of constants define HLL resources. HLL stands for Halcyon /
  / LINK LIBRARY and can contain any kind of content usables.                     /
  /------------------------------------------------------------------------------*/
  String HLL = RESOURCES + "hlls" + use_HalcyonCore.getFileSeparator();
  String HLL_HALCYONTOP_TOOLTIP = HLL + "dguiHalcyonTop_toolTip.hll";

  /*----------------------------------------------------------------------- /
  / The follow block of constants define SCENE IMAGES, which are not stable /
  /------------------------------------------------------------------------*/
  String SCENES = RESOURCES + "scenes" + use_HalcyonCore.getFileSeparator();
}
