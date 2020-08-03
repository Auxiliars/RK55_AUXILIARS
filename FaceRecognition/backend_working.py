
import os.path
import numpy as np
import cv2
import json
from flask import Flask,request,Response
import uuid
from PIL import Image

#to detect face from image
def faceDetect(imgIn):
    face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + './haarcascade_frontalface_default.xml')
    gray = cv2.cvtColor(imgIn,cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray,1.3,5)
    
    for x,y,w,h in faces:
        img = cv2.rectangle(imgIn,(x,y),(x+w,y+h),(0,255,0))
#        data = np.array(img)
#        print(data)

    #save File
    image1 = img[y:y+h,x:x+h]
#    image1 = Image.fromarray(data[y:y+h,x:x+w],'RGB') 
    path_file = ('static/%s.jpg' %uuid.uuid4().hex)
#    cv2.imshow("Hey",image1)
    cv2.imwrite(path_file,image1)
#    cv2.imwrite(path_file,image1)
    return json.dumps(path_file) #return Image file name

#API
app = Flask(__name__)

#route
@app.route('/api/upload',methods=['POST'])
def upload():
    img = cv2.imdecode(np.fromstring(request.files['image'].read(),np.uint8),cv2.IMREAD_UNCHANGED)
    #process Image
    img_processed = faceDetect(img)
    #response
    return Response(response=img_processed,status=200,mimetype='application/json')

#Starting the server
app.run(host="0.0.0.0",port=5000)