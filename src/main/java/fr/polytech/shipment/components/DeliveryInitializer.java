package fr.polytech.shipment.components;

import javax.ejb.Local;

import fr.polytech.dronepark.exception.ExternalDroneApiException;
import fr.polytech.entities.Delivery;

@Local
public interface DeliveryInitializer {

    /**
     * Initializes drone launching by sending the launch signal to the drone at the
     * right time and by updating drone and delivery status.
     *
     * @param delivery
     * @return
     * @throws ExternalDroneApiException
     */
    boolean initializeDelivery(Delivery delivery) throws ExternalDroneApiException;
}
