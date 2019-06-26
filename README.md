# CHeF
Everyday update and notification for NBP CHF exchange rate with Spring Boot

## General Info
This Spring Boot web application is created for own purposes. It allows me to check
everyday 'ask' exchange rate for Swiss Franc currency using connection with NBP API in JSON format. Application is still in
develoment mode. My final goal is to make this app automatically notify me by e-mail or SMS about everyday
changes of CHF currency, comparing them if it goes up or down and optionally saving them to
own database and make graphs of saved values.

## Technologies
Project created with:
* Java 8
* Spring Boot
* Spring Security
* Google Gson library
* Twilio API
* Thymeleaf template engine

### NBP API which I use
http://api.nbp.pl/api/exchangerates/rates/c/chf/?format=json
