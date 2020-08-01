  // Your web app's Firebase configuration
  var firebaseConfig = {
    apiKey: "AIzaSyBghW9N54DAOpj2De3sKwO8SBcAxFQPX6M",
    authDomain: "auxiliarsweb.firebaseapp.com",
    databaseURL: "https://auxiliarsweb.firebaseio.com",
    projectId: "auxiliarsweb",
    storageBucket: "auxiliarsweb.appspot.com",
    messagingSenderId: "855340108370",
    appId: "1:855340108370:web:f9d1302479536fae28ad8b",
    measurementId: "G-XHJY4JZFN4"
  };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  firebase.analytics();
  var database = firebase.database();

const inputZip = document.getElementById('zipcode')
const inputState = document.getElementById('state')
const inputCity =  document.getElementById('city')
const formSubmit = document.getElementById('details_submit')
// let firstName,lastName,dob,age,contactNumber,zipcode,state,city,subject;
//For Api Reference Checkout 'http://www.postalpincode.in/Api-Details'
urlZip = "https://api.postalpincode.in/pincode/"
let city ="",state="";
inputZip.addEventListener('keypress',(e)=>{
    
    console.log(e.key)
    if(parseInt(e.key)>=0 && parseInt(e.key) <=9){
       
    }
    if(inputZip.value.length>=6){
        inputZip.value = inputZip.value.substr(0,5)
    }
})
inputZip.addEventListener('blur',()=>{
    
    let urlZipreq = urlZip+inputZip.value;
    const Http = new XMLHttpRequest();
    
    Http.open("GET", urlZipreq);
    Http.send();
    
    Http.onreadystatechange = (e) => {
      var resp = JSON.parse(Http.responseText)[0]["PostOffice"][0]
      city = resp["Block"]
      state = resp["State"]

      inputCity.value = city
      inputState.value = state
    }
})

//Get all Values From Form
formSubmit.addEventListener('submit',(e)=>{
  e.preventDefault()
  firstName = formSubmit.elements["firstname"].value
  lastName = formSubmit.elements["lastname"].value
  dob = formSubmit.elements["dob"].value
  age = parseInt(formSubmit.elements["age"].value)
  contactNumber = parseInt(formSubmit.elements[ "contactNumber"].value)
  locationLatitude = parseFloat(formSubmit.elements[ "locationLatitude"].value)
  locationLongitude = parseFloat(formSubmit.elements[ "locationLongitude"].value)
  zipcode = parseInt(formSubmit.elements["zipcode"].value)
  state = formSubmit.elements["state"].value
  city = formSubmit.elements["city"].value
  subject = formSubmit.elements["subject"].value
  

// var ref = firebase.database().ref();

// ref.on("value", function(snapshot) {
//   var number = snapshot.child("criminal");
//    console.log(snapshot.val());
// }, function (error) {
//    console.log("Error: " + error.code);
// });
//var reportNumber ;
var data;
var commentsRef = firebase.database().ref('criminal/');
// commentsRef.on('value', gotData, errData);
commentsRef.on("value", function (snapshot){
  snapshot.forEach(function (childSnapshot){
   data = childSnapshot.val().reportNumber+1;
    console.log(data);
  })
})

console.log(data);

  firebase.database().ref('criminal/').push({
    "reportNumber" : data,
    "firstName" : firstName,
    "lastName":lastName,
    "dob":dob,
    "age":age,
    "contactNumber":contactNumber,
    "location" : {"locationLatitude": locationLatitude,
    "locationLongitude":locationLongitude
  },
    "zipcode": zipcode,
    "state":state,
    "city":city,
    "subject":subject,
  });



})



//use firstName,lastName,dob,age,contactNumbet.zipcode,state,city,subject for reffering to variables
// Get a reference to the database service


