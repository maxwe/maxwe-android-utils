package org.maxwe.android.views.treelist;

import org.maxwe.eventbus.Bus;
import org.maxwe.eventbus.impl.SimpleBus;
import org.maxwe.eventbus.scheduler.AndroidPlatform;

/**
 * Description: BusProvider
 * Author: danhantao
 * Update: danhantao(2014-10-26 12:54)
 * Email: danhantao@yeah.net
 * <p/>
 * Maintains a singleton instance for obtaining the bus. Ideally this would be replaced with a more
 * efficient means such as through injection directly into interested classes.
 */
public final class BusProvider {
  static {
    AndroidPlatform.register();
  }

  private static final Bus BUS = new SimpleBus();

  private BusProvider() {
    // No instances.
  }

  public static Bus get() {
    return BUS;
  }
}
