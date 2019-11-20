# Web Coding Challenge Backend
this project represents my backend participation in the recruitment challenge of United Remote.

## User Stories
- As a User, I can sign up using my username & password
- As a User, I can sign in using my username & password
- As a User, I can display the list of shops sorted by distance (paginated)
- As a User, I can like a shop, so it can be added to my preferred shops (liked shops shouldn’t be displayed on the main page)
- As a User, I can dislike a shop, so it won’t be displayed within “Nearby Shops” list during the next 2 hours
- As a User, I can display the list of preferred shops (paginated)
- As a User, I can remove a shop from my preferred shops list
## Requirements
- jdk >= 1.8
- maven >= 3
## External Packages
- [GSON](https://github.com/google/gson) : to parse the JSON
- [JJWT](https://github.com/jwtk/jjwt) : to process the JWT (Json Web Token)
## Build
``` sh
$ git clone https://github.com/razouq/web-coding-challenge-shops-backend.git
$ cd web-coding-challenge-shops-backend
$ mvn install
$ mvn spring-boot:run
```
each line the shops.json file inside resources folder, is a json object which represents a shop,
the first time you run the app it will parse this file and store every shop in the H2 database
(a relational database management system written in Java)

## API
### base URL
``` http
http://localhost:8080
```
### EndPoints
#### 1. Create a new account
##### URL
``` http
POST /api/account/register
```
##### Request Body
``` json
{
  "username": "user",
  "password": "user123",
  "passwordConfirm": "user123"
}
```
##### Response Body
``` json
{
  "username": "user"
}
```
#### 2. Login
##### URL
``` http
POST /api/authentication/login
```
##### Request Body
``` json
{
  "username": "user",
  "password": "user123"
}
```
##### Response Body
``` json
{
  "token": "ey$2....brj"
}
```

#### 3. get a list of nearby shops
##### URL
``` http
POST /api/shops/getNearby?page={page_number}
```
##### Request Body
``` json
{
	"lat": "-6.82861",
	"lon": "33.99216"
}
```
##### Response Body
``` json
[
    {
        "id": 253,
        "picture": "http://image.com/image",
        "name": "shop name",
        "email": "shop@email.com",
        "city": "Azemmour"
    },
    ...
]
```

#### 4. like a shop
##### URL
``` http
GET /api/shops/like/{shop_id}
```

#### 5. dislike a shop
##### URL
``` http
GET /api/shops/dislike/{shop_id}
```

#### 6. get a list of preferred shops
##### URL
``` http
GET /api/shops/getPreferred?page={page_number}
```

##### Response Body
``` json
[
    {
        "id": 253,
        "picture": "http://image.com/image",
        "name": "shop name",
        "email": "shop@email.com",
        "city": "Azemmour"
    },
    ...
]
```

#### 7. remove a shop from preferred shops list
##### URL
``` http
GET /api/shops/removeLikedShop/{shop_id}
```
# Contributor
Anass Bendarsi 
bendarsi@gmail.com
