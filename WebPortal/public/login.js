

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
  if (!firebase.apps.length) {
    firebase.initializeApp(firebaseConfig);
 }
  
  
  firebase.analytics();
  var database = firebase.database();

  var nameField = document.getElementById('userName')
  var passField = document.getElementById('password')
  var positionField = document.getElementById('position')
  var submitButton = document.getElementById('submit-btn')
  
  submitButton.addEventListener('click',(e) =>{
      e.preventDefault()
      document.getElementById('login-form')
      var name = nameField.value;
      var pass = passField.value;
      var position = positionField.value;
    console.log(name+pass+position);
    
// // var ref = firebase.database().ref();

// // ref.on("value", function(snapshot) {
// //   var number = snapshot.child("criminal");
// //    console.log(snapshot.val());
// // }, function (error) {
// //    console.log("Error: " + error.code);
// // });
// //var reportNumber ;
// var data;
// var commentsRef = firebase.database().ref('accounts/');
// // commentsRef.on('value', gotData, errData);
// commentsRef.on("value", function (snapshot){
//   snapshot.forEach(function (childSnapshot){
//    data = childSnapshot.val().reportNumber+1;
//     console.log(data);
//   })
// })

//Calling from database

    if(position==="admin"){
      if(name==="ad007" & pass=="1234"){
        window.open("homepage.html","_self");
      }
      else{
        alert("Password or User name is wrong");
      }
    }
    else if(position ==="police"){
      if(name =="pc01" && pass =="123"){
        window.open("policeHomepage.html","_self");
      } 
      else{
        alert("Password or User name is wrong");
      }
     
    }
  });
  
  
  