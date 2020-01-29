package com.clericyi.helper;


import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.clericyi.basehelper.base.BaseActivity;
import com.clericyi.basehelper.util.PermissionUtil;


public class MainActivity extends BaseActivity<LoginPresenter, LoginContract.View> {

    private EditText nameEt;
    private EditText pwdEt;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MainActivity.this,new String[]{Manifest.permission.WRITE_CONTACTS},1);
            Log.e(TAG, "1");
        } else {
            read();

        }

    }

    // 初始化控件
    private void initView() {
        nameEt = findViewById(R.id.et_name);
        pwdEt = findViewById(R.id.et_pwd);
        btn = findViewById(R.id.bt_login);
    }

    private void read() {
        try (Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null)) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Log.e(TAG, name + " ;" + number);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        getContentResolver().delete(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null);
    }

    // 点击事件
    public void doLoginAction(View view) {
        String name = nameEt.getText().toString();
        String pwd = pwdEt.getText().toString();
        // 发起需求，让Presenter处理
        p.getContract().requestLogin(name, pwd);
    }

    @Override
    public LoginContract.View getContract() {
        return new LoginContract.View<UserInfo>() {
            @Override
            public void handlerResult(UserInfo userInfo) {
                if (userInfo != null) {
                    Toast.makeText(MainActivity.this, userInfo.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    public LoginPresenter getPresenter() {
        return new LoginPresenter();
    }
}