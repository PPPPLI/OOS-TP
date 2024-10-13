# OOS - TP Car Rent Project

<h3><b>Introduction</b></h3>

Ce projet est un simple système de location de voitures. Il intègre des concepts abordés en cours tels que les microservices, les API REST, ainsi que gRPC.
Chaque service est associé à une base de données spécifique (MySQL) afin de garantir que les données sont stockées localement. Entre les services, en cas 
d'échec lors de l'enregistrement des données, le modèle Saga est appliqué en effectuant des compensations pour assurer la cohérence des données.


<h3><b>Architecture du projet</b></h3>

![image](https://github.com/user-attachments/assets/b753daab-cf23-40ad-a38b-9422959eb1b6)

<br>

## **Démarrage**

1. Colon ce repository
2. Modifier la configuration pour la partie de datasource en fonction de paramètres locaux lié à votre propre base de données(username, password...)
   
   ![image](https://github.com/user-attachments/assets/003af4a1-d0bc-4f89-b3c7-4f6bd4c9c0b4)
   
3. Démarrer les deux services de Springboot repectivement
4. Tester les APIs selon l'outil de votre choix (Postman, HTTP Client...)


## **URL d'api-doc**

- **http://localhost:8081/api-doc**
- **http://localhost:8082/api-doc**

## Golbal Exception Handler

### - Rest Api
  - @RestControllerAdvice et @ExceptionHandler
 
    ![image](https://github.com/user-attachments/assets/38f4ee12-9c7e-47d3-8402-e5d22d431692)


### - Grpc
  - @GrpcGlobalServerInterceptor
  - Héritage de l'interface: ServerInterceptor
  - Implémentation de la méthode interceptCall
  - Convertir l'exception en Status
 
  ![image](https://github.com/user-attachments/assets/68d25355-22f1-4f52-a136-c949326facbf)


## **Configuration de niveau de logging**

![image](https://github.com/user-attachments/assets/b3fb8f73-2b47-4d8b-b26a-16c41c8082b8)


## **Imprimer l'historique de log dans le terminal**

  - @slf4j
  - log.info()
  - log.error()

## **Modèle Saga en cas d'échec lors de l'enregistrement des données**

![image](https://github.com/user-attachments/assets/6ac9817e-7977-4d3c-87aa-2bfacd4b8283)

## **Unit Test**

- @SpringBootTest
- @AutoConfigureMockMvc
- @Test
- @MockBean

### **L'un des exemples de test**
![image](https://github.com/user-attachments/assets/8e46fad4-6077-4584-aba9-0bb07326ccb2)

![image](https://github.com/user-attachments/assets/69ad9907-5dec-43ab-becd-2b6845bd0d88)

![image](https://github.com/user-attachments/assets/579044fc-3fab-434d-8014-8b31c6bd94b5)
![image](https://github.com/user-attachments/assets/c6442012-07cf-41d9-bbc4-b90a754db916)




    

 


