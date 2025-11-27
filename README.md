# 🏢 Sistema de Gestión de Excusas - Excusas S.A.

![Java](https://img.shields.io/badge/Java-17%20%7C%2021-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen) ![TDD](https://img.shields.io/badge/Methodology-TDD-blue) ![Build](https://img.shields.io/badge/Build-Maven-yellow)

> **API RESTful desarrollada para la modernización del sistema de gestión de excusas de la empresa ficticia "Excusas S.A.".**

Este sistema permite a otras áreas (RRHH, Control de Personal) registrar excusas, consultar estados y administrar la línea de encargados.

El núcleo del sistema implementa el patrón de diseño **Chain of Responsibility** para evaluar dinámicamente si una excusa es aprobada o rechazada según la jerarquía de encargados y sus competencias. Todo el desarrollo fue guiado estrictamente por **TDD (Test Driven Development)**.

---

## 👥 Integrantes del Equipo

| Nombre Completo | Usuario GitHub | Rol |
|-----------------|----------------|-----|
| Bruno Martins | [@brunomartins27](https://github.com/brunomartins27) | Developer |
| Marcus Mitidiere | [@Uruk707](https://github.com/Uruk707) | Developer |
| Ariadna Sofia | [@AriadnaHiga](https://github.com/AriadnaHiga) | Developer |


---

## 🛠️ Tecnologías y Herramientas

* **Lenguaje:** Java 17 / 21
* **Framework:** Spring Boot 3.2 (Web, Data JPA, Validation)
* **Base de Datos:**
    * *Dev/Prod:* MySQL
    * *Test:* H2 (In-Memory)
* **Testing:** JUnit 5, Mockito, Spring Boot Test (MockMvc)
* **Herramientas:** Maven, Lombok

---

## ✅ Estado del Proyecto (Milestones)

| Milestone | Estado | Descripción |
|-----------|:------:|-------------|
| **Diseño del sistema** | ✅ | Modelado de clases, relaciones OOP y principios SOLID. |
| **Chain of Responsibility** | ✅ | Lógica dinámica para la aprobación de excusas. |
| **Lógica de Emails** | ✅ | Simulación de notificaciones mediante `NotificationService`. |
| **Prontuarios** | ✅ | Generación automática de historial ante rechazos. |
| **TDD & Testing** | ✅ | +30 Tests unitarios y de integración. |
| **API REST** | ✅ | Endpoints documentados y funcionales. |

---

## 🔌 API Endpoints Reference

### 👤 Empleados & Encargados

| Método | Endpoint | Descripción |
|:------:|----------|-------------|
| `GET` | `/api/empleados` | Listar todos los empleados. |
| `POST` | `/api/empleados` | Registrar un nuevo empleado. |
| `GET` | `/api/encargados` | Ver la configuración actual de la cadena de mando. |
| `POST` | `/api/encargados` | Agregar un nuevo encargado (Jefe, Gerente, etc.). |
| `PUT` | `/api/encargados/modo` | Cambiar modo de trabajo (`NORMAL`, `VAGO`, `PRODUCTIVO`). |

### 📝 Excusas

| Método | Endpoint | Descripción |
|:------:|----------|-------------|
| `POST` | `/api/excusas` | **Core:** Registrar nueva excusa (Dispara la evaluación). |
| `GET` | `/api/excusas` | Listar todas las excusas. |
| `GET` | `/api/excusas/{legajo}` | Ver historial de un empleado específico. |
| `GET` | `/api/excusas/rechazadas` | Filtrar solo las excusas rechazadas. |
| `GET` | `/api/excusas/busqueda` | Búsqueda avanzada por fechas y legajo. |
| `DEL` | `/api/excusas/eliminar` | Eliminar excusas anteriores a una fecha límite. |

### 📂 Prontuarios

| Método | Endpoint | Descripción |
|:------:|----------|-------------|
| `GET` | `/api/prontuarios` | Listado de sanciones/prontuarios generados. |

---

## 🧪 Estrategia de Testing (TDD)

El proyecto cuenta con una cobertura de pruebas exhaustiva (**>80%**) dividida en tres capas:

1.  **Unitarias (Modelo):** Se validan las reglas de negocio dentro de las entidades `Encargado`, `Excusa`, etc. *(Ej: Un encargado "VAGO" aprueba todo).*
2.  **Unitarias (Servicio):** Se utiliza **Mockito** para aislar la lógica de negocio de la base de datos y probar el flujo de la cadena de responsabilidad.
3.  **Integración (Controller):** Se utiliza **MockMvc** y **H2** para simular peticiones HTTP reales y verificar códigos de respuesta (`200 OK`, `201 Created`, `400 Bad Request`).

---

## 💻 Instalación y Ejecución

### Prerrequisitos
* JDK 17+
* Maven 3.6+
* MySQL *(Opcional, por defecto usa H2)*

### Pasos

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/tu-usuario/excusas-api.git](https://github.com/tu-usuario/excusas-api.git)
    cd excusas-api
    ```

2.  **Ejecutar Tests (Verificación TDD):**
    ```bash
    mvn test
    ```

3.  **Iniciar la aplicación:**
    ```bash
    mvn spring-boot:run
    ```

4.  **Acceder:**
    La API estará disponible en `http://localhost:8080/api`.

---

## 📐 Documentación y Diagramas

Los diagramas UML requeridos (Clases, Secuencia, Arquitectura y DER) se encuentran alojados en la Wiki del repositorio.

[👉 **Ir a la Wiki del Proyecto**](../../wiki)

---
