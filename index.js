function mostrarMateria(side) {
    let selector;

    // Seleccionamos el selector correspondiente según el parámetro 'side'
    if (side === 'left') {
        selector = document.getElementById('selector-materia-left');
    } else if (side === 'right') {
        selector = document.getElementById('selector-materia-right');
    }

    // Verificamos si el selector fue encontrado
    if (selector) {
        let materiaSeleccionada = selector.value;
        document.getElementById('resultado').innerHTML = 'Tu materia favorita seleccionada es: ' + materiaSeleccionada;
    } else {
        console.error('El selector no se encontró');
    }
}
