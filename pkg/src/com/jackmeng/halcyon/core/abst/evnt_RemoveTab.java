package com.jackmeng.halcyon.core.abst;

@FunctionalInterface public abstract interface evnt_RemoveTab
{

  /**
   * Notifies of a removed tab.
   *
   * No arguments are provided.
   */
  void removedTab();
  /*-------------------------------------------------------- /
  / This is a pretty lame way of getting an event to be run. /
  / But oh well.                                             /
  /---------------------------------------------------------*/
}
