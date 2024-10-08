Необходимо реализовать REST API, который позволяет отслеживать почтовые
отправления.
В системе должны регистрировать почтовые отправления — письма, посылки — их
передвижение между почтовыми отделениями, а также должна быть реализована
возможность получения информации и всей истории передвижения конкретного
почтового отправления.
Операции, которые должны быть реализованы:
● регистрации почтового отправления,
● его прибытие в промежуточное почтовое отделение,
● его убытие из почтового отделения,
● его получение адресатом,
● просмотр статуса и полной истории движения почтового отправления.
Почтовое отправление определяется следующими свойствами:
● идентификатор,
● тип (письмо, посылка, бандероль, открытка),
● индекс получателя,
● адрес получателя,
● имя получателя.
Почтовое отделение характеризуется следующими свойствами :
● индекс,
● название,
● адрес получателя.
Сервис может быть реализован в видео JSON либо XML-сервиса на выбор. Сервис
может быть реализован при помощи стека Java EE или Spring.
СУБД для хранения данных может использоваться любая.
Работа с данными должна быть выполнена с помощью ORM, библиотека может
использоваться любая.
Приложение должно быть собрано при помощи Maven или Gradle.
Результатом работы должен быть war или ear-архив, который может быть размещен на
сервер приложений. Для отладки и демонстрации может использоваться любой сервер
приложений.
К приложению должно прилагаться описание его API — структура запросов и ответов,
список допустимых операций, можно это реализовать в виде проекта SoapUI.
Код должен быть покрыт тестами минимум на 70% (приложить скрин покрытия).
