package se.rickylagerkvist.whotune.login;

import android.content.Intent;
import android.view.View;

import com.google.android.gms.auth.api.Auth;

/**
 * Created by rickylagerkvist on 2017-04-05.
 */

public class LoginPresenter {

    private View view;

    LoginPresenter(View view) {
        this.view = view;
    }

    public interface View{

    }
}
