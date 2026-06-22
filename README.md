# DentalFlow - Migracion a Microservicios

Este repositorio parte de la base ya existente en `OmarPaz2/DentalFlow` (auth-service,
patient-service, dentist-service) y migra el resto de la logica del proyecto SOAP
monolitico (`apiDentalFlowSoap`) a una arquitectura de microservicios completa.

## Servicios

| Servicio             | Puerto | Base de datos     | Tabla(s) principal(es)                  | Estado            |
|-----------------------|--------|--------------------|------------------------------------------|--------------------|
| eureka-server         | 8761   | -                  | -                                         | Nuevo              |
| gateway                | 8080   | -                  | -                                         | Nuevo              |
| auth-service           | 8081   | auth_db            | users                                     | **Sin cambios**    |
| patient-service        | 8082   | patient_db         | patients                                  | Modificado (minimo)|
| specialty-service      | 8083   | specialty_db       | specialties                               | Nuevo              |
| material-service       | 8084   | material_db        | materiales                                | Nuevo              |
| dentist-service        | 8085   | dentist_db         | clinical_staff                            | Modificado         |
| appointment-service    | 8086   | appointment_db     | citas, appointment_types                  | Nuevo              |
| treatment-service      | 8087   | treatment_db       | tratamientos, sesiones_tratamiento        | Nuevo              |
| payment-service        | 8088   | payment_db         | pagos                                     | Nuevo              |
| dashboard-service      | 8089   | dashboard_db       | dashboard_metrics                         | Nuevo              |

Cada microservicio tiene **su propia base de datos** con unicamente la(s) tabla(s)
que necesita. Las referencias entre microservicios (`patient_id`, `dentist_id`,
`treatment_id`, `appointment_id`, `specialty_id`, etc.) se guardan como **enteros
planos, sin foreign key ni relacion JPA** hacia otra base de datos. Las unicas
foreign keys que se mantienen son las que son 100% internas a un mismo servicio
(p. ej. `sesiones_tratamiento.treatment_id -> tratamientos.id`, ambas en
`treatment_db`; o `citas.appointment_type_id -> appointment_types.id`, ambas en
`appointment_db`).

### auth-service

Tal como se pidio, **no se modifico ningun archivo de auth-service**. Por eso:

- No se le agrego cliente Eureka: el Gateway lo enruta por URL estatica
  (`AUTH_SERVICE_URI`), no por `lb://auth-service`.
- Se le agrego unicamente un `Dockerfile` (infraestructura, no codigo de negocio)
  para poder levantarlo con docker-compose junto a los demas.
- Todos los demas servicios validan el JWT emitido por auth-service consultando
  su endpoint `/.well-known/jwks.json`, igual que ya estaba pensado en el repo
  original.

### dentist-service -> "personal clinico"

Por indicacion del negocio, lo que antes era `DentistEntity` (solo odontologos)
ahora es `ClinicalStaffEntity`, con un campo `staffType` (`ADMINISTRADOR`,
`RECEPCIONISTA`, `ODONTOLOGO`). La base de ruta REST se mantiene en
`/api/v1/dentists` para no romper la integracion ya prevista con el Gateway y
el frontend. La especialidad se extrajo a `specialty-service` (su propia base
de datos) y se resuelve via Feign + CircuitBreaker.

De paso se corrigio un bug del repo original: `DentistService` no tenia la
anotacion `@Service`, por lo que Spring nunca hubiera podido inyectar
`IDentistService` (la app no arrancaba). La nueva `ClinicalStaffService` si
esta correctamente registrada como bean.

## Donde se uso Feign, RabbitMQ y Circuit Breaker (analisis + implementacion)

### Feign + CircuitBreaker (llamadas sincronas, "necesito el dato ya")

Se uso en toda relacion donde un servicio necesita **validar o leer datos** de
otro antes de continuar con su propia logica:

- `dentist-service -> specialty-service`: para mostrar el nombre de la
  especialidad de un odontologo.
