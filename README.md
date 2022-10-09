# Задание
Приложение "Калькулятор отпускных".

Микросервис на SpringBoot + Java 11 c одним API:

GET "/calculacte"
Минимальные требования: Приложение принимает твою среднюю зарплату за 12 месяцев и количество дней отпуска - отвечает суммой отпускных, которые придут сотруднику.

Доп. задание: При запросе также можно указать точные дни ухода в отпуск, тогда должен проводиться рассчет отпускных с учётом праздников и выходных.

Проверяться будет чистота кода, структура проекта, название полей\классов, правильность использования паттернов. Желательно написание юнит-тестов, проверяющих расчет.

## Решение задания

Доступ к API с помощью Postman. URL запроса http://localhost:8080/calculate

Доступ к API с помощью Swagger UI. URL запроса http://localhost:8080/swagger-ui/index.html#/calculate-controller/calculateUsingGET

### Входные данные
```
yearSalary - сумма зарплаты за год

numberOfVacationDays - количество дней в отпуске

startVacation - дата начала отпуска

endVacation - дата начала отпуска
```

### Выходные данные
```
Сумма отпускных равна: ЗНАЧЕНИЕ
```
