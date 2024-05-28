package co.edu.uniquindio.storify.services;

import co.edu.uniquindio.storify.model.TiendaMusica;
import co.edu.uniquindio.storify.util.Persistencia;

public class PersistenciaThread implements Runnable {

     private TiendaMusica tienda;

     public PersistenciaThread(TiendaMusica tienda) {
          this.tienda = tienda;
     }

     @Override
     public void run() {
          Persistencia.guardarRecursoBancoBinario(tienda);
     }
}
