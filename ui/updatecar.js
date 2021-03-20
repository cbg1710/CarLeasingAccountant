function getvin() {
    var vins = new XMLHttpRequest();
    vins.onreadystatechange = function() {
         if (this.readyState == 4 && this.status == 200) {
            var vin = JSON.parse(this.response)[1].vehicle
            getvehicledata(vin)
         }
    };
    vins.open("GET", "http://raspberrypi:8080/listVehicles", true);
    vins.setRequestHeader("Content-type", "application/json");
    vins.send();
}
getvin()


function getvehicledata(vin) {
    var vehicledata = new XMLHttpRequest();
    vehicledata.onreadystatechange = function() {
         if (this.readyState == 4 && this.status == 200) {
            document.getElementById("returnDay").value = JSON.parse(this.response).returnDay;
            document.getElementById("pickUpDay").value = JSON.parse(this.response).pickUpDay;
            document.getElementById("maximumDistance").value = JSON.parse(this.response).maximumDistance;
            document.getElementById("vin").value = vin;
         }
    };
    vehicledata.open("GET", "http://raspberrypi:8080/getOverview?vin=" + vin, true);
    vehicledata.setRequestHeader("Content-type", "application/json");
    vehicledata.send();
}