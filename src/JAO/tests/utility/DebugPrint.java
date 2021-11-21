package JAO.tests.utility;

import java.util.HashMap;
import java.util.function.DoubleBinaryOperator;

public class DebugPrint {
    private static final HashMap<Integer, DebugStatus> idToStatus = new HashMap<>();

    public static synchronized void register(int id, String name) {
        DebugStatus debugStatus = new DebugStatus(id, name);
        idToStatus.put(id, debugStatus);
    }

    public static synchronized void addOperation(int id, DebugEntry entry) {
        if (idToStatus.containsKey(id)){
            idToStatus.get(id).addEntry(entry);
        } else {
            throw new RuntimeException("Thread with id [" + id + "] didn't register itself.");
        }
    }

    public static synchronized void  printDiagnostics() {
        for(var status : idToStatus.values()) {
            status.printStatus();
            System.out.println("-------------------------------------------------------------------------------------------");
        }
    }

    public static synchronized DebugEntry getSumUpResults() {
        DebugEntry results = new DebugEntry();
        results.unitsOfWork = 0;
        for(var status : idToStatus.values()) {
            results.unitsOfWork += status.getSumUpEntry().unitsOfWork;
        }
        return results;
    }

    public static synchronized void clear() {
        idToStatus.clear();
    }

}
