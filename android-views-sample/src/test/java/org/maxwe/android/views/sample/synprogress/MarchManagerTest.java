package org.maxwe.android.views.sample.synprogress;

import android.test.AndroidTestCase;
import org.maxwe.android.views.synprogress.MarchManager;
import org.maxwe.android.views.synprogress.Marching;

/**
 * Created by dingpengwei on 3/18/15.
 */
public class MarchManagerTest extends AndroidTestCase {

    public void testMarching() throws Exception{
        MarchManager instance = MarchManager.getInstance();

        instance.addMarching("thread-1",new Marching("thread-1-1"));

        Marching marching = instance.getMarching("thread-1");

    }
}
