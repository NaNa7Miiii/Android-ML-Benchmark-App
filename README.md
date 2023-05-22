# Android-ML-Benchmark-App
This project aims to calculate the latency of transformer models in android devices. 

# Current Models included
| Model Name            | Input size | Top 1 Accuracy (%) | # of Parameters (M) | FLOPs (G) | # of Activations (million) | Model Link                                                                                 |
|-----------------------|------------|--------------------|---------------------|-----------|----------------------------|--------------------------------------------------------------------------------------------|
| Nextvit_small         | 224        | 82.5               | 31.763              | 5.811     | 18.4387                    | https://github.com/bytedance/Next-ViT                                                      |
| Nextvit_base          | 224        | 83.2               | 44.819              | 8.294     | 23.7119                    | https://github.com/bytedance/Next-ViT                                                      |
| Nextvit_large         | 224        | 83.6               | 57.874              | 10.777    | 28.99                      | https://github.com/bytedance/Next-ViT                                                      |
| Efficientformer_v1_L1 | 224        | 80.2               | 12.29               | 1.302     | 5.53                       | https://github.com/snap-research/EfficientFormer                                           |
| Efficientformer_v1_L3 | 224        | 82.4               | 31.406              | 3.929     | 12                         | https://github.com/snap-research/EfficientFormer                                           |
| Efficientformer_v1_L7 | 224        | 83.3               | 82.229              | 10.166    | 24.45                      | https://github.com/snap-research/EfficientFormer                                           |
| Efficientformer_v2_S0 | 224        | 76.2               | 3.6                 | 0.407     | 5.3                        | https://github.com/snap-research/EfficientFormer                                           |
| Efficientformer_v2_S1 | 224        | 79.7               | 6.186               | 0.668     | 7.66                       | https://github.com/snap-research/EfficientFormer                                           |
| Efficientformer_v2_S2 | 224        | 82                 | 12.71               | 1.272     | 11.77                      | https://github.com/snap-research/EfficientFormer                                           |
| Efficientformer_v2_L  | 224        | 83.5               | 26.322              | 2.952     | 18.54                      | https://github.com/snap-research/EfficientFormer                                           |
| Efficientnet_b0       | 224        | 77.692             | 5.289               | 0.422     | 6.75                       | https://pytorch.org/vision/main/models/generated/torchvision.models.efficientnet_b0.html   |
| Efficientnet_b1       | 240        | 78.642             | 7.794               | 0.62      | 9.36                       | https://pytorch.org/vision/main/models/generated/torchvision.models.efficientnet_b1.html   |
| Efficientnet_b2       | 260        | 80.608             | 9.11                | 0.712     | 9.81                       | https://pytorch.org/vision/master/models/generated/torchvision.models.efficientnet_b2.html |
| Efficientnet_b3       | 300        | 82.008             | 12.233              | 1.033     | 13.01                      | https://pytorch.org/vision/main/models/generated/torchvision.models.efficientnet_b3.html   |
| Efficientnet_b4       | 380        | 83.384             | 19.342              | 1.596     | 17.05                      | https://pytorch.org/vision/main/models/generated/torchvision.models.efficientnet_b4.html   |
| Efficientnet_b5       | 456        | 83.444             | 30.39               | 2.483     | 23.42                      | https://pytorch.org/vision/main/models/generated/torchvision.models.efficientnet_b5.html   |
| Efficientnet_b6       | 528        | 84.008             | 43.041              | 3.522     | 29.97                      | https://pytorch.org/vision/main/models/generated/torchvision.models.efficientnet_b6.html   |
| Efficientnet_b7       | 600        | 84.122             | 66.348              | 5.387     | 40.09                      | https://pytorch.org/vision/main/models/generated/torchvision.models.efficientnet_b7.html   |
| Efficientnet_v2_s     | 224        | 84.228             | 21.458              | 2.878     | 12.19                      | https://pytorch.org/vision/main/models/efficientnetv2.html                                 |
| Efficientnet_v2_m     | 224        | 85.112             | 54.139              | 5.407     | 19.62                      | https://pytorch.org/vision/main/models/efficientnetv2.html                                 |
| Efficientnet_v2_l     | 224        | 85.808             | 119                 | 12.31     | 34.5                       | https://pytorch.org/vision/main/models/efficientnetv2.html                                 |
| Mobilenet_v1          | 224        | 70.9               | 4.233               | 0.579     | 5.04                       | https://huggingface.co/docs/transformers/model_doc/mobilenet_v1#overview                   |
| Mobilenet_v2          | 224        | 71.88              | 3.505               | 0.314     | 6.68                       | https://pytorch.org/hub/pytorch_vision_mobilenet_v2/                                       |
| Mobilenet_v3_small    | 224        | 67.668             | 2.543               | 0.05965   | 1.42                       | https://pytorch.org/vision/main/models/mobilenetv3.html                                    |
| Mobilenet_v3_large    | 224        | 74.042             | 5.483               | 0.226     | 4.41                       | https://pytorch.org/vision/main/models/mobilenetv3.html                                    |
| Mobilevit-s           | 224        | 78.4               | 5.579               | 1.547     | 14.94                      | https://huggingface.co/apple/mobilevit-small                                               |
| Mobilevit-xs          | 224        | 74.8               | 2.318               | 0.793     | 12.14                      | https://huggingface.co/apple/mobilevit-x-small                                             |
| Mobilevit-xxs         | 224        | 69                 | 1.272               | 0.31      | 5.99                       | https://huggingface.co/apple/mobilevit-xx-small                                            |
| mobilevit_v2_050      | 256        | 70.2               | 1.371               | 0.48      | 8.04                       | https://huggingface.co/timm/mobilevitv2_050.cvnets_in1k                                    |
| mobilevit_v2_100      | 256        | 78.1               | 4.902               | 1.844     | 16.08                      | https://huggingface.co/timm/mobilevitv2_100.cvnets_in1k                                    |
| mobilevit_v2_150      | 256        | 80.4               | 10.595              | 4.09      | 24.11                      | https://huggingface.co/timm/mobilevitv2_150.cvnets_in1k                                    |
| MobileOne_s0          | 224        | 71.4               | 2.079               | 0.275     | 3.79                       | https://github.com/apple/ml-mobileone                                                      |
| MobileOne_s1          | 224        | 75.9               | 4.765               | 0.825     | 6.27                       | https://github.com/apple/ml-mobileone                                                      |
| MobileOne_s2          | 224        | 77.4               | 7.808               | 1.299     | 7.56                       | https://github.com/apple/ml-mobileone                                                      |
| MobileOne_s3          | 224        | 78.1               | 10.078              | 1.896     | 9.13                       | https://github.com/apple/ml-mobileone                                                      |
| MobileOne_S4          | 224        | 79.4               | 14.838              | 2.979     | 11.81                      | https://github.com/apple/ml-mobileone                                                      |

For the calculation of flops, parameters, and activations, I use the [fvcore](https://github.com/facebookresearch/fvcore/blob/main/docs/flop_count.md) library.
The image dataset I use comes from this [Tensorflow Tutorial](https://www.tensorflow.org/tutorials/images/classification), feel free to drag your own images to the folder flowers
This App provides a default model for [Mobilevit-xxs](https://huggingface.co/apple/mobilevit-xx-small), and for the other models please follow this [onnxruntime tutorial](https://onnxruntime.ai/docs/get-started/) to convert them into onnx format.
