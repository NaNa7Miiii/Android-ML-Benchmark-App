package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.CompatibilityList;
import org.tensorflow.lite.gpu.GpuDelegate;
import org.tensorflow.lite.nnapi.NnApiDelegate;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;

public class SelectModelActivity extends AppCompatActivity {

    private TextView result;
    private String selectedModelName;
    private String selectedUnitName;

    @SuppressLint({"MissingInflatedId", "StaticFieldLeak", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_model);

        Button backBtn = findViewById(R.id.backButton);
        Button predictBtn = findViewById(R.id.predictBtn);
        EditText inputNumber = findViewById(R.id.input_number);
        result = findViewById(R.id.result);
        final TextView nline = new TextView(this);
        nline.setSingleLine(false);
        selectedModelName = "mobilenet_v3_small";
        selectedUnitName = "CPU"; // default unit
        NumberPicker unitPicker = findViewById(R.id.unitPicker);
        NumberPicker numberPicker = findViewById(R.id.modelPicker);
        modelNames.initModelNames();
        numberPicker.setMaxValue(modelNames.getModelNamesArrayList().size() - 1);
        numberPicker.setMinValue(0);
        numberPicker.setDisplayedValues(modelNames.models());
        unitNames.initUnitNames();
        unitPicker.setMaxValue(unitNames.getUnitNamesArrayList().size() - 1);
        unitPicker.setMinValue(0);
        unitPicker.setDisplayedValues(unitNames.units());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            numberPicker.setTextColor(Color.BLACK);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            unitPicker.setTextColor(Color.BLACK);
        }
        backBtn.setOnClickListener(v -> {
            // go back to the FunctionActivity
            Intent intent = new Intent(SelectModelActivity.this, FunctionActivity.class);
            startActivity(intent);
        });

