\## Mejora con IA



Para la mejora del proyecto utilicé IA como apoyo para implementar la validación de datos y un botón para limpiar el formulario. La solución fue revisada y probada antes de incorporarla a la rama `mejora-ia`.



| Prompt que usé | Qué generó Gemini | Qué acepté o corregí (y por qué) |

|---|---|---|

| "Agrega a este formulario de registro de producto una validación para evitar que se agreguen productos cuando el nombre esté vacío, el precio sea inválido o menor o igual a 0, o la cantidad sea inválida o menor o igual a 0. También agrega un botón LIMPIAR que borre todos los campos y oculte el resumen." | Generó una validación mediante `when`, utilizando `toDoubleOrNull()` y `toIntOrNull()`. También agregó un estado `mensajeError` y un botón `LIMPIAR` para limpiar los campos y ocultar la tarjeta de resumen. | Acepté esta propuesta porque cumplía con la mejora solicitada. La probé y comprobé que mostraba mensajes de error y que el botón LIMPIAR funcionaba correctamente. |

| "Revisa la validación del precio para que también permita ingresar valores decimales usando coma, por ejemplo 5,50, además del punto decimal." | Propuso utilizar `replace(",", ".").toDoubleOrNull()` para convertir la coma decimal en punto antes de realizar la conversión. | Acepté y corregí el código aplicando esta solución tanto en la validación como en el cálculo del importe, porque en español es habitual utilizar la coma como separador decimal. |

| "Verifica que la corrección para aceptar coma decimal también se aplique al cálculo del importe y no solamente a la validación." | Indicó que la conversión con `replace(",", ".")` también debía utilizarse al calcular el precio dentro del resumen. | Acepté esta corrección y la probé ingresando un precio de `5,50` y cantidad `2`. El resultado fue `Importe: S/ 11.00`, confirmando que el cálculo funcionaba correctamente. |



\### Decisiones tomadas



La IA se utilizó como herramienta de apoyo, pero el código fue revisado y probado antes de aceptarlo.



La mejora final permite:



\- Validar que el nombre del producto no esté vacío.

\- Validar que el precio sea numérico y mayor que 0.

\- Validar que la cantidad sea numérica y mayor que 0.

\- Mostrar un mensaje de error cuando los datos sean incorrectos.

\- Limpiar todos los campos mediante el botón `LIMPIAR`.

\- Aceptar precios con punto o coma decimal.

\- Calcular correctamente el importe del producto.

\- Mantener el resumen oculto cuando los datos no son válidos.

