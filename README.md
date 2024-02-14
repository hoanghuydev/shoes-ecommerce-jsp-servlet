# Web Shoes Ecommerce JSP Servlet

Web Shoes Ecommerce is a Java-based web application that allows users to browse, buy, and manage their favorite shoes. This project is built using JSP and Servlet technologies, providing a secure and feature-rich experience for online shoe shopping.

## Table of Contents

-   [Features](#features)
-   [Demo](#demo)
-   [Getting Started](#getting-started)
-   [Usage](#usage)
-   [Dependencies](#dependencies)
-   [Security](#security)

## Features

**User Authentication:** Secure login and registration functionality.

**Email Communication:** Send emails for various purposes like registration confirmation, password reset, etc.

**Image Editing:** Edit shoe images using FFmpeg for customization.

**Real-time Feedback:** Real-time opinions and reviews using socket communication.

**Shopping Cart:** Manage shopping cart items with both session and database storage.

**Payment Integration:** Support for payment gateways like Momo and VNPay.

**Image Upload:** Utilize Cloudinary for secure image uploads.

**External API Integration**: Use the GHTK API for shipping information.

**CRUD Operations:** Implement CRUD operations for orders, products, users, sizes, etc.

## Demo

Access [https://hoanghuydev.click:8443](https://hoanghuydev.click:8443) to test

## Getting Started

To get started with the Web Super Film project, follow these steps:

1. **Clone the Repository:**
    ```
    https://github.com/hoanghuydev/shoes-ecommerce-jsp-servlet-jquery.git
    ```
2. **Navigate to the Project Directory:**
    ```
    cd shoes-ecommerce-jsp-servlet-jquery
    ```
3. **Execute SQL File:**
   Excute file sql in database/shoes_web.sql to create table of project
4. **Edit Configuration:**
   Open the src/main/resources/env.properties file and edit it with your specific configuration details.
   ```
    sudo vi /src/main/resources/env.properties 
   ```
5. **Run Project:**
   Using tomcat to run project on local server.

## Usage

**Login and Register:**
Navigate to the login and registration pages to access the full functionality of the web application.

**Email Communication:**
Experience email functionality for registration confirmation, contact, and other communication purposes.

**Image Editing:**
Edit shoe images using the built-in FFmpeg image editing feature.

**Real-time Feedback:**
Provide and receive real-time opinions and reviews using socket communication.

**Shopping Cart:**
Manage your shopping cart with the ability to store items in both session and database.

**Payment Integration:**
Complete the payment process using integrated payment gateways like Momo and VNPay.

**Image Upload:**
Securely upload shoe images using Cloudinary for image hosting.

**External API Integration:**
Access shipping information through the GHTK API for a seamless shopping experience.

**CRUD Operations:**
Perform CRUD operations for orders, products, users, sizes, and other entities.

## Dependencies

The Web Shoes Ecommerce project has the following dependencies:
* Java Servlet and JSP technologies
* FFmpeg for image editing
* Cloudinary for image hosting
* External APIs like GHTK for shipping information
* Payment gateways: Momo, VNPay

## Security

The Web Shoes Ecommerce project prioritizes security with the following measures:

* XSS Prevention
* Bcrypt for password hashing
* Env file
* Protection against SQL injection attacks
