Сборка проекта:
```
maven package install
```
Запуск проекта:
```
java -jar target/elevator-1.0.0-SNAPSHOT-fat.jar
```
> Либо через "Run/Edit configurations.." создаём конфигурацию "JAR Application" и там выбираем jar-fat из /target.

Затем в браузере набираем:
```
http:\\localhost:8080
```