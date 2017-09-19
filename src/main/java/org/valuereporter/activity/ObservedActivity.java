package org.valuereporter.activity;

import org.valuereporter.Observation;
import org.valuereporter.util.JsonMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * This class will represent an activity. This activity will have some main characteristics:
 * - activityName: Unique identifier for a type of activity
 * - startTime: When the activity occured
 * - contextInfo: map of parameters, aka payload
 *
 * These methods will be forwarded to the Valuereporter analyzer, as an activity that has occured.
 *
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public class ObservedActivity implements Observation {
    //The activityName is the identification of a method. Typically the activityName is the full activityName, including class, and package.
    private String activityName;
    private long startTime;
    private Map<String,Object> contextInfo = new HashMap<>();

    private ObservedActivity() {

    }
    public ObservedActivity(String activityName, long startTime) {
        this.activityName = activityName;
        this.startTime = startTime;
    }

    public String getActivityName() {
        return activityName;
    }

    @Override
    public String getName() {
        return getActivityName();
    }

    public long getStartTime() {
        return startTime;
    }


    public Map<String, Object> getContextInfo() {
        return contextInfo;
    }

    /**
     * Add extra info describing the context this activity was used in.
     * @param key name of info element.
     * @param info value of info element.
     */
    public void addContextInfo(String key, Object info){
       contextInfo.put(key,info);
    }

    /**
     * @Deprecated Please use addContextInfo(key, info)
     * @param key name of info element.
     * @param info name of info element.
     */
    public void put(String key, Object info) {
        addContextInfo(key,info);
    }

    @Override
    public String toString() {
        return "ObservedActivity{" +
                "activityName='" + activityName + '\'' +
                ", startTime=" + startTime +
                ", contextInfo=" + contextInfo +
                '}';
    }

    @Override
    public String toJson() {
        String json = "{\"activityName\": \"" + activityName + "\"," +
                "\"startTime\": " + startTime + "," +
                "\"contextInfo\": " + JsonMapper.toJson(contextInfo) + "}";
        return json;
    }

    public static class Builder {
        private ObservedActivity observedActivity = new ObservedActivity();

        private Builder() {
            observedActivity.startTime = System.currentTimeMillis();
        }
        public static Builder observe(String activityName) {
            Builder builder = new Builder();
            builder.setActivityName(activityName);
            return builder;
        }
        public Builder setActivityName(String activityName) {
            observedActivity.activityName = activityName;
            return this;
        }
        public Builder addContext(String key, String info) {
            observedActivity.addContextInfo(key, info);
            return this;
        }

        public ObservedActivity build() {
            assert observedActivity.activityName != null;
            return observedActivity;
        }

        public Builder context(String key, String info) {
            return addContext(key, info);
        }
    }


}
