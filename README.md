# guest-note-app
> Description

A guest note app which helps each user to send and receive different types of notes from and to his friends and guests daily.

The application is designed and implemented using cutting edge technologies such as:

- Java
- Spring Boot
- Lombok
- Map Struct
- H2 Database for poc

> Installation 
Follow the commands below to install the application.

```bash 
$ git clone
```

```bash 
$ mvn clean install
```

```bash 
$ mvn spring-boot:run
```

> How it Works?

We have 3 Endpoints:
- An endpoint to send a note to one or multiple users with specific type - but this type should be enabled
- An endpoint to list user's timeline notes in the last 30 days and could be filtered by one or more type with lazy loading pagination
- An endpoint to to delete (soft delete) one or more of user's received notes

 > Send a note to one or multiple users

       Request: POST http://localhost:8080/api/guestNote
       Request body:{
            "users":[1,2,3],
            "guestNote":{
                "title":"title",
                "msgBody":"msgBody",
                "typeId":2
            }
        }
        Response Body:[
            {
                "id": 7,
                "title": "title",
                "msgBody": "msgBody",
                "typeId": 2,
                "userId": 1,
                "creationDate": "2023-10-15T22:16:57.9477186+02:00"
            },
            {
                "id": 8,
                "title": "title",
                "msgBody": "msgBody",
                "typeId": 2,
                "userId": 2,
                "creationDate": "2023-10-15T22:16:57.948719+02:00"
            },
            {
                "id": 9,
                "title": "title",
                "msgBody": "msgBody",
                "typeId": 2,
                "userId": 3,
                "creationDate": "2023-10-15T22:16:57.9497061+02:00"
            }
        ]

> List user's timeline notes

    Request: GET http://localhost:8080/api/guestNotes/1?size=5&page=0&noteTypes=1,2
       Response body:[
            {
                "id": 1,
                "title": "title",
                "msgBody": "msgBody",
                "typeId": 1,
                "userId": 1,
                "creationDate": "2023-10-16T00:46:52.295588+02:00"
            }
        ]

> Delete (soft delete) one or more of user's received notes

    Request: Delete http://localhost:8080/api/guestNotes/1,2
       Response body:[list of undeleted ids]
