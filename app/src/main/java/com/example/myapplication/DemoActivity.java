package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Pair;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.ml.LiteModelEfficientnetLite0Uint82;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DemoActivity extends AppCompatActivity {

    Button selectBtn, predictBtn, batchInferenceBtn, backBtn;
    TextView result;
    ImageView imageView;
    Bitmap bitmap;

    @SuppressLint({"MissingInflatedId", "SetTextI18n", "StaticFieldLeak"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        backBtn = findViewById(R.id.backButton);
        selectBtn = findViewById(R.id.selectBtn);
        predictBtn = findViewById(R.id.predictBtn);
        batchInferenceBtn = findViewById(R.id.batchInference);
        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView);

        backBtn.setOnClickListener(v -> {
            // go back to the FunctionActivity
            Intent intent = new Intent(DemoActivity.this,  MainActivity.class);
            startActivity(intent);
        });

        selectBtn.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 10);
        });

        predictBtn.setOnClickListener(v -> {
            Category maxCate = performInference(bitmap);
            // Display the label with the highest probability on the UI
            if (maxCate != null) {
                result.setText(maxCate.getLabel());
                result.setTextColor(Color.BLACK);
            } else {
                result.setText("No prediction result");
            }
        });

        batchInferenceBtn.setOnClickListener(view -> {
            AssetManager assetManager = getAssets();
            String[] categories = {"flowers"};
            // Collect all image paths
            List<String> imagePaths = new ArrayList<>();
            for (String category : categories) {
                String[] imageFiles = new String[0];
                try {
                    imageFiles = assetManager.list(category);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (String imageFile : imageFiles) {
                    imagePaths.add(category + "/" + imageFile);
                }
            }
            // Shuffle and select 10 images
            Collections.shuffle(imagePaths);
            imagePaths = imagePaths.subList(0, 5);

            // Run batch inference on a separate thread
            List<String> finalImagePaths = imagePaths;
            new AsyncTask<Void, Pair<Category, Bitmap>, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    for (final String imagePath : finalImagePaths) {
                        try {
                            InputStream inputStream = assetManager.open(imagePath);
                            final Bitmap map = BitmapFactory.decodeStream(inputStream);
                            // Resize the image
                            final Bitmap resizedBitmap = Bitmap.createScaledBitmap(map, 224, 224, true);

                            final Category maxCategory = performInference(resizedBitmap);
                            // Publish the inference result to the UI thread
                            publishProgress(new Pair<>(maxCategory, map));

                            // Sleep for 1 second between each image
                            Thread.sleep(1000);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    result.setText("The Demo Inference is Completed");
                    return null;
                }

                @SafeVarargs
                @Override
                protected final void onProgressUpdate(Pair<Category, Bitmap>... values) {
                    // Display the label with the highest probability on the UI
                    if (values[0] != null) {
                        result.setText(values[0].first.getLabel());
                        result.setTextColor(Color.BLACK);
                        imageView.setImageBitmap(values[0].second);
                    } else {
                        result.setText("No prediction result");
                    }
                }
            }.execute();
        });
    }

    private Category performInference(Bitmap bitmap) {
        try {
            // Load the model
            LiteModelEfficientnetLite0Uint82 model = LiteModelEfficientnetLite0Uint82.newInstance(DemoActivity.this);

            // Create input tensor from the bitmap
            TensorImage image = TensorImage.fromBitmap(bitmap);

            // Run inference on the input tensor
            LiteModelEfficientnetLite0Uint82.Outputs outputs = model.process(image);
            List<Category> probability = outputs.getProbabilityAsCategoryList();

            // Find the category with the highest probability
            Category maxCategory = null;
            float maxScore = 0.0f;
            for (Category category : probability) {
                if (category.getScore() > maxScore) {
                    maxCategory = category;
                    maxScore = category.getScore();
                }
            }

            // Release model resources
            model.close();

            return maxCategory;
        } catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) { // request on image selection for single inference
            if (data != null) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}