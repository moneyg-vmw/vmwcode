import java.net.URL;

import com.vmware.vim25.GuestOsDescriptorFirmwareType;
import com.vmware.vim25.VirtualMachineBootOptions;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineFlagInfo;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.Task;

public class EnableVBS {
    VirtualMachine vm = null;
    ServiceInstance si = null;
    Folder rootFolder = null;

    public void login(String vcIp, String userName, String password){
        si = new ServiceInstance(new URL("https://" + vcIP + "/sdk"), userName, password, true);
        rootFolder = si.getRootFolder();
    }

    public void enableVBSonVM(String vmName) {
        vm = (VirtualMachine) new InventoryNavigator(
                rootFolder).searchManagedEntity("VirtualMachine", vmName);
        VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();
        vmConfigSpec.setFirmware(GuestOsDescriptorFirmwareType.EFI.value());
        vmConfigSpec.setNestedHVEnabled(true);

        VirtualMachineBootOptions bootOptions = new VirtualMachineBootOptions();
        bootOptions.setEfiSecureBootEnabled(true);
        vmConfigSpec.setBootOptions(bootOptions);

        VirtualMachineFlagInfo vmFlagInfo = new VirtualMachineFlagInfo();
        vmFlagInfo.setVvtdEnabled(true);
        vmFlagInfo.setVbsEnabled(true);

        vmConfigSpec.setFlags(vmFlagInfo);
        Task task = vm.reconfigVM_Task(vmConfigSpec);
    }

    public static void main(String args[]){
        String vcIP = "";
        String userName = "";
        String password = "";
        String vmName = "";

       EnableVBS vbs = new EnableVBS();
       vbs.login(vcIP, userName, password);
       vbs.enableVBSonVM(vmName);
    }
}

