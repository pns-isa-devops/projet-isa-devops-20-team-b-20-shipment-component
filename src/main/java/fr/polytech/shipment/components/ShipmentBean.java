package fr.polytech.shipment.components;

import fr.polytech.dronepark.components.DroneLauncher;
import fr.polytech.dronepark.exception.DroneNotAvailableException;
import fr.polytech.dronepark.exception.ExternalDroneApiException;
import fr.polytech.entities.Delivery;
import fr.polytech.entities.DeliveryStatus;
import fr.polytech.entities.Drone;
import fr.polytech.entities.TimeSlot;
import fr.polytech.shipment.exception.NoDroneAttachOnDeliveryException;
import fr.polytech.shipment.exception.NoTimeSlotAttachOnDeliveryException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ShipmentBean implements DeliveryInitializer, ControlledShipment {

    @EJB
    private DroneLauncher droneLauncher;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void useDroneLauncherReference(DroneLauncher droneLauncher) {
        this.droneLauncher = droneLauncher;
    }

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
    @Override
    public Delivery initializeDelivery(Delivery delivery) throws ExternalDroneApiException,
            NoDroneAttachOnDeliveryException, NoTimeSlotAttachOnDeliveryException, DroneNotAvailableException {
        Drone drone = delivery.getDrone();

        drone = entityManager.merge(drone);
        if (drone == null) {
            throw new NoDroneAttachOnDeliveryException(delivery.getDeliveryId());
        }

        TimeSlot timeSlot = drone.getTimeSlot(delivery);

        if (timeSlot == null) {
            throw new NoTimeSlotAttachOnDeliveryException(drone.getDroneId());
        }

        droneLauncher.initializeDroneLaunching(drone, timeSlot.getDate(), delivery);
        delivery.setStatus(DeliveryStatus.ONGOING);

        entityManager.flush();
        entityManager.clear();
        return entityManager.find(Delivery.class, delivery.getId());
    }

}
