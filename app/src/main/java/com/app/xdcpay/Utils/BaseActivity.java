package com.app.xdcpay.Utils;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.app.xdcpay.Activities.CreateWalletActivity;
import com.ybs.passwordstrengthmeter.PasswordStrength;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public abstract void getId();

    public abstract void setListener();

    public abstract void setData();

    public abstract void onClick(View v);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getId();
        setListener();

    }

    public void showtoast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void updatePasswordStrengthView(EditText password, ProgressBar progressBar)
    {
        if (password.getText().toString().isEmpty()) {
            progressBar.setProgress(0);
            return;
        }
        PasswordStrength str = PasswordStrength.calculateStrength(password.getText().toString());
        progressBar.getProgressDrawable().setColorFilter(str.getColor(), android.graphics.PorterDuff.Mode.SRC_IN);
        if (str.getText(BaseActivity.this).equals("Weak")) {
            progressBar.setProgress(25);
        } else if (str.getText(BaseActivity.this).equals("Medium")) {
            progressBar.setProgress(50);
        } else if (str.getText(BaseActivity.this).equals("Strong")) {
            progressBar.setProgress(75);
        } else {
            progressBar.setProgress(100);
        }
    }

}
