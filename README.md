# Weather_App
Is a simple app, that uses two API's to show you a weather(current, hourly for 24 hours and daily for a week), 
First API(OpenCageData) is used to have a geographical coordinates of city, that you entered(cause weather API can't work with just city name)
Second is just a weather forecast(OpenWeatherMap)
Work with this app is very easy, you just enter your city name(autocompletion, oh yeah!) and click the button, then you'll see a second page with weather forecast
To update info, just swipe down, when you are at the forecast page

To get started you need a two API keys, all of them must be inserted at class named Global.java(app/src/main/java/com/example/weatherapp)
APP_ID - OpenWeatherMap API(https://openweathermap.org/api)
OPENCAGE_API - OpenCageData API(https://opencagedata.com/)
*just register and get a key
