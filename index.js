function mostrarMateria(){
    let selector = document.getElementById('selector-materia');
    let materiaSeleccionada = selector.value;
    document.getElementById('resultado').innerHTML = 'Tu materia Favorita es: ' + materiaSeleccionada;

}