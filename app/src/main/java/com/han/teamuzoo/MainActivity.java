package com.han.teamuzoo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.han.teamuzoo.api.NetworkClient;
import com.han.teamuzoo.api.TimerApi;
import com.han.teamuzoo.api.UserApi;
import com.han.teamuzoo.config.Config;
import com.han.teamuzoo.model.MainTimer;
import com.han.teamuzoo.model.MainTimerRes;
import com.han.teamuzoo.model.UserRes;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import me.tankery.lib.circularseekbar.CircularSeekBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private CircularSeekBar progressCircular;
    private TextView txtTimer;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView bottomsheetstart;
    Button iconMenu;
    Intent intent;
    ImageView btnStart;
    ImageView btnCancel;
    ImageView btnStop;
    ImageView imgLeft;
    int newId;



    //    Button sheetButtonStart;
    /* test */
    Button testbutton;
    Button btn_dialog;
    /* test */


    TimerTask timerTask;
    Timer timer = new Timer();
    boolean isPaused = false;


    //                  ????????? ????????? ?????? !!
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();}
//  todo : ?????? ????????? ????????? 3??????????????? ???????????? ???????????? !!


    @Override // ??????????????? ?????? //
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            Log.i("aaa", "drawer toggle on clicked");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static final String TAG = "MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_dialog = findViewById(R.id.btn_dialog);
        testbutton = findViewById(R.id.testbutton);
        testbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BottomSheet.class);
                startActivity(intent);

            }
        });

        imgLeft = findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RekogActivity.class);
                startActivity(intent);
            }
        });



        btnCancel = findViewById(R.id.btnCancel);
        btnStop = findViewById(R.id.btnStop);
        btnStart = findViewById(R.id.btnStart);
