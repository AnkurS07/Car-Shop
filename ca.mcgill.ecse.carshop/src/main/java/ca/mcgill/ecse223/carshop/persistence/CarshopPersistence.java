package ca.mcgill.ecse223.carshop.persistence;

import ca.mcgill.ecse.carshop.model.CarShop;

public class CarshopPersistence {
	private static String filename = "data.carshop";
	
	public static void save(CarShop carshop) {
		PersistenceObjectStream.serialize(carshop);
	}
	
	public static CarShop load() {
		PersistenceObjectStream.setFilename(filename);
		CarShop carshop = (CarShop) PersistenceObjectStream.deserialize();
		// If model cannot be loaded, create an empty carShop
		if (carshop == null) {
			carshop = new CarShop();
		}
		return carshop;
	}
	
}
