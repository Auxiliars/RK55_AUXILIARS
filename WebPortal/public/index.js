
    const firebaseConfig = {
  apiKey: "AIzaSyBghW9N54DAOpj2De3sKwO8SBcAxFQPX6M",
  authDomain: "auxiliarsweb.firebaseapp.com",
  databaseURL: "https://auxiliarsweb.firebaseio.com",
  projectId: "auxiliarsweb",
  storageBucket: "auxiliarsweb.appspot.com",
  messagingSenderId: "855340108370",
  appId: "1:855340108370:web:f9d1302479536fae28ad8b",
  measurementId: "G-XHJY4JZFN4"
};
var arrObj=[]

var inm = document.querySelector('#in');

firebase.initializeApp(firebaseConfig);

    var database = firebase.database();
    console.log(database)

//Initializing Variables
var ctx = document.getElementById('chart').getContext('2d');
    var ctx2 = document.getElementById('chart2').getContext('2d');
    var ref= database.ref('criminal')
    var citySet = new Map
    var stateSet = new Map
    var citySetValues = []
    var stateSetValues = []

    
//Getting data from Firebase    
    ref.on('value',gotData,errData);
    function gotData(data){
      data.forEach(element => {
        arrObj =[...arrObj,element.val()]
      });
      console.log(arrObj)
     
    //  initChart()
     
     arrObj.forEach(element => {
       if(citySet.hasOwnProperty(element.city)) citySet[element.city] += 1
       else citySet[element.city] = 1
         
        if(stateSet.hasOwnProperty(element.state)) stateSet[element.state] += 1
       else stateSet[element.state] = 1
     })
    //  citySet = [...citySet]
     console.log(citySet)
   
     initChart(ctx,citySet)
     initChart(ctx2,stateSet)
    }


    function errData(err){
      console.log(err)
    }

    function initChart(cont,set){
      var myChart = new Chart(cont, {
    type: 'bar',
    data: {
        
        labels: Object.keys(set),
        datasets: [{
            
            label: 'Crimes',
            data: Object.values(set),
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    }
});
myChart.render()
}




///Experiment Time

var clickHandler = ojb =>{
    console.log(obj)
}
    
// firebase.database().ref('/users/' + userId).once('value').then(function(snapshot) {
//   var username = (snapshot.val() && snapshot.val().username) || 'Anonymous';
  
// });