- `appointment-service -> patient-service, dentist-service`: para validar que
  el paciente y el personal clinico existen antes de agendar una cita.
- `treatment-service -> patient-service, dentist-service, payment-service`:
  para validar paciente/odontologo al crear un tratamiento, y para calcular
  `montoPagado` consultando el total pagado en payment-service.
- `payment-service -> treatment-service, appointment-service`: para validar
  el monto a pagar contra el costo real del tratamiento/cita **antes** de
  registrar el pago (esta es la validacion mas critica del sistema).
- `payment-service -> dentist-service`: para enriquecer el recibo con el
  nombre del odontologo/especialidad.
- `dashboard-service -> material-service`: para mostrar en vivo la cantidad
  de materiales en stock critico.
- `gateway -> *`: cada ruta del Gateway tiene su propio CircuitBreaker con
  fallback a `/fallback` (no es Feign, pero es el mismo patron a nivel de
  borde del sistema).

Cada cliente Feign tiene su `CircuitBreaker` (Resilience4j) configurado en el
`application.yaml` de cada servicio. Se uso `FallbackFactory` (no `fallback`
simple) en los casos donde hace falta distinguir un **404 real** ("el paciente
no existe") de una **caida del servicio** ("patient-service no responde"):
son errores semanticamente distintos y el llamador necesita reaccionar
distinto (400 vs 503). Donde el dato es solo "decorativo" (nombre de
especialidad, nombre de odontologo en un recibo, conteo de stock critico para
el dashboard) el fallback **degrada con un valor por defecto** en lugar de
romper la operacion principal.

### RabbitMQ (eventos asincronos, "que se entere quien le importe")

Se uso para desacoplar efectos secundarios que **no deberian bloquear** la
operacion principal ni acoplar un servicio a la logica interna de otro:

- `appointment-service` publica `appointment.created` -> lo consume
  `dashboard-service` para su contador `citasHoy`.
- `treatment-service` publica `treatment.completed` (cuando se completa la
  ultima sesion) -> lo consume `dashboard-service` para su contador
  `tratamientosCompletados`.
- `payment-service` publica `payment.completed` (todo pago) -> lo consume
  `dashboard-service` para sus contadores de pagos.
- `payment-service` publica `payment.appointment.paid` (solo pagos de citas)
  -> lo consume `appointment-service`, que confirma automaticamente la cita
  (`PENDIENTE`/`REPROGRAMADA` -> `CONFIRMADA`) sin que payment-service tenga
  que conocer el modelo de estados de una cita.

Todo viaja por un unico exchange topic `dentalflow.events` (uno por entorno),
cada servicio declara sus propias colas/bindings.

## Como levantar todo

```bash
docker compose up --build
```

- Gateway: http://localhost:8080
- Eureka dashboard: http://localhost:8761
- RabbitMQ management UI: http://localhost:15672 (guest/guest)

Cada servicio de negocio se registra en Eureka (excepto auth-service, ver
arriba) y el Gateway enruta por nombre logico de servicio (`lb://...`).

## Para correr localmente sin Docker

1. Levanta MySQL y ejecuta los scripts de `database/*.sql` (cada uno crea su
   propia base) o usa los mismos archivos copiados en `docker/mysql-init/`.
2. Levanta RabbitMQ (`rabbitmq:3.13-management`).
3. Levanta `eureka-server` primero.
4. Levanta el resto de servicios (el orden no es critico gracias a los
   CircuitBreakers, pero se recomienda auth-service y eureka-server primero).
5. Levanta `gateway` al final.

## Pendiente / fuera de alcance de esta migracion

- El frontend del repo es solo un placeholder (`frontend/borrar este doc...txt`),
  no se toco.
- No se agrego un Config Server centralizado (cada servicio usa variables de
  entorno con valores por defecto razonables para desarrollo local).
- No se incluyeron tests automatizados nuevos; el repo original tampoco
  los traia.
