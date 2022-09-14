package com.han.teamuzoo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.han.teamuzoo.api.AchivementApi;
import com.han.teamuzoo.api.NetworkClient;
import com.han.teamuzoo.api.RekognitionApi;
import com.han.teamuzoo.config.Config;
import com.han.teamuzoo.model.AchiveRes;
import com.han.teamuzoo.model.GetCoin;
import com.han.teamuzoo.model.RekogRes;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RekogActivity extends AppCompatActivity {

    ImageView imgCamera;
    ImageView imgRekog;
    ImageView imgBack;

    ImageView imageView;

    TextView txtResult;
    TextView txtCoin;

    ArrayList<String> strResult= new ArrayList<>();
    ArrayList<String> strDetected= new ArrayList<>(Arrays.asList("Computer","Pen","Eraser","Book","Desk"));

    int i;

    int realAddedCoin;

    //MainActivity에서 가져온 변수
    Button btnCoin = (Button) ((MainActivity)MainActivity.context).btnCoin;


    // 사진관련된 변수들
    private File photoFile;

    public static final int REQUEST_PERMISSION = 11;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekog);

        Log.i("check","rekog Activity 호출됨");

        imgCamera = findViewById(R.id.imgCamera);

        imgRekog = findViewById(R.id.imgRekog);
        imageView = findViewById(R.id.imageView);
        imgBack = findViewById(R.id.imgBack);
        txtResult = findViewById(R.id.txtResult);
        txtCoin = findViewById(R.id.txtCoin);

        // 화살표 버튼 누르면 MainActivity
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("check","Back 버튼 호출됨");
                finish();
            }
        });

        // 카메라 구현
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 예외 처리: 두 번 누르면 다시 찍으라고, 알람 뜨기
                // 분석 중복 방지용
                i = 0;
                
                Log.i("check","Camera 버튼 실행됨");
                // 버튼을 누르면 카메라에서 선택인지 앨범에서 선텍인지
                // 고를수있게 알러트 다이얼로그 띄운다.  밑에 알러트 다이얼로그 띄우는 함수 만든다.
                showDialog();

                // 카메라 선택하면 사진찍는것
                // 앨범선택하면 에범에서 사진 선택할수있도록 한다.

                // 그 결과는 이미지 뷰에서 보여준다.

//                checkPermission(); //권한체크
            }
        });

         // 분석
        imgRekog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Retrofit retrofit = NetworkClient.getRetrofitClient(RekogActivity.this);
                RekognitionApi api = retrofit.create(RekognitionApi.class);
                Log.i("check","retrofit 호출 됨");

                if(i == 1){
                    Toast.makeText(RekogActivity.this, "다시 촬영해주세요", Toast.LENGTH_SHORT).show();
                    
                } else{
                Log.i("check","rekognition 버튼 눌러짐");

                // API 호출
                if (photoFile == null) {
                    Toast.makeText(RekogActivity.this, "사진을 선택하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 멀티파트로 파일 보내는 경우 파일 파라미터 만드는 방법
                RequestBody fileBody = RequestBody.create(photoFile, MediaType.parse("image/*"));
                MultipartBody.Part photo = MultipartBody.Part.createFormData("photo",  // 키값
                        photoFile.getName(), fileBody);
                Log.i("check","파일 파라미터 만듬");

                // 헤더에 들어갈 억세스토큰 가져온다.
                SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
                String accessToken = sp.getString("accessToken", "");

                Call<RekogRes> call = api.rekognition("Bearer " + accessToken, photo);
                Log.i("check","API 호출 중");


                // 성공하면, 파일을 업로드 하고, 그 파일을 S3에 저장하고,

                call.enqueue(new Callback<RekogRes>() {
                    @Override
                    public void onResponse(Call<RekogRes> call, Response<RekogRes> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RekogActivity.this, "사물인식이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            Log.i("check","rekognition 분석됨");

                            strResult = response.body().getDetected_labels();
                            txtResult.setText(""+strResult);

                            Log.i("check",strResult.getClass().getName());

                            // 중복값 찾기
                            strDetected.retainAll(strResult);
                            Log.i("check","중복된 값은 "+ strDetected.toString());


                            realAddedCoin = strDetected.toString().length() * 2;
                            txtCoin.setText( "3개의 키워드가 맞았습니다. 6 코인이 추가됩니다.");

//                            btnCoin.setText("1252");

//                            txtCoin.setText(strDetected.toString().length() + "개의 키워드가 맞았습니다. " + realAddedCoin +"코인이 추가됩니다.");

                        } else {
                        }
                    }
                    @Override
                    public void onFailure(Call<RekogRes> call, Throwable t) {
                        Log.i("check","API 호출 실패");
//                        dismissProgress();
                    }
                });
                Log.i("check","호출 과정 건너뜀");

                    // i 추가하기 위함
                i = i+1;

//                    // Todo 코인 추가하는 API 추가하기.
//                    realAddedCoin = strDetected.toString().length() * 2;
//
//                    // API 호출
//                    AchivementApi api2 = retrofit.create((AchivementApi.class));
//
//                    // 보낼 데이터를 만든다.
//                    GetCoin addedCoin = new GetCoin(realAddedCoin);
//                    Call<AchiveRes> call2 = api2.addCoin("Bearer "+ accessToken,addedCoin);
//
//                    call2.enqueue(new Callback<AchiveRes>() {
//                        @Override
//                        public void onResponse(Call<AchiveRes> call, Response<AchiveRes> response) {
//                            // 네트워크로부터 데이터를 받았을 때,
//                            if(response.isSuccessful()){
//
//                                AchiveRes achiveRes = response.body();
////
//                                txtCoin.setText(strDetected.toString().length() + "개의 키워드가 맞았습니다. " +addedCoin +"코인이 추가됩니다.");
//                            }
//                        }
//                        @Override
//                        public void onFailure(Call<AchiveRes> call, Throwable t) {
//                            Toast.makeText(RekogActivity.this, "에러 발생", Toast.LENGTH_SHORT).show();
//                        }
//                    });



                
            }
        }});


        // Todo 우리가 지정한 키워드와 맞는지 확인하고, 키워드가 맞으면, 개당 코인 추가



            }

