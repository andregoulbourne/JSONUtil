# JSONUtil
An libary based in java for reading json strings.
Publishable to maven repository and containerized using docker.

# Technologies Used
* Java
* Maven
* JSON
* Docker

# Features
* Reading of JSON string

## Running the app

### Run With Docker
* cd <"Project Directory">  
* docker build -t <"image name"> .  
* docker run <"image tag">
This will publish the JSONUtil to jar to a docker container
  
### Run Locally
* Publish The custom JSONUtil jar use in this project to your local mvn repository
mvn install:install-file -Dfile=${pathToJSONJar} -DgroupId=com.andre -DartifactId=JSONUtil -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar -DgeneratePom=true
* refer to jar in of the project we would like to use it in
![image](https://github.com/user-attachments/assets/18f83c0e-b1db-4a1b-880c-1a83bb450b62)



### Sample Code
![image](https://github.com/user-attachments/assets/d04fd14a-16ea-47a8-8978-fefc5c6fdd6d)
Params
json string
Input(attribute)

Return is List of strings that corespond to parameter attribute.



For any User input prompts press enter twice once input has been entered
And for generating the report in the docker please copy the reports from docker container to the machine you would like to view the report on


