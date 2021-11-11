//
// Created by yuanhao on 20-6-10.
//

#ifndef LIVEBODYEXAMPLE_FACE_DETECTOR_H
#define LIVEBODYEXAMPLE_FACE_DETECTOR_H

#include <opencv2/core/mat.hpp>
#include "../include/ncnn/net.h"
#include "../definition.h"


class FaceDetector {
public:
    FaceDetector();

    ~FaceDetector();

    void SetMinFaceSize(int size);

    int LoadModel(AAssetManager* assetManager);

    int Detect(cv::Mat& src, std::vector<FaceBox>& boxes);

private:
    ncnn::Net net_;
    ncnn::Net face_net_;
    ncnn::Net attr_net_;
    int input_size_ = 192;
    const std::string net_input_name_ = "data";
    const std::string net_output_name_ = "detection_out";
    const std::string face_net_input_name = "data";
    const std::string face_net_output_name = "mobileclip0_feature_flatten0_flatten0";
    ncnn::Option option_;
    float threshold_;
    const float mean_val_[3] = {104.f, 117.f, 123.f};
    int thread_num_;
    int min_face_size_;
    int face_det_h = 224;
    int face_det_w = 224;

    int face_rec_det_h = 112;
    int face_rec_det_w = 112;

    int face_attr_det_w = 64;
    int face_attr_det_h = 64;
};



#endif //LIVEBODYEXAMPLE_FACE_DETECTOR_H
