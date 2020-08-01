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
//Firebase

var commentsRef =firebase.database().ref('criminals');   
var data = [];      
commentsRef.on("value", function(snapshot){
              snapshot.forEach(function(childSnapshot){
              data.push( childSnapshot.val());
          
            //   console.log(data);
            //   debugger
            })
          })


function convertToCSV(objArray) {
    var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
    var str = '';

    for (var i = 0; i < array.length; i++) {
        var line = '';
        for (var index in array[i]) {
            if (line != '') line += ','

            line += array[i][index];
        }

        str += line + '\r\n';
    }

    return str;
}

function exportCSVFile(headers, items, fileTitle) {
    if (headers) {
        items.unshift(headers);
    }

    // Convert Object to JSON
    var jsonObject = JSON.stringify(items);

    var csv = this.convertToCSV(jsonObject);

    var exportedFilenmae = fileTitle + '.csv' || 'export.csv';

    var blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    if (navigator.msSaveBlob) { // IE 10+
        navigator.msSaveBlob(blob, exportedFilenmae);
    } else {
        var link = document.createElement("a");
        if (link.download !== undefined) { // feature detection
            // Browsers that support HTML5 download attribute
            var url = URL.createObjectURL(blob);
            link.setAttribute("href", url);
            link.setAttribute("download", exportedFilenmae);
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }
    }
}



function download(){
  var headers = {
      name: 'Name'.replace(/,/g, ''), // remove commas to avoid errors
      age: "Age",
      dob: "DOB",
      authentication: "Authentication Done",
      phone:"Phone",
      email:"Email",
      lastLocation:"Last Known Location"
  };

  var itemsFormatted = [];
    data.forEach((item) => {
    // console.log(item)
    itemsFormatted.push({
        name: item["name"].replace(/,/g, ''), // remove commas to avoid errors
        age: item["age"],
        dob: item["dob"],
        authentication: item["authentication"],
        phone:item["phone"] || "NA",
        email:item["email"] || "NA",
        lastLocation:item["location"]["l01"] +" " + item["location"]["l02"]
    })
  });
  var date = new Date();
  var fileTitle = 'Criminal Details__' + date.getDate() + "_" + (date.getMonth()+1) +  "_" + date.getFullYear(); // or 'my-unique-title'

  exportCSVFile(headers, itemsFormatted, fileTitle); // call the exportCSVFile() function to process the JSON and trigger the download
}