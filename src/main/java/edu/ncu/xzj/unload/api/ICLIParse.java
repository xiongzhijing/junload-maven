package edu.ncu.xzj.unload.api;

import java.util.Map;

public interface ICLIParse {
    Map<String, String> getOpts();

    Map<String, String> parseCLI(String[] args);

}