//        sheetButtonStart = findViewById(R.id.sheetButtonStart);

        // ??????????????? timer ?????? ??????????????? //
        txtTimer = findViewById(R.id.txtTimer);
        txtTimer.setText( 30 + "???");




        // ??????????????? ????????? ?????? ??????????????? //
        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        // ?????? ?????????????????? ?????????, ???????????? ??????????????? ????????????,
        //    ????????? ?????????, ?????????????????? API ????????????, ????????????????????? ????????? ??? ?????? ????????????.
        if (accessToken.isEmpty()) {

            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);

            finish();
            return;
        }
        // ??????????????? ????????? ?????? ??????????????? //


        // todo : ?????????????????? ????????? ?????? ????????? ???????????? ?????? timer ??????????????? ????????? !!!


        progressCircular = findViewById(R.id.progress_circular);
        progressCircular.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(@Nullable CircularSeekBar circularSeekBar, float v, boolean b) {
                Log.i("progress", "onProgressChanged");

                int txtTiti = (int) Math.round(progressCircular.getProgress());
                txtTimer.setText(txtTiti + "???");



                Log.i("progress", "" + progressCircular.getProgress());
                Log.i("progress", "" + txtTiti);

            }

            @Override
            public void onStopTrackingTouch(@Nullable CircularSeekBar circularSeekBar) {
                Log.i("progress", "onProgressStop");

            }

            @Override
            public void onStartTrackingTouch(@Nullable CircularSeekBar circularSeekBar) {
                Log.i("progress", "onStartTracking");

            }
        });

        // ?????????????????? ???????????? ?????? ???????????? ????????? ??????
        long duration = TimeUnit.MINUTES.toMillis((long) progressCircular.getProgress());





        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ?????? ????????? ??????
                btnStart.setVisibility(View.INVISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
                startTimerTask();

                int setTime = (int) progressCircular.getProgress();
                Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
                TimerApi api = retrofit.create(TimerApi.class);

                SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
                String token = sp.getString("accessToken", "");

                MainTimer mainTimer = new MainTimer(setTime);

                Call<MainTimerRes> call = api.startTimer("Bearer "+token, mainTimer);

                call.enqueue(new Callback<MainTimerRes>() {
                    @Override
                    public void onResponse(Call<MainTimerRes> call, Response<MainTimerRes> response) {
                        if(response.isSuccessful()) {

                            Toast.makeText(MainActivity.this, "????????? ??????",Toast.LENGTH_SHORT).show();


                        }
                    }

                    @Override
                    public void onFailure(Call<MainTimerRes> call, Throwable t) {

                    }
                });






//                new CountDownTimer(duration, 1000) {
//                    @Override
//                    public void onTick(long l) {
//                        //When tick
//                        //Convert millisecond to minute and second
//                        String sDuration = String.format(Locale.ENGLISH, "%02d:%02d"
//                                , TimeUnit.MILLISECONDS.toMinutes(l)
//                                , TimeUnit.MILLISECONDS.toSeconds(l) -
//                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
//                        // Set converted string on text view
////                        txtTimer.setText(sDuration);
//
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        txtTimer.setVisibility(View.GONE);
//                        Toast.makeText(getApplicationContext(),
//                                "???????????? ???????????????", Toast.LENGTH_LONG).show();
//                        // ?????? ?????????????????? ??????????????? ????????? ??????
//                        btnStart.setVisibility(View.VISIBLE);
//                        btnCancel.setVisibility(View.INVISIBLE);
//
//                    }
//                }.start();
                // ??????????????? timer ?????? ??????????????? //


            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ?????? ????????? ??????
                btnCancel.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.VISIBLE);
                stopTimerTask();


                Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
                TimerApi api = retrofit.create(TimerApi.class);

                SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME,MODE_PRIVATE);
                String accessToken = sp.getString("accessToken", "");

                Call<MainTimerRes> call = api.timerList("Bearer "+accessToken);

                call.enqueue(new Callback<MainTimerRes>() {
                    @Override
                    public void onResponse(Call<MainTimerRes> call, Response<MainTimerRes> response) {
                        if(response.isSuccessful()){

                            newId = response.body().getNewId();
                            Call<MainTimerRes> call2 = api.deleteTimer("Bearer "+ accessToken, newId);

                            call2.enqueue(new Callback<MainTimerRes>() {
                                @Override
                                public void onResponse(Call<MainTimerRes> call2, Response<MainTimerRes> response) {
                                    if(response.isSuccessful()){
                                        Toast.makeText(MainActivity.this, "???????????? ?????????????????????.", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<MainTimerRes> call2, Throwable t) {

                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<MainTimerRes> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "????????? ?????? ??????", Toast.LENGTH_SHORT).show();

                    }



                });


                txtTimer.setText(Math.round(progressCircular.getProgress())+"???");

                // todo : 10??? ?????? < ???????????? API ??????

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ?????? ????????? ??????
                btnStart.setVisibility(View.VISIBLE);
                btnStop.setVisibility(View.INVISIBLE);
                stopTimerTask();

                txtTimer.setText(Math.round(progressCircular.getProgress())+"???");
                Log.i("aaaStop", "btnStop ????????? ??????");

                Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
                TimerApi api = retrofit.create(TimerApi.class);

                SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME,MODE_PRIVATE);
                String accessToken = sp.getString("accessToken", "");

                Call<MainTimerRes> call = api.timerList("Bearer "+accessToken);

                call.enqueue(new Callback<MainTimerRes>() {
                    @Override
                    public void onResponse(Call<MainTimerRes> call, Response<MainTimerRes> response) {
                        if(response.isSuccessful()){

                            newId = response.body().getNewId();
                            Call<MainTimerRes> call2 = api.failTimer("Bearer "+ accessToken, newId);

                            call2.enqueue(new Callback<MainTimerRes>() {
                                @Override
                                public void onResponse(Call<MainTimerRes> call2, Response<MainTimerRes> response) {
                                    if(response.isSuccessful()){
                                        Toast.makeText(MainActivity.this, "????????? ??????.", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<MainTimerRes> call2, Throwable t) {

                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<MainTimerRes> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "????????? ?????? ??????", Toast.LENGTH_SHORT).show();

                    }



                });


                txtTimer.setText(Math.round(progressCircular.getProgress())+"???");

                //todo : ????????? ?????? API ??????
            }
        });



        // ??????????????? SidePanel ?????? ??????????????? //
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Log.i("aaa", "drawer toggle on clicked11");

                switch (item.getItemId()) {


                    case R.id.nav_planet:
                        Log.i("MENU_DRAWER_TAG", "Planet item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent = new Intent(MainActivity.this, MyplanetActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_follower:
                        Log.i("MENU_DRAWER_TAG", "Follower item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent = new Intent(MainActivity.this, FollowerActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_list:
                        Log.i("MENU_DRAWER_TAG", "List item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent = new Intent(MainActivity.this, ListActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_quest:
                        Log.i("MENU_DRAWER_TAG", "Quest item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent = new Intent(MainActivity.this, QuestActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_shop:
                        Log.i("MENU_DRAWER_TAG", "Shop item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent = new Intent(MainActivity.this, ShopActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_setting:
                        Log.i("MENU_DRAWER_TAG", "Setting item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.menuLogout:
                        Log.i("MENU_DRAWER_TAG", "Logout item is clicked");
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);

                        Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
                        UserApi api = retrofit.create(UserApi.class);

                        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
                        String accessToken = sp.getString("accessToken", "");

                        Call<UserRes> call = api.logout("Bearer " + accessToken);
                        call.enqueue(new Callback<UserRes>() {
                            @Override
                            public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                                if (response.isSuccessful()) {
                                    Log.i("MENU_DRAWER_TAG", "Logout on Response");

                                    Log.i("MyPostingApp", response.body().getResult());

                                    SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("accessToken", "");
                                    editor.apply();
                                    Log.i("MENU_DRAWER_TAG", "Logout on Response2");


                                } else {

                                }
                            }

                            @Override
                            public void onFailure(Call<UserRes> call, Throwable t) {
                                Log.i("MENU_DRAWER_TAG", "Logout on Failure");
                            }
                        });
                        Log.i("MENU_DRAWER_TAG", "Logout on Break");
                        break;
                }

                return true;
            }
        });
        // ??????????????? SidePanel ?????? ??????????????? //


        // ??????????????? ??????????????? ?????? ?????? ??????????????? //
        bottomsheetstart = findViewById(R.id.bottomsheetStart);


        bottomsheetstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        MainActivity.this, R.style.bottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.activity_bottom_sheet,
                                (LinearLayout) findViewById(R.id.bottomSheetContainer)
                        );

                bottomSheetView.findViewById(R.id.sheetButtonStart).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Start...", Toast.LENGTH_LONG).show();
                        bottomSheetDialog.dismiss();
                        btnStart.setVisibility(View.INVISIBLE);
                        btnCancel.setVisibility(View.VISIBLE);
                        startTimerTask();


                    }
                });




                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        // ??????????????? ??????????????? ?????? ??????????????? //



        // ???????????? ????????? ?????? //
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
                    // ????????? ????????????
                    Log.i("aaa", "?????? ??????");

                }
            }
        };
        registerReceiver(receiver, intentFilter);

