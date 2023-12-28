package utils;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ProcessUtils {

    public static void kill(String searchable) {
        long pid = ProcessHandle.current().pid();
        List<ProcessHandle> processes = ProcessHandle.allProcesses()
                .filter(p -> p.info().command().orElse("not").contains(searchable))
                .toList();
        for(ProcessHandle p : processes) {
            if(pid == p.pid()) continue;
            log.info("There is only room for one of us: " + p.info().command().orElse("wtf"));
            p.destroyForcibly();
        }
    }
}
