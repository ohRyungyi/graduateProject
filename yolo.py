# -*- coding: utf-8 -*-
import cv2
import numpy as np

net = cv2.dnn.readNet("/home/ec2-user/foodrecommender/yolo/yolov4.cfg", "/home/ec2-user/foodrecommender/yolo/yolov4_14900.weights")
#net = cv2.dnn.readNet("E:/YOLOv4/darknet/cfg/testcfg/yolov4.cfg", "E:/YOLOv4/darknet/cfg/yolov4.weights")
classes = []
with open("/home/ec2-user/foodrecommender/yolo/obj.names" , "r") as f:
#with open("E:/YOLOv4/darknet/build/darknet/x64/data/obj.names" , "r") as f:
    classes = [line.strip() for line in f.readlines()]
layer_names = net.getLayerNames()
output_layers = [layer_names[i[0] - 1] for i in  net.getUnconnectedOutLayers()]
colors = np.random.uniform(0, 255, size=(500,3))

img = cv2.imread("/home/ec2-user/foodrecommender/test/test3.jpg")
#img = cv2.imread("/gdrive/MyDrive/darknet/bin/darknet/data/test/garlic_test10.jpg")
#img = cv2.imread("/gdrive/MyDrive/darknet/bin/darknet/data/test/cabbage_test10.jpg")
#img = cv2.imread("/gdrive/MyDrive/darknet/bin/darknet/data/test/cucumber_test10.jpg")
#img = cv2.imread("/gdrive/MyDrive/darknet/bin/darknet/data/test/meat_test10.jpg")
#img = cv2.imread("/gdrive/MyDrive/darknet/bin/darknet/data/test/carrot_test10.jpg")
#img = cv2.imread("/gdrive/MyDrive/darknet/bin/darknet/data/test/leek_test8.jpg")
#img = cv2.imread("/gdrive/MyDrive/darknet/bin/darknet/data/test/onion_test6.jpg")
#img = cv2.imread("/gdrive/MyDrive/darknet/bin/darknet/data/test/mushroom_test10.jpg")
#img = cv2.imread("/gdrive/MyDrive/darknet/bin/darknet/data/test/piment_test10.jpg")
#img = cv2.imread("/gdrive/MyDrive/darknet/bin/darknet/data/test/potato_test10.jpg")
#img = cv2.imread("/gdrive/MyDrive/darknet/bin/darknet/data/test/radish_test10.jpg")
#img = cv2.imread("/gdrive/MyDrive/darknet/bin/darknet/data/test/leek_test9.jpg")

img = cv2.resize(img, None, fx=0.4, fy=0.4)
height, width, channels = img.shape

blob = cv2.dnn.blobFromImage(img, 0.00392, (416, 416), (0, 0, 0), True, crop=False)
net.setInput(blob)
outs = net.forward(output_layers)

class_ids = []
confidences = []
boxes = []
for out in outs:
    for detection in out:
        scores = detection[5:]
        class_id = np.argmax(scores)
        confidence = scores[class_id]
        if confidence > 0.5:
            # Object detected
            center_x = int(detection[0] * width)
            center_y = int(detection[1] * height)
            w = int(detection[2] * width)
            h = int(detection[3] * height)
            # 좌표
            x = int(center_x - w / 2)
            y = int(center_y - h / 2)
            boxes.append([x, y, w, h])
            confidences.append(float(confidence))
            class_ids.append(class_id)

indexes = cv2.dnn.NMSBoxes(boxes, confidences, 0.6, 0.4)
font = cv2.FONT_HERSHEY_PLAIN
labels = []
for i in range(len(boxes)):
    if i in indexes:
        x, y, w, h = boxes[i]
        label = str(classes[class_ids[i]])
        labels.append(label)
        color = colors[i]
        cv2.rectangle(img, (x, y), (x + w, y + h), color, 2)
        cv2.putText(img, label, (x, y + 30), font, 3, color, 3)

#print(len(labels))
labels_set = set(labels)
labels_eng = list(labels_set)
labels_kor = []
for eng in labels_eng:
    if eng == 'potato':
        labels_kor.append('감자')
    elif eng == 'garlic':
        labels_kor.append('마늘')
    elif eng == 'radish':
        labels_kor.append('무')
    elif eng == 'meat':
        labels_kor.append('고기')
    elif eng == 'cabbage':
        labels_kor.append('양배추')
    elif eng == 'leek':
        labels_kor.append('파')
    elif eng == 'mushroom':
        labels_kor.append('버섯')
    elif eng == 'piment':
        labels_kor.append('피망')
    elif eng == 'cucumber':
        labels_kor.append('오이')
    elif eng == 'carrot':
        labels_kor.append('당근') 
#print(labels_eng)
#print(labels_kor)
#cv2_imshow(img)
cv2.waitKey(0)
cv2.destroyAllWindows()
