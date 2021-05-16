package io.github.benzwreck.wykop4j;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class WykopRequest {
    private final String url;
    private final Map<String, String> apiParams;
    private final Map<String, String> namedParams;
    private final Map<String, String> postParams;
    private final File file;
    private final String shownFileName;
    private final boolean fullData;
    private final boolean clearOutput;

    private WykopRequest(Map<String, String> apiParams, Map<String, String> namedParams, Map<String, String> postParams, String url, File file, String shownFileName, boolean fullData, boolean clearOutput) {
        this.apiParams = apiParams;
        this.namedParams = namedParams;
        this.postParams = postParams;
        this.url = url;
        this.file = file;
        this.shownFileName = shownFileName;
        this.fullData = fullData;
        this.clearOutput = clearOutput;
    }

    String url() {
        return url;
    }

    Map<String, String> apiParams() {
        return apiParams;
    }

    Map<String, String> namedParams() {
        return namedParams;
    }

    Map<String, String> postParams() {
        return postParams;
    }

    File file() {
        return file;
    }

    String shownFileName() {
        return shownFileName;
    }

    boolean fullData() {
        return fullData;
    }

    boolean clearOutput() {
        return clearOutput;
    }

    public static final class Builder {
        private Map<String, String> apiParams = new HashMap<>();
        private Map<String, String> namedParams = new HashMap<>();
        private Map<String, String> postParams = new TreeMap<>();
        private String url;
        private File file;
        private String shownFileName;
        private boolean fullData = true;
        private boolean clearOutput = true;

        public Builder() {
        }

        public Builder apiParam(String name, String value) {
            this.apiParams.put(name, value);
            return this;
        }

        public Builder namedParam(String name, String value) {
            this.namedParams.put(name, value);
            return this;
        }

        public Builder postParam(String name, String value) {
            this.postParams.put(name, value);
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder file(File file) {
            this.file = file;
            this.shownFileName = file.getName();
            return this;
        }

        public Builder file(File file, String shownFileName) {
            this.file = file;
            this.shownFileName = shownFileName;
            return this;
        }

        public Builder fullData(boolean option) {
            this.fullData = option;
            return this;
        }

        public Builder clearOutput(boolean option) {
            this.clearOutput = option;
            return this;
        }

        public WykopRequest build() {
            return new WykopRequest(apiParams, namedParams, postParams, url, file, shownFileName, fullData, clearOutput);
        }
    }
}
