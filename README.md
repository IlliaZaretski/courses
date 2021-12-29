# Courses

Demo project which allows business owners to manage their courses, classes, members, memberships etc.

# Story - Create Classes
As a studio owner i want to create classes for my studio so that my members can attend classes

Acceptance Criteria:
- Implement an API to create classes(`/courses`). Assume this api doesn't need to have any
authentication to start with.
- Few bare minimum details we need to create classes are - class name, start_date, end_date,
capacity. For now, assume that there will be only one class per given day. Ex: If a class by
name pilates starts on 1st Dec and ends on 20th Dec, with capacity 10, that means Pilates
has 20 classes and for each class the maximum capacity of attendance is 10.

# Story - Book for a class
As a member of a studio, I can book for a class, so that I can attend a class.

Acceptance Criteria:
- Implement an API endpoint (`/bookings`). Assume this api doesn't need to have any
authentication to start with.
- Few bare minimum details we need for reserving a class are - name(name of the member who
is booking the class), date(date for which the member want to book a class)


# Deployment
1) Run MySQL database: docker run --name mysql -e MYSQL_ROOT_PASSWORD=root -d -p 3306:3306 mysql:8
2) Run MongoDB database: docker run --name mongo -d -p 27017:27017 mongo:latest
3) Go to the root project directory
4) Run build and tests: ./gradlew build
5) Run application: ./gradlew bootRun
6) Use following URLs:
- http://localhost:8080/courses and http://localhost:8080/bookings for classic Rest APIs
- http://localhost:8080/pseudoreactive/courses for Pseudo-reactive API on top of non-reactive MySQL/JPA
- http://localhost:8080/reactive/courses for fully Reactive API 
