package fr.polytech.shipment.business;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import java.util.GregorianCalendar;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import arquillian.AbstractShipmentTest;
import fr.polytech.dronepark.components.DroneLauncher;
import fr.polytech.entities.Delivery;
import fr.polytech.entities.DeliveryStatus;
import fr.polytech.entities.Drone;
import fr.polytech.entities.Parcel;
import fr.polytech.entities.TimeSlot;
import fr.polytech.entities.TimeState;
import fr.polytech.shipment.components.ControlledShipment;

/**
 * DronePark
 */
@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class ShipmentTest extends AbstractShipmentTest {

    @EJB
    private ControlledShipment shipment;

    @PersistenceContext
    private EntityManager entityManager;

    Delivery delivery;
    Drone drone;
    TimeSlot timeslot;
    Parcel parcel;

    @Before
    public void setUpContext() throws Exception {
        DroneLauncher mocked = mock(DroneLauncher.class);
        doNothing().when(mocked).initializeDroneLaunching(drone, new GregorianCalendar(), delivery);
        this.shipment.useDroneLauncherReference(mocked);

        parcel = new Parcel("123456789A", "az", "az", "az");
        entityManager.persist(parcel);
        drone = new Drone("777");
        entityManager.persist(drone);
        this.delivery = new Delivery();
        this.delivery.setDeliveryId("123456789A");
        this.delivery.setParcel(parcel);
        this.delivery.setDrone(drone);
        entityManager.persist(delivery);
        this.drone.setCurrentDelivery(delivery);
        this.timeslot = new TimeSlot();
        this.timeslot.setDate(new GregorianCalendar());
        this.timeslot.setDelivery(delivery);
        this.timeslot.setState(TimeState.DELIVERY);
        entityManager.persist(timeslot);
        delivery.getDrone().add(timeslot);
    }

    @Test
    public void initializeDelivery() throws Exception {
        delivery = entityManager.merge(delivery);
        assertEquals(DeliveryStatus.NOT_DELIVERED, delivery.getStatus());
        shipment.initializeDelivery(delivery);
        assertEquals(DeliveryStatus.ONGOING, delivery.getStatus());
    }

    @Test

    public void dronePersistanceTest() {
        delivery = entityManager.merge(delivery);
        Drone d = delivery.getDrone();
        assertTrue(!d.getTimeSlots().isEmpty());
    }

}
