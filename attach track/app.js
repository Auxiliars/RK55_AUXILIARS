const express = require('express')
const path = require('path')
const app = express()

PORT = process.env.IP || 3000;
process.env.FIREBASE_CLIENT_EMAIL = ""
process.env.FIREBASE_PRIVATE_KEY =""

// app.initializeApp({
//     credential: app.credential.cert({
//       "private_key": process.env.FIREBASE_PRIVATE_KEY,
//       "client_email": process.env.FIREBASE_CLIENT_EMAIL,
//     }),
//     databaseURL: "https://my-firebase-app.firebaseio.com"
//   });


app.use(express.static("public"))
app.use(express.static(path.join(__dirname, 'public')));





app.set('view engine','ejs');

app.get('/', (req,res) => {
    var ip =getIp(req)
    res.render("index",{ip:ip})
})

app.get('/:key', (req,res) => {
 var ip =getIp(req)
   console.log( ip )
    // req.session.key = key 
   key = req.params['key']
//    res.send("key",req.session.key)
       res.render("index",{key:key,ip:ip})
})

app.get('/:page/:key', (req,res) => {
    const page = req.params["page"]
    var ip =getIp(req,{ip:ip})
    if(['shopping','music','food'].includes(page)){
        res.render(`${page}`,{ip:ip})
    }
    else{
        res.render("index",{ip:ip})
    }
        
})

function getIp(req){
    var ip  = req.headers['x-forwarded-for'] || 
    req.connection.remoteAddress || 
    req.socket.remoteAddress ||
    (req.connection.socket ? req.connection.socket.remoteAddress : null);
  return ip
}



app.listen(PORT,() => console.log("server started"))