        // handle the onclick event for the selectModelBtn
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> selectedModelName = modelNames.getModelNamesArrayList().get(newVal).getName());

        unitPicker.setOnValueChangedListener((picker, oldVal, newVal) -> selectedUnitName = unitNames.getUnitNamesArrayList().get(newVal).getName());

        predictBtn.setOnClickListener(v -> {
            // access the number input from the user
            String numberStr = inputNumber.getText().toString();
            int number = Integer.parseInt(numberStr);

            // use the input number on inference
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
            // Shuffle and select given images based on the input number
            Collections.shuffle(imagePaths);
            imagePaths = imagePaths.subList(0, number);

            // Run batch inference on a separate thread
            List<String> finalImagePaths = imagePaths;
            final float[] totalLatency = {0};
            new AsyncTask<Void, Pair<Category, Bitmap>, Void>() {
                @SuppressLint("SetTextI18n")
                String start = null, end = null;
                protected Void doInBackground(Void... voids) {
                    for (final String imagePath : finalImagePaths) {
                        try {
                            InputStream inputStream = assetManager.open(imagePath);
                            final Bitmap map = BitmapFactory.decodeStream(inputStream);
                            Bitmap resizedBitmap;
                            String modelPath;
                            Pair<Long, Pair<Long, Long>> res;
                            int imgWidth, imgHeight;

                            switch (selectedModelName) {
                                case "efficientformer_l1":
                                case "efficientformer_l3":
                                case "efficientformer_l7":
                                case "efficientformerv2_s0":
                                case "efficientformerv2_s1":
                                case "efficientformerv2_s2":
                                case "efficientformerv2_l":
                                case "nextvit_base_in1k_224":
                                case "nextvit_large_in1k_224":
                                case "mobilevit_v1_xs":
                                case "mobilevit_v1_xxs":
                                case "mobilenet_v1":
                                case "mobilenet_v2":
                                case "mobilenet_v3_small":
                                case "mobilenet_v3_large":
                                case "efficientnet_b0":
                                case "nextvit_small_in1k_224":
                                case "efficientnet_v2_s":
                                case "efficientnet_v2_l":
                                case "efficientnet_v2_m":
                                case "MobileOne_s0":
                                case "MobileOne_s1":
                                case "MobileOne_s2":
                                case "MobileOne_s3":
                                case "MobileOne_s4":
                                case "mobilevit_v1_s":
                                    imgWidth = 224;
                                    imgHeight = 224;
                                    break;
                                case "mobilevit_v2_050":
                                case "mobilevit_v2_100":
                                case "mobilevit_v2_150":
                                    imgWidth = 256;
                                    imgHeight = 256;
                                    break;
                                case "efficientnet_b1":
                                    imgWidth = 240;
                                    imgHeight = 240;
                                    break;
                                case "efficientnet_b2":
                                    imgWidth = 260;
                                    imgHeight = 260;
                                    break;
                                case "efficientnet_b3":
                                    imgWidth = 300;
                                    imgHeight = 300;
                                    break;
                                case "efficientnet_b4":
                                    imgWidth = 380;
                                    imgHeight = 380;
                                    break;
                                case "efficientnet_b5":
                                    imgWidth = 456;
                                    imgHeight = 456;
                                    break;
                                case "efficientnet_b6":
                                    imgWidth = 528;
                                    imgHeight = 528;
                                    break;
                                case "efficientnet_b7":
                                    imgWidth = 600;
                                    imgHeight = 600;
                                    break;
                                default:
                                    continue;
                            }
                            // Resize the image
                            resizedBitmap = Bitmap.createScaledBitmap(map, imgWidth, imgHeight, true);
                            modelPath = modelPathHelper(selectedModelName + ".onnx");
                            res = onnxInference(modelPath, resizedBitmap, imgWidth, imgHeight);

                            totalLatency[0] += res.first;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                Instant instantStartTime = Instant.ofEpochMilli(res.second.first);
                                Instant instantEndTime = Instant.ofEpochMilli(res.second.second);

                                start = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss.SSS").withZone(ZoneId.systemDefault()).format(instantStartTime);
                                end =  DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss.SSS").withZone(ZoneId.systemDefault()).format(instantEndTime);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }

                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    result.setText("Average Latency = " + totalLatency[0] + " ms\n" + start + " ms\n - " + end + " ms");
                }
            }.execute();
        });
    }

    public String modelPathHelper(String model_name) {
        AssetManager assetManager = getAssets();
        try (InputStream inputStream = assetManager.open(model_name)) {
            File modelFile = new File(getCacheDir(), model_name);
            try (OutputStream outputStream = new FileOutputStream(modelFile)) {
                byte[] buffer = new byte[1024];
                int read;
                while ((read = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
                return modelFile.getAbsolutePath();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Pair<Long, Pair<Long, Long>> onnxInference(String modelPath, Bitmap bitmap, int x, int y) {
        try {
            OrtEnvironment env = OrtEnvironment.getEnvironment();
            OrtSession.SessionOptions options = new OrtSession.SessionOptions();
            if (selectedUnitName.equals("NNAPI") && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                // Initialize interpreter with NNAPI delegate for Android Pie or above
                try {
                    options.addNnapi();
                } catch (OrtException e) {
                    e.printStackTrace();
                }
            }
            OrtSession session = env.createSession(modelPath, options);
            // Create input tensor from the bitmap
            float[] inputData = preprocessImage(bitmap, x, y);
            long[] dimensions = new long[]{1, 3, x, y};
            FloatBuffer sourceData = FloatBuffer.wrap(inputData);
            OnnxTensor inputTensor = OnnxTensor.createTensor(env, sourceData, dimensions);

            // Warm up runs (50 iterations)
            for (int i = 0; i < 50; i++) {
                session.run(Collections.singletonMap("input.1", inputTensor));
            }

            // Run the actual inference (500 iterations)
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 500; i++) {
                session.run(Collections.singletonMap("input.1", inputTensor));
            }
            long endTime = System.currentTimeMillis();

            long inferenceTime = (endTime - startTime) / 500; // Average inference time in ms

            // Cleanup
            inputTensor.close();
            session.close();
            env.close();
            // Return inference time along with start and end times
            return new Pair<>(inferenceTime, new Pair<>(startTime, endTime));

        } catch (OrtException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Pair<Float, Pair<Float, Float>> tfliteInference(Bitmap bitmap, String filename, int x, int y) {
        try {
            // Load TFLite model
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open(filename);
            byte[] model = new byte[inputStream.available()];
            inputStream.read(model);
            ByteBuffer tfliteModel = ByteBuffer.allocateDirect(model.length);
            tfliteModel.order(ByteOrder.nativeOrder());
            tfliteModel.put(model);
            Interpreter.Options tfliteOptions = new Interpreter.Options();

            CompatibilityList compatList = new CompatibilityList();

            if (selectedUnitName.equals("NNAPI") && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                // Initialize interpreter with NNAPI delegate for Android Pie or above
                NnApiDelegate nnApiDelegate = new NnApiDelegate();
                tfliteOptions.addDelegate(nnApiDelegate);
            } else if (selectedUnitName.equals("GPU")) {
                if (compatList.isDelegateSupportedOnThisDevice()) {
                    GpuDelegate.Options delegateOptions = compatList.getBestOptionsForThisDevice();
                    GpuDelegate gpuDelegate = new GpuDelegate(delegateOptions);
                    tfliteOptions.addDelegate(gpuDelegate);
                }
            }
            Interpreter tflite = new Interpreter(tfliteModel, tfliteOptions);

            // Preprocess input image
            float[] inputArray = preprocessImage(bitmap, x, y);
            TensorBuffer inputTensorBuffer = TensorBuffer.createFixedSize(new int[]{1, x, y, 3}, DataType.UINT8);
            inputTensorBuffer.loadArray(inputArray);

            // Run inference
            TensorBuffer outputTensorBuffer = TensorBuffer.createFixedSize(new int[]{1, 1001}, DataType.UINT8);
            tflite.run(inputTensorBuffer.getBuffer(), outputTensorBuffer.getBuffer());
            float endTime = System.nanoTime() / 1000000f;

            // Return inference time
            float inferenceTime = tflite.getLastNativeInferenceDurationNanoseconds() / 1000000f; // convert the result to ms

            // Cleanup
            tflite.close();
            return new Pair<>(inferenceTime, new Pair<>((endTime-inferenceTime), endTime));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private float[] preprocessImage(Bitmap bitmap, int inputWidth, int inputHeight) {
        // Resize the bitmap
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, inputWidth, inputHeight, false);

        // Normalize the pixel values to be in the range of [0, 1]
        float[] output = new float[inputWidth * inputHeight * 3];
        int[] pixels = new int[inputWidth * inputHeight];
        resizedBitmap.getPixels(pixels, 0, inputWidth, 0, 0, inputWidth, inputHeight);
        for (int i = 0; i < pixels.length; i++) {
            final int val = pixels[i];
            output[i * 3] = ((val >> 16) & 0xFF) / 255.0f;
            output[i * 3 + 1] = ((val >> 8) & 0xFF) / 255.0f;
            output[i * 3 + 2] = (val & 0xFF) / 255.0f;
        }
        return output;
    }
}