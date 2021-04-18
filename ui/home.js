function getdistancediff(vin) {
    var distancediff = new XMLHttpRequest();
    distancediff.onreadystatechange = function() {
         if (this.readyState == 4 && this.status == 200) {
            document.getElementById("distancediff").innerHTML = this.response;
            document.getElementById("distancediffword").innerHTML = this.response >= 0 ? "Weniger": "Mehr";
         }
    };
    distancediff.open("GET", "http://raspberrypi:8080/getDistanceDiff?vin=" + vin, true);
    distancediff.setRequestHeader("Content-type", "application/json");
    distancediff.send();
}

function getdistancedifftrend(vin) {
    var distancedifftrend = new XMLHttpRequest();
    distancedifftrend.onreadystatechange = function() {
         if (this.readyState == 4 && this.status == 200) {
            var trend = JSON.parse(this.response).type;
            console.log(trend)
            var trendword = "";
            if (trend === "FALLING"){
                trendword = "fallend"
            }
            if (trend === "RISING"){
                trendword = "steigend"
            }
            if (trend === "STABLE"){
                trendword = "stagnierend"
            }
            if (trend === "NO_TREND"){
                trendword = "kann noch nicht berechnet werden"
            }
            document.getElementById("distancedifftrend").innerHTML = trendword;
         }
    };
    distancedifftrend.open("GET", "http://raspberrypi:8080/trend?vin=" + vin, true);
    distancedifftrend.setRequestHeader("Content-type", "application/json");
    distancedifftrend.send();
}

function getvehicledata(vin) {
    var vehicledata = new XMLHttpRequest();
    vehicledata.onreadystatechange = function() {
         if (this.readyState == 4 && this.status == 200) {
            document.getElementById("currentOdometer").innerHTML = JSON.parse(this.response).currentOdometer.odometer;
            document.getElementById("remainingDistance").innerHTML = JSON.parse(this.response).remainingDistance;
            document.getElementById("remainingDays").innerHTML = JSON.parse(this.response).remainingDays;
         }
    };
    vehicledata.open("GET", "http://raspberrypi:8080/getOverview?vin=" + vin, true);
    vehicledata.setRequestHeader("Content-type", "application/json");
    vehicledata.send();
}

function getholiday(vin) {
    var holiday = new XMLHttpRequest();
    holiday.onreadystatechange = function() {
         if (this.readyState == 4 && this.status == 200) {
            document.getElementById("holidayDistance").innerHTML = this.response;
         }
    };
    holiday.open("GET", "http://raspberrypi:8080/holiday?vin=" + vin, true);
    holiday.setRequestHeader("Content-type", "application/json");
    holiday.send();
}

function getvin() {
    var vins = new XMLHttpRequest();
    vins.onreadystatechange = function() {
         if (this.readyState == 4 && this.status == 200) {
            vin = JSON.parse(this.response)[1].vehicle
            getdistancediff(vin)
            getdistancedifftrend(vin)
            getvehicledata(vin)
            getholiday(vin)
         }
    };
    vins.open("GET", "http://raspberrypi:8080/listVehicles", true);
    vins.setRequestHeader("Content-type", "application/json");
    vins.send();
}
getvin()
