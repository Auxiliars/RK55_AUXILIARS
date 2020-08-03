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
      // firebase.analytics();
            
      var LocationArray = [];
      var init = true;
      var mapTypes = {"maps":['terrain','satellite','roadmap','hybrid'],"index":0}
      const idBtn = document.getElementById('getid')
      const idBox = document.getElementById('targetId')
      var lat=0.0
      var longi = 0.0
      var OS,ip;

     function getId(){
       var ID= idBox.value
       console.log(ID)
       LoadMap(ID)
     }

    function LoadMap(ID) {
      //Retraving Longitude and latitude from database
      var database = firebase.database();
      
          var commentsRef =firebase.database().ref(`Tracking/${ID}`);
          // commentsRef.on('value', gotData, errData);
        commentsRef.on("value", function(snapshot){
          lat = snapshot.val()["currLocation"][0]
          longi = snapshot.val()["currLocation"][1]
          OS = snapshot.val()["OS"]
          IP = snapshot.val()["IP"]

              console.log(lat,longi)
              addMarkers(lat,longi,ID)

              alert(`ID:${ID} \n Latitude : ${lat} \n Longitude:${longi} \n OS: ${OS} \n IPv6 : ${IP}`)
            })
          
          
         
         // mapLoad()
          // This example requires the Visualization library. Include the libraries=visualization
          // parameter when you first load the API. For example:
          // <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&libraries=visualization">
    
    }
    
      var map, heatmap;
            
          function initMap() {
              // The location of Uluru
              
              // The map, centered at Uluru
              var map = new google.maps.Map(
                  document.getElementById('map'), {zoom:6, center: {lat:21.247343 , lng:81.631776}});
              // The marker, positioned at Uluru
            }
      
        
            const addMarkers = (latm,longi,ID) =>{
              
              
              
              // console.log(latm,longi +" as")
              // var uluru = {lat:parseFloat(latm),lng:parseFloat(longi)}
              // var marker = new google.maps.Marker({position: uluru, map: map});
              const myLatLng = { lat: longi, lng: latm };

              const map = new google.maps.Map(document.getElementById("map"), {
                zoom: 9,
                center: myLatLng
              });
              new google.maps.Marker({
                position: myLatLng,
                map,
                title: ID
              });
              
            }
        
      

            //Toggle Map
        function toggleMap(){
            mapTypes["index"] = (mapTypes["index"] + 1)% mapTypes["maps"].length 
            map.setMapTypeId(mapTypes["maps"][mapTypes["index"]]);
        }
      
      
    
    