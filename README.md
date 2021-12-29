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
