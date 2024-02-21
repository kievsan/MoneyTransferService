# по Замечаниям 16.02.24: ветка raw (https://github.com/kievsan/MoneyTransferService/tree/raw)

список-1
-----------
1. https://github.com/kievsan/MoneyTransferService/tree/master/.mvn/wrapper - рекомендуется убрать директорию .mvn/wrapper из репозитория. Для этого следует добавить .mvn/wrapper в файл .gitignore. Это поможет избежать индексации бинарных файлов в Git, уменьшит размер репозитория и предотвратит возможные конфликты при слиянии изменений.
- ответ: исполнено

2. https://github.com/kievsan/MoneyTransferService/blob/2c73946e8f393f64587adb491fb5332b736c3329/src/main/java/ru/mail/knhel7/money_transfer_service/controller/CardMoneyTransferController.java#L21 - не обязательно явно указывать типы MediaType.APPLICATIONJSONVALUE для consumes и produces в аннотации @RequestMapping. По умолчанию Spring MVC использует JSON как формат для сериализации и десериализации данных, если не указаны другие типы данных. Поэтому можно опустить параметры consumes и produces, если контроллер работает только с JSON. Это сделает код более лаконичным и читаемым.
- ответ: исправлено

3. https://github.com/kievsan/MoneyTransferService/blob/2c73946e8f393f64587adb491fb5332b736c3329/src/main/java/ru/mail/knhel7/money_transfer_service/controller/CardMoneyTransferController.java#L31-L41 - согласно спецификации, метод getListTransfers() должен быть доступен только через HTTP POST. Следовательно, необходимо удалить метод getListTransfers() с аннотацией @GetMapping.
- ответ: согласен, в финальной версии не останется. Просто мне было удобно на определенном этапе разработки для моментальной проверки.

4. https://github.com/kievsan/MoneyTransferService/blob/2c73946e8f393f64587adb491fb5332b736c3329/src/main/java/ru/mail/knhel7/money_transfer_service/controller/CardMoneyTransferController.java#L33 - а почему бы сразу не вернуть из сервиса объект TransferResponse? И ниже аналогично.
- ответ: Исправлено. Объект Transaction<Transfer> transaction был необходим моему логгеру. Этот вопрос перекликается с вопросом 8 (см. ниже)

5. https://github.com/kievsan/MoneyTransferService/blob/2c73946e8f393f64587adb491fb5332b736c3329/src/main/java/ru/mail/knhel7/money_transfer_service/controller/CardMoneyTransferController.java#L18 - лишняя строка.
- ответ: исправлено

6. https://github.com/kievsan/MoneyTransferService/blob/master/src/main/java/ru/mail/knhel7/money_transfer_service/controller/TransferExceptionHandlerAdvice.java - почему возвращаете только статус и сообщение? Согласно спецификации ответ по ошибкам должен содержать объект с двумя полями id и description.
- ответ: это не так, возвращаю объект TransferExResp errResp с полями message и id cогласно спецификации

7. https://github.com/kievsan/MoneyTransferService/blob/2c73946e8f393f64587adb491fb5332b736c3329/src/main/java/ru/mail/knhel7/money_transfer_service/controller/TransferExceptionHandlerAdvice.java#L22 - для чего используете?
- ответ: из-за trows Exception в logger был добавлен айдишкой. Удалено (см. п.8)

8. https://github.com/kievsan/MoneyTransferService/blob/master/src/main/java/ru/mail/knhel7/money_transfer_service/logger/Logger.java - логгер не является потокобезопасным. ... Я рекомендую использовать готовые реализации логгеров, так как они обеспечивают высокую производительность, поддерживаются сообществом разработчиков и имеют встроенные механизмы для обеспечения безопасности и конфигурирования. Например Logback или Log4j.
- ответ: исправлено. Я с самого начала спрашивал про параллельность, соблюдать ли потокобезопасность, подразумевая и этот момент тоже.

