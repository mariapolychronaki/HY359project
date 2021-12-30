function check_password() {
	var password_1 = document.getElementById("password1").value;
	var password_2 = document.getElementById("password2").value;
	var ok = true;

	if (password_1 != password_2) {
		document.getElementById("submit").disabled = true;
		document.getElementById("passmessage").innerHTML = "Passwords don't match";
		ok = false;
	} else {
		document.getElementById("submit").disabled = false;
		document.getElementById("passmessage").innerHTML = "";
		ok = true;
	}
	return ok;
}

function showPassword() {
	var x = document.getElementById("password1");
	if (x.type === "password") {
		x.type = "text";
	} else {
		x.type = "password";
	}
}
function checkPasswordValid() {
	var password_1 = document.getElementById("password1").value;
	var counter = 0;

	var patt = /([a-z])\1/i;
	var result = patt.test(password_1);
	console.log(result);

	var obj = {};
	var repeats = [];
	for (x = 0, length = password_1.length; x < length; x++) {
		var l = password_1.charAt(x);
		obj[l] = isNaN(obj[l]) ? 1 : obj[l] + 1;
	}

	for (let key in obj) {
		console.log(obj[key]);
		if (obj[key] >= password_1.length / 2) {
			document.getElementById("passwordMessage").innerHTML = "Weak password";
			document.getElementById("passwordMessage").style.display = "block";
			return;
		}
	}

	for (var i = 0; i < password_1.length; i++) {
		if (password_1.charAt(i) >= "0" && password_1.charAt(i) <= "9") {
			counter++;
		}
	}

	if (counter >= password_1.length / 2) {
		document.getElementById("passwordMessage").style.display = "block";
	} else {
		document.getElementById("passwordMessage").style.display = "block";
		document.getElementById("passwordMessage").innerHTML = "Medium password";
	}
}

function checkAMKA() {
	var birthDate = document.getElementById("birthDate").value;
	var AMKA = document.getElementById("AMKA").value;

	var year = birthDate.substring(2, birthDate.indexOf("-"));
	var yearIndex = birthDate.indexOf("-");
	var month = birthDate.substring(
		yearIndex + 1,
		birthDate.indexOf("-", yearIndex + 1)
	);
	var monthIndex = birthDate.indexOf("-", yearIndex + 1);
	var day = birthDate.substring(monthIndex + 1);

	var reversedDate = day + month + year;
	if (reversedDate == AMKA.substring(0, 6)) {
		document.getElementById("submit").disabled = false;
		document.getElementById("AMKAmessage").style.display = "none";
	} else {
		document.getElementById("AMKAmessage").style.display = "block";
		document.getElementById("submit").disabled = true;
	}
}

function checkType() {
	var selectedType = document.getElementById("type").value;

	if (selectedType == "Doctor") {
		document.getElementById("Specialty").style.display = "block";
		document.getElementById("addrLabel").innerHTML = "Office Address";
	} else {
		document.getElementById("Specialty").style.display = "none";
		document.getElementById("addrLabel").innerHTML = "Home Address";
	}
}

function checkTerms() {
	var x = document.getElementById("confirm").checked;
	if (x) {
		document.getElementById("submit").disabled = false;
	} else {
		document.getElementById("submit").disabled = true;
	}
}

