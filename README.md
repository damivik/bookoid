FORMAT: 1A
HOST: http://localhost:8080/

# bookoid

Boookid is the backend of an online bookstore.

## Customers Collection [/customers]

### Create a New Customer [POST]

You may create a new customer using this action. It takes a JSON
object containing Email, First Name, Last Name, and Password of the new Customer.

+ Request (application/json)

        {
            "email": "johndoe@mail.com",
            "firstName": "John",
            "lastName": "Doe",
            "password": "password",
        }

+ Response 201 (application/json)

    + Headers

            Location: /customers/2

    + Body

            {
                "id": 2,
                "email": "johndoe@mail.com",
                "firstName": "John",
                "lastName": "Doe",
            }

## Customer [/customers/{customer_id}]

+ Parameters
    + customer_id (number) - ID of the Customer in the form of an integer

### View a Customer Detail [GET]

+ Response 200 (application/json)

        {
            "id": 2,
            "email": "johndoe@mail.com",
            "firstName": "John",
            "lastName": "Doe",
        }
        
### Update Customer Detail [PATCH]

You may update a customer detail using this action. It takes a JSON
object containing the fields to be updated.

+ Request (application/json)

        {
            "firstName": "Jane"
        }
        

+ Response 200 (application/json)

        {
            "id": 1
            "email": "johndoe@mail.com",
            "firstName": "John",
            "lastName": "Doe",
        }

### Delete a Customer [DELETE]

+ Response 200 (application/json)

## Categories Collection [/categories]

### Create a New Category [POST]

You may create a new category using this action. It takes a JSON
object containing name of the new Category.

+ Request (application/json)

        {
            "name": "Non Fiction",
        }

+ Response 201 (application/json)

    + Headers

            Location: /categories/2

    + Body

            {
                "id": 2,
                "name": "Non Fiction",
            }

## Category [/categories/{category_id}]

+ Parameters
    + category_id (number) - ID of the Category in the form of an integer

### Retrieve Category [GET]

+ Response 200 (application/json)

        {
            "id": 2,
            "name": "Non Fiction",
        }

### Update Category [PATCH]

You may update a category using this action. It takes a JSON object containing the
new name of the category.

+ Request (application/json)

        {
            "name": "Fiction"
        }
        

+ Response 200 (application/json)

        {
            "id": 3,
            "name": "Fiction",
        }

### Delete Category [DELETE]

+ Response 200 (application/json)

## Books Collection [/books]

### Create New Book [POST]

+ Request (application/json)

        {
            "title" : "1984"
            "authors": "Geroge Orwell";
            "price": 5500,
            "categories" : [1,2],
        }

+ Response 201 (application/json)

    + Headers

            Location: /books/2

    + Body

            {
                "id": 2,
                "title" : "1984"
                "authors": "Geroge Orwell";
                "price": 5500,
                "categories" : [1,2],
            }
            
## Book [/books/{book_id}]

+ Parameters
    + book_id (number) - ID of the Book in the form of an integer

### Retrieve Category [GET]

+ Response 200 (application/json)

        {
            "id": 2,
            "title" : "1984"
            "authors": "Geroge Orwell";
            "price": 5500,
            "categories" : [1,2]
        }

### Update Book [PATCH]

+ Request (application/json)

        {
            "price": 5500,
        }
        

+ Response 200 (application/json)

        {
            "id": 2,
            "title" : "1984"
            "authors": "Geroge Orwell";
            "price": 5500,
            "categories" : [1,2]
        }

### Delete Book [DELETE]

+ Response 200 (application/json)

## Orders Collection [/orders]

### Create Order [POST]

+ Request (application/json)

        {
            "customerId":1,
            "items":
                [
                    {
                        "bookId":1,
                        "quantity":3
                    },
                    {
                        "bookId":2,
                        "quantity":4
                    }
                ]
            }
        }

+ Response 201 (application/json)

    + Headers

            Location: /orders/2

    + Body

### Retrieve Order [GET]

+ Response 200 (application/json)
           
        {
            "id":2,
            "customer":
                {
                    "id": 2,
                    "email": "dami@mail.com",
                    "firstName": "Dami",
                    "lastName": "Vik"
                },
            "items": 
                [
                    {
                        "book":"/books/3",
                        "quantity":3
                    },
                    {
                        "book":"/books/4",
                        "quantity":4
                    }
                ]
        }
