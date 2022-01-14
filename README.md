# Courses
Demo project which allows business owners to manage their courses, classes, members, memberships etc.

# Business cases
1) As a studio owner I want to create classes for my studio so that my members can attend classes

  Acceptance Criteria:
- Implement an API to create classes(`/courses`). Assume this api doesn't need to have any
authentication to start with.
- Few bare minimum details we need to create classes are - class name, start_date, end_date,
capacity. For now, assume that there will be only one class per given day. Ex: If a class by
name pilates starts on 1st Dec and ends on 20th Dec, with capacity 10, that means Pilates
has 20 classes and for each class the maximum capacity of attendance is 10.

2) As a member of a studio, I can book for a class, so that I can attend a class.

  Acceptance Criteria:
- Implement an API endpoint (`/bookings`). Assume this api doesn't need to have any
authentication to start with.
- Few bare minimum details we need for reserving a class are - name(name of the member who
is booking the class), date(date for which the member want to book a class)

# Build
1) Go to the root project directory
2) Run build and tests: ./gradlew build

# Deployment

**Local + DB in Docker**
1) Run MySQL database: docker run --name mysql -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:8
2) Run MongoDB database: docker run --name mongo -p 27017:27017 -d mongo:latest
3) Run application: ./gradlew bootRun
4) Use following URLs:
- http://localhost:8080/courses and http://localhost:8080/bookings for classic Rest APIs
- http://localhost:8080/pseudoreactive/courses for Pseudo-reactive API on top of non-reactive MySQL/JPA
- http://localhost:8080/reactive/courses for fully Reactive API 

**Fully dockerized with Docker Compose**
1) Build the application image: docker image build -t courses .
2) Run app containers: docker-compose up

# API specification
- Open API 3 definition available at http://localhost:8080/api-docs
- Interactive API specification via Swagger UI: http://localhost:8080/swagger-ui.html

- You can bypass Swagger and use these URLS directly:
a) http://localhost:8080/courses and http://localhost:8080/bookings for classic Rest APIs
b) http://localhost:8080/pseudoreactive/courses for Pseudo-reactive API on top of non-reactive MySQL/JPA
c) http://localhost:8080/reactive/courses for fully Reactive API 
