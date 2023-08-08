# Video Streaming Subscription Services

### Requirments
**Java**
The project is configured to Java 8. It is created using the IDE Intellije.
 


**Maven**
Maven is required to build, test and run the project. I used maven 3


### Running

- unzip the file and save it to a  desired location
- navigate to the directory where the pom file of the project is placed

To test the application via the command line - 
	while the prompt is on the directory with pom file, execute the command - mvn clean test
	
To build the application via the command line - 
	while the prompt is on the directory with pom file, execute the command - mvn clean install
	
	
To run the application via the command line - 
  - while the prompt is on the directory with pom file,  execute the command - mvn spring-boot:run
  - Alternatively, the project can be run using the jar file once the project is built:
  java -jar [path to the jar file] 

As no server port is set in the application, the default port of 8080 will be used when Tomcat starts. 

### APIs Documentation
#### Registration Services
1)  **registring user/customer**
	url - /users/registr
	method - POST
	param - type:com.gluereply.videostreamingservices.data.User
			value: {  
					"username": "james", 
					"password": "Validpass1", 
					"email": "james@yahoo.com",
					"dob" : "1990-12-31",
					"creditCard" : "1234567898765432"
					}
	response -  type: ValidationResult
				value: {
						"rejectionReasons": [
							"INVALID_EMAIL",
							"USERNAME_EXISTS",
							"INVALID_PASSWORD",
							"INVALID_USERNAME",
							"EMPTY_FIELDS",
							"UNDER_AGE",
							"INVALID_CREDIT_CARD",
						]
					}
				* one or more or no values can be returned
		
2)	**getting users with credit card filter**
	url - /users/get
	method - GET
	params - type:String, value: yes/no/
	Response -  type: List<User>
				value: [
							{
								"username": "david",
								"password": "Password12",
								"email": "david@yahoo.com",
								"dob": "2001-04-15",
								"creditCard": null
							},
							{
								"username": "monica",
								"password": "Password12",
								"email": "monica@yahoo.com",
								"dob": "1981-12-31",
								"creditCard": null
							}
						]
				
#### Payment Services

1)	**customer payment**
	url - /payments/pay
	method - POST
	params - type: com.gluereply.videostreamingservices.data.Payment, 
			 value: {  
					"id": "1255", 
					"username": "james", 
					"creditCard" : "1234567898765432",
					"amount" : 100
				}			
	response -  type: ValidationResult
				value: {
						"rejectionReasons": [
							"INVALID_CREDIT_CARD",
							"INVALID_AMOUNT",
							"UNREGISTERED_CREDIT_CARD"
						]
					}
					
* one or more or no values can be returned


## Technologies
* Springboot 2
* JUnit 5
* RestAssured 3
* Maven 3