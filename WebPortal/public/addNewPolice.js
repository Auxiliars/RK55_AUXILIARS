    // Your web app's Firebase configuration

    var firebaseConfig = {
      apiKey: "AIzaSyD9RpT8KWF6LZwWBASy1OK2fx0NvpF9Cqg",
      authDomain: "auxiliars-c7b21.firebaseapp.com",
      databaseURL: "https://auxiliars-c7b21.firebaseio.com",
      projectId: "auxiliars-c7b21",
      storageBucket: "auxiliars-c7b21.appspot.com",
      messagingSenderId: "260085271639",
      appId: "1:260085271639:web:f71a4d4bf5ed999f8a2712",
      measurementId: "G-0F5SHD5H9C"
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
  userName = formSubmit.elements["userName"].value
  password = formSubmit.elements["password"].value
  zipcode = parseInt(formSubmit.elements["zipcode"].value)
  state = formSubmit.elements["state"].value
  city = formSubmit.elements["city"].value

// var ref = firebase.database().ref();

// ref.on("value", function(snapshot) {
//   var number = snapshot.child("criminal");
//    console.log(snapshot.val());
// }, function (error) {
//    console.log("Error: " + error.code);
// });
//var reportNumber ;
// var data;
// var commentsRef = firebase.database().ref('accounts/');
// // commentsRef.on('value', gotData, errData);
// commentsRef.on("value", function (snapshot){
//   snapshot.forEach(function (childSnapshot){
//    data = childSnapshot.val().reportNumber+1;
//     console.log(data);
//   })
// })
// var encryptedAES = CryptoJS.AES.encrypt("Message", "My Secret Passphrase");
  firebase.database().ref('Account/'+userName+"/").set({
    "Id":"1",
    "FirstName" : firstName,
    "LastName":lastName,
    "DoB":dob,
    "Age":age,
    // "encrypt":encryptedAES,
    "PhoneNumber":"+91"+contactNumber,
    "Password":password,
    "zipcode": zipcode,
    "state":state,
    "city":city,
  });

})

//use firstName,lastName,dob,age,contactNumbet.zipcode,state,city,subject for reffering to variables
// Get a reference to the database service
