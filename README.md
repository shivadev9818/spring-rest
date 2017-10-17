# Spring-rest Document
=====================

A simple document archive with REST interface.

Build and run
==============

```bash
git clone https://github.com/shivadev9818/spring-rest.git
cd Springboot-Restapi
mvn package/ mvn clean install
java -jar target/spring-rest-1.0-SNAPSHOT.jar
```

Open (http://localhost:8080/) to see the frontend.

REST API
=========

* **Add a document**
----------------------

   */archive/upload?file={file}&person={person}&date={date} POST*

  * file: A file posted in a multipart request
  * person: The name of the uploading person
  * date: The date of the document

* **Find documents**
-----------------------

   */archive/documents?person={person}&date={date} GET*

  * person: The name of the uploading person
  * date: The date of the document

* **Get a document**
----------------------

   */archive/document/{id} GET*

  * id: The UUID of a document

Documentation
==============

* REST Service Controller
--------------------------

  Executes incoming request and defines URL to service method mappings. All remote call are delegated to the archive service.
  * Service
  * Interface
  * Implementation

  A service to save, find and get documents from an archive.
  * Data access object
  * Interface
  * Implementation

   Data access object to insert, find and load documents. FileSystemDocumentDao saves documents in the file system. No database in involved. For each document a folder is created. The folder contains the document and a properties files with the meta data of the document. Each document in the archive has a Universally Unique Identifier (UUID). The name of the documents folder is the UUID of the document.

* Client
---------

   ServiceClient.java

   A client for the document archive which is using the REST service.

* Web client
--------------

   src/main/resources/static

   A web client made with [AngularJS](https://angularjs.org/).
