# Project Logistica - Mónica Díaz

## Installation

Clone source code from git

```sh
$  git clone https://github.com/MonicaDiazH/logistica.git
```

## Test application

```sh
$ curl localhost:8082/api/cliente/24
```

The response should be:
```sh
{
    "id": 24,
    "nombre": "Juan Carlos Mod",
    "telefono": "12454555",
    "direccion": "Direccion de cliente Juan Carlos"
}
```

## Filters
```sh
GET /api/cliente?nombre=Juan
```

## Security
```sh
POST /api/auth
```
Credentials:

Admin:
```sh
{
    "username": "admin",
    "password": "admin"
}
```

User:
```sh
{
    "username": "user",
    "password": "password"
}
```

Disabled:
```sh
{
    "username": "disabled",
    "password": "password"
}
```

Response Bearer Token:
```sh
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZGllbmNlIjoid2ViIiwiY3JlYXRlZCI6MTY2NDU5NzUxNTgxMiwiZXhwIjoxNjY1MjAyMzE1fQ.s-Sa44jrQMSUsIsqSH2yEJ2EvkDhPbqLU8uB2bS9gHxudmQfbCfDz6_t7s80fr3JUrLtOO72B0BpwUQGdvrIFg"
}
```


