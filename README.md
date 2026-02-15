# Overview

The Holiday Validation Microservice is a REST-based service responsible for determining whether a given date is a non-working day in Poland.

The service supports validation of:
* Public holidays with fixed dates (e.g., January 1st, May 3rd)
* Public holidays with movable dates (e.g., Easter Monday, Corpus Christi)
* Weekends (Saturdays and Sundays)

Each type of non-working day can be enabled or disabled via configuration, allowing flexible behavior depending on business requirements.

# Features
* Validation of a single date
* Validation of a collection of dates
* Validation of a date range (between two dates)
* Configurable handling of:
  * Fixed public holidays
  * Movable public holidays
  * Saturdays
  * Sundays
* RESTful API
* Stateless design

#  Building and running 
Build:
```bash
mvn clean install
```

Run:
```bash
java -jar dayoff-0.1.0.jar
```

# Examples
Validate that January 1st is a public holiday in Poland (New Year’s Day).
```bash
curl "http://localhost:8080/api/days-off/2025-01-01"
```
Response:
```json
{
    "day": "2025-01-01",
    "isOff": true,
    "name": "New Year's Day"
}
```

Validate that January 1st is a public holiday in Poland (New Year’s Day), response in Polish language.
```bash
curl "http://localhost:8080/api/days-off/2025-01-01?lang=pl"
```
Response:
```json
{
    "day": "2025-01-01",
    "isOff": true,
    "name": "Nowy Rok"
}
```

Validate that January 2nd, 2025 is not a public holiday in Poland and is considered a working day.
```bash
curl "http://localhost:8080/api/days-off/2025-01-02"
```
Response:
```json
{
    "day": "2025-01-02",
    "isOff": false,
    "name": null
}
```

Validate every day between 2025-01-02 and 2025-01-06 (inclusive).
```bash
curl "http://localhost:8080/api/days-off/range?from=2025-01-02&to=2025-01-06"
```
Response:
```json
[
  {
    "day": "2025-01-02",
    "isOff": false,
    "name": null
  },
  {
    "day": "2025-01-03",
    "isOff": false,
    "name": null
  },
  {
    "day": "2025-01-04",
    "isOff": true,
    "name": "saturday"
  },
  {
    "day": "2025-01-05",
    "isOff": true,
    "name": "sunday"
  },
  {
    "day": "2025-01-06",
    "isOff": true,
    "name": "Epiphany"
  }
]
```

Validate a collection of specified dates.
```bash
curl "http://localhost:8080/api/days-off/dates?days=2025-01-01&days=2025-01-06"
```
Response:
```json
[
  {
    "day": "2025-01-01",
    "isOff": true,
    "name": "New Year's Day"
  },
  {
    "day": "2025-01-06",
    "isOff": true,
    "name": "Epiphany"
  }
]
```

Swagger is available at: /swagger-ui/index.html

# Author
Adam Woźniak <adam85.w@gmail.com>
# License
This microservice is licensed under GNU GENERAL PUBLIC LICENSE v3.