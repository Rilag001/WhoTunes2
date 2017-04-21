package se.rickylagerkvist.whotune;

import android.support.v4.app.Fragment;

/**
 * Created by rickylagerkvist on 2017-04-21.
 */

public interface MainActivityNavigationInterFace {

    void changeFragment(Fragment fragment, boolean addToBackstack);
}
