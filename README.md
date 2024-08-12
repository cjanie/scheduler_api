Environment: Java 22

API URI: http://localhost:8080/automations

If the application runs in a docker container, API URI is: http://127.0.0.1:8080/automations

POST request payload structure (JSON):

    {
    
        "devicesAddresses" : string[],
    
        "powerOnTime" : string, // format "hh:mm:ss" 
        
        "powerOffTime" : string, // format "hh:mm:ss"
        
        "zoneId" : string // Ex: Europe/Paris, Africa/Cairo...
    
    }
