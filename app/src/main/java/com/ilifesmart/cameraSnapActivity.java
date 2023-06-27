package com.ilifesmart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.ilifesmart.weather.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class cameraSnapActivity extends AppCompatActivity {
    public static final String TAG = cameraSnapActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_snap);

        findViewById(R.id.openAlbumA).setOnClickListener((v) -> {
            openAlbum();
        });

        findViewById(R.id.openCamera).setOnClickListener((v) -> {
            openCamera();
        });

        List<String> perms = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            perms.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            perms.add(Manifest.permission.READ_MEDIA_IMAGES);
        }

        List<String> permissions = new ArrayList<>();
        for (String perm : perms) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(perm);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions.size() > 0) {
            requestPermissions(permissions.toArray(new String[permissions.size()]), 1234);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1234) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: not granted " + permissions[i]);
                }
            }
        }
    }

    private void openAlbum() {
//        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(i, 1240);
        Intent pickImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickImageIntent.setType("image/*");

        startActivityForResult(pickImageIntent, 1240);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: requestCode " + requestCode + "resultCode " + resultCode + " data " + data + " ok? " + (resultCode == RESULT_OK));

        if (requestCode == 1240) {
            Log.d(TAG, "onActivityResult: 1111111 ");
            if (resultCode == RESULT_OK) {
                try {
                    Log.d(TAG, "onActivityResult: 222222 ");
                    Uri uri = data.getData();

                    getPathFromUri(this, uri);
//                    Log.e(TAG, "onActivityResult: uri " + uri);
//                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                    Cursor cursor = getContentResolver().query(uri,
//                            filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
//                    cursor.moveToFirst();
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    String path = cursor.getString(columnIndex);  //获取照片路径
//                    cursor.close();
//                    Bitmap bitmap = BitmapFactory.decodeFile(path);
//
//                    Log.d(TAG, "onActivityResult: bitmap>>> " + (bitmap != null));
//                    if (bitmap != null) {
//                        findViewById(R.id.image).setBackground(new BitmapDrawable(bitmap));
//
//                        (new Handler(getMainLooper())).postDelayed(() -> {
////                            cropImage(uri);
//                            cropImage(uri);
//                        }, 2_000);
//                    }
                } catch (Exception ex) {
                    Log.d(TAG, "onActivityResult: " + ex.getMessage());
                }
            }
        } else if (requestCode == 1390) {
            Log.d(TAG, "onActivityResult: here 1390");
            if (data != null) {
                Bundle extras = data.getExtras();
                Bitmap bitmap = extras != null ? (Bitmap) extras.getParcelable("data") : null;

                Log.d(TAG, "onActivityResult: isRight " + (bitmap != null));
                if (bitmap != null) {
                    findViewById(R.id.image).setBackground(new BitmapDrawable(bitmap));
                }
            }
        }

        Log.d(TAG, "onActivityResult: 33333333 ");
    }

    private void openCamera() {

    }

    private void cropImage(Uri uri) {
        Log.d(TAG, "cropPhoto: uir " + " path " + uri.getPath() );
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        if (uri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.putExtra("return-data", false);
        } else {
            intent.putExtra("return-data", true);
        }

        Log.d(TAG, "cropPhoto: uri " + uri + " outputUri " + uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 把“return-data”设置为了true然后在onActivityResult中通过data.getParcelableExtra("data")来获取数据，
        // 不过这样的话dp这个变量的值就不能太大了，不然程序就挂了。
        startActivityForResult(intent, 1390);
    }

    private void pictureCropping(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        // 返回裁剪后的数据
        intent.putExtra("return-data", false);

        startActivityForResult(intent, 1390);
    }

    String getPathFromUri(final Context context, final Uri uri) {
        File file = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        boolean success = false;
        try {
            String extension = getImageExtension(context, uri);
            Log.d(TAG, "getPathFromUri: extension " + extension);
            inputStream = context.getContentResolver().openInputStream(uri);

            file = File.createTempFile("image_picker", extension, context.getCacheDir());
            file.deleteOnExit();
            outputStream = new FileOutputStream(file);
            if (inputStream != null) {
                copy(inputStream, outputStream);
                success = true;
            }
        } catch (IOException ignored) {
            Log.d(TAG, "getPathFromUri: ignored " + ignored.getMessage());
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException ignored) {
            }
            try {
                if (outputStream != null) outputStream.close();
            } catch (IOException ignored) {
                // If closing the output stream fails, we cannot be sure that the
                // target file was written in full. Flushing the stream merely moves
                // the bytes into the OS, not necessarily to the file.
                success = false;
            }
        }

        Log.d(TAG, "getPathFromUri: success " + success + " path " + file.getPath());
        return success ? file.getPath() : null;
    }

    private static String getImageExtension(Context context, Uri uriImage) {
        String extension = null;

        try {
            String imagePath = uriImage.getPath();
            if (uriImage.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
                final MimeTypeMap mime = MimeTypeMap.getSingleton();
                extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uriImage));
            } else {
                extension =
                        MimeTypeMap.getFileExtensionFromUrl(
                                Uri.fromFile(new File(uriImage.getPath())).toString());
            }
        } catch (Exception e) {
            extension = null;
        }

        if (extension == null || extension.isEmpty()) {
            //default extension for matches the previous behavior of the plugin
            extension = "jpg";
        }

        return "." + extension;
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        final byte[] buffer = new byte[4 * 1024];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        out.flush();
    }


}