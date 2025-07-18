@echo off
:: Запускаем Selenium Grid из текущей папки
java -jar selenium-server-4.34.0.jar standalone --selenium-manager true --log-level FINE
pause