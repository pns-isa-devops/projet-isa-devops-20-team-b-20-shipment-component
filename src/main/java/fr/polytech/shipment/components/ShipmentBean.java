package fr.polytech.shipment.components;

import fr.polytech.dronepark.components.DroneLauncher;
import fr.polytech.dronepark.exception.ExternalDroneApiException;
import fr.polytech.entities.Delivery;
import fr.polytech.entities.DeliveryStatus;
import fr.polytech.entities.TimeSlot;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ShipmentBean implements DeliveryInitializer {

    @EJB
    private DroneLauncher droneLauncher;

    public ShipmentBean(DroneLauncher droneLauncher) {
        this.droneLauncher = droneLauncher;
    }

    public ShipmentBean() {
    }

    /**
     * Initializes drone launching by sending the launch signal to the drone at the
     * right time and by updating drone and delivery status.
     *
     * @param delivery
     * @return
     * @throws ExternalDroneApiException
     */
    @Override
    public boolean initializeDelivery(Delivery delivery) throws ExternalDroneApiException {
        delivery.setStatus(DeliveryStatus.ONGOING);
        TimeSlot timeSlot = delivery.getDrone().getTimeSlot(delivery);
        boolean result = droneLauncher.initializeDroneLaunching(delivery.getDrone(), timeSlot.getDate(), delivery);
        return result;
    }

}
