package co.edu.uniquindio.storify.controllers.controladorFlujo;

import co.edu.uniquindio.storify.estructurasDeDatos.pila.Pila;

public class AdministradorComandos {
    private Pila<Comando> pilaDeshacer = new Pila<>();
    private Pila<Comando> pilaRehacer = new Pila<>();

    public void ejecutarComando(Comando comando) {
        comando.ejecutar();
        pilaDeshacer.add(comando);
        pilaRehacer.clear();
    }

    public void deshacer() {
        if (!pilaDeshacer.isEmpty()) {
            Comando comando = pilaDeshacer.desapilar();
            comando.deshacer();
            pilaRehacer.add(comando);
        }
    }

    public void rehacer() {
        if (!pilaRehacer.isEmpty()) {
            Comando comando = pilaRehacer.desapilar();
            comando.rehacer();
            pilaDeshacer.add(comando);
        }
    }
}