список-2
----------
изучается
1. https://github.com/kievsan/MoneyTransferService/blob/2c73946e8f393f64587adb491fb5332b736c3329/src/main/java/ru/mail/knhel7/money_transfer_service/model/addit_code/Currency.java#L5 от куда взяли валюты? В задании ничего об этом не говорилось, да и кроме этого переводы в иностранных валютах на территории РФ запрещены ЦБ. Не нужно добавлять эту логику, валюта у нас стандартно - рубли. от куда взяли валюты? В задании ничего об этом не говорилось, да и кроме этого переводы в иностранных валютах на территории РФ запрещены ЦБ. Не нужно добавлять эту логику, валюта у нас стандартно - рубли.
- ответ: поскольку amount - объект с двумя полями value и current, логично было предположить, что current - не только о рублях. Кроме всего, нет никаких отсылок к контексту бизнес-задачи или законодательству... В ответ на полученное замечание логика валют исключена из проекта.

2. https://github.com/kievsan/MoneyTransferService/blob/2c73946e8f393f64587adb491fb5332b736c3329/src/main/java/ru/mail/knhel7/money_transfer_service/model/addit_code/TransactionStatus.java#L3 - немного намудрили, слишком сложно, это похоже на статусную модель, но не полностью реализованную. Ваша задача получить транзакцию и сохранит ее, предварительно выведя лог (и возможно добавить какие нибудь поля). Баланс проверять не нужно, подтвержедние от себя тоже придумывать не нужно.
- ответ: исправлено

3. Учитывая, что вы используете последнюю LTS версию Java, хотелось бы увидеть ваши дата классы (модели) в виде record, а не обычных классов.
- ответ: например, модель Money изначально представлена в виде record

4. https://github.com/kievsan/MoneyTransferService/blob/master/src/main/java/ru/mail/knhel7/money_transfer_service/model/transaction/ITransaction.java - в этом классе нет необходимости в данном проекте.
- ответ: исполнено

5. https://github.com/kievsan/MoneyTransferService/blob/2c73946e8f393f64587adb491fb5332b736c3329/src/main/java/ru/mail/knhel7/money_transfer_service/model/transaction/Transaction.java#L12-L14 - потокобезопасность с таким подходом не гарантируется. Я бы рекомендовал id транзакции получать в другом месте, а тут его просто сетить, типа того что вы делали в дз по спрингу.
- ответ: исправлено

6. https://github.com/kievsan/MoneyTransferService/blob/2c73946e8f393f64587adb491fb5332b736c3329/src/main/java/ru/mail/knhel7/money_transfer_service/repository/CardRepo.java#L19-L26 - в этих картах нет необходимости. Для чего они тут?
- ответ: удалено (см. пункт 2)

7. В репозитории многовато методов, они точно тут нужны, исходя из нашего задания? По сути вам нужен метод, который будет сохранять транзакцию и метод, который будет транзакцию отдавать. + сюда же можно добавить проставление id для ващих транзакций.
- ответ: исправлено

8. Валидацию https://github.com/kievsan/MoneyTransferService/blob/2c73946e8f393f64587adb491fb5332b736c3329/src/main/java/ru/mail/knhel7/money_transfer_service/service/CardMoneyTransferService.java#L58 следует вынести в отдельный класс, не забываем про СОЛИД.
- ответ: исполнено

9. По аналогии с репозиторием, методов и функционала слишком много. По сути в вашем сервисе должно быть чуть больше методов чем в репозитории, т.к. логика приложения не сложная и линейная.
- ответ: исполнено

10. https://github.com/kievsan/MoneyTransferService/blob/master/src/main/java/ru/mail/knhel7/money_transfer_service/tools/DateTimeTool.java - хорошо, но только не tool, а util. Тоже самое касается пакета.
- ответ: исполнено

11. Именования: Интерфейс именуем просто MoneyService, его реализацию, если она одна - MoneyServiceImpl, никаких букв I в начале не нудно ставить.
- ответ: исполнено
