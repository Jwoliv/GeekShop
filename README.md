# GeekShop
___
This project is an example of a simple online store for selling clothes.

### Technologies
___
:closed_book: Spring (MVC, Data, Security, Boot)<br>
:closed_book: Thymeleaf<br>
:closed_book: PostgreSQL<br>

### User capabilities
___
This application has two roles of User:

:key: USER
<br>
:key: ADMIN

#### What can do person with role USER
* :unlock: Add / Remove product to the basket<br>
* :unlock: Add / Remove product to the liked list<br>
* :unlock: Create orders<br>
* :unlock: Send a message for the admins<br>
* :unlock: Change firstname, lastname, password, email<br>
* :unlock: Delete account<br>

#### What can do person with role ADMIN?

* :unlock: All capabilities of user
* :unlock: Add / edit / remove
  * :unlock: Product
  * :unlock: Theme
  * :unlock: Category
  * :unlock: Season
* :unlock: Send answer for the messages of users
* :unlock: Process orders


### Usage
___
You need to clone this repository
```
git clone https://github.com/Jwoliv/GeekShop.git
```
Launch the maven 
```
mvn clean install
```
Go to this address
```
http://localhost:8020
```
Create account or login in the account use so paths:
* [Login](http://localhost:8020/login)
* [Sing in](http://localhost:8020/registration)
