package kenhlaptrinh.net.pingicmp.model;

/**
 * Created by ninhvanluyen on 9/19/17.
 */

public class ItemPing {
    private String result;
    private String time;
    private String ip;
    private String seq;

    public ItemPing(String result, String time, String ip, String seq) {
        this.result = result;
        this.time = time;
        this.ip = ip;
        this.seq = seq;
    }

    public ItemPing() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "ItemPing{" +
                "result='" + result + '\'' +
                ", time='" + time + '\'' +
                ", ip='" + ip + '\'' +
                ", seq='" + seq + '\'' +
                '}';
    }
}
