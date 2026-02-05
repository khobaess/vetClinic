# Ветеринарная Клиника - Система управления

Веб-приложение для управления ветеринарной клиникой, разработанное на Java 17+ с использованием Spring Boot 3 и Spring Data JPA (Hibernate).

## Функциональность

Приложение предоставляет следующие возможности:

1. **Управление пациентами**
   - Добавление нового пациента
   - Редактирование информации о пациенте
   - Удаление пациента
   - Расширенный поиск пациентов с пагинацией и сортировкой
   - Просмотр списка всех пациентов с пагинацией
   - Подсчет количества записей и медицинских карточек для каждого пациента

2. **Записи к врачу** (REST API)
   - Создание записи на прием с проверкой конфликтов времени
   - Просмотр записей
   - Редактирование записей
   - Удаление записей
   - Поиск записей по пациенту или врачу
   - Валидация времени записи (предотвращение двойных записей)

3. **Медицинские карточки** (REST API)
   - Создание медицинской карточки
   - Просмотр карточек
   - Редактирование карточек
   - Удаление карточек
   - Просмотр истории по пациенту

## Технологический стек

- **Java 17+**
- **Spring Boot 3.2.0**
- **Spring Data JPA** (Hibernate)
- **PostgreSQL** 
- **MapStruct** - маппинг DTO
- **SpringDoc OpenAPI (Swagger)** - документация API
- **JUnit 5 + Mockito** - тестирование
- **Maven**

## Схема базы данных

### Таблица `patients`
- `id` (Long, Primary Key, Auto Increment)
- `owner_name` (String, NOT NULL) - Имя владельца
- `pet_name` (String, NOT NULL) - Имя питомца
- `species` (String, NOT NULL) - Вид животного
- `breed` (String, NOT NULL) - Порода
- `date_of_birth` (LocalDate, NOT NULL) - Дата рождения
- `notes` (String) - Дополнительные заметки

### Таблица `appointments`
- `id` (Long, Primary Key, Auto Increment)
- `patient_id` (Long, Foreign Key -> patients.id, NOT NULL)
- `doctor_name` (String, NOT NULL) - Имя врача
- `appointment_date_time` (LocalDateTime, NOT NULL) - Дата и время записи
- `reason` (String) - Причина визита
- `notes` (String) - Заметки
- `status` (Enum: SCHEDULED, COMPLETED, CANCELLED) - Статус записи

### Таблица `medical_records`
- `id` (Long, Primary Key, Auto Increment)
- `patient_id` (Long, Foreign Key -> patients.id, NOT NULL)
- `record_date` (LocalDateTime, NOT NULL) - Дата записи
- `diagnosis` (String, NOT NULL) - Диагноз
- `symptoms` (String) - Симптомы
- `treatment` (String) - Лечение
- `prescriptions` (String) - Назначения
- `notes` (String) - Заметки
- `doctor_name` (String, NOT NULL) - Имя врача

## REST API Endpoints

### Пациенты (`/api/patients`)
- `GET /api/patients?page={page}&size={size}&sortBy={field}&sortDir={asc|desc}` - Получить всех пациентов с пагинацией
- `GET /api/patients/{id}` - Получить пациента по ID
- `POST /api/patients` - Создать нового пациента
- `PUT /api/patients/{id}` - Обновить пациента
- `DELETE /api/patients/{id}` - Удалить пациента
- `GET /api/patients/search?petName={name}&ownerName={name}&species={species}&page={page}&size={size}` - Расширенный поиск с пагинацией

### Записи (`/api/appointments`)
- `GET /api/appointments` - Получить все записи
- `GET /api/appointments/{id}` - Получить запись по ID
- `POST /api/appointments` - Создать новую запись
- `PUT /api/appointments/{id}` - Обновить запись
- `DELETE /api/appointments/{id}` - Удалить запись
- `GET /api/appointments/patient/{patientId}` - Записи пациента
- `GET /api/appointments/doctor/{doctorName}` - Записи врача

### Медицинские карточки (`/api/medical-records`)
- `GET /api/medical-records` - Получить все карточки
- `GET /api/medical-records/{id}` - Получить карточку по ID
- `POST /api/medical-records` - Создать новую карточку
- `PUT /api/medical-records/{id}` - Обновить карточку
- `DELETE /api/medical-records/{id}` - Удалить карточку
- `GET /api/medical-records/patient/{patientId}` - Карточки пациента
- `GET /api/medical-records/doctor/{doctorName}` - Карточки врача

## Лицензия

Этот проект создан в образовательных целях.