function check() {
	//var map= new google.maps.Map(document.getElementById('map'), {zoom: 8, center: {lat: -34, lng: 150}});
	//var themap = document.getElementById("map");
	//document.getElementById("map").style.visibility = "hidden";
	var country = document.getElementById("country").value;
	var town = document.getElementById("town").value;
	var address = document.getElementById("addr").value;

	document.getElementById("map_div").style.display = "none";

	console.log(country, town, address);
	const data = {};

	var xhttp = new XMLHttpRequest();
	xhttp.withCredentials = true;
	xhttp.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			var json_response = this.response;
			if (json_response.length > 0) {
				if (!json_response[0].display_name.includes("Region of Crete")) {
					document.getElementById("warningCrete").style.display = "block";
				} else {
					document.getElementById("warningCrete").style.display = "none";

					var lon = json_response[0].lon;
					var lat = json_response[0].lat;
					document.getElementById("lat").value = lat;
					document.getElementById("lon").value = lon;

					console.log(json_response);

					document.getElementById("show").style.visibility = "visible";
					document
						.getElementById("show")
						.addEventListener("click", function() {
							var map = new OpenLayers.Map("map_div");
							var mapnik = new OpenLayers.Layer.OSM();
							map.addLayer(mapnik);
							document.getElementById("map_div").style.display = "block";

							function setPosition(lat, lon) {
								var fromProjection = new OpenLayers.Projection("EPSG:4326"); // Transform from WGS 1984
								var toProjection = new OpenLayers.Projection("EPSG:900913"); // to Spherical Mercator Projection
								var position = new OpenLayers.LonLat(lon, lat).transform(
									fromProjection,
									toProjection
								);
								return position;
							}

							function handler(position, message) {
								var popup = new OpenLayers.Popup.FramedCloud(
									"Popup",
									position,
									null,
									message,
									null,
									true // <-- true if we want a close (X) button, false otherwise
								);
								map.addPopup(popup);
								var div = document.getElementById("divID");
								div.innerHTML += "Energopoitihike o Handler<br>";
							}

							//Markers
							var markers = new OpenLayers.Layer.Markers("Markers");
							map.addLayer(markers);

							//Protos Marker
							var position = setPosition(lat, lon);
							var mar = new OpenLayers.Marker(position);
							markers.addMarker(mar);
							mar.events.register("mousedown", mar, function(evt) {
								handler(position, json_response[0].display_name);
							});

							//Orismos zoom
							const zoom = 16;
							map.setCenter(position, zoom);
						});
				}
			} else {
				document.getElementById("address_message").innerHTML = "Wrong Address";
			}
		}
	};
	xhttp.open(
		"GET",
		"https://forward-reverse-geocoding.p.rapidapi.com/v1/search?q=" +
		country +
		" " +
		town +
		" " +
		address +
		"&accept-language=en&polygon_threshold=0.0"
	);
	xhttp.responseType = "json";
	xhttp.setRequestHeader(
		"x-rapidapi-host",
		"forward-reverse-geocoding.p.rapidapi.com"
	);
	xhttp.setRequestHeader(
		"x-rapidapi-key",
		"4898357f6bmsh71d2d103cb18353p124c0fjsn4cde5c0cca54"
	);
	xhttp.send(data);
}

function open_frame() {
	if (document.getElementById("radio_yes").checked) {
		if (document.getElementById("username").checkValidity()) {
			document.getElementById("frame_msg").innerHTML = "";
			var mod = document.getElementById("frame1");

			mod.height = "550";
			mod.width = "500";
			mod.src = "index.html";
			mod.style.visibility = "visible";
		} else {
			document.getElementById("frame_msg").innerHTML =
				"Please give a correct user_name";
		}
	} else {
		var mod = document.getElementById("frame1");

		mod.height = "0";
		mod.width = "0";
		mod.src = "about:blank";
		mod.style.visibility = "hidden";
	}
}

function get_loc() {
	var lat;
	var lon;
	var x = document.getElementById("demo");
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			lat = position.coords.latitude;
			lon = position.coords.longitude;
			auto_complete(lat, lon);
		});
	} else {
		document.getElementById("demo").innerHTML = "No gps found on this device";
	}
}

