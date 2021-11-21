package JAO.tests.utility;

import java.util.ArrayList;
import java.util.List;

public class DebugStatus {
    private final int id;
    private final String name;
    private final List<DebugEntry> entries = new ArrayList<>();

    public void addEntry(DebugEntry entry) {
        entries.add(entry);
    }

    public DebugStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private static String getShortenInt(Integer n) {
        String[] postFixes = {"", "k", "M", "B"};
        int postFixIdx = 0;
        while (postFixIdx < postFixes.length - 1 && n > 1000) {
            n /= 1000;
            postFixIdx++;
        }
        final var postFix = postFixes[postFixIdx];
        return n.toString() + postFix;
    }

    public DebugEntry getSumUpEntry() {
        DebugEntry result = new DebugEntry();
        result.unitsOfWork = 0;
        for(var entry : entries) {
            result.unitsOfWork += entry.unitsOfWork;
        }
        return result;
    }

    void printStatus() {
        var status = this.getSumUpEntry();
        System.out.println(this.name + " | id [" + id + "] | actions [" + getShortenInt(entries.size()) +"] | units of work done [" + getShortenInt(status.unitsOfWork) + "]");
    }
}
