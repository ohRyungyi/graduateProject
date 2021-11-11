//
// Created by yuanhao on 20-6-9.
//

#ifndef LIVEBODYEXAMPLE_DEFINITION_H
#define LIVEBODYEXAMPLE_DEFINITION_H

#include <string>

#define JAVA_ARRAY_LIST_CLASSPATH           "java/util/ArrayList"
#define ANDROID_FACE_BOX_CLASSPATH          "com/mv/engine/FaceBox"

#define FACE_DETECTOR_METHOD(METHOD_NAME) \
    Java_com_mv_engine_FaceDetector_##METHOD_NAME


#define LIVE_METHOD(METHOD_NAME) \
    Java_com_mv_engine_Live_##METHOD_NAME

struct FaceRect {
    float data[128];
};

struct FaceAttr {
    float score;
    float land[10];
    float glass;
    float male;
    float smile;
    float hat;
    float age;
};

struct FaceBox {
    float confidence;
    float x1;
    float y1;
    float x2;
    float y2;
    unsigned int id;
    unsigned int score;
    float area;
    FaceAttr attr;
    FaceRect rec;
};

struct ModelConfig {
    float scale;
    float shift_x;
    float shift_y;
    int height;
    int width;
    std::string name;
    bool org_resize;
};

#ifndef MIN
#define MIN(a, b) ((a) < (b) ? (a) : (b))
#endif

#ifndef MAX
#define MAX(a, b) ((a) > (b) ? (a) : (b))
#endif

#ifndef MID
#define MID(a, b, c) ((a)<(b) ? ((b)<(c)?(b):(a)<(c)?(c):(a)) : ((b)>(c)?(b):(a)>(c)?(c):(a)))
#endif

#define MAX_FACE_NUM (30)
#define MAX_FACE_LEN (1+1+4+6+10+128) // id score x y w h,score glass male smile hat age land
//
//int face_det_w = 224;
//int face_det_h = 224;
//const int face_fpn_num = 3;
//const int face_anchor_num = 2;
//const int face_num_class = 0;
//const int face_channels_per_box = 4 + 1;
//const float face_anchor[6 * 2] = { 192.0, 240.0, 384.0, 480.0, 48.0, 60.0, 96.0, 120.0, 12.0, 15.0, 24.0, 30.0 };//feature 从小到大,对应Anchor从大到小
////const float anchor[6 * 2] = { 256.0, 320.0, 512.0, 640.0, 64.0, 80.0, 128.0, 160.0, 16.0, 20.0, 32.0, 40.0 };//feature 从小到大,对应Anchor从大到小
//float face_confidence_threshold = 0.4f;
//float face_nms_threshold = 0.5f;
//
//const int face_attr_det_w = 64;
//const int face_attr_det_h = 64;
//
//const int face_rec_det_w = 112;
//const int face_rec_det_h = 112;

#endif //LIVEBODYEXAMPLE_DEFINITION_H
