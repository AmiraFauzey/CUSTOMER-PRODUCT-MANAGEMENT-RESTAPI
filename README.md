# CUSTOMER-PRODUCT-MANAGEMENT-RESTAPI
Spring boot CRUD REST API to Manage Customers and Product

**Project Purpose**:
The goal of this project is to develop a RESTful API using Spring Boot that manages customer and product information. The API provides the following functionalities:

**Customer Management**
1. **Create a Customer**: Add a new customer to the database.
2. **Edit Existing Customer Information**: Update details of an existing customer.
3. **Retrieve a Customer by ID**:Fetch customer details using a specific ID.
4. **Retrieve All Customers**: Get a list of all customers from the database.
5. **Delete a Customer by ID**: Remove a customer from the database using a specific ID.

**Product Management**
1. **Create a Product**: Add a new product to the database.
2. **Edit Existing Product Information**: Update details of an existing product.
3. **Retrieve a Product by ID**: Fetch product details using a specific ID.
4. **Retrieve All Products**: Get a list of all products from the database.
5. **Delete a Product by ID**: Remove a product from the database using a specific ID.

**Technologies Used**
1. **Backend Framework**:Spring Boot 3.3.3
2. **Database**: MySQL
3. **Build Tool**: Maven
4. **Java Version**: Java 17
5. **API Documentation Tool**: Swagger
6. **Testing Framework**: JUnit, Mockito
7. **IDE**: IntelliJ IDEA 2024

**Database Configuration**
The Spring boot application uses MySQL as its database. To configure the database, update the `application.properties` file as below:
```properties
spring.application.name=customerProductManagement
spring.datasource.url=jdbc:mysql://localhost:3306/customer_product_management_db?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=username
spring.datasource.password=password

spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

**Project Setup**
1. **Prerequisites**
   * Ensure you have the following software installed:
     * Java Development Kit (JDK): Java 17 or later
     * Apache Maven 3.8.6
     * IDE: IntelliJ IDEA (recommended)
3. **Clone the Repository**
     ```
     https://github.com/AmiraFauzey/CUSTOMER-PRODUCT-MANAGEMENT-RESTAPI.git
     ```
5. **How to Clone a Project into IntelliJ IDEA**
     * **Open IntelliJ IDEA**: Launch IntelliJ IDEA on your computer.
     * **Get from Version Control**: On the welcome screen, click on "Get from VCS" button.
     * **Choose Version Control System**: In the window that pops up, select "Git" from the list of version control options.
     * **Enter Repository URL**: In the URL field, paste the repository URL:
        ```https://github.com/AmiraFauzey/CUSTOMER-PRODUCT-MANAGEMENT-RESTAPI.git```
     * **Select Directory**: In the Directory field, choose the local directory where you want to clone the project by clicking the folder icon and navigating to your desired location.
     * **Clone the Repository**: Click the "Clone" button to start cloning the repository to your selected directory.
     * **Open the Project**:  Once the cloning process is complete, IntelliJ IDEA will automatically open the project in a new window.
6. **Run the Application**
     * **Open the CustomerProductManagementApplication Class**: In your project, navigate to the class named **CustomerProductManagementApplication**. This class should be annotated with @SpringBootApplication.
     * **Run the Application**: Once you have the class open, click the green play button (►) located next to the class name or main method to run the application.
7. **Access the API**
     * **Open Swagger UI**: Open your web browser and go to the following URL: ```http://localhost:8080/swagger-ui/index.html```
     * **Explore and Test the API**: The Swagger UI will display all available REST APIs for the application. You can use this interface to explore the API endpoints and test them directly from your browser.
8. **How to Test the API Using Swagger UI**
     * **Customer API**
         * POST API
             1. Click Try out button
             2. In the request body box, fill up the data you want to create for example
                ```
                {
                  "bookTitle": "string",
                  "bookPrice": 1,
                  "bookQuantity": 1,
                  "createdDate": "2024-08-27T01:45:31.968Z",
                  "lastModifiedDate": "2024-08-27T01:45:31.968Z"
                }
                ```
             3. Click the execute button
                
         * PUT API
           1. Click Try out button
           2. In the bookId field, put the ID that you want to update
           3. In the request body box, update the fields that you want
               ```
                {
                  "bookTitle": "string",
                  "bookPrice": 1,
                  "bookQuantity": 1,
                  "createdDate": "2024-08-27T01:45:31.968Z",
                  "lastModifiedDate": "2024-08-27T01:45:31.968Z"
                }
                ```
            4. Click the execute button

          * GET API ( Get All customers )
            1. Click Try out button
            2. In the sortBy field, choose data that you want to sort by, for example: **bookTitle**
            3. Click the execute button
                
          * GET API ( Get specific customer by ID)
            1. Click Try out button
            2. In the bookId field, choose customerId that you want to get
            3. Click execute

          * DELETE API
            1. Click Try out button
            2. In the bookId field, choose customerId that you want to delete
            3. It will give message **Customer deleted successfully.**
      
9. You can also view logging to view API Request body and Response body
    ![Alt text](C:\Users\Hp\OneDrive\Desktop/ResponseAndRequestBody.jpg)       
            
       

          
             
    
