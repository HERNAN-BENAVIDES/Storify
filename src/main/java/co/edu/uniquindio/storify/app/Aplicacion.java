package co.edu.uniquindio.storify.app;

public class Aplicacion extends Application {

    private Stage stage;
    private AnchorPane rootLayout;

    public VentanaInicioController ventanaInicioController;
    public VentanaRegistroIngresoController ventanaRegistroIngresoController;

    private ModelFactoryController mfm = ModelFactoryController.getInstance();

    public void start(Stage stage) throws Exception {

        Cliente cliente=null;
        Administrador admin=null;

        this.stage=stage;
        this.stage.setTitle("Sporify");
        //this.stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/logoFinalVentana.png")));
        this.stage.setResizable(false);
        mostrarVentanaRegistroIngreso();
        //mostrarVentanaPrincipal(cliente, admin);

    }

    /**
     * Inicia la Ventana de Registro e Ingreso
     */
    public void mostrarVentanaRegistroIngreso() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Aplicacion.class.getResource("/ventanas/VentanaRegistroIngreso.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.setTitle("Storify | Inicio de Sesion");
            stage.setResizable(false);
            stage.show();
            VentanaRegistroIngresoController controlador = loader.getController();
            controlador.setVentana(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarVentanaPrincipal(Usuario usuario) throws AtributoVacioException, UsuarioNoExistenteException {
        try{
            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource("/ventanas/VentanaInicio.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            VentanaInicioController controlador = loader.getController();
            ventanaInicioController=loader.getController();
            controlador.setUsuario(usuario);
            String tipoUsuario= mfm.getTiendaMusica().obtenerTipoUsuario(usuario.getUsername(), usuario.getPassword());

            switch (tipoUsuario) {
                case "Administrador":
                    /**
                     * controlador.mostrarPanelIzquierdoAdmin();
                     *                  controlador.mostrarPanelDerechoGestionarCanciones();
                     *                  controlador.mostrarBarraSuperiorAdmin(); break;
                     */

                case "Cliente":
                    controlador.mostrarPanelIzquierdoCliente();
                    //controlador.mostrarPanelDerechoCanciones();
                    //controlador.mostrarBarraSuperiorCliente(); break;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarVentanaMisCanciones(Usuario usuario){
        ventanaInicioController.mostrarPanelDerechosMisFavoritos(usuario);
    }

}
