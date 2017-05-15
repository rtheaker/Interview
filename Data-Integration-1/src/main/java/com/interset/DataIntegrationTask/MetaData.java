package com.interset.DataIntegrationTask;

/**
 * Created by Richard on 12/05/2017.
 */
public class MetaData {

    private long eventId;
    private String user;
    private String ipAddr;
    private String file;
    private String activity;
    private String timestamp;
    private String timeOffset;


    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimeOffset() {
        return timeOffset;
    }

    public void setTimeOffset(String timeOffset) {
        this.timeOffset = timeOffset;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetaData metaData = (MetaData) o;

        if (eventId != metaData.eventId) return false;
        if (user != null ? !user.equals(metaData.user) : metaData.user != null) return false;
        if (ipAddr != null ? !ipAddr.equals(metaData.ipAddr) : metaData.ipAddr != null) return false;
        if (file != null ? !file.equals(metaData.file) : metaData.file != null) return false;
        if (activity != null ? !activity.equals(metaData.activity) : metaData.activity != null) return false;
        if (timestamp != null ? !timestamp.equals(metaData.timestamp) : metaData.timestamp != null) return false;
        return timeOffset != null ? timeOffset.equals(metaData.timeOffset) : metaData.timeOffset == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (eventId ^ (eventId >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (ipAddr != null ? ipAddr.hashCode() : 0);
        result = 31 * result + (file != null ? file.hashCode() : 0);
        result = 31 * result + (activity != null ? activity.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (timeOffset != null ? timeOffset.hashCode() : 0);
        return result;
    }
}
