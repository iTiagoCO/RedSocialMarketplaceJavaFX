package co.uniquindio.singleton;

import co.uniquindio.modelo.Red;

public class ModelFactoryController {

	Red redSocial;

	// ------------------------------ Singleton
	// ------------------------------------------------
	// Clase estatica oculta. Tan solo se instanciara el singleton una vez
	private static class SingletonHolder {
		// El constructor de Singleton puede ser llamado desde aquí al ser
		// protected
		private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
	}

	public Red getRedSocial() {
		return redSocial;
	}

	// Método para obtener la instancia de nuestra clase
	public static ModelFactoryController getInstance() {
		return SingletonHolder.eINSTANCE;
	}

	public ModelFactoryController() {
		redSocial = new Red();
	}

}