//
//    // 다이얼로그 만드는 함수
//
//    void showProgress(String message) {
//
//        dialog = new ProgressDialog(RekogActivity.this);
//        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        dialog.setMessage(message);
//        dialog.show();
//
//    }
//
//    void dismissProgress() {
//        dialog.dismiss();
//    }


    // 알러트 다이얼로그 띄우는 함수

    void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RekogActivity.this);
        builder.setTitle(R.string.alert_title);

        // 미리 string 아이템을 만들고 그것을 활용한다.
        builder.setItems(R.array.alert_photo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    // TODO: 사진찍은 코드 실행
                    camera();

                }
//                else if (i == 1){
//                    // TODO: 앨범에서 사진 가져오는 코드 실행
//                    album();
//                }
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }


// 권한 확인

//    public void checkPermission() {
//        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
//        int permissionRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
//        int permissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//        //권한이 없으면 권한 요청
//        if (permissionCamera != PackageManager.PERMISSION_GRANTED
//                || permissionRead != PackageManager.PERMISSION_GRANTED
//                || permissionWrite != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
//                Toast.makeText(this, "카메라 인증을 실행하기 위해 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
//            }
//
//            ActivityCompat.requestPermissions(this, new String[]{
//                    Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case REQUEST_PERMISSION: {
//                // 권한이 취소되면 result 배열은 비어있다.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    Toast.makeText(this, "권한 확인", Toast.LENGTH_LONG).show();
//
//                } else {
//                    Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();
////                    finish(); //권한이 없으면 앱 종료
//                }
//            }
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        checkPermission(); //권한체크
    }

    // 카메라 함수
    private void camera(){
        int permissionCheck = ContextCompat.checkSelfPermission(
                RekogActivity.this, Manifest.permission.CAMERA);

        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(RekogActivity.this,
                    new String[]{Manifest.permission.CAMERA} ,
                    1000);
            Toast.makeText(RekogActivity.this, "카메라 권한 필요합니다.",
                    Toast.LENGTH_SHORT).show();
            return;
        } else {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(i.resolveActivity(RekogActivity.this.getPackageManager())  != null  ){

                // 사진의 파일명을 만들기
                String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                photoFile = getPhotoFile(fileName);

                Uri fileProvider = FileProvider.getUriForFile(RekogActivity.this,
                        "com.eyoo.camera.fileprovider", photoFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
                startActivityForResult(i, 100);

            } else{
                Toast.makeText(RekogActivity.this, "이폰에는 카메라 앱이 없습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File getPhotoFile(String fileName) {
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try{
            return File.createTempFile(fileName, ".jpg", storageDirectory);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private void album(){
        if(checkPermission()){
            displayFileChoose();
        }else{
            requestPermission();
        }
    }

    private void requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(RekogActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(RekogActivity.this, "권한 수락이 필요합니다.",
                    Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(RekogActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 500);
        }
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(RekogActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_DENIED){
            return false;
        }else{
            return true;
        }
    }

    private void displayFileChoose() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "SELECT IMAGE"), 300);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(RekogActivity.this, "권한 허가 되었음",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RekogActivity.this, "아직 승인하지 않았음",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case 500: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(RekogActivity.this, "권한 허가 되었음",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RekogActivity.this, "아직 승인하지 않았음",
                            Toast.LENGTH_SHORT).show();
                }

            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100 && resultCode == RESULT_OK){

            Bitmap photo = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

            ExifInterface exif = null;
            try {
                exif = new ExifInterface(photoFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            photo = rotateBitmap(photo, orientation);

            // 압축시킨다. 해상도 낮춰서
            OutputStream os;
            try {
                os = new FileOutputStream(photoFile);
                photo.compress(Bitmap.CompressFormat.JPEG, 50, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            }

            photo = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

            imageView.setImageBitmap(photo);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            // 네트워크로 데이터 보낸다.



        }else if(requestCode == 300 && resultCode == RESULT_OK && data != null &&
                data.getData() != null){

            Uri albumUri = data.getData( );
            String fileName = getFileName( albumUri );
            try {

                ParcelFileDescriptor parcelFileDescriptor = getContentResolver( ).openFileDescriptor( albumUri, "r" );
                if ( parcelFileDescriptor == null ) return;
                FileInputStream inputStream = new FileInputStream( parcelFileDescriptor.getFileDescriptor( ) );
                photoFile = new File( this.getCacheDir( ), fileName );
                FileOutputStream outputStream = new FileOutputStream( photoFile );
                IOUtils.copy( inputStream, outputStream );

//                //임시파일 생성
//                File file = createImgCacheFile( );
//                String cacheFilePath = file.getAbsolutePath( );


                // 압축시킨다. 해상도 낮춰서
                Bitmap photo = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                OutputStream os;
                try {
                    os = new FileOutputStream(photoFile);
                    photo.compress(Bitmap.CompressFormat.JPEG, 60, os);
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                }

                imageView.setImageBitmap(photo);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

//                imageView.setImageBitmap( getBitmapAlbum( imageView, albumUri ) );

            } catch ( Exception e ) {
                e.printStackTrace( );
            }

            // 네트워크로 보낸다.
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    //앨범에서 선택한 사진이름 가져오기
    public String getFileName( Uri uri ) {
        Cursor cursor = getContentResolver( ).query( uri, null, null, null, null );
        try {
            if ( cursor == null ) return null;
            cursor.moveToFirst( );
            @SuppressLint("Range") String fileName = cursor.getString( cursor.getColumnIndex( OpenableColumns.DISPLAY_NAME ) );
            cursor.close( );
            return fileName;

        } catch ( Exception e ) {
            e.printStackTrace( );
            cursor.close( );
            return null;
        }
    }

    //이미지뷰에 뿌려질 앨범 비트맵 반환
    public Bitmap getBitmapAlbum( View targetView, Uri uri ) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = getContentResolver( ).openFileDescriptor( uri, "r" );
            if ( parcelFileDescriptor == null ) return null;
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor( );
            if ( fileDescriptor == null ) return null;

            int targetW = targetView.getWidth( );
            int targetH = targetView.getHeight( );

            BitmapFactory.Options options = new BitmapFactory.Options( );
            options.inJustDecodeBounds = true;

            BitmapFactory.decodeFileDescriptor( fileDescriptor, null, options );

            int photoW = options.outWidth;
            int photoH = options.outHeight;

            int scaleFactor = Math.min( photoW / targetW, photoH / targetH );
            if ( scaleFactor >= 8 ) {
                options.inSampleSize = 8;
            } else if ( scaleFactor >= 4 ) {
                options.inSampleSize = 4;
            } else {
                options.inSampleSize = 2;
            }
            options.inJustDecodeBounds = false;

            Bitmap reSizeBit = BitmapFactory.decodeFileDescriptor( fileDescriptor, null, options );

            ExifInterface exifInterface = null;
            try {
                if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ) {
                    exifInterface = new ExifInterface( fileDescriptor );
                }
            } catch ( IOException e ) {
                e.printStackTrace( );
            }

            int exifOrientation;
            int exifDegree = 0;

            //사진 회전값 구하기
            if ( exifInterface != null ) {
                exifOrientation = exifInterface.getAttributeInt( ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL );

                if ( exifOrientation == ExifInterface.ORIENTATION_ROTATE_90 ) {
                    exifDegree = 90;
                } else if ( exifOrientation == ExifInterface.ORIENTATION_ROTATE_180 ) {
                    exifDegree = 180;
                } else if ( exifOrientation == ExifInterface.ORIENTATION_ROTATE_270 ) {
                    exifDegree = 270;
                }
            }

            parcelFileDescriptor.close( );
            Matrix matrix = new Matrix( );
            matrix.postRotate( exifDegree );

            Bitmap reSizeExifBitmap = Bitmap.createBitmap( reSizeBit, 0, 0, reSizeBit.getWidth( ), reSizeBit.getHeight( ), matrix, true );
            return reSizeExifBitmap;

        } catch ( Exception e ) {
            e.printStackTrace( );
            return null;
        }

    }

}
