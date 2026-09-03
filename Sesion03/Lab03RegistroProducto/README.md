## Mejora con IA

Para esta mejora utilicé inteligencia artificial como herramienta de apoyo para implementar nuevas funcionalidades en el formulario de registro de productos. La propuesta generada fue revisada, adaptada y probada antes de incorporarla a la rama `mejora-ia`.

### Prompts utilizados y decisiones tomadas

| Prompt que usé                                                                                                                                                                                                                                                                                                       | Qué generó Gemini                                                                                                                                                                                                           | Qué acepté o corregí y por qué                                                                                                                                                                      |
| -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **"Agrega a este formulario de registro de producto una validación para evitar que se agreguen productos cuando el nombre esté vacío, el precio sea inválido o menor o igual a 0, o la cantidad sea inválida o menor o igual a 0. También agrega un botón LIMPIAR que borre todos los campos y oculte el resumen."** | Generó una validación mediante `when`, utilizando `toDoubleOrNull()` y `toIntOrNull()`. También agregó el estado `mensajeError` para mostrar errores y un botón `LIMPIAR` para restablecer los campos y ocultar el resumen. | **Acepté** la propuesta porque cumplía con la mejora solicitada. Después revisé el código y comprobé mediante pruebas que la validación y el botón `LIMPIAR` funcionaran correctamente.             |
| **"Revisa la validación del precio para que también permita ingresar valores decimales usando coma, por ejemplo 5,50, además del punto decimal."**                                                                                                                                                                   | Propuso utilizar `replace(",", ".").toDoubleOrNull()` para convertir la coma decimal en punto antes de realizar la conversión a `Double`.                                                                                   | **Corregí y adapté** el código para aplicar esta conversión en la validación del precio. Esto permite ingresar valores como `5,50`, una forma habitual de representar decimales en español.         |
| **"Verifica que la corrección para aceptar coma decimal también se aplique al cálculo del importe y no solamente a la validación."**                                                                                                                                                                                 | Indicó que la conversión mediante `replace(",", ".")` también debía utilizarse al obtener el precio para calcular el importe.                                                                                               | **Acepté la corrección** y la incorporé también en el cálculo. Finalmente, realicé una prueba ingresando `5,50` como precio y `2` como cantidad, obteniendo correctamente un importe de `S/ 11.00`. |

### Decisiones tomadas

La inteligencia artificial se utilizó como **herramienta de apoyo**, pero las propuestas generadas no fueron incorporadas automáticamente. El código fue revisado, adaptado y probado para verificar que cumpliera con los requisitos del laboratorio y funcionara correctamente.

La mejora final incorpora las siguientes funcionalidades:

* Validación del nombre del producto para evitar campos vacíos.
* Validación del precio para comprobar que sea numérico y mayor que `0`.
* Validación de la cantidad para comprobar que sea numérica y mayor que `0`.
* Mensajes de error cuando los datos ingresados no son válidos.
* Botón `LIMPIAR` para borrar todos los datos ingresados.
* Ocultamiento del resumen cuando los datos son inválidos o se limpia el formulario.
* Compatibilidad con precios ingresados utilizando punto o coma decimal.
* Cálculo correcto del importe mediante precio × cantidad.
* Prueba funcional con un precio de `5,50` y una cantidad de `2`, obteniendo `S/ 11.00`.

### Resultado de la mejora

La incorporación de estas funcionalidades permitió mejorar la experiencia de usuario, evitando registros con datos incorrectos y facilitando el ingreso de precios decimales según el formato utilizado habitualmente en español.
