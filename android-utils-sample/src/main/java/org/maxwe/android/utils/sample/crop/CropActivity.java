package org.maxwe.android.utils.sample.crop;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.maxwe.android.utils.sample.R;
import org.maxwe.android.utils.views.crop.Crop;

import java.io.File;

/**
 * Created by Pengwei Ding on 2016-07-05 16:00.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class CropActivity  extends Activity implements View.OnClickListener{
    private ImageView resultView;
    private static final String temp = "/sdcard/YMEPub/temp.jpeg";
    private static final String temp_crop = "/sdcard/YMEPub/temp_crop.jpeg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.org_maxwe_demo_crop_activity);
        resultView = (ImageView) findViewById(R.id.org_maxwe_demo_crop_bt_image);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.org_maxwe_demo_crop_bt_camera:
                resultView.setImageBitmap(null);
                Crop.setCameraIntent(Uri.fromFile(new File(temp)));
                Crop.setRequestCode(Crop.REQUEST_CROP_CAMERA);
                Crop.pickImage(this);
                break;
            case R.id.org_maxwe_demo_crop_bt_gallery:
                resultView.setImageBitmap(null);
                Crop.setRequestCode(Crop.REQUEST_CROP_GALLEYR);
                Crop.setGalleryIntent();
                Crop.pickImage(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {

        } else if (requestCode == Crop.REQUEST_CROP_GALLEYR && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP_CAMERA && resultCode == RESULT_OK) {
            beginCrop(Uri.fromFile(new File(temp)));
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(temp_crop));
        Crop.of(source, destination).asPng(true).start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            resultView.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
