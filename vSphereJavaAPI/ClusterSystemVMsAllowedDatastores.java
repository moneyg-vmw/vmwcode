/*
*@author Money Garg (moneyg@vmware.com)
*Reconfigure cluster to add/remove Allowed datastores for Cluster System VMs i.e. vSphere Cluster Services (vCLS)  VMs
*/

import com.vmware.vim25.*;
import com.vmware.vim25.mo.*;

public class ClusterSystemVMsAllowedDatastores {

    public void removeDatastoreFromClusterSystemVMsAllowedList(String clusterName, ManagedObjectReference selectedDatastore) {
        ClusterComputeResource ccr = (ClusterComputeResource) new InventoryNavigator(
                rootFolder).searchManagedEntity("ClusterComputeResource", clusterName);

        ClusterConfigSpecEx clusterSpecEx = new ClusterConfigSpecEx();
        clusterSpecEx.setDrsConfig(new ClusterDrsConfigInfo());
        clusterSpecEx.setDpmConfig(new ClusterDpmConfigInfo());

        ClusterSystemVMsConfigSpec clusterSystemVmConfigSpec = new ClusterSystemVMsConfigSpec();
        ClusterDatastoreUpdateSpec dsUpdateSpec = new ClusterDatastoreUpdateSpec();
        dsUpdateSpec.setDatastore(selectedDatastore);

        clusterSystemVmConfigSpec.getNotAllowedDatastores().add(dsUpdateSpec);
        clusterSpecEx.setSystemVMsConfig(clusterSystemVmConfigSpec);

        Task reconfigClusterTask = ccr.reconfigureComputeResource_Task(newSpec, true);
    }

    public void addDatastoreToClusterSystemVMsAllowedList(String clusterName, ManagedObjectReference selectedDatastore) {
        ClusterComputeResource ccr = (ClusterComputeResource) new InventoryNavigator(
                rootFolder).searchManagedEntity("ClusterComputeResource", clusterName);

        ClusterConfigSpecEx clusterSpecEx = new ClusterConfigSpecEx();
        clusterSpecEx.setDrsConfig(new ClusterDrsConfigInfo());
        clusterSpecEx.setDpmConfig(new ClusterDpmConfigInfo());

        ClusterSystemVMsConfigSpec clusterSystemVmConfigSpec = new ClusterSystemVMsConfigSpec();
        ClusterDatastoreUpdateSpec dsUpdateSpec = new ClusterDatastoreUpdateSpec();
        dsUpdateSpec.setDatastore(selectedDatastore);

        clusterSystemVmConfigSpec.getAllowedDatastores().add(dsUpdateSpec);
        clusterSpecEx.setSystemVMsConfig(clusterSystemVmConfigSpec);

        Task reconfigClusterTask = ccr.reconfigureComputeResource_Task(clusterSpecEx, true);
    }
}
