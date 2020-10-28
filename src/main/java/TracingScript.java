import static org.openjdk.btrace.core.BTraceUtils.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.openjdk.btrace.core.annotations.*;

@BTrace
public class TracingScript
{
    @Property
    private static Map<String, AtomicLong> countCallsOfClass = newHashMap();

    @Property
    private static Map<String, AtomicLong> durationExecutionMethodInNs = newHashMap();

    @OnExit // on exit
    public static void onexit(int code)
    {
        printNumberMap("count", countCallsOfClass);
        printNumberMap("duration", durationExecutionMethodInNs);
    }

    @OnTimer(500) // on 500 ms
    public static void print()
    {
        printNumberMap("count", countCallsOfClass);
        printNumberMap("duration", durationExecutionMethodInNs);
    }

    @OnMethod(clazz = "/example.+/", method = "/.*/", location = @Location(Kind.RETURN))
    public static void onHCriteria(@ProbeClassName String probeClass, @ProbeMethodName(fqn = false) String probeMethod,
            @Duration long duration)
    {
        fillMaps(probeClass, probeMethod, duration);
    }

    /**
     * Fill result maps
     *
     * @param probeClass name of class
     * @param probeMethod name of method
     * @param duration duration of method in ns
     */
    public static void fillMaps(String probeClass, String probeMethod, long duration)
    {
        String fullMethod = probeClass + "#" + probeMethod + "()";

        AtomicLong countOfCall = get(countCallsOfClass, fullMethod);
        if (countOfCall == null)
        {
            countOfCall = newAtomicLong(1);
            put(countCallsOfClass, fullMethod, countOfCall);
        }
        else
        {
            incrementAndGet(countOfCall);
        }

        AtomicLong durationExecutionInNs = get(durationExecutionMethodInNs, fullMethod);
        if (durationExecutionInNs == null)
        {
            durationExecutionInNs = newAtomicLong(0);
        }
        put(durationExecutionMethodInNs, fullMethod, newAtomicLong(get(durationExecutionInNs) + duration));
    }
}