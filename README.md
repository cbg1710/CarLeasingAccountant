# CarLeasingAccountant
CarLeasingAccountant gives you an overview above some interesting figures for car leasing.
It was mainly developed to get information about the already passed distance and how many kilometres are left.

The controlling of the system is happening via HTTP requests.

Open the file /doc/swagger-api-doc.yaml in http://editor.swagger.io to see the full documentation of the rest
endpoints.

### Build

This package can be build with Apache Maven. Since you have already installed maven on your system, 
simple execute following command in the same directory where the pom.xml is located. 

```bash
mvn clean install
```

### Docker

For starting the server in a docker container simply execute the build.sh 
script and after that the run.sh script. The run script will ask you for a directory where 
to save the vehicle information on your host.

You can also use existing docker images at the [docker hub](https://cloud.docker.com/repository/docker/glaesec/leasing-accountant/general). 


## License

Copyright &copy; 2019 [cbg1710](https://github.com/cbg1710).
Licensed under the [MIT License](LICENSE).

