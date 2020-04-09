package fr.polytech.shipment.business;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.apache.openejb.api.LocalClient;
import org.junit.Before;
import org.junit.Test;

import arquillian.AbstractShipmentTest;
import fr.polytech.dronepark.components.DroneLauncher;
import fr.polytech.dronepark.exception.ExternalDroneApiException;
import fr.polytech.entities.Delivery;
import fr.polytech.entities.DeliveryStatus;
import fr.polytech.entities.Drone;
import fr.polytech.entities.TimeSlot;
import fr.polytech.shipment.components.ShipmentBean;

/**
 * DronePark
 */
@LocalClient
public class ShipmentTest extends AbstractShipmentTest {

    private ShipmentBean shipment;

    @Before
    public void setUpContext() throws ExternalDroneApiException {
        DroneLauncher mocked = mock(DroneLauncher.class);
        shipment = new ShipmentBean(mocked);
        try {
            when(mocked.initializeDroneLaunching(new Drone(), new GregorianCalendar(),new Delivery())).thenReturn(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void initializeDelivery() throws ExternalDroneApiException {
        Delivery delivery = new Delivery();
        delivery.setDrone(new Drone());
        Set<TimeSlot> timeSlots = new HashSet<>();
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setDate(new GregorianCalendar());
        timeSlot.setDelivery(delivery);
        timeSlots.add(timeSlot);
        delivery.getDrone().setTimeSlots(timeSlots);
        assertEquals(DeliveryStatus.NOT_DELIVERED, delivery.getStatus());
        shipment.initializeDelivery(delivery);
        assertEquals(DeliveryStatus.ONGOING, delivery.getStatus());
    }

}