function auto_complete(lat, lon) {
	var country;
	var city;
	var address;
	console.log(lat, lon);
	const data = null;

	var xhttp1 = new XMLHttpRequest();
	xhttp1.withCredentials = true;
	xhttp1.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			var resp = this.response;
			if (resp) {
				document.getElementById("locationMessage").style.display = "none";
				if (resp.address.road) {
					address = resp.address.road;
				} else {
					address = resp.address.neighbourhood;
				}
				country = resp.address.country;
				city = resp.address.county;
				console.log(address);
				console.log(country);
				console.log(city);
				document.getElementById("addr").value = address.toString();
				document.getElementById("town").value = city.toString();
				document.getElementById("country").value = country.toString();
			} else {
				document.getElementById("locationMessage").style.display = "block";
			}
		}
	};

	xhttp1.open(
		"GET",
		"https://forward-reverse-geocoding.p.rapidapi.com/v1/reverse?lat=" +
		lat +
		"&lon=" +
		lon +
		"&accept-language=en&polygon_threshold=0.0"
	);
	xhttp1.responseType = "json";
	xhttp1.setRequestHeader(
		"x-rapidapi-host",
		"forward-reverse-geocoding.p.rapidapi.com"
	);
	xhttp1.setRequestHeader(
		"x-rapidapi-key",
		"4898357f6bmsh71d2d103cb18353p124c0fjsn4cde5c0cca54"
	);
	xhttp1.send(data);
}
/*function showMap(lon, lat){
	var map = new OpenLayers.Map("map_div");
	map.addLayer(new OpenLayers.Layer.OSM());

	var lonLat = new OpenLayers.LonLat( lon , lat )
		  .transform(
			new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
			map.getProjectionObject() // to Spherical Mercator Projection
		  );
		  
	var zoom=16;

	var markers = new OpenLayers.Layer.Markers( "Markers" );
	map.addLayer(markers);
    
	markers.addMarker(new OpenLayers.Marker(lonLat));
    
	map.setCenter (lonLat, zoom);
	map.updateSize();
	document.getElementById("map_div").style.visibility = "visible";

}
*/

/*function check_addr(geocoder){
	var country = document.getElementById("country").value;
	var town = document.getElementById("town").value;
	var address = document.getElementById("addr").value;

		the_address = country+' '+town+' '+address;
		console.log(the_address);
		geocoder.geocode({'address': the_address}, function(results, status){
			if (status ==='OK') {
				document.getElementById("address_res").innerHTML = "Correct Address";
			}else{
				document.getElementById("address_res").innerHTML = "Wrong Address";
			}
		});
}

*/

function createUser() {

	let myForm = document.getElementById('loginForm');
	let formData = new FormData(myForm);

	const data = {};
	formData.forEach((value, key) => (data[key] = value));
	var jsonData = JSON.stringify(data);

	console.log(jsonData);


	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			const responseData = JSON.parse(xhr.responseText);
			console.log(responseData)
			if (responseData.doctor_id != null) {
				document.getElementById('ajaxContent').innerHTML = 'Request OK. Doctor : ' + responseData.username +' is added . Returned status of ' + xhr.status + "<br>";
			} else {
				document.getElementById('ajaxContent').innerHTML = 'Request OK. User : ' + responseData.username + ' is added . Returned status of ' + xhr.status + "<br>";
			}

		} else if (xhr.status !== 200) {
			document.getElementById('ajaxContent').innerHTML = 'Request failed ' + xhr.responseText + '. Returned status of ' + xhr.status + "<br>";
		}
	}
	xhr.open('POST', 'RegisterServlet');
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send(JSON.stringify(data));
}


