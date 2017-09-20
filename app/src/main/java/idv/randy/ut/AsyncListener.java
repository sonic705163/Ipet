package idv.randy.ut;


import java.util.List;

public interface AsyncListener {
    void onError();
    void onGoing(int progress);
    void onFinish(String result);
}
