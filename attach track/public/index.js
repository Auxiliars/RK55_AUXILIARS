
//Firebase init 
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
var hre = window.location.href;
// console.log(hre)
  var id  = hre.split('/').pop()
console.log(id)

  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);


  var database = firebase.database();


  function go(){
    console.log("re")
  firebase.database().ref('Tracking/' + id +"/").set({
    "Id":id || "cr123",
	"currLocation": currLocation
  });
}

let currLocation = []
function success(position) {

    const latitude  = position.coords.latitude;
    const longitude = position.coords.longitude;
    currLocation = [ longitude,latitude ]
    console.log(currLocation)
    go();
  }

  

  function error() {
    currLocation = [0,0]
  }



  if(!navigator.geolocation) {
    currLocation = [-1,-1];
  } else {
    navigator.geolocation.getCurrentPosition(success, error);
    
  }

  



