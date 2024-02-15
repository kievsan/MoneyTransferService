# Card money transfer service

Сервис предназначен для перевода денег между банковскими картами.

REST-сервис работает на http://localhost:5500/transfer по POST-запросу application/json.

---

## Запуск сервиса:

1. Клонировать из github:
#### git clone
2. Собрать архивный файл:
#### mvn package
3. Построить Docker image:
#### docker build -t rest_transfer .
4. Запустить:
#### docker compose up -d

---

## FRONT:
https://serp-ya.github.io/card-transfer