# How to Run the Application on Docker

This guide provides step-by-step instructions to build and run a Spring Boot application connected to a MongoDB database using Docker.

---

## Prerequisites

- Install [Docker](https://www.docker.com/products/docker-desktop) on your system.

---

## Clone the Project Repository

First, clone the project repository from GitHub:

```bash
git clone https://github.com/siddheswar-tripurari/ems-spring-mongodb.git
```

Navigate into the project directory:

```bash
cd ems-spring-mongodb
```

---

## Build the Docker Image

Build the Docker image for the Spring Boot application:

```bash
docker build -t springboot-app:1.0 .
```

After successfully building, you can find the Docker image `springboot-app:1.0` in your Docker Desktop or by listing your images with:

```bash
docker images
```

---

## Pull the MongoDB Image

Pull the MongoDB Community Server image from the Docker Registry:

```bash
docker pull mongodb/mongodb-community-server
```

After pulling, the MongoDB image will be available in your Docker environment.

---

## Create a Docker Network

To enable communication between the application server and MongoDB, create a custom Docker network:

```bash
docker network create spring-to-mongo
```

---

## Create a Docker volume

To store data locally, create a new docker network

```bash
docker volume create mongodata
```

---

## Run the Containers

Start the MongoDB container on the created network and map the container volume to local volume:

```bash
docker run -d -p 27017:27017 --network=spring-to-mongo -v mongodata:/data/db --name mongodb mongodb/mongodb-community-server:latest
```

Run the Spring Boot application container on the same network:

```bash
docker run -d -p 8080:8080 --network=spring-to-mongo --name spring-boot-ems springboot-app:1.0
```

Verify that the containers are running by executing:

```bash
docker ps
```

---

## Access the MongoDB Container

To interact with the MongoDB database, access the terminal of the MongoDB container and execute the following commands:

```bash
docker exec -it mongodb bash
```

Start the MongoDB shell:

```bash
mongosh 0.0.0.0:27017
```

Create a new database:

```bash
use ems
```

Create a new collection:

```bash
db.createCollection("employee")
```

---

## Access the Application

Once the containers are running:

- Access the application via `http://localhost:8080` in your web browser.
- The MongoDB server is accessible on port `27017`.

---

Congratulations! Your Spring Boot application with MongoDB is now up and running using Docker.

