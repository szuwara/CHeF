# CHeF
Everyday update and notification for NBP CHF exchange rate with Spring Boot

# Application is online!
Application is online as web-service settled on Heroku service:
https://chefrank.herokuapp.com/

credentials:
* username: user
* password: password

## General Info
This Spring Boot web application is created for own purposes. It allows me to check
everyday 'ask' exchange rate for Swiss Franc currency using connection with NBP API in JSON format. Currently application is sending SMS on my personal mobile phone everyday on particular time with current exchange rate. App is still in
develoment mode. My final goal is to put some security access levels, add some extra features like changing app parameters via browser and make this app automatically comparing exchange rates day by day, if it goes up or down and optionally saving them to own database and make graphs of saved values.

## Technologies
Project created with:
* Java 8
* Spring Boot
* Spring Security
* Google Gson library
* Twilio API
* Thymeleaf template engine
* Bootstrap 4 CSS styles

### NBP API which I use
http://api.nbp.pl/api/exchangerates/rates/c/chf/?format=json