//    startTimerTask();
    }

    // ????????? ??????????????? ??????
    public void OnClickHandler(View view) {
        CustomDialog dialog = new CustomDialog(this);
        dialog.callDialog();
    }




    @Override
    protected void onPause() {
        super.onPause();
        Log.i("aaa", "onPause()");

        // ????????? ???????????? ????????? ??????,
        // ???????????? ??????.

        // ?????????, ?????? ???????????? OFF ????????????, ????????? ????????? ????????????
        // ???????????? ?????? ?????????.
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> info = manager.getRunningTasks(1);
        ComponentName componentName= info.get(0).topActivity;
        String ActivityName = componentName.getShortClassName().substring(1);
        Log.i("aaa", ActivityName);

        // ?????? ???????????? ???????????? ??????
        if (ActivityName.equals("MainActivity")){
            isPaused = false;
        } else {
            // ?????? API ??????

            Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);
            TimerApi api = retrofit.create(TimerApi.class);

            SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME,MODE_PRIVATE);
            String accessToken = sp.getString("accessToken", "");

            Call<MainTimerRes> call = api.timerList("Bearer "+accessToken);

            call.enqueue(new Callback<MainTimerRes>() {
                @Override
                public void onResponse(Call<MainTimerRes> call, Response<MainTimerRes> response) {
                    if(response.isSuccessful()){

                        newId = response.body().getNewId();
                        Call<MainTimerRes> call2 = api.deleteTimer("Bearer "+ accessToken, newId);

                        call2.enqueue(new Callback<MainTimerRes>() {
                            @Override
                            public void onResponse(Call<MainTimerRes> call2, Response<MainTimerRes> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(MainActivity.this, "???????????? ?????????????????????.", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<MainTimerRes> call2, Throwable t) {

                            }
                        });

                    }
                }

                @Override
                public void onFailure(Call<MainTimerRes> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "????????? ?????? ??????", Toast.LENGTH_SHORT).show();

                }



            });

            timerTask.cancel();
        }

    }


    private void startTimerTask()
    {
        stopTimerTask();

        timerTask = new TimerTask()
        {
            int count = (int) progressCircular.getProgress();
            @Override
            public void run(){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(isPaused == true){
                            return;
                        }

                        count--;
                        Log.i("aaa", count + "");
                        if(count == 0)
                            timerTask.cancel();

                        if(count <= count - 10){
                            btnCancel.setVisibility(View.INVISIBLE);
                        } else if ( count > count - 10) {

                            // handler postin
                            btnStop.setVisibility(View.VISIBLE);
                            btnCancel.setVisibility(View.INVISIBLE);
                        }


                        txtTimer.post(new Runnable() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        txtTimer.setText(count + "???");
                                    }
                                });
                            }

                        });
                    }
                });
            }

        };
        timer.schedule(timerTask,0 ,1000);
        // period 60000?????? ??????, 1000??? ????????????
    }

    private void stopTimerTask()
    {   Toast.makeText(MainActivity.this, "????????? ??????2", Toast.LENGTH_SHORT).show();
        Log.i("aaaStop", "????????? ?????? 111");
        if(timerTask != null)
        {
//            textView.setText("60 ???");
            Toast.makeText(this, "????????? ??????", Toast.LENGTH_LONG).show();
            Log.i("aaaStop", "????????? ?????? 222");
            timerTask.cancel();
//            timerTask = null;

        }
    }








}






