package fr.polytech.shipment.components;

import javax.ejb.Local;

import fr.polytech.dronepark.components.DroneLauncher;

@Local
public interface ControlledShipment extends DeliveryInitializer {

    void useDroneLauncherReference(DroneLauncher droneLauncher);
}
