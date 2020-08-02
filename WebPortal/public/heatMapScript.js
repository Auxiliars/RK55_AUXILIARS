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
            
      var LocationArray = [];
      var init = true;
      var mapTypes = {"maps":['terrain','satellite','roadmap','hybrid'],"index":0}
      
    function LoadMap() {
      //Retraving Longitude and latitude from database
      var database = firebase.database();
     LocationArray.push(new google.maps.LatLng(21.247343 , 81.631776));
          var commentsRef =firebase.database().ref('criminal/');
          // commentsRef.on('value', gotData, errData);
        commentsRef.on("value", function(snapshot){
              snapshot.forEach(function(childSnapshot){
                  console.log(childSnapshot)
             var {locationLatitude,locationLongitude} = childSnapshot.val().location;
          
              console.log(locationLatitude ,locationLongitude);
              LocationArray.push(new google.maps.LatLng(locationLatitude, locationLongitude));
            })
          }).then(initMap())
          
         
         // mapLoad()
          // This example requires the Visualization library. Include the libraries=visualization
          // parameter when you first load the API. For example:
          // <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&libraries=visualization">
    
    }
    
      var map, heatmap;
            
          async function initMap() {
              map = new google.maps.Map(document.getElementById('map'), {
                zoom: 10,
                center: {lat:21.247343 , lng:81.631776},
                mapTypeId: mapTypes["maps"][mapTypes["index"]]
              });
      
              heatmap = new google.maps.visualization.HeatmapLayer({
                data: await getPoints(),
                map: map
              });
    
              //experiment
              addMarkers()
            }
      
            function toggleHeatmap() {
              heatmap.setMap(heatmap.getMap() ? null : map);
            }
      
            function changeGradient() {
              var gradient = [
                'rgba(0, 255, 255, 0)',
                'rgba(0, 255, 255, 1)',
                'rgba(0, 191, 255, 1)',
                'rgba(0, 127, 255, 1)',
                'rgba(0, 63, 255, 1)',
                'rgba(0, 0, 255, 1)',
                'rgba(0, 0, 223, 1)',
                'rgba(0, 0, 191, 1)',
                'rgba(0, 0, 159, 1)',
                'rgba(0, 0, 127, 1)',
                'rgba(63, 0, 91, 1)',
                'rgba(127, 0, 63, 1)',
                'rgba(191, 0, 31, 1)',
                'rgba(255, 0, 0, 1)'
              ]
              heatmap.set('gradient', heatmap.get('gradient') ? null : gradient);
            }
      
            function changeRadius() {
              heatmap.set('radius', heatmap.get('radius') ? null : 20);
            }
      
            function changeOpacity() {
              heatmap.set('opacity', heatmap.get('opacity') ? null : 0.2);
            }
            const addMarkers = () =>{
              setTimeout(()=>{
                const markers = LocationArray.map(point => {
                // point.setMap(map)
                return new google.maps.Marker({
                  position: new google.maps.LatLng(point.lat(),point.lng()),
                  map: map,
                  title:"yes"
                })
              })
              console.log(markers)
    
              },3000)
                      }
        
            // Heatmap data: 500 Points
          function getPoints() {
              console.log(LocationArray  + "W")
              return LocationArray;
            }

            //Toggle Map
        function toggleMap(){
            mapTypes["index"] = (mapTypes["index"] + 1)% mapTypes["maps"].length 
            map.setMapTypeId(mapTypes["maps"][mapTypes["index"]]);
        }
      
      
    
    