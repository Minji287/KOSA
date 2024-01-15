const WAPI_KEY = weather_config.WAPI_KEY;

document.addEventListener("DOMContentLoaded", function () {
    showPosition();
});

function showPosition(position) {

    fetchWeatherByCoords(37.4964224, 127.123456);
}

function showError(error) {
    switch (error.code) {
        case error.PERMISSION_DENIED:
            document.getElementById("weatherInfo").innerHTML = " ";
            break;
        case error.POSITION_UNAVAILABLE:
            document.getElementById("weatherInfo").innerHTML = " ";
            break;
        case error.TIMEOUT:
            document.getElementById("weatherInfo").innerHTML = " ";
            break;
        default:
            document.getElementById("weatherInfo").innerHTML = " ";
    }
}

function fetchWeatherByCoords(lat, lon) {
    const url = `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${WAPI_KEY}&lang=kr&units=metric`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            const weather = data.weather[0].description;
            const temp = data.main.temp;
            document.querySelector('.weather_icon').innerHTML = weather;
            document.querySelector('.weather_temp').innerHTML = temp + "°C";
            // document.getElementById("weatherInfo").innerHTML = `현재 날씨: ${weather}, 온도: ${temp}°C`;
        })
        .catch(error => {
            console.error("날씨 정보를 가져오는데 실패했습니다.", error);
        });
}
