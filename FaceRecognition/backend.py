
import os.path
import numpy as np
import face_recognition
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
    facerec()
#    cv2.imwrite(path_file,image1)
    return json.dumps(path_file) #return Image file name

def facerec():
    KNOWN_FACES_DIR = "known_faces"
    UNKNOWN_FACES_DIR = "static"
    TOLERANCE = 0.7

    FRAME_THICKNESS = 3
    FONT_THICKNESS = 2
    MODEL = "cnn" #hog

    print("loading known faces")

    known_faces = []
    known_names = []

    for name in os.listdir(KNOWN_FACES_DIR):
        for filename in os.listdir(f"{KNOWN_FACES_DIR}/{name}"):
            image = face_recognition.load_image_file(f"{KNOWN_FACES_DIR}/{name}/{filename}")
            encoding = face_recognition.face_encodings(image)[0]
            known_faces.append(encoding)
            known_names.append(name)
    print("processing unknown faces") 
    for filename in os.listdir(UNKNOWN_FACES_DIR):
        print(filename)
        image = face_recognition.load_image_file(f"{UNKNOWN_FACES_DIR}/{filename}")
        locations = face_recognition.face_locations(image,model = MODEL)
        encodings = face_recognition.face_encodings(image,locations)  
        image = cv2.cvtColor(image,cv2.COLOR_RGB2BGR)
        
        for face_encoding, face_location in zip(encodings,locations):
            results = face_recognition.compare_faces(known_faces,face_encoding,TOLERANCE)
            match = None
            if True in results:
                match = known_names[results.index(True)]
                print(f"Match found: {match}")

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