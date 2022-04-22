function showValue(){
	var age=document.getElementById('range');
	var output=document.getElementById('agevalue');
	output.innerHTML=this.value;
	
}
function changeInput() { output.innerHTML = this.value;}


class Person {
	constructor(goal,weight,height,age,gender,activity){
		this.goal=goal;
		this.gender=gender;
		this.weight=weight;
		this.height=height;
		this.age=age;
		this.activity=activity;
	}
	count_calories_intake(weight,height,age,gender,activity,goal){
	var intake=0;
	if(this.gender==="male")
	{
		 intake=(10*this.weight+6.25*this.height-5*this.age+5)*this.activity;
	}
	else if(this.gender==="female")
	{
		 intake= (10*this.weight+6.25*this.height-5*this.age-161)*this.activity;
	}
	switch(this.goal){
		case 'l': intake=intake-intake*0.2;
		break;
		case 'm': 
		break;
		case 'g': intake=intake+intake*0.2;
		break;
	}
	return intake;
	}
	count_macros(){
	var macros=[0,0,0,0];
	var intake;
	intake=this.count_calories_intake(this.weight,this.height,this.age,this.gender,this.activity,this.goal);

	switch(this.goal){
		case 'l': 
					intake=intake-intake*0.2;
					macros[0]=(intake*0.4)/4;
					macros[1]=(intake*0.3)/9;
					macros[2]=(intake*0.3)/4;
					macros[3]=intake;
		break;
		case 'm':

				 	macros[0]=(intake*0.35)/4;
					macros[1]=(intake*0.3)/9;
					macros[2]=(intake*0.35)/4;
					macros[3]=intake;
		break;
		case 'g':
					intake=intake+intake*0.02;
				 	macros[0]=(intake*0.35)/4;
					macros[1]=(intake*0.25)/9;
					macros[2]=(intake*0.40)/4;
					macros[3]=intake;
		break;
	}
	return macros;
}
}

 function compute_results(){
	var p=new Person();
	p.goal=document.getElementsByClassName("goal")[0].value;
	try
	{
		p.age=document.getElementById("range").value;
		p.weight=document.getElementsByClassName("winput")[0].value;
		p.height=document.getElementsByClassName("hinput")[0].value;
		if(p.age==0)
			throw "AGE Cannot Be Zero";
		else if(p.weight==0 || isNaN(p.weight))
			throw "Wrong Weight Input";
		else if(p.height==0 || isNaN(p.height))
			throw "Wrong Height Input";
		else
		{
			p.gender=document.getElementsByClassName("genderchoices")[0].value;
	
	p.activity=document.getElementsByClassName("activity")[0].value;
	macros=p.count_macros();
	console.log(macros);
	document.getElementsByClassName("calories")[0].innerHTML="Calories";
	document.getElementsByClassName("proteins")[0].innerHTML="Proteins";
	document.getElementsByClassName("carbos")[0].innerHTML="Carbohydrates";
	document.getElementsByClassName("fats")[0].innerHTML="Fats";

	document.getElementsByClassName("calnum")[0].innerHTML=Math.ceil(macros[3])+" Kcal";
	document.getElementsByClassName("protnum")[0].innerHTML=Math.ceil(macros[0])+"g";
	document.getElementsByClassName("carbnum")[0].innerHTML=Math.ceil(macros[2])+"g";
	document.getElementsByClassName("fatnum")[0].innerHTML=Math.ceil(macros[1])+"g";

		}

	}
	catch(e)
	{
		
		alert("Error: "+e);
	}
	
	
}

