# Trabajo Final - Ingeniería de Software II

## **Índice**

1. [Equipo](#equipo)
2. [Propósito del Proyecto](#propósito-del-proyecto)
3. [Tecnologías](#tecnologías)
4. [Pipeline](#pipeline)

## **Equipo**

**Nombre del Proyecto:** ProceedHub ex Hamuk

**Integrantes:**

- Cahuana Nina, Melany Maria Cristina
- Cuela Morales, Andrea Lucia
- Malcoaccha Díaz, Erick Ruben
- Muñoz Curi, Giomar Danny
- Sumire Ramos, Marko Julio
- Taipe Saraza, Christian Daniel
- Zavalaga Orozco, Rushell Vanessa

## **Propósito del Proyecto**

### Objetivo

El objetivo del proyecto es desarrollar una plataforma web accesible y moderna que conecte a las personas con oportunidades de becas, brindando una experiencia atractiva e intuitiva. A través de una interfaz inspiradora, la plataforma busca superar barreras económicas y sociales, promoviendo el empoderamiento educativo y motivando a los usuarios a explorar y aprovechar al máximo estas oportunidades para alcanzar su máximo potencial.

### Arquitectura de Software

El backend sigue una arquitectura basada en Domain-Driven Design (DDD), estructurada en capas con Spring Boot como motor principal. Las capas están organizadas de la siguiente manera:
- **Capa de Aplicación:** Contiene los casos de uso y los servicios RESTful expuestos a los clientes.
- **Capa de Dominio:** Define las entidades del negocio, los agregados, y los servicios del dominio, siguiendo principios de diseño orientado al dominio.
- **Capa de Infraestructura:** Integra la persistencia de datos, servicios externos, y configuraciones de seguridad.
El diseño sigue principios de separación de responsabilidades y extensibilidad para facilitar futuras integraciones. Se emplea un sistema de base de datos relacional (SQL), utilizando PostgreSQL como base de datos principal y Hibernate como framework ORM para gestionar la persistencia. Para pruebas, se incluye H2 como base de datos embebida.

![](img/hexagonalArch.jpg)

```
main.java.com.mistysoft.proceedhub
│   ProceedHubApplication.java
│
├───apps
│   └───backend
│           ScholarshipController.java
│           UserController.java
│
└───modules
    ├───scholarship
    │   ├───application
    │   │   │   CreateScholarship.java
    │   │   │   DeleteScholarship.java
    │   │   │   GetAllScholarships.java
    │   │   │   SearchScholarship.java
    │   │   │   UpdateScholarship.java
    │   │   │
    │   │   └───dto
    │   │           ScholarshipDTO.java
    │   │
    │   ├───domain
    │   │       IScholarshipRepository.java
    │   │       Requirement.java
    │   │       Scholarship.java
    │   │
    │   └───infrastructure
    │           ISpringDataScholarshipRepository.java
    │           JpaScholarshipRepository.java
    │           ScholarshipEntity.java
    │           ScholarshipMapper.java
    │
    ├───shared
    │   ├───config
    │   │       SecurityConfig.java
    │   │       WebConfig.java
    │   │
    │   └───security
    │           JwtUtil.java
    │
    └───user
        ├───application
        │   │   LoginUser.java
        │   │   RegisterUser.java
        │   │   SearchUser.java
        │   │
        │   └───dto
        │           UserDTO.java
        │
        ├───domain
        │       IUserRepository.java
        │       Role.java
        │       User.java
        │       UserId.java
        │
        └───infrastructure
                ISpringDataUserRepository.java
                JpaUserRepository.java
                UserEntity.java
                UserMapper.java
```
### Funcionalidades principales

#### Gestión de usuarios

- **Registro de Usuarios**
![](img/Register.png)
- **Inicio de sesión**
![](img/Login.png)

#### Seguridad

- Autenticación basada en tokens JWT.
- Protección de rutas y servicios REST.

#### Gestión de becas

- **Listado de becas disponibles.**
![](img/userlistScholarships.jpg)

- **Listado de becas para el administrador**
![](img/listScholarships.jpg)

- **Creacion de becas**
![](img/createScholarship.jpg)

- **Actualizacion de becas**
![](img/updateScholarship.jpg)

## **Tecnologías**

### Lenguajes de Programación

- **Frontend:**
  - JavaScript
  - TypeScript (opcional)
- **Backend:**
  - Java

### Frameworks

- **Frontend:**
  - React
  - Vite
  - TailwindCSS
- **Backend:**
  - Spring Boot
  - Spring Security
  - Spring Data JPA

### Bibliotecas

- **Frontend:**
  - Axios
  - DayJS
  - React Router
  - React Hook Form
  - React Icons
  - JS Cookie
- **Backend:**
  - Lombok
  - JWT
  - JUnit
  - Mockito
  - Spring Boot Starters

## **Herramientas de construcción y pruebas**

### Frontend

- Vite
- TailwindCSS
- PostCSS
- Autoprefixer
- pnpm
- Selenium

### Backend

- Gradle
- JUnit
- Mockito
- Jacoco
- Thunder Client

## **Pipeline**

### Configuración del Pipeline

El pipeline automatiza el proceso de construcción, pruebas y análisis estático, además de ejecutar la aplicación.

```groovy
pipeline {
  agent any

  tools {
    jdk 'JAVA'
    gradle 'gradle'
  }

  environment {
    DB_URL = credentials('ProceedHub-db-url')
    DB_USERNAME = credentials('ProceedHub-db-username')
    DB_PASSWORD = credentials('ProceedHub-db-password')
    JWT_EXPIRATION = credentials('ProceedHub-jwt-expiration')
    JWT_TOKEN = credentials('ProceedHub-jwt-token')
    PORT = credentials('ProceedHub-port')
    SCANNER_HOME = tool 'sonar-scanner'
    DOCKERHUB_CREDENTIALS = credentials('natzgun-dockerhub')
    CORS_ALLOWED_ORIGINS = 'http://localhost:5173'
  }

  stages {
    stage("Git Checkout") {
      steps {
        echo "Cloning the develop branch..."
        git branch: 'develop',
                changelog: false,
                poll: false,
                url: 'https://github.com/Natzgun/ProceedHub'
      }
    }
    stage('Build') {
      steps {
        echo 'Building the application...'
        sh './gradlew clean build'
      }
    }
    stage('Test') {
      steps {
        echo 'Running tests...'
        sh './gradlew test'
      }
    }
    stage('Copy dependecy for Sonarqube') {
      steps {
        echo 'Building the application...'
        sh './gradlew copyDependencies'
      }
    }
    stage("SonarQube Analysis ") {
      steps {
        script {
          withSonarQubeEnv('sonarqube-proceedhub') {
            sh """$SCANNER_HOME/bin/sonar-scanner \
                        -Dsonar.projectKey=ProceedHub \
                        -Dsonar.projectName=ProceedHub \
                        -Dsonar.sources=. \
                        -Dsonar.java.libraries=build/libs/*.jar,build/classes/java/main \
                        -Dsonar.java.binaries=. """
          }
        }

      }
    }
    stage('Docker Build and Deploy in Render') {
      steps {
        sh 'docker build --platform linux/amd64 -t natzgun/proceedhub-aplication-ddd:latest .'
      }
    }
  }
}
```

### Construcción Automática
En esta etapa de se construye la aplicacion del backend previamente clonado.

**Herramienta:** Gradle

```groovy
stage("Git Checkout") {
    steps {
        echo "Cloning the develop branch..."
        git branch: 'develop',
            changelog: false,
            poll: false,
            url: 'https://github.com/Natzgun/ProceedHub'
    }
}
stage('Build') {
    steps {
        echo 'Building the application...'
        sh './gradlew clean build'
    }
}
```

### Análisis Estático

**Herramienta:** SonarQube

```properties
sonar.projectKey=ProceedHub
sonar.sources=src/main
sonar.tests=src/test
sonar.java.binaries=build/classes
sonar.coverage.exclusions=**/*Config.java
sonar.java.libraries=build/libs/*.jar
```

### Pruebas Unitarias
Para la implementacion de las pruebas unitarias se considero verificacion por caja blanca con cobertura de decicion, para ello fue necesario identificar todas los caminos de ejecucion segun la complejidad ciclomatica de los metodos:

**Herramientas:** JUnit + Mockito
**Metodo a probar:** UpdateScholarship

- La complejidad ciclomatica de este metodo es 14:
![](img/updateScholarship.png)

- Por lo tanto se implementan 14 casos de prueba:
![](img/updateScholarTestCases.png)
```java
@Test
void testUpdateScholarshipNotFound() {
    String id = UUID.randomUUID().toString();
    ScholarshipDTO request = ScholarshipDTO.builder().build();
    when(scholarshipRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> updateScholarship.execute(request, id));
}
```

### Pruebas Funcionales
Para las pruebas funcionales se usaron metodos de caja negra:

**Diseño de Casos de Prueba**
| **Funcionalidad** | **Caso de Prueba**                | **Descripción**                                                                 | **Resultado Esperado**                                                  |
|--------------------|-----------------------------------|---------------------------------------------------------------------------------|--------------------------------------------------------------------------|
| Login              | Login Exitoso                   | Verificar inicio de sesión con credenciales válidas.                           | Redirección exitosa al perfil del usuario.                              |
| Login              | Credenciales Incorrectas        | Probar inicio de sesión con email o contraseña incorrectos.                    | Mostrar mensaje de error indicando credenciales inválidas.              |
| Login              | Campos Vacíos                   | Intentar iniciar sesión sin completar los campos requeridos.                   | Mostrar mensaje de error indicando que los campos son obligatorios.     |
| Login              | Bloqueo tras Intentos Fallidos  | Realizar múltiples intentos fallidos consecutivos.                             | Bloqueo del usuario y mensaje de advertencia.                           |
| Registro           | Registro Exitoso                | Completar el formulario de registro con datos válidos y únicos.                | Redirección exitosa a la página de becas.                               |
| Registro           | Email Existente                 | Intentar registrarse con un email ya registrado.                               | Mostrar mensaje de error indicando que el email ya está registrado.     |



**Herramienta:** Selenium

```java
@Test
public void testLogin() {
    driver.get("http://localhost:3000/login");
    driver.findElement(By.id("email")).sendKeys("test@mail.com");
    driver.findElement(By.id("password")).sendKeys("password");
    driver.findElement(By.id("login-button")).click();
    assertTrue(driver.findElement(By.id("dashboard")).isDisplayed());
}
```

### Pruebas de Seguridad

**Herramienta:** OWASP ZAP

```yaml
auth:
  loginUrl: 'http://localhost:3000/api/auth/login'
  loginRequestData:
    email: '${user}'
    password: '${pass}'
  loginIndicator: "auth-token"

attacks:
  - name: "xss"
  - name: "sql-injection"
  - name: "csrf"
```

### Pruebas de Performance

**Herramienta:** Apache JMeter

#### **Casos de prueba**
| Escenario de prueba     | HTTP Request     | Usuarios     | Tiempo de respuesta aceptable |
|-------------------------|------------------|--------------|------------------------------|
| TestGetAllScholarships  | **GET:** http://localhost:8086/api/scholarships/get_all   | 10 | 2s |
| TestGetScholarship      | **GET:** http://localhost:8086/api/scholarships/48b51e19-7102-4533-9123-272effa73562   | 10 | 2s |
| TestUpdateScholarship   | **POST:** http://localhost:8086/api/scholarships/update/984e38e2-0161-4fc6-be04-126fe9e4d660   | 10 | 5s |
| TestGetUser   | **GET:** http://localhost:8086/api/users/chad   | 10 | 2s |
| TestLoginUser   | **POST:** http://localhost:8086/api/users/login   | 10 | 5s |

#### Performance Test Plan
![Performance Test Plan](img/performanceTestPlan.png)

#### Creacion de un grupo de hilos
```xml
<TestPlan>
  <ThreadGroup>
    <stringProp name="ThreadGroup.num_threads">100</stringProp>
    <stringProp name="ThreadGroup.ramp_time">10</stringProp>
    <HTTPSamplerProxy>
      <stringProp name="HTTPSampler.path">/api/scholarships</stringProp>
      <stringProp name="HTTPSampler.method">GET</stringProp>
    </HTTPSamplerProxy>
  </ThreadGroup>
</TestPlan>
```

#### TestUpdateScholarship
Para actualizar una beca se uso un contador, *counter_id*, dentro del cuerpo del json de la solicitud HTTP en JMeter:
```json
{
    "title": "Scholarship 1",
    "description": "Beca para estudiantes llamados VALGIR${counter_id} uwu",
    "date": "2024-12-10T00:00:00Z",
    "image": "http://example.com/image.jpg",
    "country": "India",
    "continent": "Asia",
    "moreInfo": "http://example.com/moreinfo",
    "requirements": [
      {
        "name": "Ser estudiante de posgrado en una universidad reconocida."
      }
    ]
}
```

#### TestLoginUser
```json
{
  "username" : "chad",
  "password" : "password123",
  "roles" : ["USER"]
}
```

## **Gestión de Tareas: Github Project**
![github project](img/githubProject.png) 
[Link del Proyecto](https://github.com/users/Natzgun/projects/2/views/1)

**Issue #30: UpdateScholarshipTest Coverage Analysis**

- **Título:** "Add more tests cases on UpdateScholarshipTest to increase coverage"
- **Etiquetas:** ["unit testing"]
- **Descripción:**
  - Se requiere agregar más casos de prueba para la clase "UpdateScholarshipTest" con el objetivo de aumentar la cobertura de las pruebas unitarias.
- **Código Afectado:** UpdateScholarship.execute()
```java
public Scholarship execute(ScholarshipDTO request, String id) {
    Optional<Scholarship> updatedScholarship = scholarshipRepository.findById(id);
    if(updatedScholarship.isEmpty()) {
        throw new IllegalArgumentException("Scholarship with this id does not exist");
    }

    Scholarship beforeScholarship = updatedScholarship.get();
    Scholarship scholarship = Scholarship.builder()
        .id(id)
        .title(request.getTitle() != null && !request.getTitle().isBlank() ? request.getTitle() : beforeScholarship.getTitle())
        .description(request.getDescription() != null && !request.getDescription().isBlank() ? request.getDescription() : beforeScholarship.getDescription())
        .date(request.getDate() != null ? request.getDate() : beforeScholarship.getDate())
        .image(request.getImage() != null && !request.getImage().isBlank() ? request.getImage() : beforeScholarship.getImage())
        .country(request.getCountry() != null && !request.getCountry().isBlank() ? request.getCountry() : beforeScholarship.getCountry())
        .continent(request.getContinent() != null && !request.getContinent().isBlank() ? request.getContinent() : beforeScholarship.getContinent())
        .moreInfo(request.getMoreInfo() != null && !request.getMoreInfo().isBlank() ? request.getMoreInfo() : beforeScholarship.getMoreInfo())
        .requirements(request.getRequirements() != null ? request.getRequirements() : beforeScholarship.getRequirements())
        .build();

    scholarshipRepository.save(scholarship);
    return scholarship;
}
```

**Issue #29: Repository Test Coverage**

- **Título:** "Unit tests for findAll and deleteById from JpaScholarshipRepository"
- **Etiquetas:** ["unit testing"]
- **Descripción:**
  - Se requiere agregar pruebas unitarias para los métodos 'findAll' y 'deleteById' de "JpaScholarshipRepository".
- **Código Afectado:** 
```java
@Override
public List<Scholarship> findAll() {
    return repository.findAll()
            .stream()
            .map(ScholarshipMapper::toDomain)
            .toList();
}

@Override
public void deleteById(String id) {
    repository.deleteById(id);
}

```


