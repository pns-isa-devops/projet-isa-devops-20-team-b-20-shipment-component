package fr.polytech.shipment.integration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import arquillian.AbstractShipmentTest;
import fr.polytech.dronepark.exception.DroneNotAvailableException;
import fr.polytech.dronepark.exception.ExternalDroneApiException;
import fr.polytech.entities.Delivery;
import fr.polytech.entities.DeliveryStatus;
import fr.polytech.entities.Drone;
import fr.polytech.entities.DroneStatus;
import fr.polytech.entities.TimeSlot;
import fr.polytech.entities.TimeState;
import fr.polytech.shipment.components.DeliveryInitializer;
import fr.polytech.shipment.exception.NoDroneAttachOnDeliveryException;
import fr.polytech.shipment.exception.NoTimeSlotAttachOnDeliveryException;

/**
 * LaunchDroneIntegrationTest
 */
@RunWith(Arquillian.class)
public class LaunchDroneIntegrationTest extends AbstractShipmentTest {

    @EJB
    private DeliveryInitializer deliveryInitializer;

    private Drone drone;
    private Delivery delivery;

    @Before
    public void setup() {
        drone = new Drone();
        drone.setDroneId("1");
        drone.setDroneStatus(DroneStatus.AVAILABLE);
        delivery = new Delivery();
        delivery.setDeliveryId("d1");
        delivery.setDrone(drone);
        delivery.setStatus(DeliveryStatus.NOT_DELIVERED);
        TimeSlot ts = new TimeSlot();
        ts.setDelivery(delivery);
        ts.setState(TimeState.DELIVERY);
        ts.setDate(new GregorianCalendar());
        List<TimeSlot> timeslots = new ArrayList<>();
        timeslots.add(ts);
        drone.setTimeSlots(timeslots);
    }

    @Test
    public void launchDroneOneTimeTest() throws ExternalDroneApiException, NoDroneAttachOnDeliveryException,
            NoTimeSlotAttachOnDeliveryException, DroneNotAvailableException {
        // launch the drone (go on delivery)
        deliveryInitializer.initializeDelivery(delivery);
    }

    @Test(expected = DroneNotAvailableException.class)
    public void launchDroneTwoTimeTest() throws ExternalDroneApiException, NoDroneAttachOnDeliveryException,
            NoTimeSlotAttachOnDeliveryException, DroneNotAvailableException {
        // launch the drone (go on delivery)
        deliveryInitializer.initializeDelivery(delivery);
        // cannot launch the drone again, already on delivery
        deliveryInitializer.initializeDelivery(delivery);
    }
}
