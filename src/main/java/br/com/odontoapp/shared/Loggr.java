package br.com.odontoapp.shared;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Loggr {

    public Loggr(String className){
        this.className = className;
        log = LogManager.getLogger(className);
    }

    private final String className;
    private final Logger log;
    private ProcessState state;
    private String method;
    private final Map<String, Object> params = new HashMap<>();

    public void info(){
        log.info("{} {} {}",
                (Objects.isNull(this.state) ? "" : "state: ".concat(this.state.name())),
                (Objects.isNull(this.method) ? "" : "- M: ".concat(this.method)),
                (this.params.isEmpty() ? "" : "- params: ".concat(getParams())));
    }

    private String getParams(){
        var list = new StringBuilder();
        params.forEach((key, value) -> list.append(key.concat(": ".concat(value.toString()).concat(", "))));
        list.replace(list.lastIndexOf(","), list.length(), "");
        return list.toString();
    }

    public Loggr m(String method){
        this.method = method;
        return this;
    }

    public Loggr param(String param, Object value){
        this.params.put(param, value);
        return this;
    }

    public Loggr state(ProcessState state){
        this.state = state;
        return this;
    }

    public Loggr event(){
        return new Loggr(this.className);
    }
}
