package fr.polytech.shipment.components;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fr.polytech.dronepark.components.DroneLauncher;
import fr.polytech.dronepark.exception.ExternalDroneApiException;
import fr.polytech.entities.Delivery;
import fr.polytech.entities.DeliveryStatus;
import fr.polytech.entities.TimeSlot;

@Stateless
public class ShipmentBean implements DeliveryInitializer {

    @EJB
    private DroneLauncher droneLauncher;

    public ShipmentBean() {

    }

    public ShipmentBean(DroneLauncher droneLauncher) {
        this.droneLauncher = droneLauncher;
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
        boolean result = false;
        try {
             result =  droneLauncher.initializeDroneLaunching(delivery.getDrone(), timeSlot.getDate(),delivery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
