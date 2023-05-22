[![xml_parse](https://github.com/Dima-Stepanov/gc-aliance_xml_parse/actions/workflows/maven.yml/badge.svg)](https://github.com/Dima-Stepanov/gc-aliance_xml_parse/actions/workflows/maven.yml)
[![codecov](https://codecov.io/gh/Dima-Stepanov/gc-aliance_xml_parse/branch/master/graph/badge.svg?token=UEeCVDW5Bv)](https://codecov.io/gh/Dima-Stepanov/gc-aliance_xml_parse)

# Parser XML from "ООО Альянс розница"</h1>

<hr>
<h3>Описание проекта</h3>
<h6>
Приложение предназначено для редактирования xml файла структуры DATAPACKET Version="2.0"<br>
У компании есть процессинг в который подгружается данные реализации продуктов по картам. <br>
С одним из партнеров обмен происходит через получение XML файла и последующую его загрузку в систему. <br>
Но некоторые коды продуктов от партнера не соответствуют базовым кодам продуктов компании. <br>
Для корректной загрузки данных от партнеров была реализовано данное приложение <br>
</h6>
<hr>
<h3>Стек технологий </h3>
Java 17 <br>
Maven 3.8.6 <br>
Junit 5
AssertJ 3.24
Lombok 1.18 <br>
<hr>
<h3>Требование к окружению</h3>
JDK 17, Maven 3.9.1 <br>

<h3>Запуск приложения</h3>

1. Скопировать проект из репозитория по ссылке:
   <a href=https://github.com/Dima-Stepanov/gc-aliance_xml_parse.git><b>Приложение xml_parse</b></a> <br>
2. Перейдите в корень проекта и при помощи Maven соберите проект командой:<br>
   """mvn install""" <br>
3. Создайте файл настроек приложение app.properties и укажите в нем входящий и исходящий каталок в формате: <br>
   in=[путь к каталогу где будут лежать xml на обработку] <br>
   out=[путь к каталогу куда будут сохранятся обработанные файлы] <br>
4. После успешной сборки проекта необходима запустить приложение чрез командную строку, <br>
при этом необходима дополнительный указать параметр <b>-p=[путь к файлу настроек приложения]</b>, <br>
если файл расположен в одном каталоге с файлом JAR то достаточно указать только название файла настроек. <br>      
         """java -jar xmlparse.jar -p=app.config""" <br>
<hr>

<h3>Контакты</h3>
email: <b>haoos@inbox.ru</b> <br>
telegram: <a href=https://t.me/Dima202020> <b>Dima202020</b> </a> <br>
github.com: <a href=https://github.com/Dima-Stepanov><b>Dima-Stepanov</b></a>