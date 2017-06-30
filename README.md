# RESTful API - File Management
API RESTful para almacenar y obtener archivos

##

#### Guardar Archivos:
curl --request POST \
  --url http://localhost:8080/files \
  --header 'content-type: multipart/form-data;'
  
#### Obtener Archivo:
curl --request GET \
 --url http://localhost:8080/files/{id}
