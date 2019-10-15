package com.app.android.app.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.android.app.Dialog.DialogConfirmInBrowse;
import com.app.android.app.R;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class BrowseImageActivity extends Activity {


    private PhotoView mPhotoView;
    private DialogConfirmInBrowse mDialogConfirm;
    private ImageView mBtnMore;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_image);
        mPhotoView = findViewById(R.id.photo_view);
        mBtnMore = findViewById(R.id.btn_more);
        mProgressBar = findViewById(R.id.pb);

        setDialog();
        setEvent();


        Intent intent = getIntent();
        Picasso.with(this)
                .load(intent.getStringExtra("url"))
                .into(mPhotoView, new Callback() {
                    @Override
                    public void onSuccess() {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });

    }

    private void setEvent() {
        mPhotoView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                finish();
            }
        });
        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mDialogConfirm.show();
                return false;
            }
        });
        mBtnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogConfirm.show();
            }
        });
    }

    private void setDialog() {
        mDialogConfirm = new DialogConfirmInBrowse(this);
        mDialogConfirm.setListener(new DialogConfirmInBrowse.OnClickListener() {
            @Override
            public void onSave() {

                // 下载图片
                new TaskDownloadImage().execute(getIntent().getStringExtra("url"));
            }

        });
    }


    public class TaskDownloadImage extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String urlPath = strings[0];

            URL url = null;
            HttpURLConnection connection = null;
            Bitmap bitmap = null;
            FileOutputStream fileOutputStream = null;
            InputStream inputStream = null;
            try {
                url = new URL(urlPath);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(6000);
                connection.setDoInput(true);
                connection.setUseCaches(true);
                inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                String fileName = UUID.randomUUID().toString() + ".webp";
                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + fileName);

                //文件夹不存在，则创建它
                if (!file.getParentFile().getAbsoluteFile().exists()) {
                    file.mkdir();
                }
                fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.WEBP, 20, fileOutputStream);
                fileOutputStream.close();


                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath()));
                sendBroadcast(intent);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bitmap != null) {
                    bitmap.recycle();
                }
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            return "下载完成";
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(BrowseImageActivity.this, s, Toast.LENGTH_LONG).show();
        }
    }
}
