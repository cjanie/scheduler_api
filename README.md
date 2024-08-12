Environment: Java 22

Springboot Application.


1. To run the application

If you don't have the Java environnement installed but Docker installed you can run the application in a docker container, using the following command lines:

    cd path/to/the/application/folder/containing/the/Dockerfile

    docker build -t scheduler_api_image .

    docker run -p 8080:8080 scheduler_api_image


2. To use the API

API URI: http://localhost:8080/automations

If the application runs in a docker container, API URI is: http://127.0.0.1:8080/automations

POST request payload structure (JSON):

    {
    
        "devicesAddresses" : string[],
    
        "powerOnTime" : string, // format "hh:mm:ss" 
        
        "powerOffTime" : string, // format "hh:mm:ss"
        
        "zoneId" : string // Ex: Europe/Paris, Africa/Cairo...
    
    }
