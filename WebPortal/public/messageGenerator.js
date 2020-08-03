var Name = document.getElementById('name')
var Theme = document.getElementById('theme')
var Id = document.getElementById('id')
var submitButton = document.getElementById('submit-btn')
var startText ="";
var middleText="";
var lastText="localhost:3000/";


submitButton.addEventListener('click',(e) =>{
    e.preventDefault()
    document.getElementById('sg-generator-form')
    var name = Name.value;
    var theme = Theme.value;
    var id = Id.value;
  console.log(name+theme+id);
  if(theme=="music"){
    middleText = " Music to me is like breathing - I don't get tired of breathing, I don't get tired of music. Here's a website for you. Listen to your fav songs click this link ";
}
else if(theme=="shopping"){
    middleText = " here's a website for you. Checkout the new stock of your fav product "
}
else if(theme=="quiz"){
    middleText = " here's a website for you. Checkout the new quiz of your fav product "
}
else if(theme=="glossary"){
    middleText = " here's a website for you. Checkout the new stock in the glossary "
}
else if(theme=="food"){
    middleText = " Quick! The hot light is on! Come in a get a fresh donut and we'll give you a free cup of a coffe. Offer only good for the next three hours. "
}
else if(theme=="game"){
    middleText = " here's a website for you. Checkout the new game for you "
}


  if(name==""){
    startText = "Hey User";
}else if(!name==""){
    startText = "Hey "+name;
}

  document.getElementById("demo").innerHTML = startText+middleText+"http://"+lastText+theme+"/"+id;
});