package machine;

import java.util.List;

public class MachineService {
	public List getList() {
		MachineDAO machinedao = new MachineDAO();
		List list = machinedao.selectAll();
		return list;
	}

	public int getaddmachine(MachineDTO machineDTO) {
		MachineDAO machineDAO = new MachineDAO();
		int a = machineDAO.insertmachine(machineDTO);
		return a;
	}
	
	public int getupdatemachine(MachineDTO machineDTO) {
		MachineDAO machineDAO = new MachineDAO();
		int a = machineDAO.updatemachine(machineDTO);
		return a;
	}
	
	public int getdeletemachine(MachineDTO machineDTO) {
		MachineDAO machineDAO = new MachineDAO();
		int a = machineDAO.deletemachine(machineDTO);
		return a;
	}
	
	
}