function setChoicesForLoggedUser(data) {
	$("#choices").html("");
	$("#choices").append("<h1>User Details</h1>");
	$("#choices").append("<form id='tempForm' name='tempForm' >");
	$("#choices").append("<label id='emailt'>Email</label>");
	$("#choices").append("<input type='text' id='emailT' value='" + data.email +"' />");
	$("#choices").append("<label id='firstnamet'>First name</label>");
	$("#choices").append("<input type='text' id='firstnameT' value='" + data.firstName + "' />");
	$("#choices").append("<label id='lastnamet'>Last name</label>");
	$("#choices").append("<input type='text' id='lastnameT' value='" + data.lastName + "' />");
	$("#choices").append("<label id='passwordt'>Password</label>");
	$("#choices").append("<input type='password' id='passwordT' value='" + data.password + "' />");
	$("#choices").append("<label id='birthdatet'>Birth date</label>");
	$("#choices").append("<input type='date' id='birthdateT' value='" + data.birthdate + "' />");
	$("#choices").append("<label id='countryt'>Country</label>");
	$("#choices").append("<input type='text' id='countryT' value='" + data.country + "' />");
	$("#choices").append("<label id='cityt'>City</label>");
	$("#choices").append("<input type='text' id='cityT' value='" + data.city + "' />");
	$("#choices").append("<label id='addresst'>Address</label>");
	$("#choices").append("<input type='text' id='addressT' value='" + data.address + "' />");
	$("#choices").append("<label id='telephonet'>Telephone</label>");
	$("#choices").append("<input type='number' id='telephoneT' value='" + data.telephone + "' />");
	$("#choices").append("<label id='heightt'>Height</label>");
	$("#choices").append("<input type='number' id='heightT' value='" + data.height + "' />");
	$("#choices").append("<label id='weightt'>Weight</label>");
	$("#choices").append("<input type='number' id='weightT' value='" + data.weight + "' />");
	$("#choices").append("<label id='bloodtypet'>Blood type</label>");
	$("#choices").append("<input type='text' id='bloodtypeT' value='" + data.bloodtype + "' />");
	$("#choices").append("<label id='gendert'>Gender</label>");
	$("#choices").append("<input type='text' id='genderT' value='" + data.gender + "' />");
	$("#choices").append("<button id='submit' type='button' value='Update' class='btn' onclick='updateUser(\"" +data.username + "\")' >Update</button>");
	$("#choices").append("</form>");
	$("#choices").append("<div><button onclick='getBMI()'>Get BMI</button></div>");
	$("#choices").append("<div><button onclick='getIdealWeight()'>Get ideal weight</button></div>");
	$("#choices").append("<div><button onclick='getDoctors()'>Get certified doctors</button></div>");


}

function updateUser(username) {
	const formData = {
		username: username,
		email: document.getElementById("emailT").value,
		firstName: document.getElementById("firstnameT").value,
		lastName: document.getElementById("lastnameT").value,
		password: document.getElementById("passwordT").value,
		birthdate: document.getElementById("birthdateT").value,
		country: document.getElementById("countryT").value,
		city: document.getElementById("cityT").value,
		address: document.getElementById("addressT").value,
		telephone: document.getElementById("telephoneT").value,
		height: document.getElementById("heightT").value,
		weight: document.getElementById("weightT").value,
		bloodtype: document.getElementById("bloodtypeT").value,
		gender: document.getElementById("genderT").value,

	}
	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		var responseData;
		if (xhr.readyState === 4 && xhr.status === 200) {
			responseData = JSON.parse(xhr.responseText);
			$("#ajaxContent").html("Successful update " +responseData.username);
		} else if (xhr.status !== 200) {
			responseData = JSON.parse(xhr.responseText);
			$("#error").html("Wrong Credentials. " + responseData.error);
			//('Request failed. Returned status of ' + xhr.status);
		}
	};

	var data = formData
	console.log(formData)
	xhr.open('POST', 'UpdateServlet');
	xhr.setRequestHeader('Content-type', 'application/json');
	xhr.send(JSON.stringify(formData));
}


function loginPOST() {
	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		var responseData;
		if (xhr.readyState === 4 && xhr.status === 200) {
			responseData = JSON.parse(xhr.responseText);
			document.getElementById("logout").style.display = "flex";
			setChoicesForLoggedUser(responseData);
			$("#ajaxContent").html("Successful Login. Welcome " + responseData.username);
		} else if (xhr.status !== 200) {
			responseData = JSON.parse(xhr.responseText);
			$("#error").html("Wrong Credentials. " + responseData.error);
			//('Request failed. Returned status of ' + xhr.status);
		}
	};
	var data = $('#loginForm').serialize();
	xhr.open('POST', 'LoginServlet');
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhr.send(data);
}

function logout() {
	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			$("#ajaxContent").html("Successful Logout");
		} else if (xhr.status !== 200) {
			alert('Request failed. Returned status of ' + xhr.status);
		}
	};
	xhr.open('POST', 'LogoutServlet');
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhr.send();
}

