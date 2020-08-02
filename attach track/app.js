const express = require('express')
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





app.set('view engine','ejs');

app.get('/', (req,res) => {
    res.render("index")
})

app.get('/:key', (req,res) => {
    // req.session.key = key 
   key = req.params['key']
//    res.send("key",req.session.key)
       res.render("index",{key:key})
})

app.get('/:page/:key', (req,res) => {
    const page = req.params["page"]
        res.render(`${page}`)
})




app.listen(PORT,() => console.log("server started"))