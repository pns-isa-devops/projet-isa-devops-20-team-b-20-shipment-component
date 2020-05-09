package fr.polytech.shipment.components;

import javax.ejb.Local;

import fr.polytech.dronepark.exception.DroneNotAvailableException;
import fr.polytech.dronepark.exception.ExternalDroneApiException;
import fr.polytech.entities.Delivery;
import fr.polytech.shipment.exception.NoDroneAttachOnDeliveryException;
import fr.polytech.shipment.exception.NoTimeSlotAttachOnDeliveryException;

@Local
public interface DeliveryInitializer {

    /**
     * Initializes drone launching by sending the launch signal to the drone at the
     * right time and by updating drone and delivery status.
     *
     * @param delivery
     * @return
     * @throws ExternalDroneApiException
     * @throws NoDroneAttachOnDeliveryException
     * @throws NoTimeSlotAttachOnDeliveryException
     * @throws DroneNotAvailableException
     */
    Delivery initializeDelivery(Delivery delivery) throws ExternalDroneApiException, NoDroneAttachOnDeliveryException,
            NoTimeSlotAttachOnDeliveryException, DroneNotAvailableException;
}