function showRegistrationForm() {
	$("#ajaxContent").load("register.html");
}

function showLogin() {
	$("#ajaxContent").load("login.html");
}


function _calculateAge(birthday) { // birthday is a date
	var year = birthday.substring(0, birthday.indexOf("-"));
	return new Date().getFullYear() - year;
}

function getBMI() {
	const data = null;
	let weight = document.getElementById("weightT").value;
	let height = document.getElementById("heightT").value;
	let age = document.getElementById("birthdateT").value;

	age = _calculateAge(age);

	console.log(age)



	const xhr = new XMLHttpRequest();
	xhr.withCredentials = true;

	xhr.addEventListener("readystatechange", function() {
		if (this.readyState === this.DONE) {

			var responseData = JSON.parse(this.responseText);
			console.log(responseData);
			$("#ajaxContent").html("<div>BMI: " + responseData.data.bmi + "</div>");
		}
	});


	xhr.open("GET", "https://fitness-calculator.p.rapidapi.com/bmi?age=" + age + "&weight=" + weight + "&height=" + height);
	xhr.setRequestHeader("x-rapidapi-host", "fitness-calculator.p.rapidapi.com");
	xhr.setRequestHeader("x-rapidapi-key", "4898357f6bmsh71d2d103cb18353p124c0fjsn4cde5c0cca54");

	xhr.send(data);
}


function getIdealWeight() {

	const data = null;
	
	let height = document.getElementById("heightT").value;
	let gender = document.getElementById("genderT").value;


	const xhr = new XMLHttpRequest();
	xhr.withCredentials = true;

	xhr.addEventListener("readystatechange", function() {
		if (this.readyState === this.DONE) {
			var responseData = JSON.parse(this.responseText);
			console.log(responseData);
			$("#ajaxContent").html("<div>Ideal weight: </div>");
			for (const [key, value] of Object.entries(responseData.data)) {
  				$("#ajaxContent").append("<div>"+`${key}: ${value}`+"</div>");
			}
			
		}
	});

	xhr.open("GET", "https://fitness-calculator.p.rapidapi.com/idealweight?gender="+gender+"&height="+height);
	xhr.setRequestHeader("x-rapidapi-host", "fitness-calculator.p.rapidapi.com");
	xhr.setRequestHeader("x-rapidapi-key", "4898357f6bmsh71d2d103cb18353p124c0fjsn4cde5c0cca54");

	xhr.send(data);

}

function createTableFromJSON(data) {
	var html="";
	 
	for(let i=0; i<data.length; i++){
		html += "<div><table><tr><th>Category</th><th>Value</th></tr>";
		for (const x in data[i]) {
			var category=x;
			var value=data[i][x];
			html += "<tr><td>" + category + "</td><td>" + value + "</td></tr>";
		}
	}	
	
	html += "</table></div>";
	return html;
}

function getDoctors() {
	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		var responseData;
		if (xhr.readyState === 4 && xhr.status === 200) {
			responseData = JSON.parse(xhr.responseText);
			console.log(responseData);
			$("#ajaxContent").html(createTableFromJSON(responseData));
		}
	};
	var data = null;
	xhr.open('POST', 'GetDoctorsServlet');
	xhr.setRequestHeader('Content-type', 'application/json');
	xhr.send(data);
}

function adminloginPOST(){
		var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		var responseData;
		if (xhr.readyState === 4 && xhr.status === 200) {
			responseData = JSON.parse(xhr.responseText);
			document.getElementById("logout").style.display = "flex";
			//setChoicesForLoggedUser(responseData);
			$("#ajaxContent").html(responseData.OK);
		} else if (xhr.status !== 200) {
			responseData = JSON.parse(xhr.responseText);
			$("#error").html(responseData.error);
			//('Request failed. Returned status of ' + xhr.status);
		}
	};
	var data = $('#adminloginForm').serialize();
	xhr.open('POST', 'AdminLoginServlet');
	console.log(data);
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhr.send(data);
}

function adminshowLogin(){
	$("#ajaxContent").load("adminLogin.html");
}
